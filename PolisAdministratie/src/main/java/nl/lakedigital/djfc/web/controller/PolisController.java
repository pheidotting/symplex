package nl.lakedigital.djfc.web.controller;

import com.google.common.collect.Lists;
import nl.lakedigital.djfc.commons.json.JsonPakket;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.commons.xml.OpvragenPolisSoortenResponse;
import nl.lakedigital.djfc.commons.xml.OpvragenPolissenResponse;
import nl.lakedigital.djfc.domain.Pakket;
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
import java.util.List;
import java.util.Map;

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

        Map<String, String> polisSoorten = polisService.allePolisSoorten(SoortVerzekering.PARTICULIER);
        //        Collections.sort(polisSoorten);

        opvragenPolisSoortenResponse.setPolisSoorten(polisSoorten);

        return opvragenPolisSoortenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alleZakelijkePolisSoorten")
    @ResponseBody
    public OpvragenPolisSoortenResponse alleZakelijkePolisSoorten() {
        OpvragenPolisSoortenResponse opvragenPolisSoortenResponse = new OpvragenPolisSoortenResponse();

        Map<String, String> polisSoorten = polisService.allePolisSoorten(SoortVerzekering.ZAKELIJK);
        //        Collections.sort(polisSoorten);

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
            List<JsonPolis> polissen = Lists.newArrayList();
            Pakket pakket = polisService.lees(Long.valueOf(id));

            JsonPakket jsonPakket = new JsonPakket();
            jsonPakket.setEntiteitId(pakket.getEntiteitId());
            jsonPakket.setId(pakket.getId());
            jsonPakket.setPolisNummer(pakket.getPolisNummer());
            jsonPakket.setSoortEntiteit(pakket.getSoortEntiteit().toString());
            jsonPakket.setMaatschappij(pakket.getMaatschappij());

            for (Polis polis : pakket.getPolissen()) {
                polissen.add(mapper.map(polis, JsonPolis.class));
            }

            jsonPakket.setPolissen(polissen);

            opvragenPolissenResponse.getPakketten().add(jsonPakket);
        } else {
            LOGGER.debug("Nieuwe Polis tonen");
            opvragenPolissenResponse.getPakketten().add(new JsonPakket());
        }

        LOGGER.debug(ReflectionToStringBuilder.toString(opvragenPolissenResponse));
        return opvragenPolissenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst/{relatieId}")
    @ResponseBody
    public OpvragenPolissenResponse lijst(@PathVariable("relatieId") String relatieId) {
        LOGGER.debug("Ophalen alle polissen voor Relatie " + relatieId);
        Long relatie = Long.valueOf(relatieId);

        List<JsonPakket> pakketten = Lists.newArrayList();
        for (Pakket pakket : polisService.alles(SoortEntiteit.RELATIE, relatie)) {
            List<JsonPolis> polissen = Lists.newArrayList();
            JsonPakket jsonPakket = new JsonPakket();
            jsonPakket.setEntiteitId(pakket.getEntiteitId());
            jsonPakket.setId(pakket.getId());
            jsonPakket.setPolisNummer(pakket.getPolisNummer());
            jsonPakket.setSoortEntiteit(pakket.getSoortEntiteit().toString());
            jsonPakket.setMaatschappij(pakket.getMaatschappij());

            for (Polis polis : pakket.getPolissen()) {
                polissen.add(mapper.map(polis, JsonPolis.class));
            }

            jsonPakket.setPolissen(polissen);
            pakketten.add(jsonPakket);
        }

        OpvragenPolissenResponse opvragenPolissenResponse = new OpvragenPolissenResponse();
        opvragenPolissenResponse.setPakketten(pakketten);
        return opvragenPolissenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstBijBedrijf/{bedrijfId}")
    @ResponseBody
    public OpvragenPolissenResponse lijstBijBedrijf(@PathVariable("bedrijfId") Long bedrijfId) {
        LOGGER.debug("Ophalen alle polissen voor Bedrijf " + bedrijfId);

        List<JsonPakket> pakketten = Lists.newArrayList();
        for (Pakket pakket : polisService.alles(SoortEntiteit.BEDRIJF, bedrijfId)) {
            List<JsonPolis> polissen = Lists.newArrayList();
            JsonPakket jsonPakket = new JsonPakket();
            jsonPakket.setEntiteitId(pakket.getEntiteitId());
            jsonPakket.setId(pakket.getId());
            jsonPakket.setPolisNummer(pakket.getPolisNummer());
            jsonPakket.setSoortEntiteit(pakket.getSoortEntiteit().toString());
            jsonPakket.setMaatschappij(pakket.getMaatschappij());

            for (Polis polis : pakket.getPolissen()) {
                polissen.add(mapper.map(polis, JsonPolis.class));
            }

            jsonPakket.setPolissen(polissen);
            pakketten.add(jsonPakket);
        }

        OpvragenPolissenResponse opvragenPolissenResponse = new OpvragenPolissenResponse();
        opvragenPolissenResponse.setPakketten(pakketten);
        return opvragenPolissenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoekOpPolisNummer/{polisNummer}")
    @ResponseBody
    public OpvragenPolissenResponse zoekOpPolisNummer(@PathVariable("polisNummer") String polisNummer) {
        OpvragenPolissenResponse opvragenPolissenResponse = new OpvragenPolissenResponse();

        //        for (Polis polis : polisService.zoekOpPolisNummer(polisNummer)) {
        //            opvragenPolissenResponse.getPokketten().add(mapper.map(polis, JsonPolis.class));
        //        }
        return opvragenPolissenResponse;
    }
}
