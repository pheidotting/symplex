package nl.lakedigital.djfc.mariadbserver;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MariaDBServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MariaDBServer.class);
    private String poortNummer;

    public static void main(String[] args) {
        new MariaDBServer().init();
    }

    public void init() {
        DBConfigurationBuilder configBuilder = DBConfigurationBuilder.newBuilder();
        configBuilder.setPort(Integer.valueOf(getPoortNummer())); // OR, default: setPort(0); => autom. detect free port
        //        configBuilder.setDataDir("/home/theapp/db"); // just an example
        try {
            System.out.println("Starting MariaDB op poort " + getPoortNummer());//NOSONAR
            DB db = DB.newEmbeddedDB(Integer.valueOf(getPoortNummer()));
            db.start();
            System.out.println("Db started");//NOSONAR
        } catch (ManagedProcessException mpe) {
            LOGGER.error("{}", mpe);
            mpe.printStackTrace();
        }
    }

    public String getPoortNummer() {
        if (poortNummer == null) {
            poortNummer = "3308";
        }
        return poortNummer;
    }

    public void setPoortNummer(String poortNummer) {
        this.poortNummer = poortNummer;
    }
}
