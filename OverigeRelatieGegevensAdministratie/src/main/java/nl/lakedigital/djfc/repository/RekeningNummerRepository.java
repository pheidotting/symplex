package nl.lakedigital.djfc.repository;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.domain.RekeningNummer;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class RekeningNummerRepository extends AbstractRepository<RekeningNummer> {
    public RekeningNummerRepository() {
        super(RekeningNummer.class);
    }

    @Inject
    private MetricsService metricsService;

    public List<RekeningNummer> alles() {
        Timer.Context timer = metricsService.addTimerMetric("alles", RekeningNummerRepository.class);

        getSession().getTransaction().begin();

        List<RekeningNummer> adressen = getSession().createQuery("select a from RekeningNummer a").list();

        getSession().getTransaction().commit();

        metricsService.stop(timer);

        return adressen;
    }
}
