package nl.dias.service;

import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.SoortVerzekering;
import nl.dias.domein.predicates.PolisOpSchermNaamPredicate;
import nl.dias.domein.predicates.PolissenOpSoortPredicate;
import nl.dias.domein.transformers.PolisToSchermNaamTransformer;
import nl.dias.messaging.SoortEntiteitEnEntiteitId;
import nl.dias.messaging.sender.VerwijderEntiteitenRequestSender;
import nl.dias.repository.KantoorRepository;
import nl.dias.repository.PolisRepository;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.collect.Iterables.*;
import static com.google.common.collect.Lists.newArrayList;

@Service
public class PolisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisService.class);

    @Inject
    private PolisRepository polisRepository;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private VerzekeringsMaatschappijService verzekeringsMaatschappijService;
    @Inject
    private List<Polis> polissen;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private SchadeService schadeService;
    @Inject
    private VerwijderEntiteitenRequestSender verwijderEntiteitRequestSender;

    public List<String> allePolisSoorten(final SoortVerzekering soortVerzekering) {
        Iterable<Polis> poli = filter(polissen, new PolissenOpSoortPredicate(soortVerzekering));
        newArrayList(poli).sort((o1, o2) -> o1.getSchermNaam().compareTo(o2.getSchermNaam()));

        Iterable<String> polisString = transform(poli, new PolisToSchermNaamTransformer());

        return newArrayList(polisString);
    }

    public void beeindigen(Long id) {
        Polis polis = polisRepository.lees(id);

        polis.setEindDatum(new LocalDate());
        polisRepository.opslaan(polis);
    }

    public void opslaan(Polis polis) {
        LOGGER.debug(ReflectionToStringBuilder.toString(polis));

        polisRepository.opslaan(polis);

        LOGGER.debug("{}", lees(polis.getId()));
    }

    public Polis lees(Long id) {
        return polisRepository.lees(id);
    }

    public Polis zoekOpPolisNummer(String polisNummer) {
        try {
            return polisRepository.zoekOpPolisNummer(polisNummer, kantoorRepository.lees(1L));
        } catch (NoResultException e) {
            LOGGER.debug("Niks gevonden ", e);
            return null;
        }
    }

    public void verwijder(List<Polis> polissen) {
        for (Polis polis : polissen) {
            verwijderEntiteitRequestSender.send(new SoortEntiteitEnEntiteitId(SoortEntiteit.POLIS, polis.getId()));
        }

        polisRepository.verwijder(polissen);
    }

    public void verwijder(String id) {
        LOGGER.debug("Ophalen Polis");
        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(id);
        Polis polis = polisRepository.lees(identificatie.getEntiteitId());

        if (polis == null) {
            throw new IllegalArgumentException("Geen Polis gevonden met id " + id);
        }
        LOGGER.debug("Polis gevonden : " + polis);

        //        LOGGER.debug("Ophalen Relatie");
        //        Relatie relatie = (Relatie) gebruikerService.lees(polis.getRelatie().getId());
        //
        //        LOGGER.debug("Verwijderen Polis bij Relatie");
        //        relatie.getPolissen().remove(polis);
        //        LOGGER.debug("Kijken of de Polis nog bij een bedrijf zit");
        //
        //        gebruikerService.opslaan(relatie);

        polisRepository.verwijder(polis);
    }

    public List<Polis> allePolissenBijRelatie(Long relatie) {
        List<Polis> polissen = polisRepository.allePolissenBijRelatie(relatie);

        return polissen.stream().map(new Function<Polis, Polis>() {
            @Override
            public Polis apply(Polis polis) {
                polis.setSchades(schadeService.alleSchadesBijPolis(polis.getId()));
                return polis;
            }
        }).collect(Collectors.toList());
    }

    public List<Polis> allePolissenBijBedrijf(Long bedrijf) {
        return polisRepository.allePolissenBijBedrijf(bedrijf);
    }

    public Polis definieerPolisSoort(String soort) {
        LOGGER.debug("definieerPolisSoort {}", soort);
        Polis p = getOnlyElement(filter(polissen, new PolisOpSchermNaamPredicate(soort)));

        LOGGER.debug("Gevonden : {}", p.getClass());
        return p.nieuweInstantie();
    }

    public void setDiscriminatorValue(String discriminatorValue, Polis polis) {
        polisRepository.setDiscriminatorValue(discriminatorValue, polis);
    }
}
