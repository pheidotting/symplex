package nl.dias.web.authorisatie;

import nl.dias.web.medewerker.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;

@RequestMapping("/log4j")
@Controller
public class Log4JController extends AbstractController {
    @RequestMapping(method = RequestMethod.POST, value = "/log4javascript")
    @ResponseBody
    public void log4javascript(@FormParam("logger") String logger, @FormParam("timestamp") String timestamp, @FormParam("level") String level, @FormParam("url") String url, @FormParam("message") String message, @FormParam("layout") String layout, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);
        MDC.put("url", url);

        String loggerName = logger;
        if (logger == null || "".equals(logger)) {
            loggerName = this.getClass().toString();
        }
        final Logger LOGGER = LoggerFactory.getLogger(loggerName);

        if ("debug".equalsIgnoreCase(level)) {
            LOGGER.debug(message);
        } else if ("info".equalsIgnoreCase(level)) {
            LOGGER.info(message);
        } else if ("warn".equalsIgnoreCase(level)) {
            LOGGER.warn(message);
        } else if ("error".equalsIgnoreCase(level)) {
            LOGGER.error(message);
        } else if ("fatal".equalsIgnoreCase(level)) {
            LOGGER.error(message);
        } else {
            LOGGER.trace(message);
        }
    }

}
