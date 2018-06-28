package nl.lakedigital.djfc.repository;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.Taak;
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
public class TaakRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaakRepository.class);

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

    //    @Transactional
    public void opslaan(Taak taak) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", TaakRepository.class);

        getTransaction();

        if (taak.getId() == null) {
            getEm().save(taak);
        } else {
            getEm().merge(taak);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public List<Taak> alleTaken(SoortEntiteit soortEntiteit, Long entiteitId) {
        Timer.Context timer = metricsService.addTimerMetric("alleTaken", TaakRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Taak.zoekOpSoortEntiteitEnEntiteitId");
        query.setParameter("entiteitId", entiteitId);
        query.setParameter("soortEntiteit", soortEntiteit);

        List<Taak> result = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return result;
    }

    public List<Taak> allesOpenstaand() {
        Timer.Context timer = metricsService.addTimerMetric("allesOpenstaand", TaakRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Taak.allesOpenstaand");

        List<Taak> result = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return result;
    }

    public Taak lees(Long id) {
        Timer.Context timer = metricsService.addTimerMetric("lees", TaakRepository.class);

        getTransaction();

        Taak taak = getSession().get(Taak.class, id);

        getTransaction().commit();

        metricsService.stop(timer);

        return taak;
    }

    public void verwijder(Taak taak) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", TaakRepository.class);

        getTransaction();

        getSession().delete(taak);

        getTransaction().commit();

        metricsService.stop(timer);
    }
}
