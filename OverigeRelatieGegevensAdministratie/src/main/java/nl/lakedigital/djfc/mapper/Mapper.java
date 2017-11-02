package nl.lakedigital.djfc.mapper;


import com.google.common.base.Predicate;
import nl.lakedigital.djfc.commons.json.AbstracteJsonEntiteitMetSoortEnId;
import nl.lakedigital.djfc.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getOnlyElement;

@Component
public class Mapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(Mapper.class);

    @Inject
    private List<JsonMapper> mappers;

    public <T> T map(final Object objectIn, final Class<T> clazz) {
        Object objectUit;

        LOGGER.debug("Mappen van {}", ReflectionToStringBuilder.toString(objectIn, ToStringStyle.SHORT_PREFIX_STYLE));

        JsonMapper mapper = getOnlyElement(filter(mappers, new Predicate<JsonMapper>() {
            @Override
            public boolean apply(JsonMapper jsonMapper) {
                return jsonMapper.isVoorMij(objectIn);
            }
        }));

        objectUit = ((AbstractMapper) mapper).map(objectIn);

        LOGGER.debug("mappen van {} naar {}", objectIn.getClass(), objectUit.getClass());
        LOGGER.debug("mappen van {} naar {}", objectIn.getClass(), objectUit.getClass());

        if (objectUit instanceof AbstracteEntiteitMetSoortEnId) {
            ((AbstracteEntiteitMetSoortEnId) objectUit).setSoortEntiteit(SoortEntiteit.valueOf(((AbstracteJsonEntiteitMetSoortEnId) objectIn).getSoortEntiteit()));
            ((AbstracteEntiteitMetSoortEnId) objectUit).setEntiteitId(((AbstracteJsonEntiteitMetSoortEnId) objectIn).getEntiteitId());
        }
        if (objectUit instanceof AbstracteJsonEntiteitMetSoortEnId) {
            ((AbstracteJsonEntiteitMetSoortEnId) objectUit).setSoortEntiteit(((AbstracteEntiteitMetSoortEnId) objectIn).getSoortEntiteit().name());
            ((AbstracteJsonEntiteitMetSoortEnId) objectUit).setEntiteitId(((AbstracteEntiteitMetSoortEnId) objectIn).getEntiteitId());
        }

        return clazz.cast(objectUit);
    }
}
