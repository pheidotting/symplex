package nl.dias.repository;

import nl.dias.domein.EmailCheck;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmailCheckRepository {
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

    public void opslaan(EmailCheck emailCheck) {
        getTransaction();

        getSession().persist(emailCheck);

        getTransaction().commit();
    }

    public void verwijder(EmailCheck emailCheck) {
        getTransaction();

        getSession().delete(emailCheck);

        getTransaction().commit();
    }

    public List<EmailCheck> alles() {
        getTransaction();
        Query query = getEm().createQuery("select e from EmailCheck e");
        List<EmailCheck> ret = query.list();

        getTransaction().commit();

        return ret;
    }
}
