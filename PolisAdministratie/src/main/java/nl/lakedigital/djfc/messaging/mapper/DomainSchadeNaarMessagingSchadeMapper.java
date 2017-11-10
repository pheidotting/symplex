package nl.lakedigital.djfc.messaging.mapper;

import nl.lakedigital.as.messaging.domain.Schade;

import java.util.function.Function;

//import org.joda.time.format.DateTimeFormatter;

public class DomainSchadeNaarMessagingSchadeMapper implements Function<nl.lakedigital.djfc.domain.Schade, Schade> {
    @Override
    public Schade apply(nl.lakedigital.djfc.domain.Schade schadeIn) {
        Schade schade = new Schade();

        String patternDatumTijd = "yyyy-MM-dd HH:mm";
        String patternDatum = "yyyy-MM-dd";

        if (schade.getDatumAfgehandeld() != null) {
            schade.setDatumAfgehandeld(schadeIn.getDatumAfgehandeld().toString(patternDatum));
        }
        schade.setDatumTijdMelding(schadeIn.getDatumTijdMelding().toString(patternDatumTijd));
        schade.setDatumTijdSchade(schadeIn.getDatumTijdSchade().toString(patternDatumTijd));
        if (schadeIn.getEigenRisico() != null) {
            schade.setEigenRisico(schadeIn.getEigenRisico().getBedrag().toString());
        }
        schade.setId(schadeIn.getId());
        schade.setLocatie(schadeIn.getLocatie());
        schade.setOmschrijving(schadeIn.getOmschrijving());
        schade.setSchadeNummerMaatschappij(schadeIn.getSchadeNummerMaatschappij());
        schade.setSchadeNummerTussenPersoon(schadeIn.getSchadeNummerTussenPersoon());
        if (schadeIn.getSoortSchade() != null) {
            schade.setSoortSchade(schadeIn.getSoortSchade().getOmschrijving());
        } else {
            schade.setSoortSchade(schadeIn.getSoortSchadeOngedefinieerd());
        }
        if (schadeIn.getStatusSchade() != null) {
            schade.setStatusSchade(schadeIn.getStatusSchade().getStatus());
        }
        schade.setIdentificatie(schadeIn.getIdentificatie());

        return schade;
    }
}
