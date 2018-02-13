package nl.dias.repository;

import com.codahale.metrics.Timer;
import nl.dias.domein.Bedrijf;
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
public class BedrijfRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(BedrijfRepository.class);

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

    public List<Bedrijf> alleBedrijvenBijRelatie(Relatie relatie) {
        Timer.Context timer = metricsService.addTimerMetric("alleBedrijvenBijRelatie", BedrijfRepository.class);

        getTransaction();

        Query query = getEm().getNamedQuery("Bedrijf.allesBijRelatie");
        query.setParameter("relatie", relatie);

        List<Bedrijf> bedrijfs = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return bedrijfs;
    }

    public List<Bedrijf> zoekOpNaam(String zoekTerm) {
        Timer.Context timer = metricsService.addTimerMetric("zoekOpNaam", BedrijfRepository.class);

        getTransaction();

        Query query = getEm().getNamedQuery("Bedrijf.zoekOpNaam");
        query.setParameter("zoekTerm", "%" + zoekTerm + "%");

        List<Bedrijf> bedrijfs = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return bedrijfs;
    }

    public Bedrijf lees(Long id) {
        Timer.Context timer = metricsService.addTimerMetric("lees", BedrijfRepository.class);

        getTransaction();

        Bedrijf bedrijf = getEm().get(Bedrijf.class, id);

        getTransaction().commit();

        metricsService.stop(timer);

        return bedrijf;
    }

    public void opslaan(Bedrijf bedrijf) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", BedrijfRepository.class);

        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(bedrijf, ToStringStyle.SHORT_PREFIX_STYLE));
        if (bedrijf.getId() == null) {
            getSession().save(bedrijf);
        } else {
            getSession().merge(bedrijf);
        }

        metricsService.stop(timer);

        getTransaction().commit();
    }

    public void verwijder(Bedrijf bedrijf) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", BedrijfRepository.class);

        getTransaction();

        getEm().delete(bedrijf);

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public List<Bedrijf> alles() {
        Timer.Context timer = metricsService.addTimerMetric("alles", BedrijfRepository.class);

        getTransaction();

        Query query = getEm().getNamedQuery("Bedrijf.alles");

        List<Bedrijf> bedrijfs = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return bedrijfs;
    }
}
