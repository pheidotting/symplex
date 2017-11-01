package nl.dias.mapper;


import com.google.common.base.Predicate;
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
    private List<AbstractMapper> mappers;

    public <T> T map(final Object objectIn, final Class<T> clazz) {
        return map(objectIn, clazz, null, null);
    }

    public <T> T map(final Object objectIn, final Class<T> clazz, Object parent, final Object bestaandObject) {
        Object objectUit;

        LOGGER.debug("Mappen van {}", ReflectionToStringBuilder.toString(objectIn, ToStringStyle.SHORT_PREFIX_STYLE));

        AbstractMapper mapper = getOnlyElement(filter(mappers, new Predicate<AbstractMapper>() {
            @Override
            public boolean apply(AbstractMapper abstractMapper) {
                return abstractMapper.isVoorMij(objectIn);
            }
        }));

        objectUit = mapper.map(objectIn, parent, bestaandObject);

        LOGGER.debug("mappen van {} naar {}", objectIn.getClass(), objectUit.getClass());

        return clazz.cast(objectUit);
    }
}
