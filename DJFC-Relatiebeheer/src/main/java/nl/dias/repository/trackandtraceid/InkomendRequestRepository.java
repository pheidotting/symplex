package nl.dias.repository.trackandtraceid;

import nl.dias.domein.trackandtraceid.InkomendRequest;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
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
        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(inkomendRequest, ToStringStyle.SHORT_PREFIX_STYLE));
        getSession().save(inkomendRequest);

        getTransaction().commit();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
