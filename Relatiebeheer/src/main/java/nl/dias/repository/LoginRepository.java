package nl.dias.repository;

import com.codahale.metrics.Timer;
import nl.dias.domein.LogIn;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;


@Repository
public class LoginRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRepository.class);

    @Autowired
    private SessionFactory sessionFactory;
    @Inject
    private MetricsService metricsService;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            LOGGER.trace("{}", e);
            return sessionFactory.openSession();
        }
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

    public void opslaan(LogIn logIn) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", LoginRepository.class);

        getTransaction();

        if (logIn.getId() == null) {
            getSession().save(logIn);
        } else {
            getSession().merge(logIn);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public void opruimen() {
        Timer.Context timer = metricsService.addTimerMetric("opruimen", LoginRepository.class);

        getTransaction();

        getSession().createQuery("delete from LogIn l where l.expireDatum < now()").executeUpdate();

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public List<Long> getIngelogdeGebruikers() {
        Timer.Context timer = metricsService.addTimerMetric("getIngelogdeGebruikers", LoginRepository.class);

        opruimen();

        getTransaction();

        Query query = getSession().createQuery("select gebruikerId from LogIn l where l.expireDatum > :datum group by gebruikerId");
        query.setParameter("datum", new Date());

        List<Long> ids = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return ids;
    }
}
