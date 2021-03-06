package nl.dias.web.servlet;

import nl.dias.repository.GebruikerRepository;

public class CheckDatabaseConnectie implements Runnable {
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