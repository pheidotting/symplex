package nl.dias.web.medewerker;

import nl.dias.domein.Aangifte;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Relatie;
import nl.dias.service.AangifteService;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.AangifteMapper;
import nl.lakedigital.djfc.commons.json.JsonAangifte;
import org.joda.time.LocalDate;
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

@RequestMapping("/aangifte")
@Controller
public class AangifteController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AangifteController.class);

    @Inject
    private AangifteService aangifteService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private AangifteMapper aangifteMapper;
    //    @Autowired
    //    private HttpServletRequest httpServletRequest;
    //    @Inject
    //    private AuthorisatieService authorisatieService;

    @RequestMapping(method = RequestMethod.GET, value = "/openAangiftes", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonAangifte> openAangiftes(@QueryParam("relatie") Long relatie) {
        return aangifteMapper.mapAllNaarJson(aangifteService.getOpenstaandeAangiftes((Relatie) gebruikerService.lees(relatie)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/afronden/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Response afronden(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        LOGGER.info("Afronden Aangifte met id " + id);

        zetSessieWaarden(httpServletRequest);

        try {
            aangifteService.afronden(id, LocalDate.now(), getGebruiker(httpServletRequest));
        } catch (Exception e) {
            LOGGER.trace("{}", e);
            return Response.status(500).build();
        }
        return Response.ok(id).build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Response opslaan(@RequestBody JsonAangifte jsonAangifte, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        Aangifte aangifte = aangifteMapper.mapVanJson(jsonAangifte);
        aangifteService.opslaan(aangifte);

        return Response.ok(aangifte.getId()).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/geslotenAangiftes", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonAangifte> geslotenAangiftes(@QueryParam("relatie") Long relatie) {
        return aangifteMapper.mapAllNaarJson(aangifteService.getAfgeslotenAangiftes((Relatie) gebruikerService.lees(relatie)));
    }

    private Gebruiker getGebruiker(HttpServletRequest httpServletRequest) {
        String sessie = null;
        if (httpServletRequest.getSession().getAttribute("sessie") != null && !"".equals(httpServletRequest.getSession().getAttribute("sessie"))) {
            sessie = httpServletRequest.getSession().getAttribute("sessie").toString();
        }

        return authorisatieService.getIngelogdeGebruiker(httpServletRequest, sessie, httpServletRequest.getRemoteAddr());

    }
}
