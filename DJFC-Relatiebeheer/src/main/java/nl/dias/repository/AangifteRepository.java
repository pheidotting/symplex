package nl.dias.repository;

import nl.dias.domein.Aangifte;
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
public class AangifteRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(AangifteRepository.class);

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

    public List<Aangifte> getOpenAngiftes(Relatie relatie) {
        getTransaction();

        Query query = getSession().getNamedQuery("Aangifte.openAangiftesBijRelatie");
        query.setParameter("relatie", relatie);

        List<Aangifte> aangiftes = query.list();

        getTransaction().commit();

        return aangiftes;
    }

    public List<Aangifte> getGeslotenAngiftes(Relatie relatie) {
        getTransaction();

        Query query = getSession().getNamedQuery("Aangifte.geslotenAangiftesBijRelatie");
        query.setParameter("relatie", relatie);

        List<Aangifte> aangiftes = query.list();

        getTransaction().commit();

        return aangiftes;
    }

    public List<Aangifte> getAlleAngiftes(Relatie relatie) {
        getTransaction();

        Query query = getSession().getNamedQuery("Aangifte.alleAangiftesBijRelatie");
        query.setParameter("relatie", relatie);

        List<Aangifte> aangiftes = query.list();

        getTransaction().commit();

        return aangiftes;
    }

    public Aangifte lees(Long id) {
        getTransaction();

        Aangifte aangifte = getSession().get(Aangifte.class, id);

        getTransaction().commit();

        return aangifte;
    }

    public void opslaan(Aangifte aangifte) {
        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(aangifte, ToStringStyle.SHORT_PREFIX_STYLE));
        if (aangifte.getId() == null) {
            getSession().save(aangifte);
        } else {
            getSession().merge(aangifte);
        }

        getTransaction().commit();
    }

    public void verwijder(Aangifte aangifte) {
        getTransaction();

        getSession().delete(aangifte);

        getTransaction().commit();
    }
}
