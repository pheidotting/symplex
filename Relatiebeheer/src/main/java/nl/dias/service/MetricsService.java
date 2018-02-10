package nl.dias.service;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.interfaces.Metrics;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static com.codahale.metrics.MetricRegistry.name;
import static com.google.common.collect.Lists.newArrayList;

@Service
public class MetricsService implements Metrics {
    @Inject
    private MetricRegistry metricRegistry;

    public void addMetric(String soortMetric, Class clazz, String extra, Boolean nieuw) {
        String extraTekst = extra == null ? "" : extra;
        String nieuwTekst = nieuw == null ? "" : nieuw ? "Nieuw" : "Bestaand";
        metricRegistry.meter("meter." + name(clazz, soortMetric) + extraTekst + nieuwTekst).mark();
    }

    public List<Timer.Context> addTimerMetric(String naam, Class c) {
        final Timer timer = metricRegistry.timer("timer." + name(c, naam));
        return newArrayList(timer.time());
    }

    public void stop(List<Timer.Context> timers) {
        for (Timer.Context timer : timers) {
            timer.stop();
        }
    }

}
