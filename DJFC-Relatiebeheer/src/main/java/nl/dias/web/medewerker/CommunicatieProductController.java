package nl.dias.web.medewerker;

import nl.dias.service.AuthorisatieService;
import nl.lakedigital.djfc.client.communicatie.CommunicatieClient;
import nl.lakedigital.djfc.commons.json.JsonCommunicatieProduct;
import nl.lakedigital.djfc.commons.json.OpslaanCommunicatieProduct;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Controller
@RequestMapping("/communicatieproduct")
public class CommunicatieProductController extends AbstractController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommunicatieProductController.class);
    @Inject
    private AuthorisatieService authorisatieService;

    @Inject
    private CommunicatieClient communicatieClient;

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonCommunicatieProduct> alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        return communicatieClient.alles(soortentiteit, parentid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/nieuw", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Long nieuwCommunicatieProduct(@RequestBody OpslaanCommunicatieProduct opslaanCommunicatieProduct, HttpServletRequest httpServletRequest) {
        LOGGER.debug("Opslaan {}", ReflectionToStringBuilder.toString(opslaanCommunicatieProduct, ToStringStyle.SHORT_PREFIX_STYLE));

        opslaanCommunicatieProduct.setMedewerker(getIngelogdeGebruiker(httpServletRequest));

        return communicatieClient.nieuwCommunicatieProduct(opslaanCommunicatieProduct, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/versturen/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void versturen(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        communicatieClient.versturen(id, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/markeerAlsGelezen/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void markeerAlsGelezen(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        communicatieClient.markeerAlsGelezen(id, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/markeerOmTeVerzenden/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void markeerOmTeVerzenden(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        communicatieClient.markeerOmTeVerzenden(id, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonCommunicatieProduct lees(@PathVariable("id") Long id) {
        return communicatieClient.lees(id);
    }

}
