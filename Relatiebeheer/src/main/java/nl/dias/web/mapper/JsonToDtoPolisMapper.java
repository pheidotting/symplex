package nl.dias.web.mapper;

import nl.dias.service.GebruikerService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.BijlageClient;
import nl.lakedigital.djfc.client.oga.GroepBijlagesClient;
import nl.lakedigital.djfc.client.oga.OpmerkingClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.domain.response.Polis;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonToDtoPolisMapper implements Function<JsonPolis, Polis> {
    private final static Logger LOGGER = LoggerFactory.getLogger(JsonToDtoPolisMapper.class);

    private BijlageClient bijlageClient;
    private GroepBijlagesClient groepBijlagesClient;
    private OpmerkingClient opmerkingClient;
    private IdentificatieClient identificatieClient;
    private GebruikerService gebruikerService;

    public JsonToDtoPolisMapper(BijlageClient bijlageClient, GroepBijlagesClient groepBijlagesClient, OpmerkingClient opmerkingClient, IdentificatieClient identificatieClient, GebruikerService gebruikerService) {
        this.bijlageClient = bijlageClient;
        this.groepBijlagesClient = groepBijlagesClient;
        this.opmerkingClient = opmerkingClient;
        this.identificatieClient = identificatieClient;
        this.gebruikerService = gebruikerService;
    }

    @Override
    public Polis apply(JsonPolis json) {
        LOGGER.debug(ReflectionToStringBuilder.toString(json));

        Identificatie identificatie = identificatieClient.zoekIdentificatie("POLIS", json.getId());

        Polis polis = new Polis();

        polis.setIdentificatie(identificatie.getIdentificatie());
        polis.setStatus(json.getStatus());
        polis.setPolisNummer(json.getPolisNummer());
        polis.setKenmerk(json.getKenmerk());
        polis.setPremie(json.getPremie());
        polis.setIngangsDatum(json.getIngangsDatum());
        polis.setEindDatum(json.getEindDatum());
        polis.setWijzigingsDatum(json.getWijzigingsDatum());
        polis.setProlongatieDatum(json.getProlongatieDatum());
        polis.setBetaalfrequentie(json.getBetaalfrequentie());
        polis.setDekking(json.getDekking());
        polis.setVerzekerdeZaak(json.getVerzekerdeZaak());
        polis.setMaatschappij(json.getMaatschappij());
        polis.setSoort(json.getSoort());
        polis.setSoortEntiteit(json.getSoortEntiteit());
        polis.setEntiteitId(json.getEntiteitId());
        polis.setOmschrijvingVerzekering(json.getOmschrijvingVerzekering());

        polis.setBijlages(bijlageClient.lijst("POLIS", json.getId()).stream().map(new JsonToDtoBijlageMapper(identificatieClient)).collect(Collectors.toList()));
        polis.setGroepBijlages(groepBijlagesClient.lijstGroepen("POLIS", json.getId()).stream().map(new JsonToDtoGroepBijlageMapper(identificatieClient)).collect(Collectors.toList()));
        polis.setOpmerkingen(opmerkingClient.lijst("POLIS", json.getId()).stream().map(new JsonToDtoOpmerkingMapper(identificatieClient, gebruikerService)).collect(Collectors.toList()));

        polis.setSchades(json.getSchades().stream().map(new JsonToDtoSchadeMapper(bijlageClient, groepBijlagesClient, opmerkingClient, identificatieClient, gebruikerService)).collect(Collectors.toList()));

        return polis;
    }
}
