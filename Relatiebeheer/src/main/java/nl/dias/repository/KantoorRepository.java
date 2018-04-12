package nl.dias.repository;

import com.codahale.metrics.Timer;
import nl.dias.domein.Kantoor;
import nl.dias.exception.BsnNietGoedException;
import nl.dias.exception.IbanNietGoedException;
import nl.dias.exception.PostcodeNietGoedException;
import nl.dias.exception.TelefoonnummerNietGoedException;
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
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Repository
public class KantoorRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorRepository.class);

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

    protected Transaction getTransaction() {
        Transaction transaction = getSession().getTransaction();
        if (transaction.getStatus() != TransactionStatus.ACTIVE) {
            transaction.begin();
        }

        return transaction;
    }

    @Transactional
    public void opslaan(Kantoor kantoor) throws PostcodeNietGoedException, TelefoonnummerNietGoedException, BsnNietGoedException, IbanNietGoedException {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", KantoorRepository.class);

        getTransaction();

        if (kantoor.getId() == null) {
            getSession().persist(kantoor);
        } else {
            getSession().merge(kantoor);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public Kantoor getIngelogdKantoor() {
        return lees(1L);
    }

    public Kantoor lees(Long id) {
        Timer.Context timer = metricsService.addTimerMetric("lees", KantoorRepository.class);

        getTransaction();

        Kantoor kantoor = getSession().get(Kantoor.class, id);

        getTransaction().commit();

        metricsService.stop(timer);

        return kantoor;
    }


    public void verwijder(Kantoor kantoor) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", KantoorRepository.class);

        getTransaction();

        getSession().delete(kantoor);

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public List<Kantoor> alles() {
        Timer.Context timer = metricsService.addTimerMetric("alles", KantoorRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Kantoor.alles");

        List<Kantoor> kantoors = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return kantoors;
    }

    public List<Kantoor> zoekOpAfkorting(String afkorting) {
        Timer.Context timer = metricsService.addTimerMetric("zoekOpAfkorting", KantoorRepository.class);

        getTransaction();

        LOGGER.trace("zoekOpAfkorting {}", afkorting);
        Query query = getSession().getNamedQuery("Kantoor.zoekOpAfkorting");
        query.setParameter("afkorting", afkorting);

        List<Kantoor> kantoors = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return kantoors;
    }

}
