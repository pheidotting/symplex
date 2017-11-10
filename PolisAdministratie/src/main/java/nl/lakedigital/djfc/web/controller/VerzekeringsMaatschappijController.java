package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.JsonVerzekeringsMaatschappij;
import nl.lakedigital.djfc.domain.VerzekeringsMaatschappij;
import nl.lakedigital.djfc.mapper.Mapper;
import nl.lakedigital.djfc.service.VerzekeringsMaatschappijService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/overig")
@Controller
public class VerzekeringsMaatschappijController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerzekeringsMaatschappijController.class);

    @Inject
    private VerzekeringsMaatschappijService maatschappijService;
    @Inject
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/lijstVerzekeringsMaatschappijen")
    @ResponseBody
    public List<JsonVerzekeringsMaatschappij> lijstVerzekeringsMaatschappijen() {
        LOGGER.debug("ophalen lijst met VerzekeringsMaatschappijen");

        List<VerzekeringsMaatschappij> lijst = maatschappijService.alles();

        LOGGER.debug("Gevonden, " + lijst.size() + " VerzekeringsMaatschappijen");

        List<JsonVerzekeringsMaatschappij> result = new ArrayList<>();

        for (VerzekeringsMaatschappij verzekeringsMaatschappij : lijst) {
            result.add(mapper.map(verzekeringsMaatschappij, JsonVerzekeringsMaatschappij.class));
        }

        return result;
    }
}
