package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.commons.domain.inkomend.InkomendeOpdracht;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InkomendeOpdrachtRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {//NOSONAR
            return sessionFactory.openSession();
        }
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

    public void opslaan(InkomendeOpdracht inkomendeOpdracht) {
        List<InkomendeOpdracht> inkomendeOpdrachten = new ArrayList();
        inkomendeOpdrachten.add(inkomendeOpdracht);

        opslaan(inkomendeOpdrachten);
    }

    public void opslaan(List<InkomendeOpdracht> inkomendeOpdrachten) {
        getTransaction();

        for (InkomendeOpdracht t : inkomendeOpdrachten) {
            if (t.getId() == null) {
                getSession().save(t);
            } else {
                getSession().merge(t);
            }
        }

        getTransaction().commit();
    }

}
