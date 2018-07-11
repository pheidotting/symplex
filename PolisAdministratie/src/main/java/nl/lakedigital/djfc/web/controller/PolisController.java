package nl.lakedigital.djfc.web.controller;

import com.google.common.collect.Lists;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.commons.xml.OpvragenPolisSoortenResponse;
import nl.lakedigital.djfc.commons.xml.OpvragenPolissenResponse;
import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.SoortVerzekering;
import nl.lakedigital.djfc.mapper.Mapper;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import nl.lakedigital.djfc.service.PolisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequestMapping("/polis")
@Controller
public class PolisController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisController.class);

    @Inject
    private PolisService polisService;
    @Inject
    private List<Polis> polissen;
    @Inject
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/alleParticulierePolisSoorten")
    @ResponseBody
    public OpvragenPolisSoortenResponse alleParticulierePolisSoorten() {
        OpvragenPolisSoortenResponse opvragenPolisSoortenResponse = new OpvragenPolisSoortenResponse();

        List<String> polisSoorten = polisService.allePolisSoorten(SoortVerzekering.PARTICULIER);
        Collections.sort(polisSoorten);

        opvragenPolisSoortenResponse.setPolisSoorten(polisSoorten);

        return opvragenPolisSoortenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alleZakelijkePolisSoorten")
    @ResponseBody
    public OpvragenPolisSoortenResponse alleZakelijkePolisSoorten() {
        OpvragenPolisSoortenResponse opvragenPolisSoortenResponse = new OpvragenPolisSoortenResponse();

        List<String> polisSoorten = polisService.allePolisSoorten(SoortVerzekering.ZAKELIJK);
        Collections.sort(polisSoorten);

        opvragenPolisSoortenResponse.setPolisSoorten(polisSoorten);

        return opvragenPolisSoortenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}")
    @ResponseBody
    public OpvragenPolissenResponse lees(@PathVariable("id") String id) {
        OpvragenPolissenResponse opvragenPolissenResponse = new OpvragenPolissenResponse();

        LOGGER.debug("ophalen Polis met id " + id);
        if (id != null && !"".equals(id) && !"0".equals(id)) {
            LOGGER.debug("ophalen Polis");
            opvragenPolissenResponse.getPolissen().add(mapper.map(polisService.lees(Long.valueOf(id)), JsonPolis.class));
        } else {
            LOGGER.debug("Nieuwe Polis tonen");
            opvragenPolissenResponse.getPolissen().add(new JsonPolis());
        }

        LOGGER.debug(ReflectionToStringBuilder.toString(opvragenPolissenResponse));
        return opvragenPolissenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/beeindigen/{id}")
    @ResponseBody
    public void beeindigen(@PathVariable("id") Long id) {
        LOGGER.debug("beeindigen Polis met id " + id);
        polisService.beeindigen(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst/{relatieId}")
    @ResponseBody
    public OpvragenPolissenResponse lijst(@PathVariable("relatieId") String relatieId) {
        LOGGER.debug("Ophalen alle polissen voor Relatie " + relatieId);
        Long relatie = Long.valueOf(relatieId);

        List<JsonPolis> polissen = Lists.newArrayList();
        for (Polis polis : polisService.alles(SoortEntiteit.RELATIE, relatie)) {
            polissen.add(mapper.map(polis, JsonPolis.class));
        }

        OpvragenPolissenResponse opvragenPolissenResponse = new OpvragenPolissenResponse();
        opvragenPolissenResponse.setPolissen(polissen);
        return opvragenPolissenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstBijBedrijf/{bedrijfId}")
    @ResponseBody
    public OpvragenPolissenResponse lijstBijBedrijf(@PathVariable("bedrijfId") Long bedrijfId) {
        LOGGER.debug("Ophalen alle polissen voor Bedrijf " + bedrijfId);

        List<JsonPolis> polissen = new ArrayList<>();
        for (Polis polis : polisService.alles(SoortEntiteit.BEDRIJF, bedrijfId)) {
            polissen.add(mapper.map(polis, JsonPolis.class));
        }

        OpvragenPolissenResponse opvragenPolissenResponse = new OpvragenPolissenResponse();
        opvragenPolissenResponse.setPolissen(polissen);
        return opvragenPolissenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoekOpPolisNummer/{polisNummer}")
    @ResponseBody
    public OpvragenPolissenResponse zoekOpPolisNummer(@PathVariable("polisNummer") String polisNummer) {
        OpvragenPolissenResponse opvragenPolissenResponse = new OpvragenPolissenResponse();

        for (Polis polis : polisService.zoekOpPolisNummer(polisNummer)) {
            opvragenPolissenResponse.getPolissen().add(mapper.map(polis, JsonPolis.class));
        }
        return opvragenPolissenResponse;
    }
}
