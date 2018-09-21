package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import org.hibernate.HibernateException;
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
public class UitgaandeOpdrachtRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UitgaandeOpdrachtRepository.class);

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

    public List<UitgaandeOpdracht> teVersturenUitgaandeOpdrachten() {
        getTransaction();

        List<UitgaandeOpdracht> result = getSession().getNamedQuery("UitgaandeOpdracht.teVersturenUitgaandeOpdrachten").list();

        getTransaction().commit();

        return result;
    }

    //    @Transactional
    //    public void verwijder(Polis polis) {
    //        List<Polis> polissen = new ArrayList();
    //        polissen.add(polis);
    //
    //        getTransaction();
    //
    //        verwijder(polissen);
    //
    //        getTransaction().commit();
    //    }
    //
    //    @Transactional
    //    public void verwijder(List<Polis> polissen) {
    //        getTransaction();
    //
    //        for (Polis polis : polissen) {
    //            getSession().delete(polis);
    //        }
    //
    //        getTransaction().commit();
    //    }
    //
    //    public void opslaan(InkomendeOpdracht inkomendeOpdracht) {
    //        List<InkomendeOpdracht> inkomendeOpdrachten = new ArrayList();
    //        inkomendeOpdrachten.add(inkomendeOpdracht);
    //
    //        opslaan(inkomendeOpdrachten);
    //    }
    //
    //    @Transactional
    public void opslaan(List<UitgaandeOpdracht> uitgaandeOpdrachten) {
        getTransaction();

        for (UitgaandeOpdracht t : uitgaandeOpdrachten) {
            if (t.getId() == null) {
                getSession().save(t);
            } else {
                getSession().merge(t);
            }
        }

        getTransaction().commit();
    }

    //        public void opslaan(Pakket pakket) {
    //        getTransaction();
    //
    //        if (pakket.getId() == null) {
    //            getSession().save(pakket);
    //        } else {
    //            getSession().merge(pakket);
    //        }
    //
    //        getTransaction().commit();
    //    }
    //
    //    //    @Transactional(readOnly = true)
    //    public Pakket lees(Long id) {
    //        getTransaction();
    //
    //        Pakket t = getSession().get(Pakket.class, id);
    //
    //        getTransaction().commit();
    //
    //        LOGGER.debug("Opzoeken Polis met id {}, gevonden {}", id, t);
    //
    //        return t;
    //    }
    //
    //    @Transactional(readOnly = true)
    //    public List<Pakket> alles() {
    //        Query query = getSession().createQuery("select p from Pakket p");
    //
    //        return query.list();
    //    }
    //
    //    @Transactional(readOnly = true)
    //    public List<Pakket> zoekOpPolisNummer(String PolisNummer) {
    //        Query query = getSession().getNamedQuery("Polis.zoekOpPolisNummer");
    //        query.setParameter("polisNummer", PolisNummer);
    //
    //        return null;//query.list();
    //    }
    //
    //    //    @Transactional(readOnly = true)
    //    public List<Pakket> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
    //        LOGGER.debug("Ophalen polissen voor SoortEntiteit {} en entiteitId {}", soortEntiteit, entiteitId);
    //        String queryString = null;
    //
    //        getTransaction();
    //
    //        if (soortEntiteit == SoortEntiteit.RELATIE) {
    //            queryString = "select p from nl.lakedigital.djfc.domain.Pakket p where p.entiteitId = :entiteitId and p.soortEntiteit = 'RELATIE'";
    //        } else if (soortEntiteit == SoortEntiteit.BEDRIJF) {
    //            queryString = "select p from nl.lakedigital.djfc.domain.Pakket p where p.entiteitId = :entiteitId and p.soortEntiteit = 'BEDRIJF'";
    //        }
    //
    //        Query query = getSession().createQuery(queryString);
    //        query.setParameter("entiteitId",entiteitId);
    //
    //        List<Pakket> result = query.list();
    //
    //        getTransaction().commit();
    //
    //        return result;
    //    }
}
