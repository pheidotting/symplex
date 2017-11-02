package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.Bijlage;
import nl.lakedigital.djfc.domain.GroepBijlages;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;


@Repository
public class BijlageRepository extends AbstractRepository<Bijlage> {
    public BijlageRepository() {
        super(Bijlage.class);
    }

    public GroepBijlages leesGroepBijlages(Long id) {
        getTransaction().begin();

        GroepBijlages groepBijlages = getSession().get(GroepBijlages.class, id);

        getTransaction().commit();

        return groepBijlages;
    }

    public List<Bijlage> alles() {
        getSession().getTransaction().begin();

        List<Bijlage> adressen = getSession().createQuery("select a from Bijlage a").list();

        getSession().getTransaction().commit();

        return adressen;
    }

    public void opslaanGroepBijlages(GroepBijlages groepBijlages) {
        getTransaction().begin();

        if (groepBijlages.getId() == null) {
            getSession().save(groepBijlages);
        } else {
            getSession().merge(groepBijlages);
        }

        getTransaction().commit();
    }

    public List<GroepBijlages> alleGroepenBijlages(SoortEntiteit soortEntiteit, Long entiteitId) {
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

        return newArrayList(groepen);
    }

    public List<GroepBijlages> alleGroepenBijlages() {
        getTransaction();

        Query query = getSession().getNamedQuery("Bijlage.zoekBijEntiteitMetGroepAlles");

        List<Bijlage> bijlages = query.list();

        Set<GroepBijlages> groepen = new HashSet<>();

        for (Bijlage bijlage : bijlages) {
            groepen.add(bijlage.getGroepBijlages());
        }

        getTransaction().commit();

        return newArrayList(groepen);
    }
}