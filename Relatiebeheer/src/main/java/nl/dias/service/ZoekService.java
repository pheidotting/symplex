package nl.dias.service;

import nl.dias.ZoekResultaat;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Relatie;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.client.polisadministratie.SchadeClient;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.commons.json.JsonSchade;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class ZoekService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZoekService.class);

    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private PolisClient polisClient;
    @Inject
    private SchadeClient schadeClient;
    @Inject
    private AdresClient adresClient;

    public ZoekResultaat zoek(String naam, LocalDate geboortedatum, String tussenvoegsel, String polisnummer, String voorletters, String schadenummer, String adres, String postcode, String woonplaats, Boolean bedrijf) {
        ZoekResultaat zoekResultaat = new ZoekResultaat();
        boolean alleenBedrijven = bedrijf == Boolean.TRUE;

        Set<Relatie> relaties = new HashSet<>();
        Set<Bedrijf> bedrijven = new HashSet<>();

        if (naam != null && !"".equals(naam)) {
            relaties.addAll(gebruikerService.zoekOpNaam(naam).stream().map(gebruiker -> (Relatie) gebruiker).collect(Collectors.toList()));
            relaties.addAll(gebruikerService.zoekRelatieOpRoepnaam(naam));
            bedrijven.addAll(bedrijfService.zoekOpNaam(naam));
        }
        if (geboortedatum != null) {
            List<Relatie> result = gebruikerService.zoekOpGeboortedatum(geboortedatum);
            if (result != null) {
                relaties.addAll(result);
            }
        }
        if (tussenvoegsel != null && !"".equals(tussenvoegsel)) {
            List<Relatie> result = gebruikerService.zoekOpTussenVoegsel(tussenvoegsel);
            if (result != null) {
                relaties.addAll(result);
            }
        }
        if (polisnummer != null && !"".equals(polisnummer)) {
            LOGGER.debug("Zoeken op polisnummer {}", polisnummer);
            List<JsonPolis> polissen = polisClient.zoekOpPolisNummer(polisnummer);
            for (JsonPolis polis : polissen) {
                LOGGER.debug("Polis gevonden: {}", ReflectionToStringBuilder.toString(polis));
                if ("RELATIE".equals(polis.getSoortEntiteit())) {
                    LOGGER.debug("Hoort bij een Relatie met id {}", polis.getEntiteitId());
                    relaties.add((Relatie) gebruikerService.lees(polis.getEntiteitId()));
                } else if ("BEDRIJF".equals(polis.getSoortEntiteit())) {
                    LOGGER.debug("Hoort bij een Bedrijf met id {}", polis.getEntiteitId());
                    bedrijven.add(bedrijfService.lees(polis.getEntiteitId()));
                }
            }
        }
        if (voorletters != null && !"".equals(voorletters)) {
            List<Relatie> result = gebruikerService.zoekOpVoorletters(voorletters);
            if (result != null) {
                relaties.addAll(result);
            }
        }
        if (schadenummer != null && !"".equals(schadenummer)) {
            List<JsonSchade> schades = schadeClient.zoekOpSchadeNummerMaatschappij(schadenummer);
            for (JsonSchade schade : schades) {
                JsonPolis polis = polisClient.lees(String.valueOf(schade.getPolis()));
                if (polis != null) {
                    if ("RELATIE".equals(polis.getSoortEntiteit())) {
                        LOGGER.debug("Hoort bij een Relatie met id {}", polis.getEntiteitId());
                        relaties.add((Relatie) gebruikerService.lees(polis.getEntiteitId()));
                    } else if ("BEDRIJF".equals(polis.getSoortEntiteit())) {
                        LOGGER.debug("Hoort bij een Bedrijf met id {}", polis.getEntiteitId());
                        bedrijven.add(bedrijfService.lees(polis.getEntiteitId()));
                    }
                }
            }
        }
        if (adres != null && !"".equals(adres)) {
            LOGGER.debug("Zoeken op adres : {}", adres);
            List<JsonAdres> adressen = adresClient.zoekOpAdres(adres);

            LOGGER.debug("{} adressen gevonden", adressen.size());
            for (JsonAdres adres1 : adressen) {
                LOGGER.debug("{}", adres1);
            }

            relaties.addAll(adressen.stream()//
                    .filter(adres1 -> "RELATIE".equals(adres1.getSoortEntiteit()))//
                    .map(adres1 -> {
                        LOGGER.debug("Relatie met id {}", adres1.getEntiteitId());
                        Gebruiker gebruiker = gebruikerService.lees(adres1.getEntiteitId());//
                        LOGGER.debug("{}", gebruiker);
                        return (Relatie) gebruiker;
                    }).filter(relatie -> relatie != null).collect(Collectors.toList()));
            bedrijven.addAll(adressen.stream()//
                    .filter(adres1 -> "BEDRIJF".equals(adres1.getSoortEntiteit()))//
                    .map(adres1 -> bedrijfService.lees(adres1.getEntiteitId()))//
                    .filter(bedrijf1 -> bedrijf1 != null).collect(Collectors.toList()));
        }
        if (postcode != null && !"".equals(postcode)) {
            List<JsonAdres> adressen = adresClient.zoekOpPostcode(postcode);

            relaties.addAll(adressen.stream()//
                    .filter(adres1 -> "RELATIE".equals(adres1.getSoortEntiteit()))//
                    .map(adres1 -> (Relatie) gebruikerService.lees(adres1.getEntiteitId()))//
                    .filter(relatie -> relatie != null).collect(Collectors.toList()));
            bedrijven.addAll(adressen.stream()//
                    .filter(adres1 -> "BEDRIJF".equals(adres1.getSoortEntiteit()))//
                    .map(adres1 -> bedrijfService.lees(adres1.getEntiteitId()))//
                    .filter(bedrijf1 -> bedrijf1 != null).collect(Collectors.toList()));
        }
        if (woonplaats != null && !"".equals(woonplaats)) {
            List<JsonAdres> adressen = adresClient.zoekOpPlaats(woonplaats);

            relaties.addAll(adressen.stream()//
                    .filter(adres1 -> "RELATIE".equals(adres1.getSoortEntiteit()))//
                    .map(adres1 -> (Relatie) gebruikerService.lees(adres1.getEntiteitId()))//
                    .filter(relatie -> relatie != null).collect(Collectors.toList()));
            bedrijven.addAll(adressen.stream()//
                    .filter(adres1 -> "BEDRIJF".equals(adres1.getSoortEntiteit()))//
                    .map(adres1 -> bedrijfService.lees(adres1.getEntiteitId()))//
                    .filter(bedrijf1 -> bedrijf1 != null).collect(Collectors.toList()));
        }

        if (!alleenBedrijven) {
            zoekResultaat.setRelaties(newArrayList(relaties));
        }
        zoekResultaat.setBedrijven(newArrayList(bedrijven));

        return zoekResultaat;
    }
}
