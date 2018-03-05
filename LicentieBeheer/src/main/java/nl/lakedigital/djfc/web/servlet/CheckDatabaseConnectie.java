package nl.lakedigital.djfc.web.servlet;

import nl.lakedigital.djfc.repository.LicentieRepository;

public class CheckDatabaseConnectie implements Runnable {
    private LicentieRepository identificatieRepository;

    public CheckDatabaseConnectie(LicentieRepository identificatieRepository) {
        this.identificatieRepository = identificatieRepository;
    }

    @Override
    public void run() {
        identificatieRepository.getSession().getTransaction().begin();
        identificatieRepository.getSession().createSQLQuery("/* ping */ SELECT 1").uniqueResult();
        identificatieRepository.getSession().getTransaction().commit();
    }

}
