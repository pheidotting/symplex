package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.VerzekeringsMaatschappij;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class VerzekeringsMaatschappijRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerzekeringsMaatschappijRepository.class);

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

    //    @Transactional(readOnly = true)
    public List<VerzekeringsMaatschappij> alles() {
        LOGGER.debug("Lijst Maatschappijen");
        getTransaction();

        Query query = getSession().getNamedQuery("VerzekeringsMaatschappij.zoekAlles");
        List<VerzekeringsMaatschappij> result = query.list();

        getTransaction().commit();

        return result;
    }
}

