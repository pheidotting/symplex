package nl.lakedigital.djfc.repository;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.commons.domain.Identificatie;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Repository
public class IdentificatieRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdentificatieRepository.class);

    @Autowired
    private SessionFactory sessionFactory;
    @Inject
    private MetricsService metricsService;

    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {//NOSONAR
            return sessionFactory.openSession();
        }
    }

    @Transactional
    public void verwijder(Identificatie identificatie) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", IdentificatieRepository.class);

        LOGGER.debug("Verwijder {}", ReflectionToStringBuilder.toString(identificatie));

        getSession().delete(identificatie);

        metricsService.stop(timer);
    }

    @Transactional
    public void verwijder(List<Identificatie> identificaties) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", IdentificatieRepository.class);

        for (Identificatie identificatie : identificaties) {
            LOGGER.debug("Verwijder {}",identificatie);

            getSession().delete(identificatie);
        }

        metricsService.stop(timer);
    }

    @Transactional
    public void opslaan(Identificatie identificatie) {
        opslaan(newArrayList(identificatie));
    }

    @Transactional
    public void opslaan(List<Identificatie> identificaties) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", IdentificatieRepository.class);

        LOGGER.debug("Opslaan {} identificaties",identificaties.size());
        for (Identificatie identificatie : identificaties) {
            LOGGER.debug("{}",identificatie);
            if (identificatie.getId() == null) {
                identificatie.nieuweIdentificatieCode();

                while (komtCodeAlVoor(identificatie.getIdentificatie())) {
                    identificatie.nieuweIdentificatieCode();
                }
                getSession().save(identificatie);
            } else {
                getSession().merge(identificatie);
            }
        }

        metricsService.stop(timer);
    }

    private boolean komtCodeAlVoor(String identificatieCode) {
        Timer.Context timer = metricsService.addTimerMetric("komtCodeAlVoor", IdentificatieRepository.class);

        LOGGER.debug("trace {} al voor?", identificatieCode);
        Query query = getSession().getNamedQuery("Identificatie.zoekOpIdentificatieCode");
        query.setParameter("identificatieCode", identificatieCode);

        metricsService.stop(timer);

        return  !query.list().isEmpty();
    }

    @Transactional(readOnly = true)
    public Identificatie zoekOpIdentificatieCode(String identificatieCode) {
        Timer.Context timer = metricsService.addTimerMetric("zoekOpIdentificatieCode", IdentificatieRepository.class);

        LOGGER.trace("Komt {} al voor?", identificatieCode);
        Query query = getSession().getNamedQuery("Identificatie.zoekOpIdentificatieCode");
        query.setParameter("identificatieCode", identificatieCode);

        List<Identificatie> identificaties = query.list();

        metricsService.stop(timer);
        if (!identificaties.isEmpty()) {
            LOGGER.trace("Gevonden Identificatie {}", identificaties.get(0).toString());
            return identificaties.get(0);
        } else {
            LOGGER.trace("Geen gevonden Identificatie");
            return null;
        }
    }

    @Transactional(readOnly = true)
    public Identificatie zoek(String soortEntiteit, Long entiteitId) {
        Timer.Context timer = metricsService.addTimerMetric("zoek", IdentificatieRepository.class);

        Query query = getSession().getNamedQuery("Identificatie.zoek");
        query.setParameter("soortEntiteit", soortEntiteit);
        query.setParameter("entiteitId", entiteitId);

        List<Identificatie> identificaties=query.list();

        metricsService.stop(timer);
        if(!identificaties.isEmpty()){
            LOGGER.trace("Gevonden Identificatie {}", identificaties.get(0).toString());
            return identificaties.get(0);
        }else{
            LOGGER.trace("Geen gevonden Identificatie");
            return null;
        }
    }
}
