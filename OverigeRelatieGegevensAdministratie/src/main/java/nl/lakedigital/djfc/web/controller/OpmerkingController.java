package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.JsonOpmerking;
import nl.lakedigital.djfc.commons.xml.OpvragenOpmerkingenResponse;
import nl.lakedigital.djfc.domain.Bijlage;
import nl.lakedigital.djfc.domain.Opmerking;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.OpmerkingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestMapping("/opmerking")
@Controller
public class OpmerkingController extends AbstractController<Opmerking, JsonOpmerking> {
    public OpmerkingController() {
        super(Opmerking.class);
    }

    @Inject
    private OpmerkingService opmerkingService;

    @Override
    public AbstractService getService() {
        return opmerkingService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenOpmerkingenResponse alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        logger.debug("alles soortEntiteit {} parentId {}", soortentiteit, parentid);

        List<Opmerking> domainEntiteiten = getService().alles(SoortEntiteit.valueOf(soortentiteit), parentid);
        OpvragenOpmerkingenResponse opvragenOpmerkingenResponse = new OpvragenOpmerkingenResponse();

        for (Opmerking entiteit : domainEntiteiten) {
            opvragenOpmerkingenResponse.getOpmerkingen().add(mapper.map(entiteit, JsonOpmerking.class));
        }

        return opvragenOpmerkingenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekTerm}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenOpmerkingenResponse zoeken(@PathVariable("zoekTerm") String zoekTerm) {
        logger.debug("Zoeken met zoeketerm {}, {}", zoekTerm, Bijlage.class);

        OpvragenOpmerkingenResponse opvragenOpmerkingenResponse = new OpvragenOpmerkingenResponse();
        List<Opmerking> opgehaald = getService().zoeken(zoekTerm);
        for (Opmerking d : opgehaald) {
            opvragenOpmerkingenResponse.getOpmerkingen().add(mapper.map(d, JsonOpmerking.class));
        }

        return opvragenOpmerkingenResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaanOpmerking")
    @ResponseBody
    public Long opslaan(@RequestBody JsonOpmerking jsonOpmerking, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        Opmerking opmerking = mapper.map(jsonOpmerking, Opmerking.class);

        opmerkingService.opslaan(opmerking);
        return opmerking.getId();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijder/{id}")
    @ResponseBody
    public void verwijder(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        opmerkingService.verwijder(id);
    }
}
