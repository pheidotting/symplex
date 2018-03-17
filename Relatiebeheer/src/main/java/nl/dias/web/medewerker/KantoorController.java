package nl.dias.web.medewerker;

import com.codahale.metrics.Timer;
import nl.dias.domein.Medewerker;
import nl.dias.exception.BsnNietGoedException;
import nl.dias.exception.IbanNietGoedException;
import nl.dias.exception.PostcodeNietGoedException;
import nl.dias.exception.TelefoonnummerNietGoedException;
import nl.dias.mapper.Mapper;
import nl.dias.repository.KantoorRepository;
import nl.dias.service.KantoorService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonKantoor;
import nl.lakedigital.djfc.domain.response.Kantoor;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.function.Consumer;

@RequestMapping("/kantoor")
@Controller("kantoorMedewerkerController")
public class KantoorController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorController.class);

    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private KantoorService kantoorService;
    @Inject
    private Mapper mapper;
    @Inject
    private MetricsService metricsService;
    @Inject
    private IdentificatieClient identificatieClient;

    @RequestMapping(method = RequestMethod.GET, value = "/lees", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Kantoor lees(HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        metricsService.addMetric("lees", KantoorController.class, null, null);
        Timer.Context timer = metricsService.addTimerMetric("lees", KantoorController.class);

        Long kantoorId = ((Medewerker) getIngelogdeGebruiker(httpServletRequest)).getKantoor().getId();

        Identificatie identificatie = identificatieClient.zoekIdentificatie("KANTOOR", kantoorId);

        LOGGER.debug("Ophalen Kantoor met id : " + kantoorId);

        nl.dias.domein.Kantoor kantoor = kantoorRepository.lees(kantoorId);
        Kantoor result = new Kantoor();
        result.setIdentificatie(identificatie.getIdentificatie());

        result.setNaam(kantoor.getNaam());
        result.setKvk(kantoor.getKvk());
        result.setRechtsvorm(kantoor.getRechtsvorm().getOmschrijving());
        result.setEmailadres(kantoor.getEmailadres());
        result.setAfkorting(kantoor.getAfkorting());

        gebruikerService.alleMedewerkers(kantoor).stream().forEach(new Consumer<Medewerker>() {
            @Override
            public void accept(Medewerker medewerker) {
                Identificatie identificatie1 = identificatieClient.zoekIdentificatie("MEDEWERKER", medewerker.getId());
                nl.lakedigital.djfc.domain.response.Medewerker mw = new nl.lakedigital.djfc.domain.response.Medewerker(identificatie1.getIdentificatie(), medewerker.getVoornaam(), medewerker.getTussenvoegsel(), medewerker.getAchternaam(), medewerker.getEmailadres());

                result.addMedewerker(mw);
            }
        });

        metricsService.stop(timer);

        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void opslaan(@RequestBody JsonKantoor jsonKantoor, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        metricsService.addMetric("opslaan", KantoorController.class, null, jsonKantoor.getId() == null);
        Timer.Context timer = metricsService.addTimerMetric("opslaan", KantoorController.class);

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(jsonKantoor.getIdentificatie());
        Long kantoorId = identificatie.getEntiteitId();
        jsonKantoor.setId(kantoorId);

        nl.dias.domein.Kantoor kantoor = kantoorRepository.lees(kantoorId);
        kantoor.setNaam(jsonKantoor.getNaam());
        kantoor.setEmailadres(jsonKantoor.getEmailadres());
        kantoor.setKvk(jsonKantoor.getKvk());

        try {
            kantoorService.aanmelden(kantoor);
        } catch (PostcodeNietGoedException | TelefoonnummerNietGoedException | BsnNietGoedException | IbanNietGoedException e) {
            e.printStackTrace();
        }

        metricsService.stop(timer);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/verwijderen/{afkorting}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijderen(@PathVariable("afkorting") String afkorting, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        metricsService.addMetric("opslaan", KantoorController.class, null, null);
        Timer.Context timer = metricsService.addTimerMetric("opslaan", KantoorController.class);

        nl.dias.domein.Kantoor kantoor = kantoorRepository.zoekOpAfkorting(afkorting).get(0);
        for (Medewerker medewerker : gebruikerService.alleMedewerkers(kantoor)) {
            gebruikerService.verwijder(medewerker.getId());
        }

        kantoorRepository.verwijder(kantoor);

        metricsService.stop(timer);
    }
}
