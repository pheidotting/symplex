package nl.dias.web.mapper;

import nl.dias.domein.Aangifte;
import nl.dias.web.mapper.dozer.LocalDateDozerConverter;
import nl.dias.web.mapper.dozer.RelatieDozerMapper;
import nl.lakedigital.djfc.commons.json.JsonAangifte;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AangifteMapper extends Mapper<Aangifte, JsonAangifte> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AangifteMapper.class);

    @Override
    public Aangifte mapVanJson(JsonAangifte json) {
        LOGGER.debug("Map van " + ReflectionToStringBuilder.toString(json));
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(Aangifte.class, JsonAangifte.class).fields("datumAfgerond", "datumAfgerond", FieldsMappingOptions.customConverter(LocalDateDozerConverter.class)).fields("uitstelTot", "uitstelTot", FieldsMappingOptions.customConverter(LocalDateDozerConverter.class)).fields("relatie", "relatie", FieldsMappingOptions.customConverter(RelatieDozerMapper.class));
            }
        });

        Aangifte aangifte = mapper.map(json, Aangifte.class);
        LOGGER.debug("naar " + ReflectionToStringBuilder.toString(aangifte));
        return aangifte;
    }

    @Override
    public JsonAangifte mapNaarJson(Aangifte object) {
        LOGGER.debug("Map naar " + ReflectionToStringBuilder.toString(object));

        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(JsonAangifte.class, Aangifte.class).fields("datumAfgerond", "datumAfgerond", FieldsMappingOptions.customConverter(LocalDateDozerConverter.class)).fields("uitstelTot", "uitstelTot", FieldsMappingOptions.customConverter(LocalDateDozerConverter.class)).fields("relatie", "relatie", FieldsMappingOptions.customConverter(RelatieDozerMapper.class));
            }
        });

        JsonAangifte aangifte = mapper.map(object, JsonAangifte.class);
        if (object.getAfgerondDoor() != null) {
            aangifte.setAfgerondDoor(object.getAfgerondDoor().getNaam());
        }

        LOGGER.debug("naar " + ReflectionToStringBuilder.toString(aangifte));
        return aangifte;
    }
}