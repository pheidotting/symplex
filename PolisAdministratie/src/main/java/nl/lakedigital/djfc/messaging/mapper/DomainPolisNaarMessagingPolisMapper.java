package nl.lakedigital.djfc.messaging.mapper;

import nl.lakedigital.as.messaging.domain.Polis;

import java.util.function.Function;

//import org.joda.time.format.DateTimeFormatter;

public class DomainPolisNaarMessagingPolisMapper implements Function<nl.lakedigital.djfc.domain.Polis, Polis> {
    @Override
    public Polis apply(nl.lakedigital.djfc.domain.Polis polisIn) {
        Polis polis = new Polis();

        polis.setId(polisIn.getId());
        if (polisIn.getStatus() != null) {
            polis.setStatus(polisIn.getStatus().name());
        }
        if (polisIn.getMaatschappij() != null) {
            polis.setMaatschappij(polisIn.getMaatschappij().toString());
        }
        if (polisIn.getBetaalfrequentie() != null) {
            polis.setBetaalfrequentie(polisIn.getBetaalfrequentie().name());
        }
        polis.setDekking(polisIn.getDekking());


        String patternDatum = "yyyy-MM-dd";

        if (polisIn.getIngangsDatum() != null && !"".equals(polisIn.getIngangsDatum())) {
            polis.setIngangsDatum(polisIn.getIngangsDatum().toString(patternDatum));
        }
        if (polisIn.getWijzigingsDatum() != null && !"".equals(polisIn.getWijzigingsDatum())) {
            polis.setWijzigingsDatum(polisIn.getWijzigingsDatum().toString(patternDatum));
        }
        if (polisIn.getProlongatieDatum() != null && !"".equals(polisIn.getProlongatieDatum())) {
            polis.setProlongatieDatum(polisIn.getProlongatieDatum().toString(patternDatum));
        }
        if (polisIn.getEindDatum() != null && !"".equals(polisIn.getEindDatum())) {
            polis.setEindDatum(polisIn.getEindDatum().toString(patternDatum));
        }
        polis.setPolisNummer(polisIn.getPolisNummer());
        polis.setKenmerk(polisIn.getKenmerk());
        if (polisIn.getPremie() != null) {
            polis.setPremie(polisIn.getPremie().getBedrag().toString());
        }
        polis.setDekking(polisIn.getDekking());
        polis.setVerzekerdeZaak(polisIn.getVerzekerdeZaak());
        if (polisIn.getBetaalfrequentie()!=null) {
            polis.setBetaalfrequentie(polisIn.getBetaalfrequentie().name());
        }

        polis.setMaatschappij(polisIn.getMaatschappij().toString());
        polis.setOmschrijvingVerzekering(polisIn.getOmschrijvingVerzekering());
        polis.setStatus(polisIn.getStatus().name());
        polis.setIdentificatie(polisIn.getIdentificatie());

        return polis;
    }
}
