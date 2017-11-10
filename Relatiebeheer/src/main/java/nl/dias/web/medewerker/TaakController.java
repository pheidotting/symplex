package nl.dias.web.medewerker;

import nl.dias.messaging.sender.TaakOpslaanSender;
import nl.lakedigital.as.messaging.domain.Taak;
import nl.lakedigital.djfc.client.taakbeheer.TaakClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@RequestMapping("/taak")
@Controller
public class TaakController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaakController.class);

    @Inject
    private TaakOpslaanSender taakOpslaanSender;
    @Inject
    private TaakClient taakClient;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaanAfgerondeTaken", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void opslaanAfgerondeTaken(@RequestBody List<Taak> taaks, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        for (Taak taak : taaks) {
            taakOpslaanSender.send(taak);
        }
    }

    @RequestMapping(value = "/alleAfgerondeTaken/{soortEntiteit}/{entiteitId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<nl.lakedigital.djfc.commons.json.Taak> alleAfgerondeTaken(@PathVariable("soortEntiteit") String soortEntiteit, @PathVariable("entiteitId") Long entiteitId) {
        try {
            return taakClient.alleAfgerondeTaken(soortEntiteit, entiteitId);
        } catch (IOException | JAXBException e) {
            LOGGER.error("{}", e);
        }
        return newArrayList();
    }
}
