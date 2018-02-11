package nl.lakedigital.djfc.interfaces;

import com.codahale.metrics.Timer;

public interface Metrics {
    void addMetric(String soortMetric, Class clazz, String extra, Boolean nieuw);

    Timer.Context addTimerMetric(String naam, Class c);

    void stop(Timer.Context timers);
}
