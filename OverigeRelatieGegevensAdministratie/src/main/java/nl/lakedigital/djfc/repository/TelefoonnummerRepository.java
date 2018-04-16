package nl.lakedigital.djfc.repository;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.domain.Telefoonnummer;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class TelefoonnummerRepository extends AbstractRepository<Telefoonnummer> {

    @Inject
    private MetricsService metricsService;

    public TelefoonnummerRepository() {
        super(Telefoonnummer.class);
    }

    public List<Telefoonnummer> alles() {
        Timer.Context timer = metricsService.addTimerMetric("alles", TelefoonnummerRepository.class);

        getSession().getTransaction().begin();

        List<Telefoonnummer> adressen = getSession().createQuery("select a from Telefoonnummer a").list();

        getSession().getTransaction().commit();

        metricsService.stop(timer);

        return adressen;
    }
}
