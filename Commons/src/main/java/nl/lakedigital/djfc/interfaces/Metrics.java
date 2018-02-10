package nl.lakedigital.djfc.interfaces;

import com.codahale.metrics.Timer;

import java.util.List;

public interface Metrics {
    void addMetric(String soortMetric, Class clazz, String extra, Boolean nieuw);

    List<Timer.Context> addTimerMetric(String naam, Class c);

    void stop(List<Timer.Context> timers);
}
