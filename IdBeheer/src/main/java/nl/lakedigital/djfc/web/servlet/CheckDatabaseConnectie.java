package nl.lakedigital.djfc.web.servlet;

import nl.lakedigital.djfc.repository.IdentificatieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckDatabaseConnectie implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckDatabaseConnectie.class);

    private IdentificatieRepository identificatieRepository;

    public CheckDatabaseConnectie(IdentificatieRepository identificatieRepository) {
        this.identificatieRepository = identificatieRepository;
    }

    @Override
    public void run() {
        identificatieRepository.getSession().getTransaction().begin();
        identificatieRepository.getSession().createSQLQuery("/* ping */ SELECT 1").uniqueResult();
        identificatieRepository.getSession().getTransaction().commit();
    }

}
