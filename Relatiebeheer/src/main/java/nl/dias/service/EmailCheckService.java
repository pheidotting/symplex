package nl.dias.service;

import com.google.common.util.concurrent.RateLimiter;
import com.ullink.slack.simpleslackapi.SlackSession;
import nl.dias.domein.EmailCheck;
import nl.dias.domein.Relatie;
import nl.dias.repository.EmailCheckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class EmailCheckService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailCheckService.class);

    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private EmailCheckRepository emailCheckRepository;
    @Inject
    private SlackService slackService;

    public void checkEmailAdressen(SlackSession session, String channelName, RateLimiter rateLimiter) {
        List<EmailCheck> checks = emailCheckRepository.alles();
        List<Relatie> relaties = gebruikerService.alleRelaties();
        List<Relatie> verdwenenAdressen = newArrayList();
        List<Relatie> gemuteerdeAdressen = newArrayList();

        checks.stream().forEach(emailCheck -> {
            LOGGER.debug("Evalueren {}", emailCheck);

            Relatie relatie = null;
            for (Relatie r : relaties) {
                if (r.getId().equals(emailCheck.getGebruiker())) {
                    relatie = r;
                }
            }
            //            Optional<Relatie> optionalRelatie = relaties.stream().filter(relatie -> relatie.getId() == emailCheck.getGebruiker()).();

            if (relatie != null) {
                //            if (optionalRelatie.isPresent()) {
                //                Relatie relatie = optionalRelatie.get();

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
        });

        LOGGER.debug("{} bestaande checks", checks.size());
        //checken op nieuwe adressen
        LOGGER.debug("{} Relaties bekijken", relaties.size());
        relaties.stream().filter(relatie -> relatie.getEmailadres() != null && !"".equals(relatie.getEmailadres())).forEach(relatie -> {
            if (checks.stream().noneMatch(emailCheck -> emailCheck.getGebruiker() == relatie.getId())) {
                emailCheckRepository.opslaan(new EmailCheck(relatie.getId(), relatie.getEmailadres()));
                slackService.stuurBericht(relatie.getEmailadres(), relatie.getId(), SlackService.Soort.NIEUW, session, channelName, rateLimiter);
            }
        });

        LOGGER.debug("{} verdwenen E-mailadressen", verdwenenAdressen.size());
        verdwenenAdressen.stream().forEach(relatie -> {
            Optional<EmailCheck> emailCheckOptional = checks.stream().filter(emailCheck -> emailCheck.getGebruiker() == relatie.getId()).findFirst();

            if (emailCheckOptional.isPresent()) {
                slackService.stuurBericht(emailCheckOptional.get().getMailadres(), relatie.getId(), SlackService.Soort.VERWIJDERD, session, channelName, rateLimiter);
            }
        });

        LOGGER.debug("{} gemuteerde E-mailadressen", gemuteerdeAdressen.size());
        gemuteerdeAdressen.stream().forEach(relatie -> slackService.stuurBericht(relatie.getEmailadres(), relatie.getId(), SlackService.Soort.GEWIJZIGD, session, channelName, rateLimiter));
    }
}
