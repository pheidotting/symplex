package nl.lakedigital.djfc.web.servlet;

import nl.lakedigital.djfc.repository.TaakRepository;

public class CheckDatabaseConnectie implements Runnable {
    private TaakRepository taakRepository;

    public CheckDatabaseConnectie(TaakRepository taakRepository) {
        this.taakRepository = taakRepository;
    }

    @Override
    public void run() {
        taakRepository.getSession().getTransaction().begin();
        taakRepository.getSession().createSQLQuery("/* ping */ SELECT 1").uniqueResult();
        taakRepository.getSession().getTransaction().commit();
    }

}
