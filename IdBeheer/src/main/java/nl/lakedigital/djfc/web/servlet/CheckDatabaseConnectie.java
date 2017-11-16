package nl.lakedigital.djfc.web.servlet;

import nl.lakedigital.djfc.repository.IdentificatieRepository;

public class CheckDatabaseConnectie implements Runnable {
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
