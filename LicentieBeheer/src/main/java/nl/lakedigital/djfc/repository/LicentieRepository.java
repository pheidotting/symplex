package nl.lakedigital.djfc.repository;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.commons.domain.Licentie;
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
public class LicentieRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(LicentieRepository.class);

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

    //@Transactional
    public void verwijder(Licentie identificatie) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", LicentieRepository.class);

        LOGGER.debug("Verwijder licentie met Id {}", identificatie.getId());

        getTransaction();

        getSession().delete(identificatie);

        getTransaction().commit();

        metricsService.stop(timer);
    }

    //@Transactional
    public void opslaan(Licentie licentie) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", LicentieRepository.class);

        getTransaction();

        if (licentie.getId() == null) {
            LOGGER.debug("Save");
            getSession().save(licentie);
        } else {
            getSession().merge(licentie);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    //@Transactional
    public List<Licentie> alleLicenties(Long kantoorId) {
        Timer.Context timer = metricsService.addTimerMetric("actieveLicentie", LicentieRepository.class);

        LOGGER.debug("Ophalen actieve licentie");

        getTransaction();

        Query query = getSession().getNamedQuery("Licentie.alleLicenties");
        query.setParameter("kantoor", kantoorId);

        List<Licentie> result = query.list();

        getTransaction().commit();

        result.sort((o1, o2) -> o2.getStartDatum().compareTo(o1.getStartDatum()));

        metricsService.stop(timer);

        return result;
    }

}
