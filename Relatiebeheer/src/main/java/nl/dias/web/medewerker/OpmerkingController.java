package nl.dias.web.medewerker;

import com.google.common.base.Function;
import nl.dias.domein.Medewerker;
import nl.dias.service.GebruikerService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.OpmerkingClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonOpmerking;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

@RequestMapping("/opmerking")
@Controller
public class OpmerkingController extends AbstractController {
    @Inject
    private OpmerkingClient opmerkingClient;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private IdentificatieClient identificatieClient;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void opslaan(@RequestBody List<JsonOpmerking> jsonEntiteiten, HttpServletRequest httpServletRequest) {
        List<JsonOpmerking> lijst = jsonEntiteiten.stream().map(new java.util.function.Function<JsonOpmerking, JsonOpmerking>() {
            @Override
            public JsonOpmerking apply(JsonOpmerking opmerking) {
                Long entiteitId = identificatieClient.zoekIdentificatieCode(opmerking.getParentIdentificatie()).getEntiteitId();

                opmerking.setEntiteitId(entiteitId);

                return opmerking;
            }
        }).collect(Collectors.toList());
        opmerkingClient.opslaan(lijst, getIngelogdeGebruiker(httpServletRequest).getId(), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonOpmerking> alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        List<JsonOpmerking> jsonEntiteiten = opmerkingClient.lijst(soortentiteit, parentid);

        return newArrayList(transform(jsonEntiteiten, new Function<JsonOpmerking, JsonOpmerking>() {
            @Override
            public JsonOpmerking apply(JsonOpmerking jsonOpmerking) {
                Medewerker medewerker = (Medewerker) gebruikerService.lees(jsonOpmerking.getMedewerkerId());

                jsonOpmerking.setMedewerker(medewerker.getNaam());

                return jsonOpmerking;
            }
        }));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijder/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijder(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        opmerkingClient.verwijder(id, getIngelogdeGebruiker(httpServletRequest).getId(), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijderen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") String parentid, HttpServletRequest httpServletRequest) {
        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(parentid);

        opmerkingClient.verwijder(soortentiteit, identificatie.getEntiteitId(), getIngelogdeGebruiker(httpServletRequest).getId(), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekTerm}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonOpmerking> zoeken(@PathVariable("zoekTerm") String zoekTerm) {
        List<JsonOpmerking> result = opmerkingClient.zoeken(zoekTerm);

        return result;
    }
}
