package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AbstractRepository<T extends AbstracteEntiteitMetSoortEnId> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    private final Class<T> type;

    public AbstractRepository(Class<T> type) {
        this.type = type;
    }

    private String getMyType() {
        return this.type.getSimpleName();
    }

    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            LOGGER.trace("{}", e);
            return sessionFactory.openSession();
        }
    }

    protected Transaction getTransaction() {
        Transaction transaction = getSession().getTransaction();
        if (transaction.getStatus() != TransactionStatus.ACTIVE) {
            transaction.begin();
        }

        return transaction;
    }

    public void verwijder(List<T> adressen) {
        if (getTransaction().getStatus() != TransactionStatus.ACTIVE) {
            getTransaction().begin();
        }

        for (T adres : adressen) {
            getSession().delete(adres);
        }

        getTransaction().commit();
    }

    public void opslaan(List<T> adressen) {
        if (getTransaction().getStatus() != TransactionStatus.ACTIVE) {
            getTransaction().begin();
        }

        for (T t : adressen) {
            LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(t, ToStringStyle.SHORT_PREFIX_STYLE));
            if (t.getId() == null) {
                LOGGER.info("save");
                getSession().save(t);
            } else {
                LOGGER.info("merge");
                getSession().merge(t);
            }
        }

        getTransaction().commit();
        getSession().close();
    }

    public T lees(Long id) {
        getTransaction().begin();

        T t = getSession().get(type, id);

        getTransaction().commit();

        return t;
    }

    public List<T> zoek(String zoekTerm) {
        getTransaction().begin();

        Query query = getSession().getNamedQuery(getMyType() + ".zoeken");
        query.setParameter("zoekTerm", "%" + zoekTerm + "%");

        List<T> lijst = query.list();

        getTransaction().commit();

        return lijst;
    }

    public List<T> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
        getTransaction().begin();

        Query query = getSession().getNamedQuery(getMyType() + ".zoekBijEntiteit");
        query.setParameter("soortEntiteit", soortEntiteit);
        query.setParameter("entiteitId", entiteitId);

        List<T> lijst = query.list();

        getTransaction().commit();

        return lijst;
    }

    public void verwijder(T t){
        getTransaction().begin();

        getSession().delete(t);

        getTransaction().commit();
    }
}
