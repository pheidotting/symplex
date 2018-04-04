package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.request.communicatie.Afzender;
import nl.lakedigital.as.messaging.request.communicatie.Geadresseerde;
import nl.lakedigital.djfc.domain.*;
import nl.lakedigital.djfc.repository.CommunicatieProductRepository;
import nl.lakedigital.djfc.service.verzenden.VerzendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommunicatieProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunicatieProductService.class);

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
    private MaakBriefService maakBriefService;
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

    public void markeerAlsGelezen(Long id) {
        LOGGER.debug("Markeer als gelezen {}", id);

        IngaandeEmail email = (IngaandeEmail) communicatieProductRepository.lees(id);

        email.setOngelezenIndicatie(null);

        communicatieProductRepository.opslaan(email);
    }

    public CommunicatieProduct lees(Long id) {
        return communicatieProductRepository.lees(id);
    }

    public void verstuur(Long id) {
        CommunicatieProduct communicatieProduct = communicatieProductRepository.lees(id);

        LOGGER.debug("Klaarzetten om te versturen : {}", communicatieProduct.getId());

        if (communicatieProduct instanceof UitgaandeEmail) {
            OnverzondenIndicatie onverzondenIndicatie = new OnverzondenIndicatie();
            onverzondenIndicatie.setUitgaandeEmail((UitgaandeEmail) communicatieProduct);
            ((UitgaandeEmail) communicatieProduct).setOnverzondenIndicatie(onverzondenIndicatie);
        }

        communicatieProductRepository.opslaan(communicatieProduct);
    }

    public void opslaan(List<CommunicatieProduct> communicatieProducts) {
        communicatieProductRepository.opslaan(communicatieProducts);
    }
        public List<CommunicatieProduct> lijst(SoortEntiteit soortEntiteit,Long entiteitId){
        LOGGER.debug("Ophalen lijst bij {} met id {}",soortEntiteit,entiteitId);
        return communicatieProductRepository.alles(soortEntiteit,entiteitId);
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

    public void markeerOmTeVerzenden(Long id){
        CommunicatieProduct communicatieProduct=communicatieProductRepository.lees(id);

        LOGGER.debug("markeerOmTeVerzenden {}", communicatieProduct.getId());

        if(communicatieProduct.getDatumTijdVerzending()==null){

        if(communicatieProduct instanceof UitgaandeBrief){
maakBriefService.verzend((UitgaandeBrief)communicatieProduct);
        }else{
            OnverzondenIndicatie onverzondenIndicatie=new OnverzondenIndicatie();
            onverzondenIndicatie.setUitgaandeEmail((UitgaandeEmail)communicatieProduct);
            ((UitgaandeEmail)communicatieProduct).setOnverzondenIndicatie(onverzondenIndicatie);

            //            JsonRelatie relatie = relatieClient.lees(communicatieProduct.getEntiteitId());

            Emailadres emailadres=new Emailadres();
            emailadres.setUitgaandeEmail((UitgaandeEmail)communicatieProduct);
            //            emailadres.setEmailadres(relatie.getEmailadres());
            ((UitgaandeEmail)communicatieProduct).setEmailadres(emailadres);
        }

        communicatieProductRepository.opslaan(communicatieProduct);
    }
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
