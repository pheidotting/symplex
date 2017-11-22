package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.Identificatie;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
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

import static com.google.common.collect.Lists.newArrayList;

@Repository
public class IdentificatieRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdentificatieRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();//NOSONAR
        }
    }

    @Transactional
    public void verwijder(Identificatie identificatie) {
        LOGGER.debug("Verwijder {}", ReflectionToStringBuilder.toString(identificatie));

        getSession().delete(identificatie);
    }

    @Transactional
    public void verwijder(List<Identificatie> identificaties) {
        for (Identificatie identificatie : identificaties) {
            LOGGER.debug("Verwijder {}",identificatie);

            getSession().delete(identificatie);
        }
    }

    @Transactional
    public void opslaan(Identificatie identificatie) {
        opslaan(newArrayList(identificatie));
    }

    @Transactional
    public void opslaan(List<Identificatie> identificaties) {
        LOGGER.debug("Opslaan {} identificaties",identificaties.size());
        for (Identificatie identificatie : identificaties) {
            LOGGER.debug("{}",identificatie);
            if (identificatie.getId() == null) {
                identificatie.nieuweIdentificatieCode();

                while (komtCodeAlVoor(identificatie.getIdentificatie())) {
                    identificatie.nieuweIdentificatieCode();
                }
                getSession().save(identificatie);
            } else {
                getSession().merge(identificatie);
            }
        }
    }

    private boolean komtCodeAlVoor(String identificatieCode) {
        LOGGER.debug("trace {} al voor?", identificatieCode);
        Query query = getSession().getNamedQuery("Identificatie.zoekOpIdentificatieCode");
        query.setParameter("identificatie", identificatieCode);

        return  !query.list().isEmpty();
    }

    @Transactional(readOnly = true)
    public Identificatie zoekOpIdentificatieCode(String identificatieCode) {
        LOGGER.debug("trace {} al voor?", identificatieCode);
        Query query = getSession().getNamedQuery("Identificatie.zoekOpIdentificatieCode");
        query.setParameter("identificatie", identificatieCode);

        List<Identificatie> identificaties = query.list();

        if (!identificaties.isEmpty()) {
            LOGGER.trace("Gevonden Identificatie {}", identificaties.get(0).toString());
            return identificaties.get(0);
        } else {
            LOGGER.trace("Geen gevonden Identificatie");
            return null;
        }
    }

    @Transactional(readOnly = true)
    public Identificatie zoek(String soortEntiteit, Long entiteitId) {
        Query query = getSession().getNamedQuery("Identificatie.zoek");
        query.setParameter("soortEntiteit", soortEntiteit);
        query.setParameter("entiteitId", entiteitId);

        List<Identificatie> identificaties=query.list();

        if(!identificaties.isEmpty()){
            LOGGER.trace("Gevonden Identificatie {}", identificaties.get(0).toString());
            return identificaties.get(0);
        }else{
            LOGGER.trace("Geen gevonden Identificatie");
            return null;
        }
    }
}
