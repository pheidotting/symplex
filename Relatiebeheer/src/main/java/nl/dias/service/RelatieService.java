package nl.dias.service;

import nl.dias.domein.Hypotheek;
import nl.dias.domein.Relatie;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonPakket;
import nl.lakedigital.djfc.metrics.MetricsService;
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
    private MetricsService metricsService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private PolisClient polisClient;
    //    private PolisService polisService;
    @Inject
    private HypotheekService hypotheekService;
    @Inject
    private AdresClient adresClient;
    //    @Inject
    //    private SchadeClient schadeClient;
    //    private SchadeService schadeService;

    public Relatie zoekRelatie(String identificatieCode) {
        LOGGER.trace("Opzoeken identificatieCode {}", identificatieCode);
        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(identificatieCode);

        if ("BEDRIJF".equals(identificatie.getSoortEntiteit())) {
            return null;
        }

        LOGGER.debug("Opgehaalde Identificatie voor soortEntiteit {} en entiteitId {}", identificatie.getSoortEntiteit(), identificatie.getEntiteitId());

        Long relatieId = null;

        switch (identificatie.getSoortEntiteit()) {
            case "RELATIE":
                relatieId = identificatie.getEntiteitId();
                LOGGER.trace("relatieId {} opgehaald uit RELATIE", relatieId);
                break;
            case "PAKKET":
            case "POLIS":
                relatieId = pakRelatieBijPolis(identificatie.getEntiteitId());
                LOGGER.trace("relatieId {} opgehaald uit POLIS", relatieId);
                break;
            case "ADRES":
                relatieId = pakRelatieBijAdres(identificatie.getEntiteitId());
                LOGGER.trace("relatieId {} opgehaald uit ADRES", relatieId);
                break;
            case "SCHADE":
                relatieId = pakRelatieBijSchade(identificatie.getEntiteitId());
                LOGGER.trace("relatieId {} opgehaald uit SCHADE", relatieId);
                break;
            case "HYPOTHEEK":
                relatieId = pakRelatieBijHypotheek(identificatie.getEntiteitId());
                LOGGER.trace("relatieId {} opgehaald uit HYPOTHEEK", relatieId);
                break;
        }

        if (relatieId == null) {
            LOGGER.debug("RelatieId kon niet worden bepaald");

            return null;
        }
        return (Relatie) gebruikerService.lees(relatieId);
    }

    private Long pakRelatieBijPolis(Long polisId) {
        LOGGER.debug("polisId {}", polisId);
        JsonPakket pakket = polisClient.lees(polisId);
        //        Polis polis = polisService.lees(polisId);

        LOGGER.debug("Polis ({}) gevonden : {}", polisId, ReflectionToStringBuilder.toString(pakket));

        //        Identificatie identificatie = identificatieClient.zoekIdentificatie("POLIS", polisId);

        return pakket == null ? null : pakket.getEntiteitId();
        //
        //        return polis.getEntiteitId();
    }

    private Long pakRelatieBijHypotheek(Long hypotheekId) {
        LOGGER.debug("hypotheekId {}", hypotheekId);
        Hypotheek hypotheek = hypotheekService.leesHypotheek(hypotheekId);

        LOGGER.debug("Hypotheek ({}) gevonden : {}", hypotheekId, ReflectionToStringBuilder.toString(hypotheek));

        return hypotheek.getRelatie();
    }

    private Long pakRelatieBijAdres(Long adresId) {
        JsonAdres adres = adresClient.lees(adresId);

        return adres.getEntiteitId();
    }

    private Long pakRelatieBijSchade(Long schadeId) {
        //        JsonSchade schade = schadeClient.lees(String.valueOf(schadeId));
        //        Schade schade = schadeService.lees(schadeId);
        //
        //        LOGGER.debug("Schade ({}) gevonden : {}", schadeId, ReflectionToStringBuilder.toString(schade));
        //
        return 0L;//pakRelatieBijPolis(Long.valueOf(schade.getPolis()));
    }
}
