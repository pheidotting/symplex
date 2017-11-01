package nl.dias.web.medewerker;

import com.google.common.collect.Lists;
import nl.dias.domein.JaarCijfers;
import nl.dias.mapper.Mapper;
import nl.dias.service.JaarCijfersService;
import nl.lakedigital.djfc.commons.json.JsonJaarCijfers;
import nl.lakedigital.djfc.commons.json.comperators.JsonJaarCijfersComparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@RequestMapping("/jaarcijfers")
@Controller
public class JaarCijfersController {
    @Inject
    private Mapper mapper;
    @Inject
    private JaarCijfersService jaarCijfersService;

    @RequestMapping(method = RequestMethod.GET, value = "/lijst", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonJaarCijfers> lijstBijBedrijf(@QueryParam("bedrijfsId") Long bedrijfsId) {
        List<JsonJaarCijfers> jsonJaarCijferses = Lists.newArrayList();

        for (JaarCijfers jaarCijfers : jaarCijfersService.alles(bedrijfsId)) {
            jsonJaarCijferses.add(mapper.map(jaarCijfers, JsonJaarCijfers.class));
        }

        Collections.sort(jsonJaarCijferses, new JsonJaarCijfersComparator());
        return jsonJaarCijferses;
    }
}
