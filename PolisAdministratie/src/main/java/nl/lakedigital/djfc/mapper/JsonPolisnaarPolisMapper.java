package nl.lakedigital.djfc.mapper;

import com.google.common.collect.Lists;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.domain.*;
import nl.lakedigital.djfc.predicates.PolisOpSchermNaamPredicate;
import nl.lakedigital.djfc.predicates.StatusPolisBijStatusPredicate;
import nl.lakedigital.djfc.service.PolisService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Iterables.*;

@Component
public class JsonPolisnaarPolisMapper extends AbstractMapper<JsonPolis, Polis> implements JsonMapper{
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonPolisnaarPolisMapper.class);

    @Inject
    private List<Polis> polissen;
    @Inject
    private PolisService polisService;

    @Override
    public Polis map(JsonPolis jsonPolis, Object parent, Object bestaandObject) {
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

        Polis polis;
        SoortEntiteit soortEntiteit = null;
        Long entiteitId = null;
        //        if (jsonPolis.getBedrijf() != null) {
        //            soortEntiteit = SoortEntiteit.BEDRIJF;
        //            entiteitId = Long.valueOf(jsonPolis.getBedrijf());
        //        } else if (jsonPolis.getRelatie() != null && jsonPolis.getRelatie() != "") {
        //            soortEntiteit = SoortEntiteit.RELATIE;
        //            entiteitId = Long.valueOf(jsonPolis.getRelatie());
        //        }
        if (jsonPolis.getId() == null || jsonPolis.getId() == 0L) {
            polis = getOnlyElement(filter(polissen, new PolisOpSchermNaamPredicate(jsonPolis.getSoort()))).nieuweInstantie(soortEntiteit,entiteitId);
        } else {
            polis = polisService.lees(jsonPolis.getId());
            polis.setSoortEntiteitEnEntiteitId(soortEntiteit, entiteitId);
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
        polis.setDekking(jsonPolis.getDekking());
        polis.setVerzekerdeZaak(jsonPolis.getVerzekerdeZaak());
        if (StringUtils.isNotEmpty(jsonPolis.getBetaalfrequentie())) {
            polis.setBetaalfrequentie(Betaalfrequentie.valueOf(jsonPolis.getBetaalfrequentie().toUpperCase().substring(0, 1)));
        }

        polis.setMaatschappij(Long.valueOf(jsonPolis.getMaatschappij()));
        //        if (StringUtils.isNotEmpty(jsonPolis.getRelatie())) {
        //            polis.setRelatie(Long.valueOf(jsonPolis.getRelatie()));
        //        }
        polis.setOmschrijvingVerzekering(jsonPolis.getOmschrijvingVerzekering());

        LOGGER.debug(ReflectionToStringBuilder.toString(polis));
        return polis;
    }

    @Override
public    boolean isVoorMij(Object object) {
        return object instanceof JsonPolis;
    }
}
