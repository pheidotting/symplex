package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonSchade;
import nl.lakedigital.djfc.domain.Schade;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SchadeNaarJsonSchadeMapper extends AbstractMapper<Schade, JsonSchade> implements JsonMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeNaarJsonSchadeMapper.class);

    String patternDatumTijd = "yyyy-MM-dd'T'HH:mm";
    String patternDatum = "yyyy-MM-dd";

    @Override
    public JsonSchade map(Schade schade, Object parent, Object bestaandObject) {
        JsonSchade jsonSchade = new JsonSchade();

        jsonSchade.setPolis(String.valueOf(schade.getPolis()));
        if (schade.getDatumAfgehandeld() != null) {
            jsonSchade.setDatumAfgehandeld(schade.getDatumAfgehandeld().toString(patternDatum));
        }
        if (schade.getDatumTijdMelding() != null) {
            jsonSchade.setDatumTijdMelding(schade.getDatumTijdMelding().toString(patternDatumTijd));
        }
        if (schade.getDatumTijdSchade() != null) {
            jsonSchade.setDatumTijdSchade(schade.getDatumTijdSchade().toString(patternDatumTijd));
        }
        if (schade.getEigenRisico() != null) {
            jsonSchade.setEigenRisico(schade.getEigenRisico().getBedrag().toString());
        }
        jsonSchade.setId(schade.getId());
        jsonSchade.setLocatie(schade.getLocatie());
        jsonSchade.setOmschrijving(schade.getOmschrijving());
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
        jsonSchade.setPolis(jsonSchade.getPolis());

        LOGGER.debug("In: {}", ReflectionToStringBuilder.toString(schade, ToStringStyle.SHORT_PREFIX_STYLE));
        LOGGER.debug("Uit: {}", ReflectionToStringBuilder.toString(jsonSchade, ToStringStyle.SHORT_PREFIX_STYLE));

        return jsonSchade;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof Schade;
    }
}
