package nl.lakedigital.djfc.repository;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.domain.Adres;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdresRepository extends AbstractRepository<Adres> {
    public AdresRepository() {
        super(Adres.class);
    }

    @Inject
    private MetricsService metricsService;

    public List<Adres> alles() {
        Timer.Context timer = metricsService.addTimerMetric("alles", AdresRepository.class);

        getSession().getTransaction().begin();

        List<Adres> adressen = getSession().createQuery("select a from Adres a").list();

        getSession().getTransaction().commit();

        metricsService.stop(timer);

        return adressen;
    }

    public List<Adres> alleAdressenBijLijstMetEntiteiten(List<Long> ids, SoortEntiteit soortEntiteit) {
        Timer.Context timer = metricsService.addTimerMetric("alleAdressenBijLijstMetEntiteiten", AdresRepository.class);

        List<Adres> result = new ArrayList<>();

        for (Long id : ids) {
            result.addAll(alles(soortEntiteit, id));
        }

        metricsService.stop(timer);

        return result;
    }

    public List<Adres> zoekOpAdres(String zoekterm) {
        Timer.Context timer = metricsService.addTimerMetric("zoekOpAdres", AdresRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Adres.zoekenOpAdres");
        query.setParameter("zoekTerm", zoekterm);

        List<Adres> adressen = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return adressen;
    }

    public List<Adres> zoekOpPostcode(String zoekterm) {
        Timer.Context timer = metricsService.addTimerMetric("zoekOpPostcode", AdresRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Adres.zoekenOpPostcode");
        query.setParameter("zoekTerm", zoekterm);

        List<Adres> adressen = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return adressen;
    }

    public List<Adres> zoekOpPlaats(String zoekterm) {
        Timer.Context timer = metricsService.addTimerMetric("zoekOpPlaats", AdresRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Adres.zoekenOpPlaats");
        query.setParameter("zoekTerm", zoekterm);

        List<Adres> adressen = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return adressen;
    }
}
