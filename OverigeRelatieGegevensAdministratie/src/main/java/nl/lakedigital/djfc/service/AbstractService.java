package nl.lakedigital.djfc.service;

import com.google.common.base.Predicate;
import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.lakedigital.djfc.messaging.sender.EntiteitenVerwijderdRequestSender;
import nl.lakedigital.djfc.repository.AbstractRepository;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;

public abstract class AbstractService<T extends AbstracteEntiteitMetSoortEnId> {
    private nl.lakedigital.as.messaging.domain.SoortEntiteit soortEntiteit;

    @Inject
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;
    @Inject
    private EntiteitenVerwijderdRequestSender entiteitenVerwijderdRequestSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);

    public abstract AbstractRepository getRepository();

    public AbstractService(nl.lakedigital.as.messaging.domain.SoortEntiteit soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public List<T> alles(SoortEntiteit soortEntiteit, Long parentId) {
        LOGGER.debug("alles soortEntiteit {} parentId {}", soortEntiteit, parentId);

        return getRepository().alles(soortEntiteit, parentId);
    }

    public T lees(Long id) {
        return (T) getRepository().lees(id);
    }

    public void opslaan(T adres) {
        getRepository().opslaan(newArrayList(adres));
    }

    public void opslaan(List<T> entiteiten) {
        getRepository().opslaan(entiteiten);

        List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds = new ArrayList<>();

        for (T t : entiteiten) {
            SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();

            soortEntiteitEnEntiteitId.setSoortEntiteit(this.soortEntiteit);
            soortEntiteitEnEntiteitId.setEntiteitId(t.getId());

            soortEntiteitEnEntiteitIds.add(soortEntiteitEnEntiteitId);
        }

        LOGGER.debug("Versturen {}", ReflectionToStringBuilder.toString(soortEntiteitEnEntiteitIds));
        entiteitenOpgeslagenRequestSender.send(soortEntiteitEnEntiteitIds);
    }

    public void opslaan(final List<T> entiteiten, SoortEntiteit soortEntiteit, Long entiteitId) {
        LOGGER.debug("Op te slaan entiteiten");
        for (T t : entiteiten) {
            LOGGER.debug(ReflectionToStringBuilder.toString(t, ToStringStyle.SHORT_PREFIX_STYLE));
        }

        LOGGER.debug("bestaande entiteiten");
        List<T> lijstBestaandeNummer = getRepository().alles(soortEntiteit, entiteitId);
        for (T t : lijstBestaandeNummer) {
            LOGGER.debug(ReflectionToStringBuilder.toString(t, ToStringStyle.SHORT_PREFIX_STYLE));
        }

        //Verwijderen wat niet (meer) voorkomt
        Iterable<T> teVerwijderen = filter(lijstBestaandeNummer, new Predicate<T>() {
            @Override
            public boolean apply(final T adres) {
                return newArrayList(filter(entiteiten, new Predicate<T>() {
                    @Override
                    public boolean apply(T t) {
                        return adres.getId().equals(t.getId());
                    }
                })).size() == 0;
            }
        });

        LOGGER.debug("Te verwijderen entiteiten");
        for (T t : teVerwijderen) {
            LOGGER.debug(ReflectionToStringBuilder.toString(t, ToStringStyle.SHORT_PREFIX_STYLE));
        }
        getRepository().verwijder(newArrayList(teVerwijderen));
        getRepository().opslaan(entiteiten);

        List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds = new ArrayList<>();

        for (T t : entiteiten) {
            SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();

            soortEntiteitEnEntiteitId.setSoortEntiteit(this.soortEntiteit);
            soortEntiteitEnEntiteitId.setEntiteitId(t.getId());

            soortEntiteitEnEntiteitIds.add(soortEntiteitEnEntiteitId);
        }

        for (T t : newArrayList(teVerwijderen)) {
            SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();

            soortEntiteitEnEntiteitId.setSoortEntiteit(this.soortEntiteit);
            soortEntiteitEnEntiteitId.setEntiteitId(t.getId());

            entiteitenVerwijderdRequestSender.send(soortEntiteitEnEntiteitId);
        }

        LOGGER.debug("Versturen {}", ReflectionToStringBuilder.toString(soortEntiteitEnEntiteitIds));
        entiteitenOpgeslagenRequestSender.send(soortEntiteitEnEntiteitIds);
    }

    public void verwijderen(SoortEntiteit soortEntiteit, Long entiteitId) {
        getRepository().verwijder(getRepository().alles(soortEntiteit, entiteitId));
    }

    public List<T> zoeken(String zoekTerm) {
        return getRepository().zoek(zoekTerm);
    }

}
