package nl.dias.service;

import com.codahale.metrics.MetricRegistry;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MetricsService {
    @Inject
    private MetricRegistry metricRegistry;

    public enum SoortMetric {
        BIJLAGE_UPLOADEN("uploadenBijlage"), RELATIE_OPSLAAN("opslaanRelatie"), CONTACTPERSOON_OPSLAAN("opslaanContactPersoon");

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
