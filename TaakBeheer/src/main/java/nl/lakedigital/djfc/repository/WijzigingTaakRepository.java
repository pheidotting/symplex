package nl.lakedigital.djfc.repository;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.domain.Taak;
import nl.lakedigital.djfc.metrics.MetricsService;
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
public class WijzigingTaakRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(WijzigingTaakRepository.class);

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
    public void opslaan(Taak taak) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", WijzigingTaakRepository.class);

        if (taak.getId() == null) {
            getSession().save(taak);
        } else {
            getSession().merge(taak);
        }
        metricsService.stop(timer);
    }
}
