package nl.dias.repository;

import nl.dias.domein.Belastingzaken;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BelastingzakenRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(BelastingzakenRepository.class);

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

    public void opslaan(Belastingzaken belastingzaken) {
        getTransaction();

        getSession().save(belastingzaken);

        getTransaction().commit();
    }

    public List<Belastingzaken> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
        getTransaction();

        Query query = getEm().getNamedQuery("Belastingzaken.zoekOpSoortEntiteitEnId");
        query.setParameter("soortEntiteit", soortEntiteit);
        query.setParameter("entiteitId", entiteitId);

        List<Belastingzaken> result = query.list();

        getTransaction().commit();

        return result;
    }
}
