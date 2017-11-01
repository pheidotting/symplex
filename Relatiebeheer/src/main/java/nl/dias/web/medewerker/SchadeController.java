package nl.dias.web.medewerker;

import nl.dias.messaging.sender.SchadeOpslaanRequestSender;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.service.SchadeService;
import nl.dias.web.mapper.SchadeMapper;
import nl.lakedigital.djfc.client.polisadministratie.SchadeClient;
import nl.lakedigital.djfc.commons.json.JsonSchade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestMapping("/schade")
@Controller
public class SchadeController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeController.class);

    @Inject
    private SchadeService schadeService;
    @Inject
    private SchadeMapper schadeMapper;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private SchadeOpslaanRequestSender schadeOpslaanRequestSender;
    @Inject
    private Destination schadeOpslaanResponseDestination;
    @Inject
    private SchadeClient schadeClient;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void opslaan(@RequestBody JsonSchade jsonSchade, HttpServletRequest httpServletRequest) {
        LOGGER.debug("{}", jsonSchade);

        zetSessieWaarden(httpServletRequest);

            schadeOpslaanRequestSender.setReplyTo(schadeOpslaanResponseDestination);
            schadeOpslaanRequestSender.send(jsonSchade);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonSchade> lijst(@QueryParam("relatieId") Long relatieId) {
        LOGGER.debug("Opzoeken Schades bij Relatie met Id {}", relatieId);

        return schadeClient.lijst(relatieId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstBijBedrijf", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonSchade> lijstBijBedrijf(@QueryParam("bedrijfId") Long bedrijfId) {
        LOGGER.debug("Opzoeken Schades bij Bedrijf met Id {}", bedrijfId);

        return schadeClient.lijstBijBedrijf(bedrijfId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonSchade lees(@QueryParam("id") String id) {
        return schadeClient.lees(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijder/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijder(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        LOGGER.debug("verwijderen Schade met id " + id);

        zetSessieWaarden(httpServletRequest);

        try {
            schadeService.verwijder(id);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Fout bij verwijderen Schade", e);
        }
    }

}
