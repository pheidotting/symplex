package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.as.messaging.request.EntiteitenOpgeslagenRequest;
import nl.lakedigital.djfc.commons.json.ZoekIdentificatieResponse;
import nl.lakedigital.djfc.domain.Identificatie;
import nl.lakedigital.djfc.service.IdentificatieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@RequestMapping("/identificatie")
@Controller
public class IdentificatieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdentificatieController.class);
    @Inject
    private IdentificatieService identificatieService;

    @RequestMapping(method = RequestMethod.GET, value = "/zoekenOpCode/{identificatie}")
    @ResponseBody
    public ZoekIdentificatieResponse zoeken(@PathVariable("identificatie") String identificatieCode) {
        LOGGER.debug("zoeken met {} ", identificatieCode);
        ZoekIdentificatieResponse zoekIdentificatieResponse = new ZoekIdentificatieResponse();

        Identificatie identificatie = identificatieService.zoekOpIdentificatieCode(identificatieCode);
        nl.lakedigital.djfc.commons.json.Identificatie json = new nl.lakedigital.djfc.commons.json.Identificatie();
        if (identificatie != null) {
            json.setId(identificatie.getId());
            json.setEntiteitId(identificatie.getEntiteitId());
            json.setIdentificatie(identificatie.getIdentificatie());
            json.setSoortEntiteit(identificatie.getSoortEntiteit());

            zoekIdentificatieResponse.getIdentificaties().add(json);
        }
        return zoekIdentificatieResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoekenMeerdere/{zoekterm}")
    @ResponseBody
    public ZoekIdentificatieResponse zoekenMeerdere(@PathVariable("zoekterm") String zoekterm) {
        LOGGER.info("Zoeken meerdere identificaties met zoekterm {}", zoekterm);
        ZoekIdentificatieResponse zoekIdentificatieResponse = new ZoekIdentificatieResponse();
        String[] zoekterms = zoekterm.split("&zoekterm=");

        for (String s : zoekterms) {
            String[] split = s.split(",");
            String soortEntiteit = split[0];
            Long entiteitId = Long.valueOf(split[1]);

            Identificatie identificatie = identificatieService.zoek(soortEntiteit, entiteitId);

            nl.lakedigital.djfc.commons.json.Identificatie json = new nl.lakedigital.djfc.commons.json.Identificatie();
            if (identificatie != null) {
                json.setId(identificatie.getId());
                json.setEntiteitId(identificatie.getEntiteitId());
                json.setIdentificatie(identificatie.getIdentificatie());
                json.setSoortEntiteit(identificatie.getSoortEntiteit());

                zoekIdentificatieResponse.getIdentificaties().add(json);
            }
        }

        return zoekIdentificatieResponse;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{soortEntiteit}/{entiteitId}")
    @ResponseBody
    public ZoekIdentificatieResponse zoeken(@PathVariable("soortEntiteit") String soortEntiteit, @PathVariable("entiteitId") Long entiteitId) {
        LOGGER.debug("zoeken met {} en {}", soortEntiteit, entiteitId);
        ZoekIdentificatieResponse zoekIdentificatieResponse = new ZoekIdentificatieResponse();

        Identificatie identificatie = identificatieService.zoek(soortEntiteit, entiteitId);
        nl.lakedigital.djfc.commons.json.Identificatie json = new nl.lakedigital.djfc.commons.json.Identificatie();
        if (identificatie != null) {
            json.setId(identificatie.getId());
            json.setEntiteitId(identificatie.getEntiteitId());
            json.setIdentificatie(identificatie.getIdentificatie());
            json.setSoortEntiteit(identificatie.getSoortEntiteit());

            zoekIdentificatieResponse.getIdentificaties().add(json);
        }
        return zoekIdentificatieResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public nl.lakedigital.djfc.commons.json.Identificatie opslaan(@RequestBody EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest) {
        Identificatie identificatie = new Identificatie(entiteitenOpgeslagenRequest.getSoortEntiteitEnEntiteitIds().get(0).getSoortEntiteit().name(), entiteitenOpgeslagenRequest.getSoortEntiteitEnEntiteitIds().get(0).getEntiteitId());
        LOGGER.debug("{}", identificatie);
        identificatieService.opslaan(identificatie);

        nl.lakedigital.djfc.commons.json.Identificatie jsonIdentificatie = new nl.lakedigital.djfc.commons.json.Identificatie();
        jsonIdentificatie.setEntiteitId(identificatie.getEntiteitId());
        jsonIdentificatie.setSoortEntiteit(identificatie.getSoortEntiteit());
        jsonIdentificatie.setIdentificatie(identificatie.getIdentificatie());

        return jsonIdentificatie;
    }
}
