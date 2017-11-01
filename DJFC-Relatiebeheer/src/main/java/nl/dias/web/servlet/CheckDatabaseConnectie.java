package nl.dias.web.servlet;

import nl.dias.repository.GebruikerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckDatabaseConnectie implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckDatabaseConnectie.class);

    private GebruikerRepository gebruikerRepository;

    public CheckDatabaseConnectie(GebruikerRepository gebruikerRepository) {
        this.gebruikerRepository = gebruikerRepository;
    }

    @Override
    public void run() {
        gebruikerRepository.getSession().getTransaction().begin();
        gebruikerRepository.getSession().createSQLQuery("/* ping */ SELECT 1").uniqueResult();
        gebruikerRepository.getSession().getTransaction().commit();
    }

}