package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.VerzekeringsMaatschappij;
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

import java.util.List;

@Repository
public class VerzekeringsMaatschappijRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            LOGGER.trace("{}", e);
            return sessionFactory.openSession();
        }
    }

    @Transactional
    public void opslaan(VerzekeringsMaatschappij verzekeringsMaatschappij) {
        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(verzekeringsMaatschappij, ToStringStyle.SHORT_PREFIX_STYLE));
        if (verzekeringsMaatschappij.getId() == null) {
            getSession().save(verzekeringsMaatschappij);
        } else {
            getSession().merge(verzekeringsMaatschappij);
        }
    }

    @Transactional(readOnly = true)
    public VerzekeringsMaatschappij zoekOpNaam(String naam) {
        Query query = getSession().getNamedQuery("VerzekeringsMaatschappij.zoekOpNaam");
        query.setParameter("naam", naam);

        VerzekeringsMaatschappij result = (VerzekeringsMaatschappij) query.uniqueResult();

        return result;
    }

    @Transactional(readOnly = true)
    public List<VerzekeringsMaatschappij> alles() {
        Query query = getSession().getNamedQuery("VerzekeringsMaatschappij.zoekAlles");
        List<VerzekeringsMaatschappij> result = query.list();

        return result;
    }
}

