package nl.dias.repository;

import nl.dias.domein.VerzekeringsMaatschappij;
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
public class VerzekeringsMaatschappijRepository {
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
    public VerzekeringsMaatschappij zoekOpNaam(String naam) {
        getTransaction();

        Query query = getSession().getNamedQuery("VerzekeringsMaatschappij.zoekOpNaam");
        query.setParameter("naam", naam);

        VerzekeringsMaatschappij verzekeringsMaatschappij = (VerzekeringsMaatschappij) query.uniqueResult();

        getTransaction().commit();

        return verzekeringsMaatschappij;
    }

    public VerzekeringsMaatschappij lees(Long id) {
        getTransaction();

        VerzekeringsMaatschappij verzekeringsMaatschappij = getSession().get(VerzekeringsMaatschappij.class, id);

        getTransaction().commit();

        return verzekeringsMaatschappij;
    }

    public void opslaan(VerzekeringsMaatschappij verzekeringsMaatschappij) {
        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(verzekeringsMaatschappij, ToStringStyle.SHORT_PREFIX_STYLE));
        if (verzekeringsMaatschappij.getId() == null) {
            getSession().save(verzekeringsMaatschappij);
        } else {
            getSession().merge(verzekeringsMaatschappij);
        }

        getTransaction().commit();
    }

    public void verwijder(VerzekeringsMaatschappij verzekeringsMaatschappij) {
        getTransaction();

        getSession().delete(verzekeringsMaatschappij);

        getTransaction().commit();
    }

    public List<VerzekeringsMaatschappij> alles() {
        getTransaction();

        Query query = getSession().getNamedQuery("VerzekeringsMaatschappij.zoekAlles");

        List<VerzekeringsMaatschappij> verzekeringsMaatschappijs = query.list();

        getTransaction().commit();

        return verzekeringsMaatschappijs;
    }
}

