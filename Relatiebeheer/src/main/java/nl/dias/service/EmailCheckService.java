package nl.dias.service;

import nl.dias.domein.EmailCheck;
import nl.dias.domein.Relatie;
import nl.dias.repository.EmailCheckRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class EmailCheckService {
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private EmailCheckRepository emailCheckRepository;
    @Inject
    private SlackService slackService;

    public void checkEmailAdressen() {
        List<EmailCheck> checks = emailCheckRepository.alles();
        List<Relatie> relaties = gebruikerService.alleRelaties();
        List<Relatie> verdwenenAdressen = newArrayList();
        List<Relatie> gemuteerdeAdressen = newArrayList();

            checks.stream().forEach(new Consumer<EmailCheck>() {
                @Override
                public void accept(EmailCheck emailCheck) {
                    Relatie relatie = relaties.stream().filter(new Predicate<Relatie>() {
                        @Override
                        public boolean test(Relatie relatie) {
                            return relatie.getId() == emailCheck.getGebruiker();
                        }
                    }).findFirst().get();
                    if (relatie.getEmailadres() == null || "".equals(relatie.getEmailadres())) {
                        verdwenenAdressen.add(relatie);
                        emailCheckRepository.verwijder(emailCheck);
                    } else if (!emailCheck.getMailadres().equals(relatie.getEmailadres())) {
                        gemuteerdeAdressen.add(relatie);
                        emailCheck.setMailadres(relatie.getEmailadres());
                        emailCheckRepository.opslaan(emailCheck);
                    }
                }
            });

            //checken op nieuwe adressen
            relaties.stream().forEach(new Consumer<Relatie>() {
                @Override
                public void accept(Relatie relatie) {
                    if (checks.stream().noneMatch(new Predicate<EmailCheck>() {
                        @Override
                        public boolean test(EmailCheck emailCheck) {
                            return emailCheck.getGebruiker() == relatie.getId();
                        }
                    })) {
                        emailCheckRepository.opslaan(new EmailCheck(relatie.getId(), relatie.getEmailadres()));
                        slackService.stuurBericht(relatie.getEmailadres(), relatie.getId(), SlackService.Soort.NIEUW);
                    }
                }
            });

            verdwenenAdressen.stream().forEach(new Consumer<Relatie>() {
                @Override
                public void accept(Relatie relatie) {
                    EmailCheck emailCheck = checks.stream().filter(new Predicate<EmailCheck>() {
                        @Override
                        public boolean test(EmailCheck emailCheck) {
                            return emailCheck.getGebruiker() == relatie.getId();
                        }
                    }).findFirst().get();

                    slackService.stuurBericht(emailCheck.getMailadres(), relatie.getId(), SlackService.Soort.VERWIJDERD);
                }
            });

            gemuteerdeAdressen.stream().forEach(new Consumer<Relatie>() {
                @Override
                public void accept(Relatie relatie) {
                    slackService.stuurBericht(relatie.getEmailadres(), relatie.getId(), SlackService.Soort.GEWIJZIGD);
                }
            });
    }
}
