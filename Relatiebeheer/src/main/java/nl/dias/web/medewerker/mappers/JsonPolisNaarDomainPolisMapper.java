package nl.dias.web.medewerker.mappers;

import com.google.common.collect.Lists;
import nl.dias.domein.Bedrag;
import nl.dias.domein.StatusPolis;
import nl.dias.domein.polis.Betaalfrequentie;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.predicates.StatusPolisBijStatusPredicate;
import nl.dias.service.PolisService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getFirst;

public class JsonPolisNaarDomainPolisMapper {
    private PolisService polisService;
    private IdentificatieClient identificatieClient;

    public JsonPolisNaarDomainPolisMapper(PolisService polisService, IdentificatieClient identificatieClient) {
        this.polisService = polisService;
        this.identificatieClient = identificatieClient;
    }

    public Polis map(nl.lakedigital.djfc.domain.response.Polis polisIn) {
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

        Polis polis;
        if (polisIn.getIdentificatie() == null) {
            polis = polisService.definieerPolisSoort(polisIn.getSoort());
        } else {
            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(polisIn.getIdentificatie());
            polis = polisService.lees(identificatie.getEntiteitId());
        }

        if (polisIn.getStatus() != null) {
            polis.setStatus(getFirst(filter(Lists.newArrayList(StatusPolis.values()), new StatusPolisBijStatusPredicate(polisIn.getStatus())), StatusPolis.ACT));
        }

        polis.setPolisNummer(polisIn.getPolisNummer());
        polis.setKenmerk(polisIn.getKenmerk());
        polis.setIngangsDatum(ingangsDatum);
        if (polisIn.getPremie() != null) {
            polis.setPremie(new Bedrag(polisIn.getPremie().replace(",", ".")));
        }
        polis.setWijzigingsDatum(wijzigingsDatum);
        polis.setProlongatieDatum(prolongatieDatum);
        polis.setEindDatum(eindDatum);
        polis.setDekking(polisIn.getDekking());
        polis.setVerzekerdeZaak(polisIn.getVerzekerdeZaak());
        if (StringUtils.isNotEmpty(polisIn.getBetaalfrequentie())) {
            polis.setBetaalfrequentie(Betaalfrequentie.valueOf(polisIn.getBetaalfrequentie().toUpperCase().substring(0, 1)));
        }

        polis.setMaatschappij(Long.valueOf(polisIn.getMaatschappij()));

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(polisIn.getParentIdentificatie());
        if ("relatie".equalsIgnoreCase(identificatie.getSoortEntiteit())) {
            polis.setRelatie(identificatie.getEntiteitId());
        } else {
            polis.setBedrijf(identificatie.getEntiteitId());
        }

        polis.setOmschrijvingVerzekering(polisIn.getOmschrijvingVerzekering());

        //    LOGGER.debug(ReflectionToStringBuilder.toString(polis));
        return polis;
    }
}
