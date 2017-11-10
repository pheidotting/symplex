package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonSchade;
import nl.lakedigital.djfc.domain.Bedrag;
import nl.lakedigital.djfc.domain.Schade;
import nl.lakedigital.djfc.service.SchadeService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class JsonSchadeNaarSchadeMapper extends AbstractMapper<JsonSchade, Schade> implements JsonMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonSchadeNaarSchadeMapper.class);
    @Inject
    private SchadeService schadeService;

    @Override
    public Schade map(JsonSchade json, Object parent, Object bestaandObject) {
        String patternDatumTijd = "yyyy-MM-dd HH:mm";
        String patternDatum = "yyyy-MM-dd";

        LocalDate datumAfgehandeld = null;
        if (json.getDatumAfgehandeld() != null) {
            datumAfgehandeld = LocalDate.parse(json.getDatumAfgehandeld(), DateTimeFormat.forPattern(patternDatum));
        }

        Schade schade;
        if (json.getId() == null) {
            schade = new Schade();
        } else {
            schade = schadeService.lees(json.getId());
        }

        if (json.getDatumTijdMelding() != null) {
            LocalDateTime datumTijdMelding = LocalDateTime.parse(json.getDatumTijdMelding(), DateTimeFormat.forPattern(patternDatumTijd));
            schade.setDatumTijdMelding(datumTijdMelding);
        }
        if (json.getDatumTijdSchade() != null) {
            LocalDateTime datumTijdSchade = LocalDateTime.parse(json.getDatumTijdSchade(), DateTimeFormat.forPattern(patternDatumTijd));
            schade.setDatumTijdSchade(datumTijdSchade);
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

        LOGGER.debug("In : {}", ReflectionToStringBuilder.toString(json, ToStringStyle.SHORT_PREFIX_STYLE));
        LOGGER.debug("Uit : {}", ReflectionToStringBuilder.toString(schade, ToStringStyle.SHORT_PREFIX_STYLE));

        return schade;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof JsonSchade;
    }
}
