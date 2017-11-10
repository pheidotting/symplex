package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.Opmerking;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OpmerkingRepository extends AbstractRepository<Opmerking> {
    public OpmerkingRepository() {
        super(Opmerking.class);
    }

    public List<Opmerking> alles() {
        getSession().getTransaction().begin();

        List<Opmerking> adressen = getSession().createQuery("select a from Opmerking a").list();

        getSession().getTransaction().commit();

        return adressen;
    }
}