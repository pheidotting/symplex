package nl.lakedigital.djfc.repository;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.domain.Taak;
import nl.lakedigital.djfc.domain.WijzigingTaak;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    protected Transaction getTransaction() {
        Transaction transaction = getSession().getTransaction();
        if (transaction.getStatus() != TransactionStatus.ACTIVE) {
            transaction.begin();
        }

        return transaction;
    }


    public void opslaan(WijzigingTaak wijzigingTaak) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", WijzigingTaakRepository.class);

        getTransaction();

        if (wijzigingTaak.getId() == null) {
            getSession().save(wijzigingTaak);
        } else {
            getSession().merge(wijzigingTaak);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public void verwijder(WijzigingTaak wijzigingTaak) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", WijzigingTaakRepository.class);

        getTransaction();

        getSession().delete(wijzigingTaak);

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public List<WijzigingTaak> allesBijTaak(Taak taak) {
        Timer.Context timer = metricsService.addTimerMetric("allesBijTaak", TaakRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("WijzigingTaak.zoekBijTaak");
        query.setParameter("taak", taak.getId());

        List<WijzigingTaak> result = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return result;
    }
}
