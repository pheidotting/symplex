package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.TaakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@RequestMapping("/licenties")
@Controller
public class LicentieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LicentieController.class);
    @Inject
    private MetricsService metricsService;
    @Inject
    private TaakService licentieService;

    //    @RequestMapping(method = RequestMethod.GET, value = "/actievelicentie/{kantoorid}")
    //    @ResponseBody
    //    public LicentieResponse actievelicentie(@PathVariable("kantoorid") Long kantoorid, HttpServletRequest httpServletRequest) {
    //        metricsService.addMetric("actievelicentie", LicentieController.class, null, null);
    //
    //        LOGGER.info("Ophalen actieve licentie bij kantoor met id {}", kantoorid);
    //
    //        zetSessieWaarden(httpServletRequest);
    //
    //        nl.lakedigital.djfc.domain.Licentie licentie = licentieService.actieveLicentie(kantoorid);
    //        LocalDate einddatum = licentieService.actieveLicentie(licentie);
    //
    //        LicentieResponse licentieResponse = new LicentieResponse();
    //
    //        licentieResponse.addLicentie(new Licentie(licentie.getClass().getSimpleName().toLowerCase(), einddatum == null ? null : einddatum.toString("yyyy-MM-dd")));
    //
    //        return licentieResponse;
    //    }
    //
    //    protected void zetSessieWaarden(HttpServletRequest httpServletRequest) {
    //        Long ingelogdeGebruiker = getIngelogdeGebruiker(httpServletRequest);
    //        String trackAndTraceId = getTrackAndTraceId(httpServletRequest);
    //        String ingelogdeGebruikerOpgemaakt = getIngelogdeGebruikerOpgemaakt(httpServletRequest);
    //        String url = getUrl(httpServletRequest);
    //
    //        MDC.put("ingelogdeGebruiker", ingelogdeGebruiker + "");
    //        MDC.put("ingelogdeGebruikerOpgemaakt", ingelogdeGebruikerOpgemaakt + "");
    //        MDC.put("url", url + "");
    //        if (trackAndTraceId != null) {
    //            MDC.put("trackAndTraceId", trackAndTraceId);
    //        }
    //    }
    //
    //    protected Long getIngelogdeGebruiker(HttpServletRequest httpServletRequest) {
    //        String ig = httpServletRequest.getHeader("ingelogdeGebruiker");
    //        if (ig != null) {
    //            return Long.valueOf(ig);
    //        } else {
    //            return null;
    //        }
    //    }
    //
    //    protected String getIngelogdeGebruikerOpgemaakt(HttpServletRequest httpServletRequest) {
    //        return httpServletRequest.getHeader("ingelogdeGebruikerOpgemaakt");
    //    }
    //
    //    protected String getTrackAndTraceId(HttpServletRequest httpServletRequest) {
    //        return httpServletRequest.getHeader("trackAndTraceId");
    //    }
    //
    //    protected String getUrl(HttpServletRequest httpServletRequest) {
    //        return httpServletRequest.getHeader("url");
    //    }
}
