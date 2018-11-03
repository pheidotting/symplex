package nl.dias.web.mapper;

import nl.dias.domein.Bedrag;
import nl.dias.service.GebruikerService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.BijlageClient;
import nl.lakedigital.djfc.client.oga.GroepBijlagesClient;
import nl.lakedigital.djfc.client.oga.OpmerkingClient;
import nl.lakedigital.djfc.client.taak.TaakClient;
import nl.lakedigital.djfc.commons.domain.response.Pakket;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonPakket;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonToDtoPakketMapper implements Function<JsonPakket, Pakket> {
    //public class JsonToDtoPolisMapper implements Function<nl.dias.domein.polis.Polis, Polis> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonToDtoPakketMapper.class);

    private BijlageClient bijlageClient;
    private GroepBijlagesClient groepBijlagesClient;
    private OpmerkingClient opmerkingClient;
    private IdentificatieClient identificatieClient;
    private GebruikerService gebruikerService;
    private TaakClient taakClient;

    public JsonToDtoPakketMapper(BijlageClient bijlageClient, GroepBijlagesClient groepBijlagesClient, OpmerkingClient opmerkingClient, IdentificatieClient identificatieClient, GebruikerService gebruikerService, TaakClient taakClient) {
        this.bijlageClient = bijlageClient;
        this.groepBijlagesClient = groepBijlagesClient;
        this.opmerkingClient = opmerkingClient;
        this.identificatieClient = identificatieClient;
        this.gebruikerService = gebruikerService;
        this.taakClient = taakClient;
    }

    @Override
    public Pakket apply(JsonPakket domein) {
        LOGGER.debug(ReflectionToStringBuilder.toString(domein));

        Identificatie identificatie = identificatieClient.zoekIdentificatie("PAKKET", domein.getId());

        Pakket pakket = new Pakket();
        pakket.setPolisNummer(domein.getPolisNummer());
        pakket.setSoortEntiteit(domein.getSoortEntiteit());
        pakket.setIdentificatie(identificatie.getIdentificatie());
        pakket.setMaatschappij(domein.getMaatschappij());
        pakket.setPolissen(domein.getPolissen().stream().map(new JsonToDtoPolisMapper(bijlageClient, groepBijlagesClient, opmerkingClient, identificatieClient, gebruikerService, taakClient)).collect(Collectors.toList()));

        pakket.setBijlages(bijlageClient.lijst("PAKKET", domein.getId()).stream().map(new JsonToDtoBijlageMapper(identificatieClient)).collect(Collectors.toList()));
        pakket.setGroepBijlages(groepBijlagesClient.lijstGroepen("PAKKET", domein.getId()).stream().map(new JsonToDtoGroepBijlageMapper(identificatieClient)).collect(Collectors.toList()));
        pakket.setOpmerkingen(opmerkingClient.lijst("PAKKET", domein.getId()).stream().map(new JsonToDtoOpmerkingMapper(identificatieClient, gebruikerService)).collect(Collectors.toList()));
        pakket.setTaken(taakClient.alles("PAKKET", domein.getId()).stream().map(new JsonToDtoTaakMapper(identificatieClient)).collect(Collectors.toList()));

        return pakket;
    }

    public static String zetBedragOm(Bedrag bedrag) {
        String waarde;
        String[] x = bedrag.getBedrag().toString().split("\\.");
        if (x[1].length() == 1) {
            waarde = bedrag.getBedrag().toString() + "0";
        } else {
            waarde = bedrag.getBedrag().toString() + "";
        }
        return waarde;
    }
}
