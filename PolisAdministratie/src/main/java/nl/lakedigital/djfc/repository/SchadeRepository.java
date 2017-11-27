package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.Schade;
import nl.lakedigital.djfc.domain.SoortSchade;
import nl.lakedigital.djfc.domain.StatusSchade;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Repository
public class SchadeRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();//NOSONAR
        }
    }

    @Transactional(readOnly = true)
    public List<SoortSchade> soortenSchade() {
        Query query = getSession().getNamedQuery("SoortSchade.alles");

        return query.list();
    }

    @Transactional(readOnly = true)
    public List<SoortSchade> soortenSchade(String omschrijving) {
        Query query = getSession().getNamedQuery("SoortSchade.zoekOpOmschrijving");
        query.setParameter("omschrijving", "%" + omschrijving + "%");

        return query.list();
    }

    @Transactional(readOnly = true)
    public List<Schade> alles() {
        Query query = getSession().createQuery("select p from Schade p");

        return query.list();
    }

    @Transactional
    public void opslaan(StatusSchade statusSchade) {
        getSession().getTransaction().begin();
        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(statusSchade, ToStringStyle.SHORT_PREFIX_STYLE));
        if (statusSchade.getId() == null) {
            getSession().save(statusSchade);
        } else {
            getSession().merge(statusSchade);
        }
    }

    @Transactional
    public void opslaan(SoortSchade soortSchade) {
        getSession().getTransaction().begin();
        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(soortSchade, ToStringStyle.SHORT_PREFIX_STYLE));
        if (soortSchade.getId() == null) {
            getSession().save(soortSchade);
        } else {
            getSession().merge(soortSchade);
        }
    }

    @Transactional
    public void verwijder(SoortSchade soortSchade) {
        getSession().delete(soortSchade);
    }

    @Transactional(readOnly = true)
    public List<Schade> alleSchades(Long polis) {
        Query query = getSession().getNamedQuery("Schade.allesBijPolis");
        query.setParameter("polis", polis);

        return query.list();
    }

    @Transactional(readOnly = true)
    public StatusSchade getStatussen(String status) {
        Query query = getSession().getNamedQuery("StatusSchade.zoekOpSoort");
        query.setParameter("status", status);

        return (StatusSchade) query.uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<StatusSchade> getStatussen() {
        Query query = getSession().createQuery("select s from StatusSchade s where s.ingebruik = '1'");

        return query.list();
    }

    @Transactional(readOnly = true)
    public List<Schade> zoekOpSchadeNummerMaatschappij(String schadeNummerMaatschappij) {
        Query query = getSession().getNamedQuery("Schade.zoekOpschadeNummerMaatschappij");
        query.setParameter("schadeNummerMaatschappij", schadeNummerMaatschappij);

        return query.list();
    }

    @Transactional
    public void verwijder(Schade schade) {
        List<Schade> schadesen = new ArrayList();
        schadesen.add(schade);

        verwijder(schadesen);
    }

    @Transactional
    public void verwijder(List<Schade> schades) {
        for (Schade schade : schades) {
            getSession().delete(schade);
        }
    }

    @Transactional
    public void verwijderSoortenSchade(List<SoortSchade> schades) {
        for (SoortSchade schade : schades) {
            getSession().delete(schade);
        }
    }

    @Transactional
    public void verwijderStatusSchade(List<StatusSchade> schades) {
        for (StatusSchade schade : schades) {
            getSession().delete(schade);
        }
    }

    @Transactional
    public void opslaan(Schade schade) {
        opslaan(newArrayList(schade));
    }

    @Transactional
    public void opslaan(List<Schade> schades) {
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
    }

    @Transactional(readOnly = true)
    public Schade lees(Long id) {
        Schade t = getSession().get(Schade.class, id);

        LOGGER.debug("Opzoeken Schade met id {}, gevonden {}", id, t);

        return t;
    }
}
