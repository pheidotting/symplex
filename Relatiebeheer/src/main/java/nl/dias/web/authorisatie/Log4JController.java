package nl.dias.web.authorisatie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.FormParam;

@RequestMapping("/log4j")
@Controller
public class Log4JController {
    @RequestMapping(method = RequestMethod.POST, value = "/log4javascript")
    @ResponseBody
    public void log4javascript(@FormParam("logger") String logger, @FormParam("timestamp") String timestamp, @FormParam("level") String level, @FormParam("url") String url, @FormParam("message") String message, @FormParam("layout") String layout) {
        final Logger LOGGER = LoggerFactory.getLogger(logger);

        if ("debug".equalsIgnoreCase(level)) {
            LOGGER.debug("URL {}, Message {}", url, message);
        } else if ("info".equalsIgnoreCase(level)) {
            LOGGER.info("URL {}, Message {}", url, message);
        } else if ("warn".equalsIgnoreCase(level)) {
            LOGGER.warn("URL {}, Message {}", url, message);
        } else if ("error".equalsIgnoreCase(level)) {
            LOGGER.error("URL {}, Message {}", url, message);
        } else if ("fatal".equalsIgnoreCase(level)) {
            LOGGER.error("URL {}, Message {}", url, message);
        } else {
            LOGGER.trace("URL {}, Message {}", url, message);
        }
    }

}
