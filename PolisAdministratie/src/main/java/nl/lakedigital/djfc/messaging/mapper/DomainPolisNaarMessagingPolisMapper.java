package nl.lakedigital.djfc.messaging.mapper;


import nl.lakedigital.djfc.commons.domain.Polis;

import java.util.function.Function;


public class DomainPolisNaarMessagingPolisMapper implements Function<nl.lakedigital.djfc.domain.Polis, Polis> {

    @Override
    public Polis apply(nl.lakedigital.djfc.domain.Polis polisIn) {
        Polis polis = new Polis();

        polis.setId(polisIn.getId());
        if (polisIn.getStatus() != null) {
            polis.setStatus(polisIn.getStatus().name());
        }
        if (polisIn.getBetaalfrequentie() != null) {
            polis.setBetaalfrequentie(polisIn.getBetaalfrequentie().name());
        }
        polis.setDekking(polisIn.getDekking());


        String patternDatum = "yyyy-MM-dd";

        if (polisIn.getIngangsDatum() != null) {
            polis.setIngangsDatum(polisIn.getIngangsDatum().toString(patternDatum));
        }
        if (polisIn.getWijzigingsDatum() != null) {
            polis.setWijzigingsDatum(polisIn.getWijzigingsDatum().toString(patternDatum));
        }
        if (polisIn.getProlongatieDatum() != null) {
            polis.setProlongatieDatum(polisIn.getProlongatieDatum().toString(patternDatum));
        }
        if (polisIn.getEindDatum() != null) {
            polis.setEindDatum(polisIn.getEindDatum().toString(patternDatum));
        }
        polis.setPolisNummer(polisIn.getPolisNummer());
        polis.setKenmerk(polisIn.getKenmerk());
        if (polisIn.getPremie() != null) {
            polis.setPremie(polisIn.getPremie().getBedrag().toString());
        }
        polis.setDekking(polisIn.getDekking());
        polis.setVerzekerdeZaak(polisIn.getVerzekerdeZaak());
        if (polisIn.getBetaalfrequentie() != null) {
            polis.setBetaalfrequentie(polisIn.getBetaalfrequentie().name());
        }

        polis.setOmschrijvingVerzekering(polisIn.getOmschrijvingVerzekering());
        polis.setStatus(polisIn.getStatus().name());
        polis.setIdentificatie(polisIn.getIdentificatie());

        return polis;
    }
}
