package nl.dias.web.mapper;

import nl.dias.domein.Bedrag;
import nl.dias.service.GebruikerService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.BijlageClient;
import nl.lakedigital.djfc.client.oga.GroepBijlagesClient;
import nl.lakedigital.djfc.client.oga.OpmerkingClient;
import nl.lakedigital.djfc.client.taak.TaakClient;
import nl.lakedigital.djfc.commons.domain.response.Polis;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonToDtoPolisMapper implements Function<JsonPolis, Polis> {
    //public class JsonToDtoPolisMapper implements Function<nl.dias.domein.polis.Polis, Polis> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonToDtoPolisMapper.class);

    private BijlageClient bijlageClient;
    private GroepBijlagesClient groepBijlagesClient;
    private OpmerkingClient opmerkingClient;
    private IdentificatieClient identificatieClient;
    private GebruikerService gebruikerService;
    private TaakClient taakClient;

    public JsonToDtoPolisMapper(BijlageClient bijlageClient, GroepBijlagesClient groepBijlagesClient, OpmerkingClient opmerkingClient, IdentificatieClient identificatieClient, GebruikerService gebruikerService, TaakClient taakClient) {
        this.bijlageClient = bijlageClient;
        this.groepBijlagesClient = groepBijlagesClient;
        this.opmerkingClient = opmerkingClient;
        this.identificatieClient = identificatieClient;
        this.gebruikerService = gebruikerService;
        this.taakClient = taakClient;
    }

    @Override
    public Polis apply(JsonPolis domein) {
        //        return null;
        //    }
        //
        //    @Override
        //    public Polis apply(nl.dias.domein.polis.Polis domein) {
        LOGGER.debug(ReflectionToStringBuilder.toString(domein));

        Identificatie identificatie = identificatieClient.zoekIdentificatie("POLIS", domein.getId());

        Polis polis = new Polis();
        polis.setIdentificatie(identificatie.getIdentificatie());
        if (domein.getStatus() != null) {
            //            polis.setStatus(domein.getStatus().getOmschrijving());
            polis.setStatus(domein.getStatus());
        }
        polis.setPolisNummer(domein.getPolisNummer());
        polis.setKenmerk(domein.getKenmerk());
        if (domein.getPremie() != null) {
            //            polis.setPremie(zetBedragOm(domein.getPremie()));
            polis.setPremie(domein.getPremie());
        }
        if (domein.getIngangsDatum() != null) {
            polis.setIngangsDatum(domein.getIngangsDatum());//.toString("yyyy-MM-dd"));
        }
        if (domein.getEindDatum() != null) {
            polis.setEindDatum(domein.getEindDatum());//.toString("yyyy-MM-dd"));
        }
        if (domein.getWijzigingsDatum() != null) {
            polis.setWijzigingsDatum(domein.getWijzigingsDatum());//.toString("yyyy-MM-dd"));
        }
        if (domein.getProlongatieDatum() != null) {
            polis.setProlongatieDatum(domein.getProlongatieDatum());//.toString("yyyy-MM-dd"));
        }
        if (domein.getBetaalfrequentie() != null) {
            polis.setBetaalfrequentie(domein.getBetaalfrequentie());//.getOmschrijving());
        }
        polis.setDekking(domein.getDekking());
        polis.setVerzekerdeZaak(domein.getVerzekerdeZaak());
        //        if (domein.getMaatschappij() != null) {
        //            polis.setMaatschappij(domein.getMaatschappij().toString());
        //        }
        //        polis.setSoort(domein.getClass().getSimpleName().replace("Verzekering", ""));
        polis.setSoort(domein.getSoort());
        //        if (domein.getBedrijf() != null) {
        //            polis.setEntiteitId(domein.getBedrijf());
        //            polis.setSoortEntiteit("BEDRIJF");
        //        }
        //        if (domein.getRelatie() != null) {
        //            polis.setEntiteitId(domein.getBedrijf());
        //            polis.setSoortEntiteit("RELATIE");
        //        }
        //        polis.setSoortEntiteit(domein.getSoortEntiteit());
        //        polis.setEntiteitId(domein.getEntiteitId());
        polis.setOmschrijvingVerzekering(domein.getOmschrijvingVerzekering());

        polis.setSchades(domein.getSchades().stream().map(new JsonToDtoSchadeMapper(bijlageClient, groepBijlagesClient, opmerkingClient, identificatieClient, gebruikerService, taakClient)).collect(Collectors.toList()));

        return polis;
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
