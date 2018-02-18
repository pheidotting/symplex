package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.commons.xml.OpvragenRekeningNummersResponse;
import nl.lakedigital.djfc.domain.Bijlage;
import nl.lakedigital.djfc.domain.RekeningNummer;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.RekeningNummerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestMapping("/rekeningnummer")
@Controller
public class RekeningNummerController extends AbstractController<RekeningNummer, JsonRekeningNummer> {
    public RekeningNummerController() {
        super(RekeningNummer.class);
    }

    @Inject
    private RekeningNummerService rekeningNummerService;

    @Override
    public AbstractService getService() {
        return rekeningNummerService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenRekeningNummersResponse alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid, HttpServletRequest httpServletRequest) {
        metricsService.addMetric("alles", RekeningNummerController.class, null, null);

        zetSessieWaarden(httpServletRequest);

        logger.debug("alles soortEntiteit {} parentId {}", soortentiteit, parentid);

        List<RekeningNummer> domainEntiteiten = getService().alles(SoortEntiteit.valueOf(soortentiteit), parentid);
        OpvragenRekeningNummersResponse opvragenRekeningNummersResponse = new OpvragenRekeningNummersResponse();

        for (RekeningNummer entiteit : domainEntiteiten) {
            opvragenRekeningNummersResponse.getRekeningNummers().add(mapper.map(entiteit, JsonRekeningNummer.class));
        }

        return opvragenRekeningNummersResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekTerm}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenRekeningNummersResponse zoeken(@PathVariable("zoekTerm") String zoekTerm, HttpServletRequest httpServletRequest) {
        metricsService.addMetric("zoeken", RekeningNummerController.class, null, null);

        zetSessieWaarden(httpServletRequest);

        logger.debug("Zoeken met zoeketerm {}, {}", zoekTerm, Bijlage.class);

        OpvragenRekeningNummersResponse opvragenRekeningNummersResponse = new OpvragenRekeningNummersResponse();
        List<RekeningNummer> opgehaald = getService().zoeken(zoekTerm);
        for (RekeningNummer d : opgehaald) {
            opvragenRekeningNummersResponse.getRekeningNummers().add(mapper.map(d, JsonRekeningNummer.class));
        }

        return opvragenRekeningNummersResponse;
    }

}
