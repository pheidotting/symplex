package nl.dias.repository;

import nl.dias.domein.Kantoor;
import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.domein.polis.Polis;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PolisRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
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

    public List<Polis> zoekPolissenOpSoort(Class<?> soort) {
        getTransaction();

        Query query = getEm().createQuery("select e from " + soort.getSimpleName() + " e");
        List<Polis> ret = query.list();

        getTransaction().commit();

        return ret;
    }

    @Transactional
    public List<Polis> allePolissenBijMaatschappij(VerzekeringsMaatschappij maatschappij) {
        getTransaction();

        Query query = getEm().getNamedQuery("Polis.allesBijMaatschappij");
        query.setParameter("maatschappij", maatschappij);

        List<Polis> polissen = query.list();

        getTransaction().commit();

        return polissen;
    }

    public List<Polis> allePolissenBijRelatie(Long relatie) {
        getTransaction();

        Query query = getEm().getNamedQuery("Polis.allesVanRelatie");
        query.setParameter("relatie", relatie);

        List<Polis> polissen = query.list();

        getTransaction().commit();

        return polissen;
    }

    public List<Polis> allePolissenBijBedrijf(Long bedrijf) {
        getTransaction();

        Query query = getEm().getNamedQuery("Polis.allesVanBedrijf");
        query.setParameter("bedrijf", bedrijf);

        List<Polis> polissen = query.list();

        LOGGER.debug("Opgehaald {} polissen bij bedrijf met id {}", polissen.size(), bedrijf);

        getTransaction().commit();

        return polissen;
    }

    @Transactional
    public Polis zoekOpPolisNummer(String PolisNummer, Kantoor kantoor) {
        getTransaction();

        Query query = getEm().getNamedQuery("Polis.zoekOpPolisNummer");
        query.setParameter("polisNummer", PolisNummer);
        //        query.setParameter("kantoor", kantoor);
        Polis polis = (Polis) query.uniqueResult();

        getTransaction().commit();

        return polis;
    }

    public Polis lees(Long id) {
        getTransaction();

        Polis polis = getEm().get(Polis.class, id);

        getTransaction().commit();

        return polis;
    }

    public void opslaan(Polis polis) {
        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(polis, ToStringStyle.SHORT_PREFIX_STYLE));
        if (polis.getId() == null) {
            getSession().save(polis);
        } else {
            getSession().merge(polis);
        }

        getTransaction().commit();
    }

    public void verwijder(Polis polis) {
        getTransaction();

        getEm().delete(polis);

        getTransaction().commit();
    }

    public void verwijder(List<Polis> polissen) {
        getTransaction();

        for (Polis polis : polissen) {
            getEm().delete(polis);
        }

        getTransaction().commit();
    }

    public void setDiscriminatorValue(String discriminatorValue, Polis polis) {
        getTransaction();

        Query query = getEm().createQuery("update Polis set SOORT = :discriminatorValue where id = :id");
        query.setParameter("discriminatorValue", discriminatorValue);
        query.setParameter("id", polis.getId());

        query.executeUpdate();

        getTransaction().commit();
    }

}
