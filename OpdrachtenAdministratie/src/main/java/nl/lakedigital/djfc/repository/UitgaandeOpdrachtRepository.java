package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Repository
public class UitgaandeOpdrachtRepository {
    private static final String TRACK_AND_TRACE_ID = "trackAndTraceId";

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

        List<UitgaandeOpdracht> result = getSession().getNamedQuery("UitgaandeOpdracht.teVersturenUitgaandeOpdrachtenOpTAndTEnWachtenOp").setParameter(TRACK_AND_TRACE_ID, trackAndTraceId).setParameter("wachtenOp", uitgaandeOpdracht).list();

        getTransaction().commit();

        return result;
    }

    public List<UitgaandeOpdracht> zoekObvTrackAndTrackeId(String trackAndTraceId) {
        getTransaction();

        List<UitgaandeOpdracht> result = getSession().getNamedQuery("UitgaandeOpdracht.zoekObvTrackAndTrackeId").setParameter(TRACK_AND_TRACE_ID, trackAndTraceId).list();

        getTransaction().commit();

        return result;
    }

    public UitgaandeOpdracht zoekOpBerichtId(String trackAndTraceId, String berichtId) {
        getTransaction();

        List<UitgaandeOpdracht> list = getSession().getNamedQuery("UitgaandeOpdracht.zoekOpBerichtId").setParameter("trackAndTraceId", trackAndTraceId).setParameter("berichtId", berichtId).list();
        UitgaandeOpdracht result = null;
        if (!list.isEmpty()) {
            result = list.get(0);
        }

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
}
