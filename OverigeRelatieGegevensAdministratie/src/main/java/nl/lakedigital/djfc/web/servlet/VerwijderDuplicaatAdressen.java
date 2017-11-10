package nl.lakedigital.djfc.web.servlet;

import nl.lakedigital.djfc.domain.Adres;
import nl.lakedigital.djfc.service.AdresService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

public class VerwijderDuplicaatAdressen implements ServletContextListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(VerwijderDuplicaatAdressen.class);

    @Inject
    private AdresService adresService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.debug("Verwijder Duplicaat adressen start");

        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);

        run();

        LOGGER.debug("Verwijder Duplicaat adressen eind");
    }

    protected void run() {
        Set<Adres> adresSet = new HashSet<>();
        List<Adres> teVerwijderen = newArrayList();

        List<Adres> adressen = adresService.alles();
        verwijderLegeStrings(adressen);
        adresService.opslaan(adressen);

        for (Adres adres : adressen) {
            int aantal = adresSet.size();

            adresSet.add(adres);

            if (adresSet.size() == aantal) {
                teVerwijderen.add(adres);
            }
        }

        adresService.verwijder(teVerwijderen);
        LOGGER.debug("{} adres(sen) verwijderd", teVerwijderen.size());
    }

    public void verwijderLegeStrings(List<Adres> adressen) {
        for (Adres adres : adressen) {
            if ("".equals(adres.getToevoeging())) {
                adres.setToevoeging(null);
            }
            if ("".equals(adres.getStraat())) {
                adres.setStraat(null);
            }
            if (adres.getPostcode() == null) {
                adres.setPostcode("");//anders gaat dit later weer fout
            }
            if ("".equals(adres.getPlaats())) {
                adres.setPlaats(null);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //Comment omdat SonarQube anders weer zit te miepen
    }
}
