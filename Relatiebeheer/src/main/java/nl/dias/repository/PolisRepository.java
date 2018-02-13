package nl.dias.repository;

import com.codahale.metrics.Timer;
import nl.dias.domein.Kantoor;
import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.domein.polis.Polis;
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
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Repository
public class PolisRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisRepository.class);

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

    public List<Polis> zoekPolissenOpSoort(Class<?> soort) {
        Timer.Context timer = metricsService.addTimerMetric("zoekPolissenOpSoort", PolisRepository.class);

        getTransaction();

        Query query = getEm().createQuery("select e from " + soort.getSimpleName() + " e");
        List<Polis> ret = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return ret;
    }

    @Transactional
    public List<Polis> allePolissenBijMaatschappij(VerzekeringsMaatschappij maatschappij) {
        Timer.Context timer = metricsService.addTimerMetric("allePolissenBijMaatschappij", PolisRepository.class);

        getTransaction();

        Query query = getEm().getNamedQuery("Polis.allesBijMaatschappij");
        query.setParameter("maatschappij", maatschappij);

        List<Polis> polissen = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return polissen;
    }

    public List<Polis> allePolissenBijRelatie(Long relatie) {
        Timer.Context timer = metricsService.addTimerMetric("allePolissenBijRelatie", PolisRepository.class);

        getTransaction();

        Query query = getEm().getNamedQuery("Polis.allesVanRelatie");
        query.setParameter("relatie", relatie);

        List<Polis> polissen = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return polissen;
    }

    public List<Polis> allePolissenBijBedrijf(Long bedrijf) {
        Timer.Context timer = metricsService.addTimerMetric("allePolissenBijBedrijf", PolisRepository.class);

        getTransaction();

        Query query = getEm().getNamedQuery("Polis.allesVanBedrijf");
        query.setParameter("bedrijf", bedrijf);

        List<Polis> polissen = query.list();

        LOGGER.debug("Opgehaald {} polissen bij bedrijf met id {}", polissen.size(), bedrijf);

        getTransaction().commit();

        metricsService.stop(timer);

        return polissen;
    }

    @Transactional
    public Polis zoekOpPolisNummer(String PolisNummer, Kantoor kantoor) {
        Timer.Context timer = metricsService.addTimerMetric("zoekOpPolisNummer", PolisRepository.class);

        getTransaction();

        Query query = getEm().getNamedQuery("Polis.zoekOpPolisNummer");
        query.setParameter("polisNummer", PolisNummer);
        //        query.setParameter("kantoor", kantoor);
        Polis polis = (Polis) query.uniqueResult();

        getTransaction().commit();

        metricsService.stop(timer);

        return polis;
    }

    public Polis lees(Long id) {
        Timer.Context timer = metricsService.addTimerMetric("lees", PolisRepository.class);

        getTransaction();

        Polis polis = getEm().get(Polis.class, id);

        getTransaction().commit();

        metricsService.stop(timer);

        return polis;
    }

    public void opslaan(Polis polis) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", PolisRepository.class);

        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(polis, ToStringStyle.SHORT_PREFIX_STYLE));
        if (polis.getId() == null) {
            getSession().save(polis);
        } else {
            getSession().merge(polis);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public void verwijder(Polis polis) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", PolisRepository.class);

        getTransaction();

        getEm().delete(polis);

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public void verwijder(List<Polis> polissen) {
        Timer.Context timer = metricsService.addTimerMetric("verwijder", PolisRepository.class);

        getTransaction();

        for (Polis polis : polissen) {
            getEm().delete(polis);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public void setDiscriminatorValue(String discriminatorValue, Polis polis) {
        Timer.Context timer = metricsService.addTimerMetric("setDiscriminatorValue", PolisRepository.class);

        getTransaction();

        Query query = getEm().createQuery("update Polis set SOORT = :discriminatorValue where id = :id");
        query.setParameter("discriminatorValue", discriminatorValue);
        query.setParameter("id", polis.getId());

        query.executeUpdate();

        getTransaction().commit();

        metricsService.stop(timer);
    }

}
