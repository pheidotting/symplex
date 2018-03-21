package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.LicentieService;
import nl.lakedigital.djfc.web.dto.Licentie;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/licenties")
@Controller
public class LicentieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LicentieController.class);
    @Inject
    private MetricsService metricsService;
    @Inject
    private LicentieService licentieService;

    @RequestMapping(method = RequestMethod.GET, value = "/actievelicentie/{kantoorid}")
    @ResponseBody
    public Licentie actievelicentie(@PathVariable("kantoorid") Long kantoorid, HttpServletRequest httpServletRequest) {
        metricsService.addMetric("actievelicentie", LicentieController.class, null, null);

        zetSessieWaarden(httpServletRequest);

        LocalDate einddatum = licentieService.eindDatumLicentie(kantoorid);

        return new Licentie(null, einddatum == null ? null : einddatum.toString("dd-MM-yyyy"));
    }


    protected void zetSessieWaarden(HttpServletRequest httpServletRequest) {
        Long ingelogdeGebruiker = getIngelogdeGebruiker(httpServletRequest);
        String trackAndTraceId = getTrackAndTraceId(httpServletRequest);
        String ingelogdeGebruikerOpgemaakt = getIngelogdeGebruikerOpgemaakt(httpServletRequest);
        String url = getUrl(httpServletRequest);

        MDC.put("ingelogdeGebruiker", ingelogdeGebruiker + "");
        MDC.put("ingelogdeGebruikerOpgemaakt", ingelogdeGebruikerOpgemaakt + "");
        MDC.put("url", url + "");
        if (trackAndTraceId != null) {
            MDC.put("trackAndTraceId", trackAndTraceId);
        }
    }


    protected Long getIngelogdeGebruiker(HttpServletRequest httpServletRequest) {
        String ig = httpServletRequest.getHeader("ingelogdeGebruiker");
        if (ig != null) {
            return Long.valueOf(ig);
        } else {
            return null;
        }
    }

    protected String getIngelogdeGebruikerOpgemaakt(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("ingelogdeGebruikerOpgemaakt");
    }

    protected String getTrackAndTraceId(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("trackAndTraceId");
    }

    protected String getUrl(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("url");
    }
}
