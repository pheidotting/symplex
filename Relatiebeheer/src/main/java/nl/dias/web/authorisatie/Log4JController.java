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
    public void log4javascript(@FormParam("logger") String loggerNameIn, @FormParam("timestamp") String timestamp, @FormParam("level") String level, @FormParam("url") String url, @FormParam("message") String message, @FormParam("layout") String layout, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);
        MDC.remove("url");
        MDC.put("url", url);

        String loggerName = loggerNameIn;
        if (loggerNameIn == null || "".equals(loggerNameIn)) {
            loggerName = this.getClass().toString();
        }
        final Logger logger = LoggerFactory.getLogger(loggerName);

        if ("debug".equalsIgnoreCase(level)) {
            logger.debug(message);
        } else if ("info".equalsIgnoreCase(level)) {
            logger.info(message);
        } else if ("warn".equalsIgnoreCase(level)) {
            logger.warn(message);
        } else if ("error".equalsIgnoreCase(level) || "fatal".equalsIgnoreCase(level)) {
            logger.error(message);
        } else {
            logger.trace(message);
        }
    }

}
