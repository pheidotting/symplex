package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.RekeningNummer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RekeningNummerRepository extends AbstractRepository<RekeningNummer> {
    public RekeningNummerRepository() {
        super(RekeningNummer.class);
    }

    public List<RekeningNummer> alles() {
        getSession().getTransaction().begin();

        List<RekeningNummer> adressen = getSession().createQuery("select a from RekeningNummer a").list();

        getSession().getTransaction().commit();

        return adressen;
    }
}
