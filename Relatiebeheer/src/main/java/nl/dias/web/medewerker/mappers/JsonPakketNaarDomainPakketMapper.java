package nl.dias.web.medewerker.mappers;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonPakket;
import nl.lakedigital.djfc.domain.response.Polis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonPakketNaarDomainPakketMapper {
    private final static Logger LOGGER = LoggerFactory.getLogger(JsonPakketNaarDomainPakketMapper.class);

    private PolisClient polisClient;
    private IdentificatieClient identificatieClient;
    private JsonPolisNaarDomainPolisMapper jsonPolisNaarDomainPolisMapper;

    public JsonPakketNaarDomainPakketMapper(PolisClient polisClient, IdentificatieClient identificatieClient) {
        this.polisClient = polisClient;
        this.identificatieClient = identificatieClient;
        this.jsonPolisNaarDomainPolisMapper = new JsonPolisNaarDomainPolisMapper(polisClient, identificatieClient);
    }

    public JsonPakket map(nl.lakedigital.djfc.domain.response.Pakket pakketIn) {
        JsonPakket pakket;
        //        if (pakketIn.getIdentificatie() == null) {
        pakket = new JsonPakket();
        //        } else {
        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(pakketIn.getIdentificatie());
        pakket.setId(identificatie.getEntiteitId());
        //            pakket = polisClient.lees(identificatie.getEntiteitId());
        //        }
        pakket.setMaatschappij(pakketIn.getMaatschappij());
        pakket.setSoortEntiteit(pakketIn.getSoortEntiteit());
        pakket.setPolisNummer(pakketIn.getPolisNummer());
        pakket.setEntiteitId(pakketIn.getEntiteitId());
        for (Polis polis : pakketIn.getPolissen()) {
            pakket.getPolissen().add(jsonPolisNaarDomainPolisMapper.map(polis));
        }

        return pakket;
    }
}
