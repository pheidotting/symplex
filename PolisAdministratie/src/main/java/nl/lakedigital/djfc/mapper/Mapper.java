package nl.lakedigital.djfc.mapper;


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
        if (objectIn != null) {
            Object objectUit;

            LOGGER.debug("Mappen van {}", ReflectionToStringBuilder.toString(objectIn, ToStringStyle.SHORT_PREFIX_STYLE));

            JsonMapper mapper = getOnlyElement(filter(mappers, jsonMapper -> jsonMapper.isVoorMij(objectIn)));

            objectUit = ((AbstractMapper) mapper).map(objectIn);

            LOGGER.debug("mappen van {} naar {}", objectIn.getClass(), objectUit.getClass());
            LOGGER.debug("mappen van {} naar {}", objectIn.getClass(), objectUit.getClass());

            return clazz.cast(objectUit);
        }
        return null;
    }
}
