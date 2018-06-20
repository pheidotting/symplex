package nl.lakedigital.djfc.repository;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.domain.Taak;
import nl.lakedigital.djfc.domain.WijzigingTaak;
import nl.lakedigital.djfc.metrics.MetricsService;
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
    public void opslaan(WijzigingTaak wijzigingTaak) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", WijzigingTaakRepository.class);

        if (wijzigingTaak.getId() == null) {
            getSession().save(wijzigingTaak);
        } else {
            getSession().merge(wijzigingTaak);
        }
        metricsService.stop(timer);
    }

    @Transactional
    public void verwijder(WijzigingTaak wijzigingTaak) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", WijzigingTaakRepository.class);

        getSession().delete(wijzigingTaak);

        metricsService.stop(timer);
    }

    @Transactional
    public List<WijzigingTaak> allesBijTaak(Taak taak) {
        Timer.Context timer = metricsService.addTimerMetric("allesBijTaak", TaakRepository.class);

        Query query = getSession().getNamedQuery("WijzigingTaak.zoekBijTaak");
        query.setParameter("taak", taak.getId());

        List<WijzigingTaak> result = query.list();

        metricsService.stop(timer);

        return result;
    }
}
