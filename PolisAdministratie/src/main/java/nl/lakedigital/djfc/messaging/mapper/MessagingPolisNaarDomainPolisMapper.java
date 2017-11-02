package nl.lakedigital.djfc.messaging.mapper;

import nl.lakedigital.as.messaging.domain.Polis;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.domain.Bedrag;
import nl.lakedigital.djfc.domain.Betaalfrequentie;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.StatusPolis;
import nl.lakedigital.djfc.predicates.JPolisOpSchermNaamPredicate;
import nl.lakedigital.djfc.service.PolisService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;

public class MessagingPolisNaarDomainPolisMapper implements Function<Polis, nl.lakedigital.djfc.domain.Polis> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingPolisNaarDomainPolisMapper.class);

    private PolisService polisService;
    private List<nl.lakedigital.djfc.domain.Polis> polissen;
    private IdentificatieClient identificatieClient;

    public MessagingPolisNaarDomainPolisMapper(PolisService polisService, List<nl.lakedigital.djfc.domain.Polis> polissen, IdentificatieClient identificatieClient) {
        this.polisService = polisService;
        this.polissen = polissen;
        this.identificatieClient = identificatieClient;
    }

    @Override
    public nl.lakedigital.djfc.domain.Polis apply(Polis polisIn) {
        nl.lakedigital.djfc.domain.Polis polis;

        if (StringUtils.isNotEmpty(polisIn.getIdentificatie())) {
            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(polisIn.getIdentificatie());
            LOGGER.debug("Opgehaalde Identificatie {}", ReflectionToStringBuilder.toString(identificatie));

            if (identificatie != null) {
                polisIn.setId(identificatie.getEntiteitId());
            }
        }
        LOGGER.debug(ReflectionToStringBuilder.toString(polisIn));

        if (polisIn.getId() == null || polisIn.getId() == 0L) {
            LOGGER.debug("Polis zonder id, nieuwe aanmaken dus");
            polis = polissen.stream().filter(new JPolisOpSchermNaamPredicate(polisIn.getSoort())).findFirst().get().nieuweInstantie(SoortEntiteit.valueOf(polisIn.getSoortEntiteit()), polisIn.getEntiteitId());
        } else {
            LOGGER.debug("Bestaande polis, ophalen dus..");
            polis = polisService.lees(polisIn.getId());
            polis.setSoortEntiteitEnEntiteitId(SoortEntiteit.valueOf(polisIn.getSoortEntiteit()), polisIn.getEntiteitId());
        }

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
        polis.setPolisNummer(polisIn.getPolisNummer());
        polis.setKenmerk(polisIn.getKenmerk());
        polis.setIngangsDatum(ingangsDatum);
        if (polisIn.getPremie() != null) {
            polis.setPremie(new Bedrag(polisIn.getPremie().toString()));
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
        polis.setOmschrijvingVerzekering(polisIn.getOmschrijvingVerzekering());
        polis.setStatus(newArrayList(StatusPolis.values()).stream().filter(statusPolis -> statusPolis.getOmschrijving()//
                .equals(polisIn.getStatus())).findFirst().orElse(null));
        polis.setIdentificatie(polisIn.getIdentificatie());
        return polis;
    }
}
