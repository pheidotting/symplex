package nl.lakedigital.djfc.web.servlet;

import nl.lakedigital.djfc.repository.LicentieRepository;

public class CheckDatabaseConnectie implements Runnable {
    private LicentieRepository licentieRepository;

    public CheckDatabaseConnectie(LicentieRepository licentieRepository) {
        this.licentieRepository = licentieRepository;
    }

    @Override
    public void run() {
        licentieRepository.getSession().getTransaction().begin();
        licentieRepository.getSession().createSQLQuery("/* ping */ SELECT 1").uniqueResult();
        licentieRepository.getSession().getTransaction().commit();
    }

}
