package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.Pakket;
import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
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
    public void verwijder(Polis polis) {
        List<Polis> polissen = new ArrayList();
        polissen.add(polis);

        getTransaction();

        verwijder(polissen);

        getTransaction().commit();
    }

    public void verwijder(List<Polis> polissen) {
        getTransaction();

        for (Polis polis : polissen) {
            getSession().delete(polis);
        }

        getTransaction().commit();
    }

    public void verwijderPakket(Pakket pakket) {
        List<Pakket> pakketten = new ArrayList();
        pakketten.add(pakket);

        getTransaction();

        verwijderPakketten(pakketten);

        getTransaction().commit();
    }

    //    @Transactional
    public void verwijderPakketten(List<Pakket> pakketten) {
        getTransaction();

        for (Pakket pakket : pakketten) {
            getSession().delete(pakket);
        }

        getTransaction().commit();
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
            //            LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(t.getPakket(), ToStringStyle.SHORT_PREFIX_STYLE));
            //            LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(t, ToStringStyle.SHORT_PREFIX_STYLE));
            if (t.getId() == null) {
                getSession().save(t);
            } else {
                getSession().merge(t);
            }
        }

        getTransaction().commit();
    }

    public void opslaan(Pakket pakket) {
        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(pakket));
        pakket.getPolissen().stream().forEach(polis -> LOGGER.info("   Opslaan {}", ReflectionToStringBuilder.toString(polis)));
        getTransaction();

        if (pakket.getId() == null) {
            getSession().save(pakket);
        } else {
            getSession().merge(pakket);
        }

        getTransaction().commit();
    }

    public Pakket lees(Long id) {
        getTransaction();

        Pakket t = getSession().get(Pakket.class, id);

        getTransaction().commit();

        LOGGER.debug("Opzoeken Polis met id {}, gevonden {}", id, t);

        return t;
    }

    public Pakket leesOpPolis(Long id) {
        getTransaction();

        Polis p = getSession().get(Polis.class, id);

        getTransaction().commit();

        LOGGER.debug("Opzoeken Polis met id {}, gevonden {}", id, p);

        return p.getPakket();
    }

    @Transactional(readOnly = true)
    public List<Pakket> alles() {
        Query query = getSession().createQuery("select p from Pakket p");

        return query.list();
    }

    @Transactional(readOnly = true)
    public List<Pakket> zoekOpPolisNummer(String PolisNummer) {
        Query query = getSession().getNamedQuery("Polis.zoekOpPolisNummer");
        query.setParameter("polisNummer", PolisNummer);

        return null;//query.list();
    }

    //    @Transactional(readOnly = true)
    public List<Pakket> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
        LOGGER.debug("Ophalen polissen voor SoortEntiteit {} en entiteitId {}", soortEntiteit, entiteitId);
        String queryString = null;

        getTransaction();

        if (soortEntiteit == SoortEntiteit.RELATIE) {
            queryString = "select p from nl.lakedigital.djfc.domain.Pakket p where p.entiteitId = :entiteitId and p.soortEntiteit = 'RELATIE'";
        } else if (soortEntiteit == SoortEntiteit.BEDRIJF) {
            queryString = "select p from nl.lakedigital.djfc.domain.Pakket p where p.entiteitId = :entiteitId and p.soortEntiteit = 'BEDRIJF'";
        }

        Query query = getSession().createQuery(queryString);
        query.setParameter("entiteitId", entiteitId);

        List<Pakket> result = query.list();

        getTransaction().commit();

        return result;
    }
}
