package nl.lakedigital.djfc.repository;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.List;

public class AbstractRepository<T extends AbstracteEntiteitMetSoortEnId> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRepository.class);

    @Autowired
    private SessionFactory sessionFactory;
    @Inject
    private MetricsService metricsService;

    private final Class<T> type;

    public AbstractRepository(Class<T> type) {
        this.type = type;
    }

    private String getMyType() {
        return this.type.getSimpleName();
    }

    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            LOGGER.trace("{}", e);
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

    public void verwijder(List<T> adressen) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", AbstractRepository.class);

        if (getTransaction().getStatus() != TransactionStatus.ACTIVE) {
            getTransaction().begin();
        }

        for (T adres : adressen) {
            getSession().delete(adres);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public void opslaan(List<T> adressen) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", AbstractRepository.class);

        if (getTransaction().getStatus() != TransactionStatus.ACTIVE) {
            getTransaction().begin();
        }

        for (T t : adressen) {
            LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(t, ToStringStyle.SHORT_PREFIX_STYLE));
            if (t.getId() == null) {
                LOGGER.info("save");
                getSession().save(t);
            } else {
                LOGGER.info("merge");
                getSession().merge(t);
            }
        }

        getTransaction().commit();
        getSession().close();

        metricsService.stop(timer);
    }

    public T lees(Long id) {
        Timer.Context timer = metricsService.addTimerMetric("lees", AbstractRepository.class);

        getTransaction().begin();

        T t = getSession().get(type, id);

        getTransaction().commit();

        metricsService.stop(timer);

        return t;
    }

    public List<T> zoek(String zoekTerm) {
        Timer.Context timer = metricsService.addTimerMetric("zoek", AbstractRepository.class);

        getTransaction().begin();

        Query query = getSession().getNamedQuery(getMyType() + ".zoeken");
        query.setParameter("zoekTerm", "%" + zoekTerm + "%");

        List<T> lijst = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return lijst;
    }

    public List<T> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
        Timer.Context timer = metricsService.addTimerMetric("alles", AbstractRepository.class);

        getTransaction().begin();

        Query query = getSession().getNamedQuery(getMyType() + ".zoekBijEntiteit");
        query.setParameter("soortEntiteit", soortEntiteit);
        query.setParameter("entiteitId", entiteitId);

        List<T> lijst = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return lijst;
    }

    public void verwijder(T t){
        Timer.Context timer = metricsService.addTimerMetric("verwijder", AbstractRepository.class);

        getTransaction().begin();

        getSession().delete(t);

        getTransaction().commit();

        metricsService.stop(timer);
    }
}
