package nl.lakedigital.djfc.metrics;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import static com.codahale.metrics.MetricRegistry.name;

public class MetricsService {
    private MetricRegistry metricRegistry;

    private Graphite graphite;
    private GraphiteReporter reporter;

    public void setMetricRegistry(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    public void init() {
        graphite = new Graphite(new InetSocketAddress("localhost", 2003));
        reporter = GraphiteReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL).build(graphite);
        reporter.start(1, TimeUnit.MINUTES);
    }

    public void addMetric(String soortMetric, Class clazz, String extra, Boolean nieuw) {
        if (graphite == null) {
            //            init();
        }
        String extraTekst = extra == null ? "" : extra;
        String nieuwTekst = nieuw == null ? "" : nieuw ? "Nieuw" : "Bestaand";
        metricRegistry.meter("meter." + name(clazz, soortMetric) + extraTekst + nieuwTekst).mark();
    }

    public Timer.Context addTimerMetric(String naam, Class c) {
        if (graphite == null) {
            //            init();
        }
        final Timer timer1 = metricRegistry.timer("timer." + name(c, naam));
        return timer1.time();
    }

    public void stop(Timer.Context timer) {
        timer.stop();
    }
}
