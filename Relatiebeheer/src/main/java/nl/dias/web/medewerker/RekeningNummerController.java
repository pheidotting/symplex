package nl.dias.web.medewerker;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.RekeningClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequestMapping("/rekeningnummer")
@Controller
public class RekeningNummerController extends AbstractController {
    @Inject
    private RekeningClient rekeningClient;
    @Inject
    private IdentificatieClient identificatieClient;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void opslaan(@RequestBody List<JsonRekeningNummer> jsonEntiteiten, HttpServletRequest httpServletRequest) {
        List<JsonRekeningNummer> lijst = jsonEntiteiten.stream().map(new Function<JsonRekeningNummer, JsonRekeningNummer>() {
            @Override
            public JsonRekeningNummer apply(JsonRekeningNummer adres) {
                Long entiteitId = identificatieClient.zoekIdentificatieCode(adres.getParentIdentificatie()).getEntiteitId();

                adres.setEntiteitId(entiteitId);

                return adres;
            }
        }).collect(Collectors.toList());
        rekeningClient.opslaan(lijst, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonRekeningNummer> alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        List<JsonRekeningNummer> jsonEntiteiten = rekeningClient.lijst(soortentiteit, parentid);

        return jsonEntiteiten;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijderen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") String parentid, HttpServletRequest httpServletRequest) {
        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(parentid);

        rekeningClient.verwijder(soortentiteit, identificatie.getEntiteitId(), getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekTerm}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonRekeningNummer> zoeken(@PathVariable("zoekTerm") String zoekTerm) {
        List<JsonRekeningNummer> result = rekeningClient.zoeken(zoekTerm);

        return result;
    }
}
