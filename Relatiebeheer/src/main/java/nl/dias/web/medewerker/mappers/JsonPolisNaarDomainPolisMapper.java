package nl.dias.web.medewerker.mappers;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonPolisNaarDomainPolisMapper {
    private final static Logger LOGGER = LoggerFactory.getLogger(JsonPolisNaarDomainPolisMapper.class);

    private PolisClient polisClient;
    private IdentificatieClient identificatieClient;

    public JsonPolisNaarDomainPolisMapper(PolisClient polisClient, IdentificatieClient identificatieClient) {
        this.polisClient = polisClient;
        this.identificatieClient = identificatieClient;
    }

    public JsonPolis map(nl.lakedigital.djfc.domain.response.Polis polisIn) {
        String patternDatum = "yyyy-MM-dd";

        LocalDate ingangsDatum = null;
        if (polisIn.getIngangsDatum() != null && !"".equals(polisIn.getIngangsDatum())) {
            ingangsDatum = LocalDate.parse(polisIn.getIngangsDatum(), DateTimeFormat.forPattern(patternDatum));
        }
        LocalDate wijzigingsDatum = null;
        if (polisIn.getWijzigingsDatum() != null && !"".equals(polisIn.getWijzigingsDatum())) {
            wijzigingsDatum = LocalDate.parse(polisIn.getWijzigingsDatum(), DateTimeFormat.forPattern(patternDatum));
        }
        LocalDate prolongatieDatum = null;
        if (polisIn.getProlongatieDatum() != null && !"".equals(polisIn.getProlongatieDatum())) {
            prolongatieDatum = LocalDate.parse(polisIn.getProlongatieDatum(), DateTimeFormat.forPattern(patternDatum));
        }
        LocalDate eindDatum = null;
        if (polisIn.getEindDatum() != null && !"".equals(polisIn.getEindDatum())) {
            eindDatum = LocalDate.parse(polisIn.getEindDatum(), DateTimeFormat.forPattern(patternDatum));
        }

        JsonPolis polis;
        //        if (polisIn.getIdentificatie() == null) {
        //            polis = polisClient.definieerPolisSoort(polisIn.getSoort());
        //        } else {
        //            LOGGER.debug(ReflectionToStringBuilder.toString(identificatie));
        //            //POLISCLIENT!
        //            polis = polisClient.lees(String.valueOf(identificatie.getEntiteitId()));
        //        polis = polisClient.lees(String.valueOf(identificatie.getEntiteitId()));
        polis = new JsonPolis();
        if (polisIn.getIdentificatie() != null) {
            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(polisIn.getIdentificatie());
            polis.setId(identificatie.getEntiteitId());
        }
        //        polis=new P

        if (polisIn.getStatus() != null) {
            LOGGER.debug("Opzoeken Status {}", polisIn.getStatus());
            LOGGER.debug(ReflectionToStringBuilder.toString(polis));
            //            polis.setStatus(getFirst(filter(Lists.newArrayList(StatusPolis.values()), new StatusPolisBijStatusPredicate(polisIn.getStatus())), StatusPolis.ACT));
            polis.setStatus(polisIn.getStatus());
        }

        polis.setPolisNummer(polisIn.getPolisNummer());
        polis.setKenmerk(polisIn.getKenmerk());
        polis.setIngangsDatum(polisIn.getIngangsDatum());
        if (polisIn.getPremie() != null) {
            //            polis.setPremie(new Bedrag(polisIn.getPremie().replace(",", ".")));
            polis.setPremie(polisIn.getPremie());
        }
        polis.setWijzigingsDatum(polisIn.getWijzigingsDatum());
        polis.setProlongatieDatum(polisIn.getProlongatieDatum());
        polis.setEindDatum(polisIn.getEindDatum());
        polis.setDekking(polisIn.getDekking());
        polis.setVerzekerdeZaak(polisIn.getVerzekerdeZaak());
        polis.setSoort(polisIn.getSoort());
        if (StringUtils.isNotEmpty(polisIn.getBetaalfrequentie())) {
            //            polis.setBetaalfrequentie(Betaalfrequentie.valueOf(polisIn.getBetaalfrequentie().toUpperCase().substring(0, 1)));
            polis.setBetaalfrequentie(polisIn.getBetaalfrequentie());
        }

        //        polis.setMaatschappij(polisIn.getMaatschappij());

        //        Identificatie identificatie1 = identificatieClient.zoekIdentificatieCode(polisIn.getParentIdentificatie());
        //        if ("relatie".equalsIgnoreCase(identificatie.getSoortEntiteit())) {
        //            polis.setRelatie(identificatie1.getEntiteitId());
        //        } else {
        //            polis.setBedrijf(identificatie1.getEntiteitId());
        //        }
        //        polis.setEntiteitId(polisIn.getEntiteitId());
        //        polis.setSoortEntiteit(polisIn.getSoortEntiteit());

        polis.setOmschrijvingVerzekering(polisIn.getOmschrijvingVerzekering());

        //    LOGGER.debug(ReflectionToStringBuilder.toString(polis));
        return polis;
    }
}
