package nl.dias.service;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import nl.dias.domein.*;
import nl.dias.domein.polis.Polis;
import nl.dias.mapper.Mapper;
import nl.dias.messaging.SoortEntiteitEnEntiteitId;
import nl.dias.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.dias.messaging.sender.VerwijderEntiteitenRequestSender;
import nl.dias.repository.GebruikerRepository;
import nl.dias.repository.InlogPogingRepository;
import nl.dias.repository.KantoorRepository;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.client.oga.TelefoonnummerClient;
import nl.lakedigital.djfc.commons.json.AbstracteJsonEntiteitMetSoortEnId;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonContactPersoon;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

@Service
public class GebruikerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GebruikerService.class);

    @Inject
    private GebruikerRepository gebruikerRepository;
    @Inject
    private PolisService polisService;
    @Inject
    private SchadeService schadeService;
    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private HypotheekService hypotheekService;
    @Inject
    private Mapper mapper;
    @Inject
    private AdresClient adresClient;
    @Inject
    private TelefoonnummerClient telefoonnummerClient;
    @Inject
    private VerwijderEntiteitenRequestSender verwijderEntiteitRequestSender;
    @Inject
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private MetricsService metricsService;
    @Inject
    private InlogPogingRepository inlogPogingRepository;

    public boolean magInloggen(Gebruiker gebruiker) {
        return inlogPogingRepository.magInloggen(gebruiker.getId());
    }

    public List<ContactPersoon> alleContactPersonen(Long bedrijfsId) {
        return gebruikerRepository.alleContactPersonen(bedrijfsId);
    }

    public void koppelenOnderlingeRelatie(Long relatieId, Long relatieMetId, String soortRelatie) {
        LOGGER.info("koppelenOnderlingeRelatie({}, {}, {})", relatieId, relatieMetId, soortRelatie);

        Relatie relatie = (Relatie) gebruikerRepository.lees(relatieId);
        Relatie relatieMet = (Relatie) gebruikerRepository.lees(relatieMetId);

        OnderlingeRelatieSoort onderlingeRelatieSoort = OnderlingeRelatieSoort.valueOf(soortRelatie);
        OnderlingeRelatieSoort onderlingeRelatieSoortTegengesteld = OnderlingeRelatieSoort.getTegenGesteld(onderlingeRelatieSoort);

        OnderlingeRelatie onderlingeRelatie = new OnderlingeRelatie(relatie, relatieMet, false, onderlingeRelatieSoort);//NOSONAR
        OnderlingeRelatie onderlingeRelatieTegengesteld = new OnderlingeRelatie(relatieMet, relatie, false, onderlingeRelatieSoortTegengesteld);//NOSONAR

        //                relatie.getOnderlingeRelaties().add(onderlingeRelatie);
        //                relatieMet.getOnderlingeRelaties().add(onderlingeRelatieTegengesteld);

        gebruikerRepository.opslaan(relatie);
        gebruikerRepository.opslaan(relatieMet);

    }

    public Gebruiker lees(Long id) {
        return gebruikerRepository.lees(id);
    }

    public Relatie leesRelatie(Long id) {
        return (Relatie) this.lees(id);
    }

    public List<Relatie> alleRelaties(Kantoor kantoor) {
        return gebruikerRepository.alleRelaties(kantoor);
    }

    public void opslaan(Gebruiker gebruiker) {
        LOGGER.debug("Opslaan {}", gebruiker);

        gebruikerRepository.opslaan(gebruiker);

        // Als Gebruiker een Relatie is en BSN leeg is, moet er een taak worden aangemaakt
        if (gebruiker instanceof Relatie) {
            nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId();
            soortEntiteitEnEntiteitId.setSoortEntiteit(SoortEntiteit.RELATIE);
            soortEntiteitEnEntiteitId.setEntiteitId(gebruiker.getId());

            entiteitenOpgeslagenRequestSender.send(newArrayList(soortEntiteitEnEntiteitId));
        }
    }

    public void opslaan(final List<JsonContactPersoon> jsonContactPersonen, Long bedrijfId) {
        LOGGER.debug("Opslaan ContactPersonen, aantal {}, bedrijfId {}", jsonContactPersonen.size(), bedrijfId);

        LOGGER.debug("Ophalen bestaande ContactPersonen bij bedrijf");
        List<Long> bestaandeContactPersonen = newArrayList(transform(alleContactPersonen(bedrijfId), contactPersoon -> contactPersoon.getId()));
        LOGGER.debug("Ids bestaande ContactPersonen {}", bestaandeContactPersonen);

        final List<Long> lijstIdsBewaren = newArrayList(filter(transform(jsonContactPersonen, contactPersoon -> {
            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(contactPersoon.getIdentificatie());
            return identificatie.getEntiteitId();
        }), new Predicate<Long>() {
            @Override
            public boolean apply(Long id) {
                return id != null;
            }
        }));
        LOGGER.debug("Ids ContactPersonen uit front-end {}", lijstIdsBewaren);

        //Verwijderen wat niet (meer) voorkomt
        Iterable<Long> teVerwijderen = filter(bestaandeContactPersonen, contactPersoon -> !lijstIdsBewaren.contains(contactPersoon));

        LOGGER.debug("Ids die dus weg kunnen {}", teVerwijderen);
        for (Long id : teVerwijderen) {
            gebruikerRepository.verwijder(lees(id));
        }

        LOGGER.debug("Opslaan ContactPersonen");
        for (JsonContactPersoon jsonContactPersoon : jsonContactPersonen) {
            LOGGER.debug("opslaan {}", jsonContactPersoon);
            ContactPersoon contactPersoon = mapper.map(jsonContactPersoon, ContactPersoon.class);

            gebruikerRepository.opslaan(contactPersoon);
        }
    }

    public void verwijder(Long id) {
        LOGGER.info("Verwijderen gebruiker met id " + id);

        // Eerst ophalen
        Gebruiker gebruiker = gebruikerRepository.lees(id);

        LOGGER.debug("Opgehaalde gebruiker : ");
        if (gebruiker != null) {
            if (gebruiker instanceof Relatie) {
                Relatie relatie = (Relatie) gebruiker;

                List<Hypotheek> hypotheeks = hypotheekService.allesVanRelatie(relatie.getId());
                hypotheekService.verwijder(hypotheeks);

                List<Schade> schades = schadeService.alleSchadesBijRelatie(relatie.getId());
                schadeService.verwijder(schades);

                //TODO verwijderen via Service en daar Bericht opsturen
                List<Polis> polises = polisService.allePolissenBijRelatie(relatie.getId());
                polisService.verwijder(polises);
            }
            // en dan verwijderen
            gebruikerRepository.verwijder(gebruiker);

            LOGGER.debug("Verwijderbericht versturen");
            verwijderEntiteitRequestSender.send(new SoortEntiteitEnEntiteitId(SoortEntiteit.RELATIE, id));
        }
    }

    public Gebruiker zoekOpSessieEnIpAdres(String sessie, String ipadres) throws NietGevondenException {
        return gebruikerRepository.zoekOpSessieEnIpadres(sessie, ipadres);
    }


    public Gebruiker zoek(String emailadres) throws NietGevondenException {
        return gebruikerRepository.zoek(emailadres);
    }

    public Gebruiker zoekOpIdentificatie(String identificatie) throws NietGevondenException {
        return gebruikerRepository.zoekOpIdentificatie(identificatie);
    }

    public List<Gebruiker> zoekOpNaam(String naam) {
        return gebruikerRepository.zoekOpNaam(naam);
    }

    public List<Relatie> zoekRelatieOpRoepnaam(String naam) {
        return gebruikerRepository.zoekRelatieOpRoepnaam(naam);
    }


    public List<Relatie> zoekOpNaamAdresOfPolisNummer(String zoekTerm) {
        LOGGER.debug("zoekOpNaamAdresOfPolisNummer met zoekTerm " + zoekTerm);
        Set<Relatie> relaties = new HashSet<Relatie>();
        for (Gebruiker g : gebruikerRepository.zoekOpNaam(zoekTerm)) {
            if (g instanceof Relatie) {
                relaties.add((Relatie) g);
            }
        }
        LOGGER.debug("Op naam {}", relaties.size());
        relaties.addAll(gebruikerRepository.zoekRelatieOpRoepnaam(zoekTerm));
        LOGGER.debug("Op roepnaam {}", relaties.size());
        relaties.addAll(destilleerRelatie(adresClient.zoeken(zoekTerm)));
        LOGGER.debug("Zoeken op telefoonnummer");
        String zoekTermNumeriek = zoekTerm.replace(" ", "").replace("-", "");
        try {
            Long.valueOf(zoekTermNumeriek);
        } catch (NumberFormatException nfe) {
            zoekTermNumeriek = null;
            LOGGER.trace("", nfe);
        }
        if (zoekTermNumeriek != null) {
            relaties.addAll(destilleerRelatie(telefoonnummerClient.zoeken(zoekTerm)));
        }
        LOGGER.debug("Zoeken op bedrijfnsaam");
        for (Gebruiker g : gebruikerRepository.zoekRelatiesOpBedrijfsnaam(zoekTerm)) {
            relaties.add((Relatie) g);
        }

        LOGGER.debug("Gevonden " + relaties.size() + " Relaties");
        Polis polis = null;
        try {
            polis = polisService.zoekOpPolisNummer(zoekTerm);
        } catch (NoResultException e) {
            LOGGER.trace("Niks gevonden ", e);
        }
        if (polis != null) {
            relaties.add((Relatie) lees(polis.getRelatie()));
        }
        LOGGER.debug("Gevonden " + relaties.size() + " Relaties");

        List<Relatie> ret = new ArrayList<>();
        ret.addAll(newArrayList(filter(relaties, new Predicate<Relatie>() {
            @Override
            public boolean apply(Relatie relatie) {
                return relatie != null;
            }
        })));

        return ret;
    }

    private List<Relatie> destilleerRelatie(List<? extends AbstracteJsonEntiteitMetSoortEnId> entiteitenMetSoortEnId) {
        List<Relatie> relaties = newArrayList(transform(newArrayList(filter(entiteitenMetSoortEnId, new Predicate<AbstracteJsonEntiteitMetSoortEnId>() {
            @Override
            public boolean apply(AbstracteJsonEntiteitMetSoortEnId adres) {
                return adres.getSoortEntiteit().equals(nl.lakedigital.djfc.domain.SoortEntiteit.RELATIE.name());
            }
        })), new Function<AbstracteJsonEntiteitMetSoortEnId, Relatie>() {
            @Override
            public Relatie apply(AbstracteJsonEntiteitMetSoortEnId adres) {
                Gebruiker gebruiker = gebruikerRepository.lees(adres.getEntiteitId());
                if (gebruiker instanceof Relatie) {
                    return (Relatie) gebruikerRepository.lees(adres.getEntiteitId());
                }
                return null;
            }
        }));

        return relaties;
    }

    public List<Relatie> zoekOpGeboortedatum(LocalDate geboortedatum) {
        return gebruikerRepository.zoekOpGeboortedatum(geboortedatum);
    }

    public List<Relatie> zoekOpTussenVoegsel(String tussenvoegsel) {
        return null;
    }

    public List<Relatie> zoekOpVoorletters(String voorletters) {
        return null;
    }

    public List<Medewerker> alleMedewerkers(Kantoor kantoor) {
        return gebruikerRepository.alleMedewerkers(kantoor);
    }
}
