package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PolisRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisRepository.class);

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

    @Transactional
    public void verwijder(Polis polis) {
        List<Polis> polissen = new ArrayList();
        polissen.add(polis);

        verwijder(polissen);
    }

    @Transactional
    public void verwijder(List<Polis> polissen) {
        for (Polis polis : polissen) {
            getSession().delete(polis);
        }
    }

    public void opslaan(Polis polis) {
        List<Polis> polissen = new ArrayList();
        polissen.add(polis);

        opslaan(polissen);
    }

    //    @Transactional
    public void opslaan(List<Polis> polissen) {
        getTransaction();

        //        if(        getSession().getTransaction().isTransactionInProgress){
        //        getSession().getTransaction().begin();
        //            getSession().beginTransaction();
        //        }
        for (Polis t : polissen) {
            LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(t, ToStringStyle.SHORT_PREFIX_STYLE));
            if (t.getId() == null) {
                getSession().save(t);
            } else {
                getSession().merge(t);
            }
        }

        getTransaction().commit();
    }

    //    @Transactional(readOnly = true)
    public Polis lees(Long id) {
        getTransaction();

        Polis t = getSession().get(Polis.class, id);

        getTransaction().commit();

        LOGGER.debug("Opzoeken Polis met id {}, gevonden {}", id, t);

        return t;
    }

    @Transactional(readOnly = true)
    public List<Polis> alles() {
        Query query = getSession().createQuery("select p from Polis p");

        return query.list();
    }

    @Transactional(readOnly = true)
    public List<Polis> zoekOpPolisNummer(String PolisNummer) {
        Query query = getSession().getNamedQuery("Polis.zoekOpPolisNummer");
        query.setParameter("polisNummer", PolisNummer);

        return query.list();
    }

    //    @Transactional(readOnly = true)
    public List<Polis> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
        LOGGER.debug("Ophalen polissen voor SoortEntiteit {} en entiteitId {}", soortEntiteit, entiteitId);
        String queryString = null;

        getTransaction();

        if (soortEntiteit == SoortEntiteit.RELATIE) {
            queryString = "select p from Polis p where relatie = :entiteitId";
        } else if (soortEntiteit == SoortEntiteit.BEDRIJF) {
            queryString = "select p from Polis p where bedrijf = :entiteitId";
        }

        LOGGER.debug("A");
        Query query = getSession().createQuery(queryString);
        LOGGER.debug("B");
        query.setParameter("entiteitId",entiteitId);
        LOGGER.debug("C");

        List<Polis> result = query.list();
        LOGGER.debug("D");

        getTransaction().commit();
        LOGGER.debug("E");

        return result;
    }
}
