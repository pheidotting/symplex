package nl.dias.service;

import nl.dias.domein.Hypotheek;
import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.polis.Polis;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class RelatieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RelatieService.class);

    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    //    private PolisClient polisClient;
    private PolisService polisService;
    @Inject
    private HypotheekService hypotheekService;
    @Inject
    private AdresClient adresClient;
    @Inject
    //    private SchadeClient schadeClient;
    private SchadeService schadeService;

    public Relatie zoekRelatie(String identificatieCode) {
        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(identificatieCode);

        LOGGER.debug("Opgehaalde Identificatie : {}", ReflectionToStringBuilder.toString(identificatie));

        Long relatieId = null;

        switch (identificatie.getSoortEntiteit()) {
            case "RELATIE":
                relatieId = identificatie.getEntiteitId();
                break;
            case "POLIS":
                relatieId = pakRelatieBijPolis(identificatie.getEntiteitId());
                break;
            case "ADRES":
                relatieId = pakRelatieBijAdres(identificatie.getEntiteitId());
                break;
            case "SCHADE":
                relatieId = pakRelatieBijSchade(identificatie.getEntiteitId());
                break;
            case "HYPOTHEEK":
                relatieId = pakRelatieBijHypotheek(identificatie.getEntiteitId());
                break;
        }

        return (Relatie) gebruikerService.lees(relatieId);
    }

    private Long pakRelatieBijPolis(Long polisId) {
        LOGGER.debug("polisId {}", polisId);
        //        JsonPolis polis = polisClient.lees(String.valueOf(polisId));
        Polis polis = polisService.lees(polisId);

        LOGGER.debug("Polis ({}) gevonden : {}", polisId, ReflectionToStringBuilder.toString(polis));

        //        Identificatie identificatie = identificatieClient.zoekIdentificatie("POLIS", polisId);

        return polis.getRelatie();
        //
        //        return polis.getEntiteitId();
    }

    private Long pakRelatieBijHypotheek(Long hypotheekId) {
        LOGGER.debug("hypotheekId {}", hypotheekId);
        Hypotheek hypotheek = hypotheekService.leesHypotheek(hypotheekId);

        LOGGER.debug("Polis ({}) gevonden : {}", hypotheekId, ReflectionToStringBuilder.toString(hypotheek));

        return hypotheek.getRelatie().getId();
    }

    private Long pakRelatieBijAdres(Long adresId) {
        JsonAdres adres = adresClient.lees(adresId);

        return adres.getEntiteitId();
    }

    private Long pakRelatieBijSchade(Long schadeId) {
        //        JsonSchade schade = schadeClient.lees(String.valueOf(schadeId));
        Schade schade = schadeService.lees(schadeId);

        LOGGER.debug("Schade ({}) gevonden : {}", schadeId, ReflectionToStringBuilder.toString(schade));

        return pakRelatieBijPolis(Long.valueOf(schade.getPolis()));
    }
}
