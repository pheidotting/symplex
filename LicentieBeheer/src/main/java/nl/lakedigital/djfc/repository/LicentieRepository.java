package nl.lakedigital.djfc.repository;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.domain.Licentie;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Repository
public class LicentieRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(LicentieRepository.class);

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
    public void verwijder(Licentie identificatie) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", LicentieRepository.class);

        LOGGER.debug("Verwijder {}", ReflectionToStringBuilder.toString(identificatie));

        getSession().delete(identificatie);

        metricsService.stop(timer);
    }

    @Transactional
    public void opslaan(Licentie licentie) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", LicentieRepository.class);

        LOGGER.debug("{}", ReflectionToStringBuilder.toString(licentie));
        if (licentie.getId() == null) {
            LOGGER.debug("Save");
            getSession().save(licentie);
        } else {
            getSession().merge(licentie);
        }
        metricsService.stop(timer);
    }

}
