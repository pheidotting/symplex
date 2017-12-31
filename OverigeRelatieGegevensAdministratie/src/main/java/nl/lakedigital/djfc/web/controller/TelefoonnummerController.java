package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.commons.xml.OpvragenTelefoonnummersResponse;
import nl.lakedigital.djfc.domain.Bijlage;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.Telefoonnummer;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.TelefoonnummerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestMapping("/telefoonnummer")
@Controller
public class TelefoonnummerController extends AbstractController<Telefoonnummer, JsonTelefoonnummer> {
    public TelefoonnummerController() {
        super(Telefoonnummer.class);
    }

    @Inject
    private TelefoonnummerService telefoonnummerService;

    @Override
    public AbstractService getService() {
        return telefoonnummerService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenTelefoonnummersResponse alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        logger.debug("alles soortEntiteit {} parentId {}", soortentiteit, parentid);

        List<Telefoonnummer> domainEntiteiten = getService().alles(SoortEntiteit.valueOf(soortentiteit), parentid);
        OpvragenTelefoonnummersResponse opvragenTelefoonnummersResponse = new OpvragenTelefoonnummersResponse();

        for (Telefoonnummer entiteit : domainEntiteiten) {
            opvragenTelefoonnummersResponse.getTelefoonnummers().add(mapper.map(entiteit, JsonTelefoonnummer.class));
        }

        return opvragenTelefoonnummersResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekTerm}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenTelefoonnummersResponse zoeken(@PathVariable("zoekTerm") String zoekTerm) {
        logger.debug("Zoeken met zoeketerm {}, {}", zoekTerm, Bijlage.class);

        OpvragenTelefoonnummersResponse opvragenTelefoonnummersResponse = new OpvragenTelefoonnummersResponse();
        List<Telefoonnummer> opgehaald = getService().zoeken(zoekTerm);
        for (Telefoonnummer d : opgehaald) {
            opvragenTelefoonnummersResponse.getTelefoonnummers().add(mapper.map(d, JsonTelefoonnummer.class));
        }

        return opvragenTelefoonnummersResponse;
    }

}
