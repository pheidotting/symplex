package nl.dias.repository;

import nl.dias.domein.InlogPoging;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InlogPogingRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(InlogPogingRepository.class);
    private static final int MAX_RESULTS = 30;

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            LOGGER.trace("{}", e);
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

    public void opslaanNieuwePoging(Long gebruikerId, boolean gelukt) {
        InlogPoging inlogPoging = new InlogPoging(gebruikerId, gelukt);
        getTransaction();

        if (gelukt) {
            getSession().getNamedQuery("InlogPoging.verwijderOudeMislukte").setParameter("gebruikerId", gebruikerId).executeUpdate();
        }
        LOGGER.debug("Opslaan InlogPoging {}", ReflectionToStringBuilder.toString(inlogPoging));
        getSession().save(inlogPoging);

        getTransaction().commit();
    }

    public boolean magInloggen(Long gebruikerId) {
        getTransaction();

        LocalDateTime tijdstip = LocalDateTime.now().minusMinutes(5);
        Query query = getSession().getNamedQuery("InlopPoging.zoekFouteInlogPogingen");
        query.setParameter("gebruikerId", gebruikerId);
        query.setParameter("tijdstip", tijdstip.toDate());

        boolean magInloggen = query.list().size() < 5;

        getTransaction().commit();

        return magInloggen;
    }

}
