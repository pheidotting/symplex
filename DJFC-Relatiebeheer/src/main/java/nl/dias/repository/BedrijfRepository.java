package nl.dias.repository;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
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
public class BedrijfRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(BedrijfRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

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
        getTransaction();

        Query query = getEm().getNamedQuery("Bedrijf.allesBijRelatie");
        query.setParameter("relatie", relatie);

        List<Bedrijf> bedrijfs = query.list();

        getTransaction().commit();

        return bedrijfs;
    }

    public List<Bedrijf> zoekOpNaam(String zoekTerm) {
        getTransaction();

        Query query = getEm().getNamedQuery("Bedrijf.zoekOpNaam");
        query.setParameter("zoekTerm", "%" + zoekTerm + "%");

        List<Bedrijf> bedrijfs = query.list();

        getTransaction().commit();

        return bedrijfs;
    }

    public Bedrijf lees(Long id) {
        getTransaction();

        Bedrijf bedrijf = getEm().get(Bedrijf.class, id);

        getTransaction().commit();

        return bedrijf;
    }

    public void opslaan(Bedrijf bedrijf) {
        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(bedrijf, ToStringStyle.SHORT_PREFIX_STYLE));
        if (bedrijf.getId() == null) {
            getSession().save(bedrijf);
        } else {
            getSession().merge(bedrijf);
        }

        getTransaction().commit();
    }

    public void verwijder(Bedrijf bedrijf) {
        getTransaction();

        getEm().delete(bedrijf);

        getTransaction().commit();
    }

    public List<Bedrijf> alles() {
        getTransaction();

        Query query = getEm().getNamedQuery("Bedrijf.alles");

        List<Bedrijf> bedrijfs = query.list();

        getTransaction().commit();

        return bedrijfs;
    }
}
