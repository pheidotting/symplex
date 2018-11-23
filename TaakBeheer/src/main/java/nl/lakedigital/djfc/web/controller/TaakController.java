package nl.lakedigital.djfc.web.controller;

import com.codahale.metrics.Timer;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.Taak;
import nl.lakedigital.djfc.commons.xml.OpvragenTakenResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.TaakService;
import nl.lakedigital.djfc.web.controller.mapper.TaakMapper;
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
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/taak")
@Controller
public class TaakController {
    private static Logger LOGGER = LoggerFactory.getLogger(TaakController.class);

    @Inject
    private TaakService taakService;
    //    @Inject
    //    private IdentificatieClient identificatieClient;
    @Inject
    private MetricsService metricsService;

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenTakenResponse lees(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        Timer.Context timer = metricsService.addTimerMetric(" lees", TaakController.class);

        zetSessieWaarden(httpServletRequest);

        Taak taak = taakService.lees(id);

        OpvragenTakenResponse opvragenTakenResponse = new OpvragenTakenResponse();
        opvragenTakenResponse.getTaken().add(new TaakMapper().apply(taak));

        metricsService.stop(timer);

        return opvragenTakenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenTakenResponse alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid, HttpServletRequest httpServletRequest) {
        Timer.Context timer = metricsService.addTimerMetric(" alles", TaakController.class);

        zetSessieWaarden(httpServletRequest);

        List<Taak> taken = taakService.alleTaken(SoortEntiteit.valueOf(soortentiteit), parentid);

        OpvragenTakenResponse opvragenTakenResponse = new OpvragenTakenResponse();
        opvragenTakenResponse.getTaken().addAll(taken.stream().map(new TaakMapper()).collect(Collectors.toList()));

        metricsService.stop(timer);

        return opvragenTakenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allesopenstaand", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenTakenResponse allesopenstaand(HttpServletRequest httpServletRequest) {
        Timer.Context timer = metricsService.addTimerMetric(" allesopenstaand", TaakController.class);

        zetSessieWaarden(httpServletRequest);

        List<Taak> taken = taakService.allesOpenstaand();

        OpvragenTakenResponse opvragenTakenResponse = new OpvragenTakenResponse();
        opvragenTakenResponse.getTaken().addAll(taken.stream().map(new TaakMapper()).collect(Collectors.toList()));

        metricsService.stop(timer);

        return opvragenTakenResponse;
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
