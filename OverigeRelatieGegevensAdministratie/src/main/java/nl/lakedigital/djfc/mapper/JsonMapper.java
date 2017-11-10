package nl.lakedigital.djfc.mapper;

import org.springframework.stereotype.Component;

@Component
@FunctionalInterface
public interface JsonMapper {
    boolean isVoorMij(Object object);
}
