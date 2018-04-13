package nl.dias.service;

import com.google.common.util.concurrent.RateLimiter;
import nl.dias.domein.EmailCheck;
import nl.dias.domein.Relatie;
import nl.dias.repository.EmailCheckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class EmailCheckService {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailCheckService.class);

    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private EmailCheckRepository emailCheckRepository;
    @Inject
    private SlackService slackService;

    public void checkEmailAdressen(String channelName, RateLimiter rateLimiter) {
        List<EmailCheck> checks = emailCheckRepository.alles();
        List<Relatie> relaties = gebruikerService.alleRelaties();
        List<Relatie> verdwenenAdressen = newArrayList();
        List<Relatie> gemuteerdeAdressen = newArrayList();

        checks.stream().forEach(new Consumer<EmailCheck>() {
            @Override
            public void accept(EmailCheck emailCheck) {
                LOGGER.debug("Evalueren {}", emailCheck);

                Relatie relatie = relaties.stream().filter(new Predicate<Relatie>() {
                    @Override
                    public boolean test(Relatie relatie) {
                        return relatie.getId() == emailCheck.getGebruiker();
                    }
                }).findFirst().get();

                if (relatie != null) {
                    LOGGER.debug("Relatie gevonden, e-mailadres is {}", relatie.getEmailadres());
                    if (relatie.getEmailadres() == null || "".equals(relatie.getEmailadres())) {
                        LOGGER.debug("Verdwenen e-mailadres");
                        verdwenenAdressen.add(relatie);
                        emailCheckRepository.verwijder(emailCheck);
                    } else if (!emailCheck.getMailadres().equals(relatie.getEmailadres())) {
                        LOGGER.debug("Gemuteerd e-mailadres");
                        gemuteerdeAdressen.add(relatie);
                        emailCheck.setMailadres(relatie.getEmailadres());
                        emailCheckRepository.opslaan(emailCheck);
                    }
                } else {
                    LOGGER.debug("Relatie is verwijderd");
                    emailCheckRepository.verwijder(emailCheck);
                }
            }
        });

        LOGGER.debug("{} bestaande checks", checks.size());
        //checken op nieuwe adressen
        LOGGER.debug("{} Relaties bekijken", relaties.size());
        relaties.stream().filter(new Predicate<Relatie>() {
            @Override
            public boolean test(Relatie relatie) {
                return relatie.getEmailadres() != null && !"".equals(relatie.getEmailadres());
            }
        }).forEach(new Consumer<Relatie>() {
            @Override
            public void accept(Relatie relatie) {
                if (checks.stream().noneMatch(new Predicate<EmailCheck>() {
                    @Override
                    public boolean test(EmailCheck emailCheck) {
                        return emailCheck.getGebruiker() == relatie.getId();
                    }
                })) {
                    emailCheckRepository.opslaan(new EmailCheck(relatie.getId(), relatie.getEmailadres()));
                    slackService.stuurBericht(relatie.getEmailadres(), relatie.getId(), SlackService.Soort.NIEUW, channelName, rateLimiter);
                }
            }
        });

        LOGGER.debug("{} verdwenen E-mailadressen", verdwenenAdressen.size());
        verdwenenAdressen.stream().forEach(new Consumer<Relatie>() {
            @Override
            public void accept(Relatie relatie) {
                EmailCheck emailCheck = checks.stream().filter(new Predicate<EmailCheck>() {
                    @Override
                    public boolean test(EmailCheck emailCheck) {
                        return emailCheck.getGebruiker() == relatie.getId();
                    }
                }).findFirst().get();

                slackService.stuurBericht(emailCheck.getMailadres(), relatie.getId(), SlackService.Soort.VERWIJDERD, channelName, rateLimiter);
            }
        });

        LOGGER.debug("{} gemuteerde E-mailadressen", gemuteerdeAdressen.size());
        gemuteerdeAdressen.stream().forEach(new Consumer<Relatie>() {
            @Override
            public void accept(Relatie relatie) {
                slackService.stuurBericht(relatie.getEmailadres(), relatie.getId(), SlackService.Soort.GEWIJZIGD, channelName, rateLimiter);
            }
        });
    }
}
