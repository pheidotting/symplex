package nl.dias.repository;

import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.SoortSchade;
import nl.dias.domein.StatusSchade;
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

import java.util.List;

@Repository
public class SchadeRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

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
        getTransaction();

        Query query = getSession().getNamedQuery("SoortSchade.alles");

        List<SoortSchade> lijst = query.list();

        getTransaction().commit();

        return lijst;
    }

    public List<SoortSchade> soortenSchade(String omschrijving) {
        getTransaction();

        Query query = getSession().getNamedQuery("SoortSchade.zoekOpOmschrijving");
        query.setParameter("omschrijving", "%" + omschrijving + "%");

        List<SoortSchade> lijst = query.list();

        getTransaction().commit();

        return lijst;
    }

    public StatusSchade getStatussen(String status) {
        getTransaction();

        Query query = getSession().getNamedQuery("StatusSchade.zoekOpSoort");
        query.setParameter("status", status);

        StatusSchade statusSchade = (StatusSchade) query.uniqueResult();

        getTransaction().commit();

        return statusSchade;
    }

    public List<StatusSchade> getStatussen() {
        getTransaction();

        Query query = getSession().createQuery("select s from StatusSchade s where s.ingebruik = '1'");

        List<StatusSchade> lijst = query.list();

        getTransaction().commit();

        return lijst;
    }

    public Schade zoekOpSchadeNummerMaatschappij(String schadeNummerMaatschappij) {
        getTransaction();

        Query query = getSession().getNamedQuery("Schade.zoekOpschadeNummerMaatschappij");
        query.setParameter("schadeNummerMaatschappij", schadeNummerMaatschappij);

        Schade schade = (Schade) query.uniqueResult();

        getTransaction().commit();

        return schade;
    }

    public List<Schade> alleSchadesBijRelatie(Relatie relatie) {
        getTransaction();

        Query query = getSession().getNamedQuery("Schade.allesVanRelatie");
        query.setParameter("relatie", relatie);

        List<Schade> lijst = query.list();

        getTransaction().commit();

        return lijst;
    }

    public List<Schade> allesBijPolis(Long polis) {
        getTransaction();

        Query query = getSession().getNamedQuery("Schade.allesBijPolis");
        query.setParameter("polis", polis);


        List<Schade> lijst = query.list();

        getTransaction().commit();

        return lijst;
    }

    public Schade lees(Long id) {
        getTransaction();

        Schade schade = getSession().get(Schade.class, id);

        getTransaction().commit();

        return schade;
    }

    public void opslaan(Schade schade) {
        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(schade, ToStringStyle.SHORT_PREFIX_STYLE));
        if (schade.getId() == null) {
            getSession().save(schade);
        } else {
            getSession().merge(schade);
        }

        getTransaction().commit();
    }

    public void verwijder(Schade schade) {
        getTransaction();

        getSession().delete(schade);

        getTransaction().commit();
    }

    public void verwijder(List<Schade> schades) {
        getTransaction();

        for (Schade schade : schades) {
            getSession().delete(schade);
        }

        getTransaction().commit();
    }

    public List<Schade> alles() {
        getTransaction();

        Query query = getSession().getNamedQuery("Schade.alles");

        List<Schade> schades = query.list();

        getTransaction().commit();

        return schades;
    }
}
