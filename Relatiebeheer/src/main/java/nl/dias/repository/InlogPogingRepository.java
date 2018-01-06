package nl.dias.repository;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import nl.dias.domein.InlogPoging;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

@Repository
public class InlogPogingRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(InlogPogingRepository.class);
    private static final int MAX_RESULTS = 30;

    @Autowired
    private SessionFactory sessionFactory;

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

    public void opslaanNieuwePoging(Long gebruikerId, boolean gelukt, String ipadres) {
        InlogPoging inlogPoging;
        File database = new File("GeoLite2-City.mmdb");
        String ip = null;
        StringBuffer adres = new StringBuffer();
        Double latitude = null;
        Double longitude = null;
        if (gelukt) {
            try (DatabaseReader dbReader = new DatabaseReader.Builder(database).build()) {
                if (ipadres == null || "".equals(ipadres) || "127.0.0.1".equals(ipadres) || "0.0.0.0".equals(ipadres) || "0:0:0:0:0:0:0:1".equals(ipadres)) {
                    URL whatismyip = new URL("http://checkip.amazonaws.com");
                    BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

                    ip = in.readLine();
                }
                InetAddress ipAddress = InetAddress.getByName(ip);
                CityResponse response = dbReader.city(ipAddress);

                String countryName = response.getCountry().getName();
                String cityName = response.getCity().getName();
                String postal = response.getPostal().getCode();
                String state = response.getLeastSpecificSubdivision().getName();

                adres.append(countryName).append("-");
                adres.append(cityName).append("-");
                adres.append(postal).append("-");
                adres.append(state);

                latitude = response.getLocation().getLatitude();
                longitude = response.getLocation().getLongitude();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GeoIp2Exception e) {
                e.printStackTrace();
            }
        }

        inlogPoging = new InlogPoging(gebruikerId, gelukt, ip, adres.toString(), latitude, longitude);
        getTransaction();

        if (gelukt) {
            getSession().getNamedQuery("InlogPoging.verwijderOudeMislukte").setParameter("gebruikerId", gebruikerId).executeUpdate();
        }
        LOGGER.debug("Opslaan InlogPoging {}", ReflectionToStringBuilder.toString(inlogPoging));
        getSession().save(inlogPoging);

        getTransaction().commit();
    }

    public boolean magInloggen(Long gebruikerId) {
        getTransaction();

        LocalDateTime tijdstip = LocalDateTime.now().minusMinutes(5);
        Query query = getSession().getNamedQuery("InlopPoging.zoekFouteInlogPogingen");
        query.setParameter("gebruikerId", gebruikerId);
        query.setParameter("tijdstip", tijdstip.toDate());

        boolean magInloggen = query.list().size() < 5;

        getTransaction().commit();

        return magInloggen;
    }

}
