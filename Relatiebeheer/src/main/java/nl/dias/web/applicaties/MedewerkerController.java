package nl.dias.web.applicaties;

import nl.dias.mapper.Mapper;
import nl.dias.service.GebruikerService;
import nl.lakedigital.djfc.commons.json.JsonMedewerker;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@RequestMapping("/medewerker")
@Controller
public class MedewerkerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedewerkerController.class);

    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonMedewerker lees(@PathVariable("id") Long id) {
        LOGGER.debug("Ophalen Relatie met id : " + id);

        JsonMedewerker jsonMedewerker = mapper.map(gebruikerService.lees(id), JsonMedewerker.class);

        LOGGER.debug("Naar de front-end : {}", ReflectionToStringBuilder.toString(jsonMedewerker, ToStringStyle.SHORT_PREFIX_STYLE));

        return jsonMedewerker;
    }
}
