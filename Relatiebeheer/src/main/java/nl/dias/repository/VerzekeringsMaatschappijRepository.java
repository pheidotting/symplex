package nl.dias.repository;

import com.codahale.metrics.Timer;
import nl.dias.domein.VerzekeringsMaatschappij;
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
public class VerzekeringsMaatschappijRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeRepository.class);

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

    public VerzekeringsMaatschappij zoekOpNaam(String naam) {
        Timer.Context timer = metricsService.addTimerMetric("zoekOpNaam", VerzekeringsMaatschappijRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("VerzekeringsMaatschappij.zoekOpNaam");
        query.setParameter("naam", naam);

        VerzekeringsMaatschappij verzekeringsMaatschappij = (VerzekeringsMaatschappij) query.uniqueResult();

        getTransaction().commit();

        return verzekeringsMaatschappij;
    }

    public VerzekeringsMaatschappij lees(Long id) {
        Timer.Context timer = metricsService.addTimerMetric("lees", VerzekeringsMaatschappijRepository.class);

        getTransaction();

        VerzekeringsMaatschappij verzekeringsMaatschappij = getSession().get(VerzekeringsMaatschappij.class, id);

        getTransaction().commit();

        metricsService.stop(timer);

        return verzekeringsMaatschappij;
    }

    public void opslaan(VerzekeringsMaatschappij verzekeringsMaatschappij) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", VerzekeringsMaatschappijRepository.class);

        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(verzekeringsMaatschappij, ToStringStyle.SHORT_PREFIX_STYLE));
        if (verzekeringsMaatschappij.getId() == null) {
            getSession().save(verzekeringsMaatschappij);
        } else {
            getSession().merge(verzekeringsMaatschappij);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public void verwijder(VerzekeringsMaatschappij verzekeringsMaatschappij) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", VerzekeringsMaatschappijRepository.class);

        getTransaction();

        getSession().delete(verzekeringsMaatschappij);

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public List<VerzekeringsMaatschappij> alles() {
        Timer.Context timer = metricsService.addTimerMetric("alles", VerzekeringsMaatschappijRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("VerzekeringsMaatschappij.zoekAlles");

        List<VerzekeringsMaatschappij> verzekeringsMaatschappijs = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return verzekeringsMaatschappijs;
    }
}

