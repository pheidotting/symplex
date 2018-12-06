package nl.lakedigital.djfc.repository;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.domain.TelefonieBestand;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class TelefonieBestandRepository {

    @Autowired
    private SessionFactory sessionFactory;
    @Inject
    private MetricsService metricsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TelefonieBestandRepository.class);

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Transaction getTransaction() {
        Transaction transaction = getSession().getTransaction();
        if (transaction.getStatus() != TransactionStatus.ACTIVE) {
            transaction.begin();
        }

        return transaction;
    }

    public List<TelefonieBestand> alles() {
        Timer.Context timer = metricsService.addTimerMetric("alles", TelefonieBestandRepository.class);

        getTransaction().begin();

        List<TelefonieBestand> telefonieBestands = getSession().createQuery("select a from TelefonieBestand a").list();

        getTransaction().commit();

        metricsService.stop(timer);

        return telefonieBestands;
    }

    public List<TelefonieBestand> allesMetTelefoonnummer(String telefoonnummer) {
        Timer.Context timer = metricsService.addTimerMetric("allesMetTelefoonnummer", TelefonieBestandRepository.class);

        getTransaction().begin();

        Query query = getSession().getNamedQuery("TelefonieBestand.allesMetTelefoonnummer");
        query.setParameter("telefoonnummer", telefoonnummer);

        List<TelefonieBestand> telefonieBestands = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return telefonieBestands;
    }

    public void opslaan(List<TelefonieBestand> telefonieBestands) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", TelefonieBestandRepository.class);

        getTransaction().begin();

        LOGGER.trace("Start opslaan telefonieBestands");

        for (TelefonieBestand telefonieBestand : telefonieBestands) {
            LOGGER.trace("Opslaan {}", telefonieBestand.getBestandsnaam());
            if (telefonieBestand.getId() == null) {
                getSession().save(telefonieBestand);
            } else {
                getSession().merge(telefonieBestand);
            }
        }

        LOGGER.trace("Einde opslaan telefonieBestands");

        getTransaction().commit();

        metricsService.stop(timer);
    }
}
