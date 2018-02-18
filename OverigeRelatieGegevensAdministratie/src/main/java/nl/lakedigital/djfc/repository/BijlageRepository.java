package nl.lakedigital.djfc.repository;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.domain.Bijlage;
import nl.lakedigital.djfc.domain.GroepBijlages;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;


@Repository
public class BijlageRepository extends AbstractRepository<Bijlage> {
    public BijlageRepository() {
        super(Bijlage.class);
    }

    @Inject
    private MetricsService metricsService;

    public GroepBijlages leesGroepBijlages(Long id) {
        Timer.Context timer = metricsService.addTimerMetric("leesGroepBijlages", BijlageRepository.class);

        getTransaction().begin();

        GroepBijlages groepBijlages = getSession().get(GroepBijlages.class, id);

        getTransaction().commit();

        metricsService.stop(timer);

        return groepBijlages;
    }

    public List<Bijlage> alles() {
        Timer.Context timer = metricsService.addTimerMetric("alles", BijlageRepository.class);

        getSession().getTransaction().begin();

        List<Bijlage> adressen = getSession().createQuery("select a from Bijlage a").list();

        getSession().getTransaction().commit();

        metricsService.stop(timer);

        return adressen;
    }

    public void opslaanGroepBijlages(GroepBijlages groepBijlages) {
        Timer.Context timer = metricsService.addTimerMetric("opslaanGroepBijlages", BijlageRepository.class);

        getTransaction().begin();

        if (groepBijlages.getId() == null) {
            getSession().save(groepBijlages);
        } else {
            getSession().merge(groepBijlages);
        }

        getTransaction().commit();

        metricsService.stop(timer);
    }

    public List<GroepBijlages> alleGroepenBijlages(SoortEntiteit soortEntiteit, Long entiteitId) {
        Timer.Context timer = metricsService.addTimerMetric("alleGroepenBijlages", BijlageRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Bijlage.zoekBijEntiteitMetGroep");
        query.setParameter("soortEntiteit", soortEntiteit);
        query.setParameter("entiteitId", entiteitId);

        List<Bijlage> bijlages = query.list();

        Set<GroepBijlages> groepen = new HashSet<>();

        for (Bijlage bijlage : bijlages) {
            groepen.add(bijlage.getGroepBijlages());
        }

        getTransaction().commit();

        metricsService.stop(timer);

        return newArrayList(groepen);
    }

    public List<GroepBijlages> alleGroepenBijlages() {
        Timer.Context timer = metricsService.addTimerMetric("alleGroepenBijlages", BijlageRepository.class);

        getTransaction();

        Query query = getSession().getNamedQuery("Bijlage.zoekBijEntiteitMetGroepAlles");

        List<Bijlage> bijlages = query.list();

        Set<GroepBijlages> groepen = new HashSet<>();

        for (Bijlage bijlage : bijlages) {
            groepen.add(bijlage.getGroepBijlages());
        }

        getTransaction().commit();

        metricsService.stop(timer);

        return newArrayList(groepen);
    }
}