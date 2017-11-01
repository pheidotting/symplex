package nl.dias.repository;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.RisicoAnalyse;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Query;
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
public class RisicoAnalyseRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(RisicoAnalyseRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Transaction getTransaction() {
        Transaction transaction = getSession().getTransaction();
        if (transaction.getStatus() != TransactionStatus.ACTIVE) {
            transaction.begin();
        }

        return transaction;
    }

    public RisicoAnalyse leesBijBedrijf(Bedrijf bedrijf) {
        LOGGER.debug("leesBijBedrijf met id {}", bedrijf.getId());

        Query query = getSession().getNamedQuery("RisicoAnalyse.alleRisicoAnalysesBijBedrijf");
        query.setParameter("bedrijf", bedrijf);

        RisicoAnalyse risicoAnalyse;
        List<RisicoAnalyse> result = query.list();
        if (result.isEmpty()) {
            risicoAnalyse = new RisicoAnalyse();
            risicoAnalyse.setBedrijf(bedrijf);
            bedrijf.getRisicoAnalyses().add(risicoAnalyse);

            opslaan(risicoAnalyse);
        } else {
            risicoAnalyse = result.get(0);
        }
        return risicoAnalyse;
    }

    public RisicoAnalyse lees(Long id) {
        getTransaction();

        RisicoAnalyse risicoAnalyse = getSession().get(RisicoAnalyse.class, id);

        getTransaction().commit();

        return risicoAnalyse;
    }

    public void opslaan(RisicoAnalyse risicoAnalyse) {
        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(risicoAnalyse, ToStringStyle.SHORT_PREFIX_STYLE));
        if (risicoAnalyse.getId() == null) {
            getSession().save(risicoAnalyse);
        } else {
            getSession().merge(risicoAnalyse);
        }

        getTransaction().commit();
    }

    public void verwijder(RisicoAnalyse risicoAnalyse) {
        getTransaction();

        getSession().delete(risicoAnalyse);

        getTransaction().commit();
    }

    public List<RisicoAnalyse> alles() {
        getTransaction();

        Query query = getSession().getNamedQuery("RisicoAnalyse.alles");

        List<RisicoAnalyse> risicoAnalyses = query.list();

        getTransaction().commit();

        return risicoAnalyses;
    }

}
