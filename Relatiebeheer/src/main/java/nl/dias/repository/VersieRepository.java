package nl.dias.repository;

import com.codahale.metrics.Timer;
import nl.dias.domein.Versie;
import nl.dias.domein.VersieGelezen;
import nl.dias.service.MetricsService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Repository
public class VersieRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersieRepository.class);

    @Autowired
    private SessionFactory sessionFactory;
    @Inject
    private MetricsService metricsService;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Session getEm() {
        return sessionFactory.getCurrentSession();
    }

    protected Transaction getTransaction() {
        Transaction transaction = getSession().getTransaction();
        if (transaction.getStatus() != TransactionStatus.ACTIVE) {
            transaction.begin();
        }

        return transaction;
    }

    public void opslaan(Versie versie) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", VersieRepository.class);

        getTransaction();

        getSession().save(versie);

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public void opslaan(VersieGelezen versieGelezen) {
        Timer.Context timer = metricsService.addTimerMetric("alleSchadesBijPolis", VersieRepository.class);

        getTransaction();

        getSession().save(versieGelezen);

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public List<Versie> getOngelezenVersies(Long gebruikerId) {
        Timer.Context timer = metricsService.addTimerMetric("getOngelezenVersies", VersieRepository.class);

        List<Versie> result = newArrayList();
        getTransaction();

        Query query = getSession().createQuery("select vg from VersieGelezen vg WHERE gebruiker = :gebruiker");
        query.setParameter("gebruiker", gebruikerId);

        List<Long> versieIds = query.list();

        for (Long id : versieIds) {
            Query versiesQuery = getSession().createQuery("select v from Versie v WHERE id = :id");
            versiesQuery.setParameter("id", id);

            result.add((Versie) versiesQuery.list().get(0));
        }

        getTransaction().commit();

        metricsService.stop(timer);

        return result;
    }
}
