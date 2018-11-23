package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.JsonSchade;
import nl.lakedigital.djfc.commons.json.JsonSoortSchade;
import nl.lakedigital.djfc.commons.xml.OpvragenSchadesResponse;
import nl.lakedigital.djfc.commons.xml.OpvragenSoortSchadeResponse;
import nl.lakedigital.djfc.commons.xml.OpvragenStatusSchadeResponse;
import nl.lakedigital.djfc.domain.Schade;
import nl.lakedigital.djfc.domain.SoortSchade;
import nl.lakedigital.djfc.domain.StatusSchade;
import nl.lakedigital.djfc.mapper.Mapper;
import nl.lakedigital.djfc.service.SchadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RequestMapping("/schade")
@Controller
public class SchadeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeController.class);

    @Inject
    private SchadeService schadeService;
    @Inject
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public Long opslaan(@RequestBody JsonSchade jsonSchade) {
        LOGGER.debug("{}", jsonSchade);

        Schade schade = mapper.map(jsonSchade, Schade.class);
        schadeService.opslaan(schade, jsonSchade.getSoortSchade(), jsonSchade.getPolis(), jsonSchade.getStatusSchade());

        return schade.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst/{relatieId}")
    @ResponseBody
    public OpvragenSchadesResponse lijst(@PathVariable("relatieId") Long relatieId) {
        LOGGER.info("Opzoeken Schades bij Relatie met Id {}", relatieId);

        OpvragenSchadesResponse opvragenSchadesResponse = new OpvragenSchadesResponse();

        List<Schade> schades = schadeService.alles(SoortEntiteit.RELATIE, relatieId);

        for (Schade schade : schades) {
            opvragenSchadesResponse.getSchades().add(mapper.map(schade, JsonSchade.class));
        }

        return opvragenSchadesResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstBijBedrijf{bedrijfId}")
    @ResponseBody
    public OpvragenSchadesResponse lijstBijBedrijf(@PathVariable("bedrijfId") Long bedrijfId) {
        LOGGER.info("Opzoeken Schades bij Bedrijf met Id {}", bedrijfId);

        OpvragenSchadesResponse opvragenSchadesResponse = new OpvragenSchadesResponse();
        List<Schade> schades = schadeService.alles(SoortEntiteit.SCHADE, bedrijfId);

        for (Schade schade : schades) {
            opvragenSchadesResponse.getSchades().add(mapper.map(schade, JsonSchade.class));
        }

        return opvragenSchadesResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoekOpSchadeNummerMaatschappij/{schadeNummerMaatschappij}")
    @ResponseBody
    public OpvragenSchadesResponse zoekOpSchadeNummerMaatschappij(@PathVariable("schadeNummerMaatschappij") String schadeNummerMaatschappij) {
        LOGGER.info("Opzoeken Schades met schadeNummerMaatschappij {}", schadeNummerMaatschappij);

        OpvragenSchadesResponse opvragenSchadesResponse = new OpvragenSchadesResponse();
        List<Schade> schades = schadeService.zoekOpSchadeNummerMaatschappij(schadeNummerMaatschappij);

        for (Schade schade : schades) {
            opvragenSchadesResponse.getSchades().add(mapper.map(schade, JsonSchade.class));
        }

        return opvragenSchadesResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}")
    @ResponseBody
    public OpvragenSchadesResponse lees(@PathVariable("id") Long id) {
        LOGGER.info("Opzoeken schade met id {}", id);
        Schade schade = schadeService.lees(id);

        if (schade == null) {
            schade = new Schade();
        }

        OpvragenSchadesResponse opvragenSchadesResponse = new OpvragenSchadesResponse();
        opvragenSchadesResponse.getSchades().add(mapper.map(schade, JsonSchade.class));
        return opvragenSchadesResponse;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
    }

    @RequestMapping(method = RequestMethod.GET, value = "/soortenSchade{query}")
    @ResponseBody
    public OpvragenSoortSchadeResponse soortenSchade(@PathVariable("query") String query) {
        List<SoortSchade> lijst = schadeService.soortenSchade(query);

        OpvragenSoortSchadeResponse opvragenSoortSchadeResponse = new OpvragenSoortSchadeResponse();
        for (SoortSchade soortSchade : lijst) {
            opvragenSoortSchadeResponse.getSoortSchade().add(mapper.map(soortSchade, JsonSoortSchade.class));
        }

        return opvragenSoortSchadeResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstStatusSchade")
    @ResponseBody
    public OpvragenStatusSchadeResponse lijstStatusSchade() {
        List<StatusSchade> lijst = schadeService.getStatussen();

        OpvragenStatusSchadeResponse opvragenStatusSchadeResponse = new OpvragenStatusSchadeResponse();
        for (StatusSchade statusSchade : lijst) {
            opvragenStatusSchadeResponse.getStatusSchade().add(statusSchade.getStatus());
        }

        return opvragenStatusSchadeResponse;
    }
}
