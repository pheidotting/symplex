package nl.dias.repository;

import com.codahale.metrics.Timer;
import nl.dias.domein.Hypotheek;
import nl.dias.domein.Relatie;
import nl.dias.domein.SoortHypotheek;
import nl.dias.service.MetricsService;
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
public class HypotheekRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(HypotheekRepository.class);

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

    public List<SoortHypotheek> alleSoortenHypotheekInGebruik() {
        Timer.Context timer = metricsService.addTimerMetric("alleSoortenHypotheekInGebruik", HypotheekRepository.class);

        LOGGER.debug("Ophalen alleSoortenHypotheekInGebruik");
        getTransaction();

        Query query = getSession().getNamedQuery("SoortHypotheek.allesInGebruik");

        List<SoortHypotheek> soortHypotheeks = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return soortHypotheeks;
    }

    public SoortHypotheek leesSoortHypotheek(Long id) {
        Timer.Context timer = metricsService.addTimerMetric("leesSoortHypotheek", HypotheekRepository.class);

        getTransaction();

        SoortHypotheek result = getSession().get(SoortHypotheek.class, id);

        getTransaction().commit();

        metricsService.stop(timer);

        return result;
    }

    public Hypotheek lees(Long id) {
        Timer.Context timer = metricsService.addTimerMetric("lees", HypotheekRepository.class);

        getTransaction();

        Hypotheek result = (Hypotheek) getSession().get(Hypotheek.class, id);

        getTransaction().commit();

        metricsService.stop(timer);

        return result;
    }

    public List<Hypotheek> allesVanRelatie(Relatie relatie) {
        Timer.Context timer = metricsService.addTimerMetric("allesVanRelatie", HypotheekRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Hypotheek.allesVanRelatie");
        query.setParameter("relatie", relatie.getId());

        List<Hypotheek> hypotheeks = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return hypotheeks;
    }

    public List<Hypotheek> allesVanRelatieInEenPakket(Relatie relatie) {
        Timer.Context timer = metricsService.addTimerMetric("allesVanRelatieInEenPakket", HypotheekRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Hypotheek.allesVanRelatieInEenPakket");
        query.setParameter("relatie", relatie);

        List<Hypotheek> hypotheeks = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return hypotheeks;
    }

    public List<Hypotheek> allesVanRelatieInclDePakketten(Relatie relatie) {
        Timer.Context timer = metricsService.addTimerMetric("allesVanRelatieInclDePakketten", HypotheekRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Hypotheek.allesVanRelatieInclDePakketten");
        query.setParameter("relatie", relatie);

        List<Hypotheek> hypotheeks = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return hypotheeks;
    }

    public void opslaan(Hypotheek hypotheek) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", HypotheekRepository.class);

        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(hypotheek, ToStringStyle.SHORT_PREFIX_STYLE));
        if (hypotheek.getId() == null) {
            getSession().save(hypotheek);
        } else {
            getSession().merge(hypotheek);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public void verwijder(Hypotheek hypotheek) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", HypotheekRepository.class);

        getTransaction();

        getSession().delete(hypotheek);

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public void verwijder(List<Hypotheek> hypotheken) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", HypotheekRepository.class);

        getTransaction();

        for (Hypotheek hypotheek : hypotheken) {
            getSession().delete(hypotheek);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public List<Hypotheek> alles() {
        Timer.Context timer = metricsService.addTimerMetric("alles", HypotheekRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Hypotheek.alles");

        List<Hypotheek> hypotheeks = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return hypotheeks;
    }

}
