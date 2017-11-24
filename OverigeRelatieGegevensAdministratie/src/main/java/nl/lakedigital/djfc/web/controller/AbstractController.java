package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.AbstracteJsonEntiteitMetSoortEnId;
import nl.lakedigital.djfc.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.inloggen.Sessie;
import nl.lakedigital.djfc.mapper.Mapper;
import nl.lakedigital.djfc.service.AbstractService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController<D extends AbstracteEntiteitMetSoortEnId, J extends AbstracteJsonEntiteitMetSoortEnId> {
    protected static Logger logger;

    private final Class<D> domainType;
    private final Class<J> jsonType;

    public AbstractController(Class<D> domainType, Class<J> jsonType) {
        this.domainType = domainType;
        this.jsonType = jsonType;
        logger = LoggerFactory.getLogger(AbstractController.class);
    }

    @Inject
    protected Mapper mapper;

    public abstract AbstractService getService();

    public abstract void opslaan(List<J> jsonEntiteiten, HttpServletRequest httpServletRequest);

    public void goOpslaan(List<J> jsonEntiteiten) {
        if (jsonEntiteiten != null && !jsonEntiteiten.isEmpty()) {
            J eersteEntiteit = jsonEntiteiten.get(0);

            List<D> entiteiten = new ArrayList<>();
            for (J jsonEntiteit : jsonEntiteiten) {
                logger.debug("Opslaan {}", ReflectionToStringBuilder.toString(jsonEntiteit, ToStringStyle.SHORT_PREFIX_STYLE));

                entiteiten.add(mapper.map(jsonEntiteit, domainType));
            }
            getService().opslaan(entiteiten, SoortEntiteit.valueOf(eersteEntiteit.getSoortEntiteit()), eersteEntiteit.getEntiteitId());
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{soortentiteit}/{parentid}")
    @ResponseBody
    public void verwijderen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid, HttpServletRequest httpServletRequest) {
        logger.debug("Verwijderen entiteiten {} bij {} en {}", domainType, soortentiteit, parentid);

        zetSessieWaarden(httpServletRequest);

        getService().verwijderen(SoortEntiteit.valueOf(soortentiteit), parentid);
    }

    protected void zetSessieWaarden(HttpServletRequest httpServletRequest) {
        Long ingelogdeGebruiker = getIngelogdeGebruiker(httpServletRequest);
        String trackAndTraceId = getTrackAndTraceId(httpServletRequest);

        MDC.put("ingelogdeGebruiker", ingelogdeGebruiker + "");
        MDC.put("trackAndTraceId", trackAndTraceId);
        Sessie.setIngelogdeGebruiker(ingelogdeGebruiker);
        Sessie.setTrackAndTraceId(trackAndTraceId);
    }


    protected Long getIngelogdeGebruiker(HttpServletRequest httpServletRequest) {
        Long ingelogdeGebruiker = Long.valueOf(httpServletRequest.getHeader("ingelogdeGebruiker"));
        logger.debug("Ingelogde Gebruiker opgehaald : {}", ingelogdeGebruiker);

        return ingelogdeGebruiker;
    }

    protected String getTrackAndTraceId(HttpServletRequest httpServletRequest) {
        String tati = httpServletRequest.getHeader("trackAndTraceId");
        logger.debug("Track And Trace Id : {}", tati);

        return tati;
    }


}
