package nl.dias.web.mapper;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Schade;
import nl.dias.domein.polis.Polis;
import nl.dias.service.PolisService;
import nl.dias.service.SchadeService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonSchade;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class SchadeMapper extends Mapper<Schade, JsonSchade> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeMapper.class);
    private String patternDatum = "yyyy-MM-dd";

    @Inject
    private SchadeService schadeService;
    @Inject
    private PolisService polisService;
    @Inject
    private IdentificatieClient identificatieClient;

    @Override
    public Schade mapVanJson(JsonSchade json) {
        LocalDate datumAfgehandeld = null;
        if (json.getDatumAfgehandeld() != null) {
            datumAfgehandeld = LocalDate.parse(json.getDatumAfgehandeld(), DateTimeFormat.forPattern(patternDatum));
        }

        Schade schade;
        if (json.getIdentificatie() == null) {
            schade = new Schade();
        } else {
            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(json.getIdentificatie());
            schade = schadeService.lees(identificatie.getEntiteitId());
        }
        schade.setId(json.getId());

        if (json.getDatumMelding() != null) {
            LocalDateTime datumMelding = LocalDateTime.parse(json.getDatumMelding(), DateTimeFormat.forPattern(patternDatum));
            schade.setDatumMelding(datumMelding);
        }
        if (json.getDatumSchade() != null) {
            LocalDateTime datumSchade = LocalDateTime.parse(json.getDatumSchade(), DateTimeFormat.forPattern(patternDatum));
            schade.setDatumSchade(datumSchade);
        }

        if (datumAfgehandeld != null) {
            schade.setDatumAfgehandeld(datumAfgehandeld);
        }
        if (json.getEigenRisico() != null) {
            schade.setEigenRisico(new Bedrag(json.getEigenRisico()));
        }
        schade.setLocatie(json.getLocatie());
        schade.setOmschrijving(json.getOmschrijving());
        schade.setSchadeNummerMaatschappij(json.getSchadeNummerMaatschappij());
        schade.setSchadeNummerTussenPersoon(json.getSchadeNummerTussenPersoon());

        return schade;
    }

    @Override
    public JsonSchade mapNaarJson(Schade schade) {
        JsonSchade jsonSchade = new JsonSchade();
        Polis polis = polisService.lees(schade.getPolis());

        //        jsonSchade.setBijlages(bijlageMapper.mapAllNaarJson(schade.getBijlages()));
        if (schade.getDatumAfgehandeld() != null) {
            jsonSchade.setDatumAfgehandeld(schade.getDatumAfgehandeld().toString(patternDatum));
        }
        jsonSchade.setDatumMelding(schade.getDatumMelding().toString(patternDatum));
        jsonSchade.setDatumSchade(schade.getDatumSchade().toString(patternDatum));
        if (schade.getEigenRisico() != null) {
            jsonSchade.setEigenRisico(schade.getEigenRisico().getBedrag().toString());
        }
        jsonSchade.setId(schade.getId());
        jsonSchade.setLocatie(schade.getLocatie());
        jsonSchade.setOmschrijving(schade.getOmschrijving());
        //        if (polis != null && polis.getRelatie() != null) {
        //            jsonSchade.setRelatie(polis.getRelatie().getId().toString());
        //        }
        if (polis != null && polis.getBedrijf() != null) {
            jsonSchade.setBedrijf(polis.getBedrijf());
        }

        //        List<JsonOpmerking> opmerkingen = opmerkingMapper.mapAllNaarJson(schade.getOpmerkingen());
        //        Collections.sort(opmerkingen);
        //        jsonSchade.setOpmerkingen(opmerkingen);
        jsonSchade.setSchadeNummerMaatschappij(schade.getSchadeNummerMaatschappij());
        jsonSchade.setSchadeNummerTussenPersoon(schade.getSchadeNummerTussenPersoon());
        if (schade.getSoortSchade() != null) {
            jsonSchade.setSoortSchade(schade.getSoortSchade().getOmschrijving());
        } else {
            jsonSchade.setSoortSchade(schade.getSoortSchadeOngedefinieerd());
        }
        if (schade.getStatusSchade() != null) {
            jsonSchade.setStatusSchade(schade.getStatusSchade().getStatus());
        }
        if (polis != null && polis.getId() != null) {
            jsonSchade.setPolis(polis.getId().toString());
        }

        LOGGER.debug("{}", jsonSchade);

        return jsonSchade;
    }
}
