package nl.dias.repository;

import com.codahale.metrics.Timer;
import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.SoortSchade;
import nl.dias.domein.StatusSchade;
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
public class SchadeRepository {
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

    public List<SoortSchade> soortenSchade() {
        Timer.Context timer = metricsService.addTimerMetric("soortenSchade", SchadeRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("SoortSchade.alles");

        List<SoortSchade> lijst = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return lijst;
    }

    public List<SoortSchade> soortenSchade(String omschrijving) {
        Timer.Context timer = metricsService.addTimerMetric("soortenSchade", SchadeRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("SoortSchade.zoekOpOmschrijving");
        query.setParameter("omschrijving", "%" + omschrijving + "%");

        List<SoortSchade> lijst = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return lijst;
    }

    public StatusSchade getStatussen(String status) {
        Timer.Context timer = metricsService.addTimerMetric("getStatussen", SchadeRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("StatusSchade.zoekOpSoort");
        query.setParameter("status", status);

        StatusSchade statusSchade = (StatusSchade) query.uniqueResult();

        getTransaction().commit();

        metricsService.stop(timer);

        return statusSchade;
    }

    public List<StatusSchade> getStatussen() {
        Timer.Context timer = metricsService.addTimerMetric("getStatussen", SchadeRepository.class);

        getTransaction();

        Query query = getSession().createQuery("select s from StatusSchade s where s.ingebruik = '1'");

        List<StatusSchade> lijst = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return lijst;
    }

    public Schade zoekOpSchadeNummerMaatschappij(String schadeNummerMaatschappij) {
        Timer.Context timer = metricsService.addTimerMetric("zoekOpSchadeNummerMaatschappij", SchadeRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Schade.zoekOpschadeNummerMaatschappij");
        query.setParameter("schadeNummerMaatschappij", schadeNummerMaatschappij);

        Schade schade = (Schade) query.uniqueResult();

        getTransaction().commit();

        metricsService.stop(timer);

        return schade;
    }

    public List<Schade> alleSchadesBijRelatie(Relatie relatie) {
        Timer.Context timer = metricsService.addTimerMetric("alleSchadesBijRelatie", SchadeRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Schade.allesVanRelatie");
        query.setParameter("relatie", relatie);

        List<Schade> lijst = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return lijst;
    }

    public List<Schade> allesBijPolis(Long polis) {
        Timer.Context timer = metricsService.addTimerMetric("allesBijPolis", SchadeRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Schade.allesBijPolis");
        query.setParameter("polis", polis);


        List<Schade> lijst = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return lijst;
    }

    public Schade lees(Long id) {
        Timer.Context timer = metricsService.addTimerMetric("lees", SchadeRepository.class);

        getTransaction();

        Schade schade = getSession().get(Schade.class, id);

        getTransaction().commit();

        metricsService.stop(timer);

        return schade;
    }

    public void opslaan(Schade schade) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", SchadeRepository.class);

        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(schade, ToStringStyle.SHORT_PREFIX_STYLE));
        if (schade.getId() == null) {
            getSession().save(schade);
        } else {
            getSession().merge(schade);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public void verwijder(Schade schade) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", SchadeRepository.class);

        getTransaction();

        getSession().delete(schade);

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public void verwijder(List<Schade> schades) {
        Timer.Context timer = metricsService.addTimerMetric("setDiscriminatorValue", SchadeRepository.class);

        getTransaction();

        for (Schade schade : schades) {
            getSession().delete(schade);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public List<Schade> alles() {
        Timer.Context timer = metricsService.addTimerMetric("alles", SchadeRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Schade.alles");

        List<Schade> schades = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return schades;
    }

    public List<Schade> alleSchadesBijPolis(Long polis) {
        Timer.Context timer = metricsService.addTimerMetric("alleSchadesBijPolis", SchadeRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Schade.allesBijPolis");
        query.setParameter("polis", polis);

        List<Schade> schades = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return schades;
    }
}
