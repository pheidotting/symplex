package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.xml.OpvragenPolisSoortenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/polis")
@Controller
public class OpdrachtenController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpdrachtenController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/alleParticulierePolisSoorten")
    @ResponseBody
    public OpvragenPolisSoortenResponse alleParticulierePolisSoorten() {
        OpvragenPolisSoortenResponse opvragenPolisSoortenResponse = new OpvragenPolisSoortenResponse();


        return opvragenPolisSoortenResponse;
    }

}
