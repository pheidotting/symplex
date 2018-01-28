package nl.dias.repository;

import nl.dias.domein.Versie;
import nl.dias.domein.VersieGelezen;
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

import static com.google.common.collect.Lists.newArrayList;

@Repository
public class VersieRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersieRepository.class);

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

    public void opslaan(Versie versie) {
        getTransaction();

        getSession().save(versie);

        getTransaction().commit();
    }

    public void opslaan(VersieGelezen versieGelezen) {
        getTransaction();

        getSession().save(versieGelezen);

        getTransaction().commit();
    }

    public List<Versie> getOngelezenVersies(Long gebruikerId) {
        List<Versie> result = newArrayList();
        getTransaction();

        Query query = getSession().createQuery("select vg from VersieGelezen vg WHERE gebruiker = :gebruiker");
        query.setParameter("gebruiker", gebruikerId);

        List<Long> versieIds = query.list();

        for (Long id : versieIds) {
            Query versiesQuery = getSession().createQuery("select v from Versie v WHERE id = :id");
            versiesQuery.setParameter("id", id);

            result.add((Versie) versiesQuery.list().get(0));
        }

        getTransaction().commit();

        return result;
    }
}
