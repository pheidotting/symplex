package nl.dias.web.medewerker;

import nl.dias.domein.features.MyFeatures;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/adres")
@Controller
public class AdresController extends AbstractController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdresController.class);

    @Inject
    private AdresClient adresClient;
    @Inject
    private IdentificatieClient identificatieClient;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void opslaan(@RequestBody List<JsonAdres> jsonEntiteiten, HttpServletRequest httpServletRequest) {
        List<JsonAdres> lijst = jsonEntiteiten.stream().map(adres -> {
            Long entiteitId;
            if (adres.getParentIdentificatie() != null && !"".equals(adres.getParentIdentificatie())) {
                try {
                    entiteitId = identificatieClient.zoekIdentificatieCode(adres.getParentIdentificatie()).getEntiteitId();
                } catch (Exception e) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        LOGGER.trace("Fout, eerste keer bij ophalen identificatie {}", e);
                        Thread.interrupted();
                    }
                    try {
                        entiteitId = identificatieClient.zoekIdentificatieCode(adres.getParentIdentificatie()).getEntiteitId();
                    } catch (Exception e2) {
                        LOGGER.error("Onverwachte fout opgetreden {}", e2);
                        throw e2;
                    }
                }

                adres.setEntiteitId(entiteitId);
            }
            return adres;
        }).collect(Collectors.toList());
        adresClient.opslaan(lijst, getIngelogdeGebruiker(httpServletRequest).getId(), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonAdres> alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        List<JsonAdres> jsonEntiteiten = adresClient.lijst(soortentiteit, parentid);

        return jsonEntiteiten;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijderen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") String parentid, HttpServletRequest httpServletRequest) {
        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(parentid);

        adresClient.verwijder(soortentiteit, identificatie.getEntiteitId(), getIngelogdeGebruiker(httpServletRequest).getId(), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekTerm}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonAdres> zoeken(@PathVariable("zoekTerm") String zoekTerm) {
        List<JsonAdres> result = adresClient.zoeken(zoekTerm);

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ophalenAdresOpPostcode/{postcode}/{huisnummer}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonAdres ophalenAdresOpPostcode(@PathVariable("postcode") String postcode, @PathVariable("huisnummer") String huisnummer) {
        return adresClient.ophalenAdresOpPostcode(postcode, huisnummer, MyFeatures.ADRES_NIET_VIA_API.isActive());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alleAdressenBijLijstMetEntiteiten", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonAdres> alleAdressenBijLijstMetEntiteiten(@RequestParam("soortEntiteit") String soortEntiteit, @RequestParam("lijst") List<Long> ids) {
        return adresClient.alleAdressenBijLijstMetEntiteiten(ids, soortEntiteit);
    }
}
