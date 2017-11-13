package nl.lakedigital.djfc.mapper;

import org.springframework.stereotype.Component;
@FunctionalInterface
@Component
public interface JsonMapper {
    boolean isVoorMij(Object object);

}
