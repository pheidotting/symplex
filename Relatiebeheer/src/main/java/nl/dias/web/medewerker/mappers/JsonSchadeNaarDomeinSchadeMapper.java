package nl.dias.web.medewerker.mappers;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Schade;
import nl.dias.service.SchadeService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

public class JsonSchadeNaarDomeinSchadeMapper {
    private SchadeService schadeService;
    private IdentificatieClient identificatieClient;

    public JsonSchadeNaarDomeinSchadeMapper(SchadeService schadeService, IdentificatieClient identificatieClient) {
        this.schadeService = schadeService;
        this.identificatieClient = identificatieClient;
    }

    public Schade map(nl.lakedigital.djfc.domain.response.Schade schadeIn) {
        String patternDatumTijd = "yyyy-MM-dd'T'HH:mm";
        String patternDatum = "yyyy-MM-dd";

        LocalDate datumAfgehandeld = null;
        if (schadeIn.getDatumAfgehandeld() != null) {
            datumAfgehandeld = LocalDate.parse(schadeIn.getDatumAfgehandeld(), DateTimeFormat.forPattern(patternDatum));
        }

        Schade schade;
        if (schadeIn.getIdentificatie() == null) {
            schade = new Schade();
        } else {
            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(schadeIn.getIdentificatie());
            schade = schadeService.lees(identificatie.getEntiteitId());
            schade.setId(identificatie.getEntiteitId());
        }

        if (schadeIn.getDatumTijdMelding() != null) {
            LocalDateTime datumTijdMelding = LocalDateTime.parse(schadeIn.getDatumTijdMelding(), DateTimeFormat.forPattern(patternDatumTijd));
            schade.setDatumTijdMelding(datumTijdMelding);
        }
        if (schadeIn.getDatumTijdSchade() != null) {
            LocalDateTime datumTijdSchade = LocalDateTime.parse(schadeIn.getDatumTijdSchade(), DateTimeFormat.forPattern(patternDatumTijd));
            schade.setDatumTijdSchade(datumTijdSchade);
        }

        if (datumAfgehandeld != null) {
            schade.setDatumAfgehandeld(datumAfgehandeld);
        }
        if (schadeIn.getEigenRisico() != null) {
            schade.setEigenRisico(new Bedrag(schadeIn.getEigenRisico().replace(",", ".")));
        }
        schade.setLocatie(schadeIn.getLocatie());
        schade.setOmschrijving(schadeIn.getOmschrijving());
        schade.setSchadeNummerMaatschappij(schadeIn.getSchadeNummerMaatschappij());
        schade.setSchadeNummerTussenPersoon(schadeIn.getSchadeNummerTussenPersoon());

        return schade;
    }
}
