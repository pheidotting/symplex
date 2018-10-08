package nl.dias.repository;

import com.codahale.metrics.Timer;
import nl.dias.domein.Belastingzaken;
import nl.dias.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Repository
public class BelastingzakenRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(BelastingzakenRepository.class);

    @Autowired
    private SessionFactory sessionFactory;
    @Inject
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;
    @Inject
    private MetricsService metricsService;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            LOGGER.trace("{}", e);
            return sessionFactory.openSession();
        }
    }

    protected Session getEm() {
        return sessionFactory.getCurrentSession();
    }

    protected Transaction getTransaction() {
        Transaction transaction = getSession().getTransaction();
        if (transaction.getStatus() != TransactionStatus.ACTIVE) {
            transaction.begin();
        }

        return transaction;
    }

    public void opslaan(Belastingzaken belastingzaken) {
        Timer.Context timer = metricsService.addTimerMetric("opslaan", BelastingzakenRepository.class);

        getTransaction();

        getSession().save(belastingzaken);

        getTransaction().commit();

        List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds = new ArrayList<>();

        SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
        soortEntiteitEnEntiteitId.setSoortEntiteit(SoortEntiteit.BELASTINGZAKEN);
        soortEntiteitEnEntiteitId.setEntiteitId(belastingzaken.getId());

        soortEntiteitEnEntiteitIds.add(soortEntiteitEnEntiteitId);

        entiteitenOpgeslagenRequestSender.send(newArrayList(soortEntiteitEnEntiteitId));

        metricsService.stop(timer);
    }

    public List<Belastingzaken> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
        Timer.Context timer = metricsService.addTimerMetric("alles", BelastingzakenRepository.class);

        getTransaction();

        Query query = getEm().getNamedQuery("Belastingzaken.zoekOpSoortEntiteitEnId");
        query.setParameter("soortEntiteit", soortEntiteit);
        query.setParameter("entiteitId", entiteitId);

        List<Belastingzaken> result = query.list();

        getTransaction().commit();

        metricsService.stop(timer);

        return result;
    }
}
