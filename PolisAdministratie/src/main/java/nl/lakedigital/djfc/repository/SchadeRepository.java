package nl.lakedigital.djfc.repository;

import com.google.common.collect.Lists;
import nl.lakedigital.djfc.domain.Schade;
import nl.lakedigital.djfc.domain.SoortSchade;
import nl.lakedigital.djfc.domain.StatusSchade;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SchadeRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeRepository.class);

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

    //    //@Transactional(readOnly = true)
    public List<SoortSchade> soortenSchade() {
        getTransaction();

        Query query = getSession().getNamedQuery("SoortSchade.alles");

        List list = query.list();

        getTransaction().commit();

        return list;
    }

    //    //@Transactional(readOnly = true)
    public List<SoortSchade> soortenSchade(String omschrijving) {
        getTransaction();

        Query query = getSession().getNamedQuery("SoortSchade.zoekOpOmschrijving");
        query.setParameter("omschrijving", "%" + omschrijving + "%");

        List list = query.list();

        getTransaction().commit();

        return list;
    }

    //    //@Transactional(readOnly = true)
    public List<Schade> alles() {
        getTransaction();

        Query query = getSession().createQuery("select p from Schade p");

        List<Schade> result = query.list();

        getTransaction().commit();

        return result;
    }

    //    //@Transactional
    public void opslaan(StatusSchade statusSchade) {
        getTransaction().begin();
        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(statusSchade, ToStringStyle.SHORT_PREFIX_STYLE));
        if (statusSchade.getId() == null) {
            getSession().save(statusSchade);
        } else {
            getSession().merge(statusSchade);
        }
        getTransaction().commit();
    }

    //    //@Transactional
    public void opslaan(SoortSchade soortSchade) {
        getTransaction().begin();
        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(soortSchade, ToStringStyle.SHORT_PREFIX_STYLE));
        if (soortSchade.getId() == null) {
            getSession().save(soortSchade);
        } else {
            getSession().merge(soortSchade);
        }
        getTransaction().commit();
    }

    //@Transactional
    public void verwijder(SoortSchade soortSchade) {
        getSession().delete(soortSchade);
    }

    //    //@Transactional(readOnly = true)
    public List<Schade> alleSchades(Long polis) {
        getTransaction();

        Query query = getSession().getNamedQuery("Schade.allesBijPolis");
        query.setParameter("polis", polis);

        List<Schade> result = query.list();

        getTransaction().commit();

        return result;
    }

    //    //@Transactional(readOnly = true)
    public StatusSchade getStatussen(String status) {
        getTransaction();

        Query query = getSession().getNamedQuery("StatusSchade.zoekOpSoort");
        query.setParameter("status", status);

        StatusSchade statusSchade = (StatusSchade) query.uniqueResult();

        getTransaction().commit();

        return statusSchade;
    }

    //@Transactional(readOnly = true)
    public List<StatusSchade> getStatussen() {
        getTransaction();

        Query query = getSession().createQuery("select s from StatusSchade s where s.ingebruik = '1'");

        List list = query.list();

        getTransaction().commit();

        return list;
    }

    //@Transactional(readOnly = true)
    public List<Schade> zoekOpSchadeNummerMaatschappij(String schadeNummerMaatschappij) {
        getTransaction();

        Query query = getSession().getNamedQuery("Schade.zoekOpschadeNummerMaatschappij");
        query.setParameter("schadeNummerMaatschappij", schadeNummerMaatschappij);

        List list = query.list();

        getTransaction().commit();

        return list;
    }

    //@Transactional
    public void verwijder(Schade schade) {
        List<Schade> schadesen = new ArrayList();
        schadesen.add(schade);

        verwijder(schadesen);
    }

    //@Transactional
    public void verwijder(List<Schade> schades) {
        getTransaction();

        for (Schade schade : schades) {
            getSession().delete(schade);
        }

        getTransaction().commit();

    }

    //@Transactional
    public void verwijderSoortenSchade(List<SoortSchade> schades) {
        getTransaction();

        for (SoortSchade schade : schades) {
            getSession().delete(schade);
        }

        getTransaction().commit();

    }

    //@Transactional
    public void verwijderStatusSchade(List<StatusSchade> schades) {
        getTransaction();

        for (StatusSchade schade : schades) {
            getSession().delete(schade);
        }

        getTransaction().commit();

    }

    //@Transactional
    public void opslaan(Schade schade) {
        opslaan(Lists.newArrayList(schade));
    }

    //@Transactional
    public void opslaan(List<Schade> schades) {
        getTransaction();

        //        getSession().getTransaction().begin();
        for (Schade t : schades) {
            LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(t, ToStringStyle.SHORT_PREFIX_STYLE));
            //            if (t.getId() == null) {
            getSession().saveOrUpdate(t);
            getSession().flush();
            //            } else {
            //                getSession().merge(t);
            //            }
        }

        getTransaction().commit();

    }

    //@Transactional(readOnly = true)
    public Schade lees(Long id) {
        getTransaction();

        Schade t = getSession().get(Schade.class, id);

        LOGGER.debug("Opzoeken Schade met id {}, gevonden {}", id, t);

        getTransaction().commit();

        return t;
    }
}
