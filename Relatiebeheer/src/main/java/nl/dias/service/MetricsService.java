package nl.dias.service;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.interfaces.Metrics;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static com.codahale.metrics.MetricRegistry.name;

@Service
public class MetricsService implements Metrics {
    @Inject
    private MetricRegistry metricRegistry;

    public void addMetric(String soortMetric, Class clazz, String extra, Boolean nieuw) {
        String extraTekst = extra == null ? "" : extra;
        String nieuwTekst = nieuw == null ? "" : nieuw ? "Nieuw" : "Bestaand";
        metricRegistry.meter("meter." + name(clazz, soortMetric) + extraTekst + nieuwTekst).mark();

        push();
    }

    public Timer.Context addTimerMetric(String naam, Class c) {
        final Timer timer = metricRegistry.timer("timer." + name(c, naam));
        return timer.time();
    }

    public void stop(Timer.Context timer) {
            timer.stop();

        push();
    }

    private void push() {
        //        final Graphite graphite = new Graphite(new InetSocketAddress("localhost", 2003));
        //        final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry)
        //                .convertRatesTo(TimeUnit.SECONDS)
        //                .convertDurationsTo(TimeUnit.MILLISECONDS)
        //                .filter(MetricFilter.ALL)
        //                .build(graphite);
        //        reporter.start(1, TimeUnit.MINUTES);
    }
}
