package nl.dias.repository;

import com.codahale.metrics.Timer;
import nl.dias.domein.Versie;
import nl.dias.domein.VersieGelezen;
import nl.lakedigital.djfc.metrics.MetricsService;
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

        LOGGER.debug("opslaan");

        getTransaction();

        getSession().save(versie);

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public void opslaan(VersieGelezen versieGelezen) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", VersieRepository.class);

        LOGGER.debug("opslaan");

        getTransaction();

        getSession().save(versieGelezen);

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public List<Versie> getOngelezenVersies(Long gebruikerId) {
        Timer.Context timer = metricsService.addTimerMetric("getOngelezenVersies", VersieRepository.class);

        LOGGER.debug("getOngelezenVersies");

        List<Versie> result = newArrayList();
        getTransaction();

        Query query = getSession().createQuery("select vg from VersieGelezen vg WHERE gebruiker = :gebruiker");
        query.setParameter("gebruiker", gebruikerId);

        List<VersieGelezen> versieIds = query.list();

        for (VersieGelezen versieGelezen : versieIds) {
            Query versiesQuery = getSession().createQuery("select v from Versie v WHERE id = :id");
            versiesQuery.setParameter("id", versieGelezen.getVersie());

            List<Versie> versies = versiesQuery.list();
            if (!versies.isEmpty()) {
                result.add(versies.get(0));

                getSession().createQuery("delete from VersieGelezen WHERE gebruiker = :gebruiker AND versie = :versie").setParameter("gebruiker", gebruikerId).setParameter("versie", versieGelezen.getVersie()).executeUpdate();
            }
        }

        getTransaction().commit();

        metricsService.stop(timer);

        return result;
    }

    public Versie lees(Long id) {
        Timer.Context timer = metricsService.addTimerMetric("lees", VersieRepository.class);

        LOGGER.debug("lees");

        getTransaction();

        Versie result = getSession().get(Versie.class, id);

        getTransaction().commit();

        metricsService.stop(timer);

        return result;
    }
}
