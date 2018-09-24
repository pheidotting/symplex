package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
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

import static com.google.common.collect.Lists.newArrayList;

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

    public List<UitgaandeOpdracht> teVersturenUitgaandeOpdrachten(String trackAndTraceId, UitgaandeOpdracht uitgaandeOpdracht) {
        getTransaction();

        List<UitgaandeOpdracht> result = getSession().getNamedQuery("UitgaandeOpdracht.teVersturenUitgaandeOpdrachtenOpTAndTEnWachtenOp").setParameter("trackAndTraceId", trackAndTraceId).setParameter("wachtenOp", uitgaandeOpdracht).list();

        getTransaction().commit();

        return result;
    }

    public UitgaandeOpdracht zoekObvSoortEntiteitEnTrackAndTrackeId(String trackAndTraceId, SoortEntiteit soortEntiteit) {
        getTransaction();

        UitgaandeOpdracht result = (UitgaandeOpdracht) getSession().getNamedQuery("UitgaandeOpdracht.zoekObvSoortEntiteitEnTrackAndTrackeId").setParameter("trackAndTraceId", trackAndTraceId).setParameter("soortEntiteit", soortEntiteit).list().get(0);

        getTransaction().commit();

        return result;
    }

    public void opslaan(UitgaandeOpdracht uitgaandeOpdracht) {
        opslaan(newArrayList(uitgaandeOpdracht));
    }
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
