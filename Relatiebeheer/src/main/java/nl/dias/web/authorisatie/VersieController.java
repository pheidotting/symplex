package nl.dias.web.authorisatie;

import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.core.MediaType;

@RequestMapping("versies")
@Controller
public class VersieController {
    private final static Logger LOGGER = LoggerFactory.getLogger(VersieController.class);

    @RequestMapping(method = RequestMethod.POST, value = "/nieuweversie", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void nieuweversie(@RequestBody String[] versieinfo) {
        LOGGER.debug(ReflectionToStringBuilder.toString(versieinfo));
    }
}
