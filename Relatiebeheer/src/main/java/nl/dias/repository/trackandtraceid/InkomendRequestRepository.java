package nl.dias.repository.trackandtraceid;

import nl.dias.domein.trackandtraceid.InkomendRequest;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InkomendRequestRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(InkomendRequestRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

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

    public void opslaan(InkomendRequest inkomendRequest) {
        getTransaction();

        Query query = getSession().createQuery("delete from InkomendRequest where tijdstip < :tijdstip");
        query.setParameter("tijdstip", LocalDateTime.now().minusMonths(1));
        query.executeUpdate();

        getSession().flush();
        getSession().save(inkomendRequest);

        getTransaction().commit();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
