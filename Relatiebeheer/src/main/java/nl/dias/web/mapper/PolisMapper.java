package nl.dias.web.mapper;

import com.google.common.collect.Lists;
import nl.dias.domein.Bedrag;
import nl.dias.domein.StatusPolis;
import nl.dias.domein.polis.Betaalfrequentie;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.PolisComperator;
import nl.dias.domein.predicates.StatusPolisBijStatusPredicate;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.service.PolisService;
import nl.dias.service.VerzekeringsMaatschappijService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.persistence.DiscriminatorValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getFirst;

@Component
public class PolisMapper extends Mapper<Polis, JsonPolis> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisMapper.class);

    private SchadeMapper schadeMapper;
    @Inject
    private PolisService polisService;
    @Inject
    private VerzekeringsMaatschappijService verzekeringsMaatschappijService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private IdentificatieClient identificatieClient;

    @Override
    public Polis mapVanJson(JsonPolis jsonPolis) {
        String patternDatum = "yyyy-MM-dd";

        LocalDate ingangsDatum = null;
        if (jsonPolis.getIngangsDatum() != null && !"".equals(jsonPolis.getIngangsDatum())) {
            ingangsDatum = LocalDate.parse(jsonPolis.getIngangsDatum(), DateTimeFormat.forPattern(patternDatum));
        }
        LocalDate wijzigingsDatum = null;
        if (jsonPolis.getWijzigingsDatum() != null && !"".equals(jsonPolis.getWijzigingsDatum())) {
            wijzigingsDatum = LocalDate.parse(jsonPolis.getWijzigingsDatum(), DateTimeFormat.forPattern(patternDatum));
        }
        LocalDate prolongatieDatum = null;
        if (jsonPolis.getProlongatieDatum() != null && !"".equals(jsonPolis.getProlongatieDatum())) {
            prolongatieDatum = LocalDate.parse(jsonPolis.getProlongatieDatum(), DateTimeFormat.forPattern(patternDatum));
        }
        LocalDate eindDatum = null;
        if (jsonPolis.getEindDatum() != null && !"".equals(jsonPolis.getEindDatum())) {
            eindDatum = LocalDate.parse(jsonPolis.getEindDatum(), DateTimeFormat.forPattern(patternDatum));
        }

        Polis polis;
        if (jsonPolis.getIdentificatie() == null) {
            polis = polisService.definieerPolisSoort(jsonPolis.getSoort());
        } else {
            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(jsonPolis.getIdentificatie());
            polis = polisService.lees(identificatie.getEntiteitId());

            Polis p = polisService.definieerPolisSoort(jsonPolis.getSoort());

            if (!polis.getSchermNaam().equals(p.getSchermNaam())) {
                DiscriminatorValue discriminatorValue = p.getClass().getAnnotation(DiscriminatorValue.class);

                polisService.setDiscriminatorValue(discriminatorValue.value(), polis);

                polis = polisService.lees(jsonPolis.getId());
            }
        }

        if (jsonPolis.getStatus() != null) {
            polis.setStatus(getFirst(filter(Lists.newArrayList(StatusPolis.values()), new StatusPolisBijStatusPredicate(jsonPolis.getStatus())), StatusPolis.ACT));
        }

        polis.setPolisNummer(jsonPolis.getPolisNummer());
        polis.setKenmerk(jsonPolis.getKenmerk());
        polis.setIngangsDatum(ingangsDatum);
        if (jsonPolis.getPremie() != null) {
            polis.setPremie(new Bedrag(jsonPolis.getPremie().replace(",", ".")));
        }
        polis.setWijzigingsDatum(wijzigingsDatum);
        polis.setProlongatieDatum(prolongatieDatum);
        polis.setEindDatum(eindDatum);
        polis.setDekking(jsonPolis.getDekking());
        polis.setVerzekerdeZaak(jsonPolis.getVerzekerdeZaak());
        if (StringUtils.isNotEmpty(jsonPolis.getBetaalfrequentie())) {
            polis.setBetaalfrequentie(Betaalfrequentie.valueOf(jsonPolis.getBetaalfrequentie().toUpperCase().substring(0, 1)));
        }

        polis.setMaatschappij(Long.valueOf(jsonPolis.getMaatschappij()));

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(jsonPolis.getParentIdentificatie());
        if ("relatie".equalsIgnoreCase(identificatie.getSoortEntiteit())) {
            polis.setRelatie(identificatie.getEntiteitId());
        } else {
            polis.setBedrijf(identificatie.getEntiteitId());
        }

        polis.setOmschrijvingVerzekering(jsonPolis.getOmschrijvingVerzekering());

        LOGGER.debug(ReflectionToStringBuilder.toString(polis));
        return polis;
    }

    @Override
    public JsonPolis mapNaarJson(Polis polis) {
        LOGGER.debug("Mappen Polis " + polis);

        JsonPolis jsonPolis = new JsonPolis();

        jsonPolis.setId(polis.getId());
        // polissen die al in het systeem staan hoeven net per se een status te
        // hebben
        if (polis.getStatus() != null) {
            jsonPolis.setStatus(polis.getStatus().getOmschrijving());
        }
        jsonPolis.setPolisNummer(polis.getPolisNummer());
        jsonPolis.setKenmerk(polis.getKenmerk());
        if (polis.getPremie() != null) {
            jsonPolis.setPremie(zetBedragOm(polis.getPremie()));
        }
        if (polis.getIngangsDatum() != null) {
            jsonPolis.setIngangsDatum(polis.getIngangsDatum().toString("yyyy-MM-dd"));
        }
        if (polis.getEindDatum() != null) {
            jsonPolis.setEindDatum(polis.getEindDatum().toString("yyyy-MM-dd"));
        }
        if (polis.getWijzigingsDatum() != null) {
            jsonPolis.setWijzigingsDatum(polis.getWijzigingsDatum().toString("yyyy-MM-dd"));
        }
        if (polis.getProlongatieDatum() != null) {
            jsonPolis.setProlongatieDatum(polis.getProlongatieDatum().toString("yyyy-MM-dd"));
        }
        if (polis.getBetaalfrequentie() != null) {
            jsonPolis.setBetaalfrequentie(polis.getBetaalfrequentie().getOmschrijving());
        }
        jsonPolis.setDekking(polis.getDekking());
        jsonPolis.setVerzekerdeZaak(polis.getVerzekerdeZaak());
        if (polis.getMaatschappij() != null) {
            jsonPolis.setMaatschappij(polis.getMaatschappij().toString());
        }
        jsonPolis.setSoort(polis.getClass().getSimpleName().replace("Verzekering", ""));
        if (polis.getBedrijf() != null) {
            jsonPolis.setEntiteitId(polis.getBedrijf());
            jsonPolis.setSoortEntiteit("BEDRIJF");
        }
        if (polis.getRelatie() != null) {
            jsonPolis.setEntiteitId(polis.getBedrijf());
            jsonPolis.setSoortEntiteit("RELATIE");
        }
        jsonPolis.setOmschrijvingVerzekering(polis.getOmschrijvingVerzekering());

        Identificatie identificatie = identificatieClient.zoekIdentificatie("POLIS", jsonPolis.getId());
        jsonPolis.setIdentificatie(identificatie.getIdentificatie());

        return jsonPolis;
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

    @Override
    public List<JsonPolis> mapAllNaarJson(Set<Polis> polissen) {
        List<JsonPolis> ret = new ArrayList<>();

        List<Polis> polissenSortable = new ArrayList<Polis>();
        for (Polis polis : polissen) {
            polissenSortable.add(polis);
        }
        Collections.sort(polissenSortable, new PolisComperator());

        for (Polis polis : polissen) {
            ret.add(mapNaarJson(polis));
        }
        return ret;
    }
}
