package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.Telefoonnummer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TelefoonnummerRepository extends AbstractRepository<Telefoonnummer> {
    public TelefoonnummerRepository() {
        super(Telefoonnummer.class);
    }

    public List<Telefoonnummer> alles() {
        getSession().getTransaction().begin();

        List<Telefoonnummer> adressen = getSession().createQuery("select a from Telefoonnummer a").list();

        getSession().getTransaction().commit();

        return adressen;
    }
}
