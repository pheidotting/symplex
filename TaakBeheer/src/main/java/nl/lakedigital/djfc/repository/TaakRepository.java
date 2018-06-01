package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.metrics.MetricsService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class TaakRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaakRepository.class);

    @Autowired
    private SessionFactory sessionFactory;
    @Inject
    private MetricsService metricsService;

    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {//NOSONAR
            return sessionFactory.openSession();
        }
    }


}
