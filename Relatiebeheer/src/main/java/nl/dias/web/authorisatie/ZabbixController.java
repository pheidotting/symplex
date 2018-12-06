package nl.dias.web.authorisatie;

import nl.dias.repository.GebruikerRepository;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@Controller
@RequestMapping("/zabbix")
public class ZabbixController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZabbixController.class);

    @Inject
    private GebruikerRepository gebruikerRepository;
    @Inject
    private PolisClient polisClient;

    @RequestMapping(method = RequestMethod.GET, value = "/checkDatabase", produces = MediaType.TEXT_PLAIN)
    @ResponseBody
    public String checkDatabase() {
        LOGGER.trace("Start checking database");

        try {
            gebruikerRepository.getSession().getTransaction().begin();
            gebruikerRepository.getSession().createSQLQuery("/* ping */ SELECT 1").uniqueResult();
            gebruikerRepository.getSession().getTransaction().commit();

            polisClient.ping();

            return "1";
        } catch (Exception e) {
            LOGGER.error("Database niet beschikbaar, rb", e);
            return "0";
        }
    }
}
