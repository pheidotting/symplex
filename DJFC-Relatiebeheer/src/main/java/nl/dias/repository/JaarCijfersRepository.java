package nl.dias.repository;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.JaarCijfers;
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
public class JaarCijfersRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(JaarCijfersRepository.class);

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

    public List<JaarCijfers> allesBijBedrijf(Bedrijf bedrijf) {
        LOGGER.debug("allesBijBedrijf met id {}", bedrijf.getId());

        getTransaction();

        Query query = getSession().getNamedQuery("JaarCijfers.allesJaarCijfersBijBedrijf");
        query.setParameter("bedrijf", bedrijf);

        List<JaarCijfers> jaarCijferses = query.list();

        getTransaction().commit();

        return jaarCijferses;
    }

    public JaarCijfers lees(Long id) {
        getTransaction();

        JaarCijfers jaarCijfers = getSession().get(JaarCijfers.class, id);

        getTransaction().commit();

        return jaarCijfers;
    }

    public void opslaan(JaarCijfers jaarCijfers) {
        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(jaarCijfers, ToStringStyle.SHORT_PREFIX_STYLE));
        if (jaarCijfers.getId() == null) {
            getSession().save(jaarCijfers);
        } else {
            getSession().merge(jaarCijfers);
        }

        getTransaction().commit();
    }

    public void verwijder(JaarCijfers jaarCijfers) {
        getTransaction();

        getSession().delete(jaarCijfers);

        getTransaction().commit();
    }

    public List<JaarCijfers> alles() {
        getTransaction();

        Query query = getSession().getNamedQuery("JaarCijfers.alles");

        List<JaarCijfers> jaarCijferss = query.list();

        getTransaction().commit();

        return jaarCijferss;
    }
}
