package nl.lakedigital.djfc.repository;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.domain.Opmerking;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class OpmerkingRepository extends AbstractRepository<Opmerking> {
    public OpmerkingRepository() {
        super(Opmerking.class);
    }

    @Inject
    private MetricsService metricsService;

    public List<Opmerking> alles() {
        Timer.Context timer = metricsService.addTimerMetric("alles", OpmerkingRepository.class);

        getSession().getTransaction().begin();

        List<Opmerking> adressen = getSession().createQuery("select a from Opmerking a").list();

        getSession().getTransaction().commit();

        metricsService.stop(timer);

        return adressen;
    }
}