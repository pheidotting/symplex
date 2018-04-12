package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.request.communicatie.Afzender;
import nl.lakedigital.as.messaging.request.communicatie.Geadresseerde;
import nl.lakedigital.djfc.domain.*;
import nl.lakedigital.djfc.repository.CommunicatieProductRepository;
import nl.lakedigital.djfc.service.verzenden.VerzendService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommunicatieProductService {

    public enum SoortCommunicatieProduct {EMAIL, BRIEF}

    public enum TemplateNaam {
        NIEUWE_VERSIE("nieuwe-versie", "Nieuwe versie van Symplex"), //
        KANTOOR_AANGEMELD("kantoor-aangemeld", "Welkom bij Symplex"), //
        WACHTWOORD_VERGETEN("wachtwoord-vergeten", "Nieuw wachtwoord"),//
        HERINNER_LICENTIE("herinner-licenties", "Herinnerling verlopen licentie"),//
        LICENTIE_GEKOCHT("licentie-gekocht", "Licentie gekocht");
        private String bestandsnaam;
        private String onderwerp;

        TemplateNaam(String bestandsnaam, String onderwerp) {
            this.bestandsnaam = bestandsnaam;
            this.onderwerp = onderwerp;
        }

        public String getBestandsnaam() {
            return bestandsnaam;
        }

        public String getOnderwerp() {
            return onderwerp;
        }
    }

    @Inject
    protected CommunicatieProductRepository communicatieProductRepository;
    @Inject
    protected TemplateEngine templateEngine;
    @Inject
    private VerzendService verzendService;

    public Context zetVariabelen(Map<String, String> variabelen) {
        Context context = new Context();

        for (Map.Entry<String, String> entry : variabelen.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }

        return context;
    }

    public void versturen(List<Geadresseerde> geadresseerden, Afzender afzender, Map<String, String> variabelen, TemplateNaam templateNaam) {
        Context context = zetVariabelen(variabelen);
        String tekst = templateEngine.process(templateNaam.getBestandsnaam(), context);

        for (Geadresseerde geadresseerde : geadresseerden) {
            Email mail = (Email) maakCommunicatieProduct(null, SoortCommunicatieProduct.EMAIL, geadresseerde.getId(), geadresseerde.getEmail(), tekst, templateNaam.getOnderwerp(), null, null);

            mail.setEmailOntvanger(geadresseerde.getEmail());
            mail.setNaamOntvanger(maakNaam(geadresseerde.getVoornaam(), geadresseerde.getTussenvoegsel(), geadresseerde.getAchternaam()).toString());
            if (afzender != null && afzender.getEmail() != null && afzender.getNaam() != null) {
                mail.setNaamVerzender(afzender.getNaam());
                mail.setEmailVerzender(afzender.getEmail());
            } else {
                mail.setNaamVerzender("Symplex");
                mail.setEmailVerzender("noreply@symplexict.nl");
            }

            communicatieProductRepository.opslaan(mail);
        }
        verzendService.verzend();
    }

    public void opslaan(List<CommunicatieProduct> communicatieProducts) {
        communicatieProductRepository.opslaan(communicatieProducts);
    }

    public CommunicatieProduct maakCommunicatieProduct(Long id, SoortCommunicatieProduct soort, Long relatieId, String email, String tekst, String onderwerp, Long antwoordOpId, Long medewerker) {
        if (soort == SoortCommunicatieProduct.EMAIL && email == null) {
            throw new IllegalStateException();
        }

        SoortCommunicatieProduct soortCommunicatieProduct = SoortCommunicatieProduct.EMAIL;

        CommunicatieProduct communicatieProduct = maakCommunicatieProduct(soortCommunicatieProduct, id);
        communicatieProduct.setSoortEntiteit(SoortEntiteit.RELATIE);
        communicatieProduct.setEntiteitId(relatieId);

        communicatieProduct.setTekst(tekst);
             communicatieProduct.setOnderwerp(onderwerp);

        if (antwoordOpId != null) {
            CommunicatieProduct antwoordOp = communicatieProductRepository.lees(antwoordOpId);
            communicatieProduct.setAntwoordOp(antwoordOp);
        }

        communicatieProduct.setMedewerker(medewerker);

        communicatieProductRepository.opslaan(communicatieProduct);

        return communicatieProduct;
    }

    private CommunicatieProduct maakCommunicatieProduct(SoortCommunicatieProduct soortCommunicatieProduct, Long id) {
        if(id !=null){
            return communicatieProductRepository.lees(id);
        }
        if (soortCommunicatieProduct == SoortCommunicatieProduct.BRIEF) {
            return new UitgaandeBrief();
        }
        return new UitgaandeEmail();
    }

    public List<CommunicatieProduct> leesOnverzondenCommunicatieProducten(){
        List<CommunicatieProduct> ret = new ArrayList<>();

        ret.addAll(communicatieProductRepository.leesOnverzondenBrieven());
        ret.addAll(communicatieProductRepository.leesOnverzondenEmails());

        return ret;
    }

    protected StringBuilder maakNaam(String voornaam, String tussenvoegsel, String achternaam) {
        StringBuilder naam = new StringBuilder();
        naam.append(voornaam);
        naam.append(" ");
        if (tussenvoegsel != null && !"".equals(tussenvoegsel)) {
            naam.append(tussenvoegsel);
            naam.append(" ");
        }
        naam.append(achternaam);

        return naam;

    }
}
