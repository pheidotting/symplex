package nl.dias.service;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Hypotheek;
import nl.dias.domein.Schade;
import nl.dias.domein.polis.Polis;
import nl.lakedigital.as.messaging.domain.Bedrijf;
import nl.lakedigital.as.messaging.domain.BedrijfOfPersoon;
import nl.lakedigital.as.messaging.domain.Persoon;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class OpvragenPersoonsOfBedrijfsGegevensService {
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private PolisService polisService;
    @Inject
    private SchadeService schadeService;
    @Inject
    private HypotheekService hypotheekService;
    @Inject
    private BedrijfService bedrijfService;

    public BedrijfOfPersoon opvragenGegevens(Long entiteitId, String soortEntiteit) {
        BedrijfOfPersoon bedrijfOfPersoon = null;
        Long persoonsId = null;
        Long polisId = null;
        Long schadeId = null;
        Long hypotheekId = null;
        Long bedrijfId = null;

        switch (soortEntiteit) {
            case "SCHADE":
                schadeId = entiteitId;
                break;
            case "POLIS":
                polisId = entiteitId;
                break;
            case "RELATIE":
                persoonsId = entiteitId;
                break;
            case "HYPOTHEEK":
                hypotheekId = entiteitId;
                break;
            case "BEDRIJF":
                bedrijfId = entiteitId;
        }

        if (schadeId != null) {
            Schade schade = schadeService.lees(entiteitId);

            polisId = schade.getPolis();
        }

        if (polisId != null) {
            Polis polis = polisService.lees(polisId);
            persoonsId = polis.getRelatie();
            bedrijfId = polis.getBedrijf();
        }

        if (hypotheekId != null) {
            Hypotheek hypotheek = hypotheekService.leesHypotheek(hypotheekId);
            persoonsId = hypotheek.getRelatie();
        }

        if (persoonsId != null) {
            bedrijfOfPersoon = new Persoon();

            Gebruiker gebruiker = gebruikerService.lees(persoonsId);

            ((Persoon) bedrijfOfPersoon).setAchternaam(gebruiker.getAchternaam());
            ((Persoon) bedrijfOfPersoon).setTussenvoegsel(gebruiker.getTussenvoegsel());
            ((Persoon) bedrijfOfPersoon).setVoornaam(gebruiker.getVoornaam());

        }
        if (bedrijfId != null) {
            bedrijfOfPersoon = new Bedrijf();

            nl.dias.domein.Bedrijf bedr = bedrijfService.lees(bedrijfId);

            ((Bedrijf) bedrijfOfPersoon).setNaam(bedr.getNaam());

        }
        return bedrijfOfPersoon;
    }
}
