package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.repository.PolisRepository;
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
    private PolisRepository polisRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/checkDatabase", produces = MediaType.TEXT_PLAIN)
    @ResponseBody
    public String checkDatabase() {
        LOGGER.trace("Start checking database");

        try {
            polisRepository.getSession().getTransaction().begin();
            polisRepository.getSession().createSQLQuery("/* ping */ SELECT 1").uniqueResult();
            polisRepository.getSession().getTransaction().commit();

            return "1";
        } catch (Exception e) {
            LOGGER.error("Database niet beschikbaar, pa", e);
            return "0";
        }
    }
}
