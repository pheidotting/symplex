package nl.dias.service;

import com.codahale.metrics.MetricRegistry;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MetricsService {
    @Inject
    private MetricRegistry metricRegistry;

    public enum SoortMetric {
        BEDRIJF_OPSLAAN("opslaanBedrijf"), //
        BEDRIJF_VERWIJDEREN("verwijdereBedrijf"), //
        BIJLAGE_UPLOADEN("uploadenBijlage"), //
        RELATIE_OPSLAAN("opslaanRelatie"), //
        RELATIE_VERWIJDEREN("verwijderenRelatie"), //
        CONTACTPERSOON_OPSLAAN("opslaanContactPersoon"),//
        WACHTWOORD_WIJZIGEN("wijzigenWachtwoord"),//
        HYPOTHEEK_OPSLAAN("opslaanHypotheek"),//
        HYPOTHEEK_VERWIJDEREN("verwijderenHypotheek"),//
        POLIS_OPSLAAN("opslaanPolis"),//
        POLIS_VERWIJDEREN("verwijderenPolis"),//
        SCHADE_OPSLAAN("opslaanSchade"),//
        SCHADE_VERWIJDEREN("verwijderenSchade"),//
        INLOGGEN("inloggen"),//
        INLOGGEN_ONJUIST_WACHTWOORD("inloggenOnjuistWachtwoord"),//
        INLOGGEN_ONBEKENDE_GEBRUIKER("inloggenOnbekendeGebruiker"),//
        INLOGGEN_TEVEEL_FOUTIEVE_POGINGEN("inloggenTeveelFoutievePogingen"),//
        WACHTWOORD_VERGETEN("wachtwoordVergeten"), KANTOOR_AANMELDEN("aanmeldenKantoor");

        private String omschrijving;

        SoortMetric(String omschrijving) {
            this.omschrijving = omschrijving;
        }

        public String getOmschrijving() {
            return omschrijving;
        }
    }

    public void addMetric(SoortMetric soortMetric, String extra, Boolean nieuw) {
        String extraTekst = extra == null ? "" : extra;
        String nieuwTekst = nieuw == null ? "" : nieuw ? "Nieuw" : "Bestaand";
        metricRegistry.meter(soortMetric.getOmschrijving() + extraTekst + nieuwTekst).mark();
    }
}
