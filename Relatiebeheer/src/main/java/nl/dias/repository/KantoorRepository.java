package nl.dias.repository;

import nl.dias.domein.Kantoor;
import nl.dias.exception.BsnNietGoedException;
import nl.dias.exception.IbanNietGoedException;
import nl.dias.exception.PostcodeNietGoedException;
import nl.dias.exception.TelefoonnummerNietGoedException;
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

import java.util.List;

@Repository
public class KantoorRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorRepository.class);

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

    @Transactional
    public void opslaanKantoor(Kantoor kantoor) throws PostcodeNietGoedException, TelefoonnummerNietGoedException, BsnNietGoedException, IbanNietGoedException {
        getTransaction();

        if (kantoor.getId() == null) {
            getSession().persist(kantoor);
        } else {
            getSession().merge(kantoor);
        }

        getTransaction().commit();
    }

    public Kantoor getIngelogdKantoor() {
        return lees(1L);
    }

    public Kantoor lees(Long id) {
        getTransaction();

        Kantoor kantoor = getSession().get(Kantoor.class, id);

        getTransaction().commit();

        return kantoor;
    }


    public void verwijder(Kantoor kantoor) {
        getTransaction();

        getSession().delete(kantoor);

        getTransaction().commit();
    }

    public List<Kantoor> alles() {
        getTransaction();

        Query query = getSession().getNamedQuery("Kantoor.alles");

        List<Kantoor> kantoors = query.list();

        getTransaction().commit();

        return kantoors;
    }

    public List<Kantoor> zoekOpAfkorting(String afkorting) {
        getTransaction();

        LOGGER.trace("zoekOpAfkorting {}", afkorting);
        Query query = getSession().getNamedQuery("Kantoor.zoekOpAfkorting");
        query.setParameter("afkorting", afkorting);

        List<Kantoor> kantoors = query.list();

        getTransaction().commit();

        return kantoors;
    }

}
