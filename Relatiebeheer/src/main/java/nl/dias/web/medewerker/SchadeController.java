package nl.dias.web.medewerker;

import nl.dias.domein.Schade;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.service.SchadeService;
import nl.dias.web.mapper.SchadeMapper;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonFoutmelding;
import nl.lakedigital.djfc.commons.json.JsonSchade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    private IdentificatieClient identificatieClient;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Response opslaan(@RequestBody JsonSchade jsonSchade, HttpServletRequest httpServletRequest) {
        LOGGER.debug("{}", jsonSchade);

        zetSessieWaarden(httpServletRequest);

        Schade schade = schadeMapper.mapVanJson(jsonSchade);
        schadeService.opslaan(schade, jsonSchade.getSoortSchade(), jsonSchade.getPolis(), jsonSchade.getStatusSchade());

        return Response.status(202).entity(new JsonFoutmelding(schade.getId().toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonSchade> lijst(@QueryParam("relatieId") Long relatieId) {
        LOGGER.debug("Opzoeken Schades bij Relatie met Id {}", relatieId);

        return schadeMapper.mapAllNaarJson(schadeService.alleSchadesBijRelatie(relatieId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstBijBedrijf", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonSchade> lijstBijBedrijf(@QueryParam("bedrijfId") Long bedrijfId) {
        LOGGER.debug("Opzoeken Schades bij Bedrijf met Id {}", bedrijfId);

        return schadeMapper.mapAllNaarJson(schadeService.alleSchadesBijBedrijf(bedrijfId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonSchade lees(@QueryParam("id") String id) {
        if (id != null && !"".equals(id) && !"0".equals(id)) {
            return schadeMapper.mapNaarJson(schadeService.lees(Long.valueOf(id)));
        } else {
            return new JsonSchade();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijder/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijder(@PathVariable("id") String id, HttpServletRequest httpServletRequest) {
        LOGGER.debug("verwijderen Schade met id " + id);

        zetSessieWaarden(httpServletRequest);

        try {
            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(id);
            schadeService.verwijder(identificatie.getEntiteitId());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Fout bij verwijderen Schade", e);
        }
    }

}
