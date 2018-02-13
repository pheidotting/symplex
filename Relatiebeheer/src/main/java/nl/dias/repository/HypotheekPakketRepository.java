package nl.dias.repository;

import com.codahale.metrics.Timer;
import nl.dias.domein.HypotheekPakket;
import nl.dias.domein.Relatie;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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

@Repository
public class HypotheekPakketRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(HypotheekPakketRepository.class);

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

    public List<HypotheekPakket> allesVanRelatie(Relatie relatie) {
        Timer.Context timer = metricsService.addTimerMetric("allesVanRelatie", HypotheekPakketRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("HypotheekPakket.allesVanRelatie");
        query.setParameter("relatie", relatie);

        List<HypotheekPakket> hypotheekPakkets = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return hypotheekPakkets;
    }

    public HypotheekPakket lees(Long id) {
        Timer.Context timer = metricsService.addTimerMetric("lees", HypotheekPakketRepository.class);

        getTransaction();

        HypotheekPakket hypotheekPakket = getSession().get(HypotheekPakket.class, id);

        getTransaction().commit();

        metricsService.stop(timer);

        return hypotheekPakket;
    }

    public void opslaan(HypotheekPakket hypotheekPakket) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", HypotheekPakketRepository.class);

        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(hypotheekPakket, ToStringStyle.SHORT_PREFIX_STYLE));
        if (hypotheekPakket.getId() == null) {
            getSession().save(hypotheekPakket);
        } else {
            getSession().merge(hypotheekPakket);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public void verwijder(HypotheekPakket hypotheekPakket) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", HypotheekPakketRepository.class);

        getTransaction();

        getSession().delete(hypotheekPakket);

        getTransaction().commit();

        metricsService.stop(timer);
    }
}
