package nl.dias.repository;

import nl.dias.domein.*;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Repository
public class GebruikerRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(GebruikerRepository.class);
    private static final int MAX_RESULTS = 30;

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            LOGGER.trace("{}", e);
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

    public List<ContactPersoon> alleContactPersonen(Long bedrijfsId) {
        getTransaction();

        Query query = getEm().getNamedQuery("ContactPersoon.alleContactPersonen");
        query.setParameter("bedrijf", bedrijfsId);

        List<ContactPersoon> result = query.list();

        getTransaction().commit();


        return result;
    }

    public List<Relatie> zoekRelatiesOpTelefoonnummer(String telefoonnummer) {
        getTransaction();

        Query query = getEm().getNamedQuery("Relatie.zoekOpTelefoonnummer");
        query.setParameter("telefoonnummer", telefoonnummer);

        List<Relatie> relaties = new ArrayList<Relatie>();

        for (Object relatie : query.list()) {
            relaties.add((Relatie) relatie);
        }
        getTransaction().commit();
        return relaties;
    }

    public List<Relatie> zoekRelatiesOpBedrijfsnaam(String bedrijfsnaam) {
        getTransaction();

        Query query = getEm().getNamedQuery("Relatie.zoekOpBedrijfsnaam");
        query.setParameter("bedrijfsnaam", "%" + bedrijfsnaam + "%");

        List<Relatie> relaties = new ArrayList<Relatie>();

        for (Object relatie : query.list()) {
            relaties.add((Relatie) relatie);
        }

        getTransaction().commit();

        return relaties;
    }

    @Transactional
    public List<Relatie> alleRelaties() {
        getTransaction();
        Query query = getEm().createQuery("select e from Relatie e");
        List<Relatie> ret = query.list();

        getTransaction().commit();

        return ret;
    }

    public List<Medewerker> alleMedewerkers() {
        getTransaction();
        Query query = getEm().createQuery("select e from Medewerker e");
        List<Medewerker> ret = query.list();

        getTransaction().commit();

        return ret;
    }

    public List<ContactPersoon> alleContactPersonen() {
        getTransaction();
        Query query = getEm().createQuery("select e from ContactPersoon e");
        List<ContactPersoon> ret = query.list();

        getTransaction().commit();

        return ret;
    }

    @Transactional
    public List<Relatie> alleRelaties(Kantoor kantoor) {
        getTransaction();

        Query query = getEm().getNamedQuery("Relatie.zoekAllesVoorKantoor");
        query.setMaxResults(MAX_RESULTS);
        query.setParameter("kantoor", kantoor);

        List<Relatie> result = query.list();

        getTransaction().commit();

        return result;
    }

    @Transactional
    public Relatie zoekOpBsn(String bsn) {
        getTransaction();

        Query query = getEm().getNamedQuery("Relatie.zoekOpBsn");
        query.setParameter("bsn", bsn);

        Relatie relatie = (Relatie) query.list().get(0);

        getTransaction().commit();

        return relatie;
    }

    @Transactional
    public Gebruiker zoek(String emailadres) throws NietGevondenException {
        LOGGER.debug("Parameter : '{}'", emailadres);

        Gebruiker gebruiker = null;

        getTransaction();

        Query query = getEm().getNamedQuery("Gebruiker.zoekOpEmail");
        query.setParameter("emailadres", emailadres);
        //        query.setParameter("emailadres", "'"+emailadres+"'");
        try {
            gebruiker = (Relatie) query.list().get(0);
        } catch (NoResultException e) {
            LOGGER.info("Niets gevonden", e);
            throw new NietGevondenException(emailadres);
        }
        getTransaction().commit();

        return gebruiker;
    }

    @Transactional
    public Gebruiker zoekOpIdentificatie(String identificatie) throws NietGevondenException {
        LOGGER.debug("zoekOpIdentificatie Parameter : '{}'", identificatie);

        Gebruiker gebruiker = null;

        getTransaction();

        Query query = getEm().getNamedQuery("Gebruiker.zoekOpIdentificatie");
        query.setParameter("identificatie", identificatie);
        //        query.setParameter("emailadres", "'"+emailadres+"'");
        try {
            gebruiker = (Gebruiker) query.list().get(0);
        } catch (NoResultException | IndexOutOfBoundsException e) {
            LOGGER.trace("Niets gevonden", e);
            throw new NietGevondenException(identificatie);
        }

        getTransaction().commit();

        return gebruiker;
    }

    public List<Gebruiker> zoekOpNaam(String naam) {
        getTransaction();

        Query query = getEm().getNamedQuery("Gebruiker.zoekOpNaam");
        query.setMaxResults(MAX_RESULTS);
        query.setParameter("naam", "%" + naam + "%");

        List<Gebruiker> result = query.list();

        getTransaction().commit();

        return result;
    }

    public List<Relatie> zoekRelatieOpRoepnaam(String naam) {
        getTransaction();

        Query query = getEm().getNamedQuery("Relatie.roepnaam");
        query.setMaxResults(MAX_RESULTS);
        query.setParameter("roepnaam", "%" + naam + "%");

        List<Relatie> result = query.list();

        getTransaction().commit();

        return result;
    }

    public Gebruiker zoekOpSessieEnIpadres(String sessie, String ipadres) throws NietGevondenException {
        LOGGER.debug("zoekOpSessieEnIpadres(" + sessie + " , " + ipadres + ")");


        Gebruiker gebruiker;

        getTransaction();

        Query query = getEm().getNamedQuery("Gebruiker.zoekOpSessieEnIpAdres");
        query.setParameter("sessie", sessie);
        query.setParameter("ipadres", ipadres);

        List<Gebruiker> lijst = query.list();

        if (lijst != null && !lijst.isEmpty()) {
            LOGGER.debug("Aantal gevonden : " + lijst.size());
            gebruiker = lijst.get(0);
        } else if (lijst != null) {
            LOGGER.debug("Lege lijst");
            throw new NietGevondenException(sessie);
        } else {
            LOGGER.debug("null lijst");
            throw new NietGevondenException(sessie);
        }

        getTransaction().commit();

        return gebruiker;
    }


    public void verwijderAdressenBijRelatie(Relatie relatie) {
        getEm().getTransaction();
        getEm().getNamedQuery("Adres.verwijderAdressenBijRelatie").setParameter("relatie", relatie).executeUpdate();
        getEm().getNamedQuery("Telefoonnummer.verwijderTelefoonnummersBijRelatie").setParameter("relatie", relatie).executeUpdate();
        getEm().getNamedQuery("RekeningNummer.verwijderRekeningNummersBijRelatie").setParameter("relatie", relatie).executeUpdate();
        getEm().getTransaction().commit();
    }


    public void verwijder(Gebruiker gebruiker) {
        getTransaction();
        getEm().delete(gebruiker);
        getTransaction().commit();
    }


    public Gebruiker lees(Long id) {
        getTransaction();

        Gebruiker g = getEm().get(Gebruiker.class, id);

        getTransaction().commit();

        return g;
    }

    public void opslaan(Gebruiker gebruiker) {
        getTransaction();

        if (gebruiker.getId() == null) {
            getSession().save(gebruiker);
        } else {
            getSession().merge(gebruiker);
        }

        getTransaction().commit();
    }

    public List<Relatie> zoekOpGeboortedatum(LocalDate geboortedatum) {
        getTransaction();

        Query query = getEm().getNamedQuery("Relatie.zoekOpGeboortedatum");
        query.setParameter("geboorteDatum", geboortedatum.toDate());

        List<Relatie> result = query.list();

        getTransaction().commit();

        return result;
    }

    public List<Relatie> zoekOpTussenVoegsel(String tussenvoegsel) {
        getTransaction();

        Query query = getEm().getNamedQuery("Gebruiker.zoekOpTussenVoegsel");
        query.setParameter("tussenvoegsel", tussenvoegsel);

        List<Gebruiker> result = query.list();

        List<Relatie> relaties = result.stream()//
                .filter(new RelatiePredicate())//
                .map(new GebruikerNaarRelatie())//
                .collect(Collectors.toList());

        getTransaction().commit();

        return relaties;
    }

    public List<Medewerker> alleGebruikersDieKunnenInloggen() {
        getTransaction();

        Query query = getEm().getNamedQuery("Medewerker.alles");

        List<Medewerker> result = query.list();

        getTransaction().commit();

        return result;
    }

    public List<Relatie> zoekOpVoorletters(String voorletters) {
        getTransaction();

        Query query = getEm().getNamedQuery("Relatie.zoekOpVoorletters");
        query.setParameter("voorletters", voorletters);

        List<Relatie> result = query.list();

        getTransaction().commit();

        return result;
    }

    private class RelatiePredicate implements Predicate<Gebruiker> {
        @Override
        public boolean test(Gebruiker gebruiker) {
            return gebruiker instanceof Relatie;
        }
    }

    private class GebruikerNaarRelatie implements Function<Gebruiker, Relatie> {
        @Override
        public Relatie apply(Gebruiker gebruiker) {
            return (Relatie) gebruiker;
        }
    }
}
