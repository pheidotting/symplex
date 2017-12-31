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

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;

public abstract class AbstractService<T extends AbstracteEntiteitMetSoortEnId> {
    private nl.lakedigital.as.messaging.domain.SoortEntiteit soortEntiteit;

    @Inject
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;
    @Inject
    private EntiteitenVerwijderdRequestSender entiteitenVerwijderdRequestSender;

    public abstract AbstractRepository getRepository();

    public abstract Logger getLogger();

    public AbstractService(nl.lakedigital.as.messaging.domain.SoortEntiteit soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public List<T> alles(SoortEntiteit soortEntiteit, Long parentId) {
        getLogger().debug("alles soortEntiteit {} parentId {}", soortEntiteit, parentId);

        return getRepository().alles(soortEntiteit, parentId);
    }

    public T lees(Long id) {
        return (T) getRepository().lees(id);
    }

    public void opslaan(T adres) {
        if (isGevuld(adres)) {

            getRepository().opslaan(newArrayList(adres));
        }
    }

    public void opslaan(List<T> entiteitenIn) {
        List<T> entiteiten = entiteitenIn.stream().filter(new java.util.function.Predicate<T>() {
            @Override
            public boolean test(T t) {
                return isGevuld(t);
            }
        }).collect(Collectors.toList());

        getRepository().opslaan(entiteiten);

        List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds = new ArrayList<>();

        for (T t : entiteiten) {
            SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();

            soortEntiteitEnEntiteitId.setSoortEntiteit(this.soortEntiteit);
            soortEntiteitEnEntiteitId.setEntiteitId(t.getId());

            soortEntiteitEnEntiteitIds.add(soortEntiteitEnEntiteitId);
        }

        getLogger().debug("Versturen {}", ReflectionToStringBuilder.toString(soortEntiteitEnEntiteitIds));
        entiteitenOpgeslagenRequestSender.send(soortEntiteitEnEntiteitIds);
    }

    public void opslaan(final List<T> entiteitenIn, SoortEntiteit soortEntiteit, Long entiteitId) {
        List<T> entiteiten = entiteitenIn.stream().filter(new java.util.function.Predicate<T>() {
            @Override
            public boolean test(T t) {
                return isGevuld(t);
            }
        }).collect(Collectors.toList());

        getLogger().debug("Op te slaan entiteiten");
        for (T t : entiteiten) {
            getLogger().debug(ReflectionToStringBuilder.toString(t, ToStringStyle.SHORT_PREFIX_STYLE));
        }

        getLogger().debug("bestaande entiteiten");
        List<T> lijstBestaandeNummer = getRepository().alles(soortEntiteit, entiteitId);
        for (T t : lijstBestaandeNummer) {
            getLogger().debug(ReflectionToStringBuilder.toString(t, ToStringStyle.SHORT_PREFIX_STYLE));
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
                })).isEmpty();
            }
        });

        getLogger().debug("Te verwijderen entiteiten");
        for (T t : teVerwijderen) {
            getLogger().debug(ReflectionToStringBuilder.toString(t, ToStringStyle.SHORT_PREFIX_STYLE));
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

        getLogger().debug("Versturen {}", ReflectionToStringBuilder.toString(soortEntiteitEnEntiteitIds));
        entiteitenOpgeslagenRequestSender.send(soortEntiteitEnEntiteitIds);
    }

    public void verwijderen(SoortEntiteit soortEntiteit, Long entiteitId) {
        getRepository().verwijder(getRepository().alles(soortEntiteit, entiteitId));
    }

    public List<T> zoeken(String zoekTerm) {
        return getRepository().zoek(zoekTerm);
    }

    public abstract boolean isGevuld(T t);
}
