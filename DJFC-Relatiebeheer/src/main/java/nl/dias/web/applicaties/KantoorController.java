package nl.dias.web.applicaties;

import nl.dias.mapper.Mapper;
import nl.dias.repository.KantoorRepository;
import nl.lakedigital.djfc.commons.json.JsonKantoor;
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

@RequestMapping("/kantoor")
@Controller
public class KantoorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorController.class);

    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonKantoor lees(@PathVariable("id") Long id) {
        LOGGER.debug("Ophalen Relatie met id : " + id);

        JsonKantoor jsonKantoor = mapper.map(kantoorRepository.lees(id), JsonKantoor.class);

        LOGGER.debug("Naar de front-end : {}", ReflectionToStringBuilder.toString(jsonKantoor, ToStringStyle.SHORT_PREFIX_STYLE));

        return jsonKantoor;
    }
}
