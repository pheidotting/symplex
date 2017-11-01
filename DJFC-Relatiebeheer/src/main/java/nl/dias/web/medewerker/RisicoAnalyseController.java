package nl.dias.web.medewerker;

import nl.dias.mapper.Mapper;
import nl.dias.service.RisicoAnalyseService;
import nl.lakedigital.djfc.commons.json.JsonRisicoAnalyse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@RequestMapping("/risicoanalyse")
@Controller
public class RisicoAnalyseController {
    @Inject
    private Mapper mapper;
    @Inject
    private RisicoAnalyseService risicoAnalyseService;

    @RequestMapping(method = RequestMethod.GET, value = "/lees", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonRisicoAnalyse lees(@QueryParam("bedrijfsId") Long bedrijfsId) {
        return mapper.map(risicoAnalyseService.leesBijBedrijf(bedrijfsId), JsonRisicoAnalyse.class);
    }
}
