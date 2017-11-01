package nl.dias.repository;

import nl.dias.domein.HypotheekPakket;
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
public class HypotheekPakketRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(HypotheekPakketRepository.class);

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

    public List<HypotheekPakket> allesVanRelatie(Relatie relatie) {
        getTransaction();

        Query query = getSession().getNamedQuery("HypotheekPakket.allesVanRelatie");
        query.setParameter("relatie", relatie);

        List<HypotheekPakket> hypotheekPakkets = query.list();

        getTransaction().commit();

        return hypotheekPakkets;
    }

    public HypotheekPakket lees(Long id) {
        getTransaction();

        HypotheekPakket hypotheekPakket = getSession().get(HypotheekPakket.class, id);

        getTransaction().commit();

        return hypotheekPakket;
    }

    public void opslaan(HypotheekPakket hypotheekPakket) {
        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(hypotheekPakket, ToStringStyle.SHORT_PREFIX_STYLE));
        if (hypotheekPakket.getId() == null) {
            getSession().save(hypotheekPakket);
        } else {
            getSession().merge(hypotheekPakket);
        }

        getTransaction().commit();
    }

    public void verwijder(HypotheekPakket hypotheekPakket) {
        getTransaction();

        getSession().delete(hypotheekPakket);

        getTransaction().commit();
    }

}
