package nl.lakedigital.djfc.web.controller;

import com.google.gson.Gson;
import nl.lakedigital.djfc.commons.json.JsonBijlage;
import nl.lakedigital.djfc.commons.json.JsonGroepBijlages;
import nl.lakedigital.djfc.commons.json.WijzigenOmschrijvingBijlage;
import nl.lakedigital.djfc.commons.xml.OpvragenBijlagesResponse;
import nl.lakedigital.djfc.commons.xml.OpvragenGroepBijlagesResponse;
import nl.lakedigital.djfc.domain.Bijlage;
import nl.lakedigital.djfc.domain.GroepBijlages;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.BijlageService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configuration
@RequestMapping("/bijlage")
@Controller
@PropertySources({@PropertySource("classpath:application.properties"), @PropertySource(value = "file:app.properties", ignoreResourceNotFound = true)})
public class BijlageController extends AbstractController<Bijlage, JsonBijlage> {
    @Inject
    private BijlageService bijlageService;
    @Value("${uploadpad}")
    private String uploadpad;

    public BijlageController() {
        super(Bijlage.class, JsonBijlage.class);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public AbstractService getService() {
        return bijlageService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenBijlagesResponse alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        logger.debug("alles soortEntiteit {} parentId {}", soortentiteit, parentid);

        List<Bijlage> domainEntiteiten = getService().alles(SoortEntiteit.valueOf(soortentiteit), parentid);
        OpvragenBijlagesResponse opvragenBijlagesResponse = new OpvragenBijlagesResponse();

        for (Bijlage entiteit : domainEntiteiten) {
            logger.debug(ReflectionToStringBuilder.toString(entiteit, ToStringStyle.SHORT_PREFIX_STYLE));
            opvragenBijlagesResponse.getBijlages().add(mapper.map(entiteit, JsonBijlage.class));
        }

        return opvragenBijlagesResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekTerm}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenBijlagesResponse zoeken(@PathVariable("zoekTerm") String zoekTerm) {
        logger.debug("Zoeken met zoeketerm {}, {}", zoekTerm, Bijlage.class);

        OpvragenBijlagesResponse opvragenBijlagesResponse = new OpvragenBijlagesResponse();
        List<Bijlage> opgehaald = getService().zoeken(zoekTerm);
        for (Bijlage d : opgehaald) {
            opvragenBijlagesResponse.getBijlages().add(mapper.map(d, JsonBijlage.class));
        }

        return opvragenBijlagesResponse;
    }


    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/opslaanBijlages")
    @ResponseBody
    public void opslaan(@RequestBody List<JsonBijlage> jsonEntiteiten, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        goOpslaan(jsonEntiteiten);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/wijzigOmschrijvingBijlage")
    @ResponseBody
    public void wijzigOmschrijvingBijlage(@RequestBody WijzigenOmschrijvingBijlage wijzigenOmschrijvingBijlage, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        bijlageService.wijzigOmschrijvingBijlage(wijzigenOmschrijvingBijlage.getBijlageId(), wijzigenOmschrijvingBijlage.getNieuweOmschrijving());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public Long opslaan(@RequestBody JsonBijlage jsonBijlage, HttpServletRequest httpServletRequest) {
        logger.info("Opslaan {}", ReflectionToStringBuilder.toString(jsonBijlage, ToStringStyle.SHORT_PREFIX_STYLE));

        zetSessieWaarden(httpServletRequest);

        Bijlage bijlage = mapper.map(jsonBijlage, Bijlage.class);
        bijlageService.opslaan(bijlage);

        return bijlage.getId();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijder/{id}")
    @ResponseBody
    public void verwijder(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        bijlageService.verwijder(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/genereerBestandsnaam")
    @ResponseBody
    public String genereerBestandsnaam() {
        String identificatie = UUID.randomUUID().toString().replace("-", "");

        return new Gson().toJson(identificatie);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUploadPad")
    @ResponseBody
    public String getUploadPad() {
        return new Gson().toJson(uploadpad);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}")
    @ResponseBody
    public OpvragenBijlagesResponse lees(@PathVariable("id") Long id) {
        OpvragenBijlagesResponse response = new OpvragenBijlagesResponse();

        response.getBijlages().add(mapper.map(bijlageService.lees(id), JsonBijlage.class));

        return response;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaanGroep")
    @ResponseBody
    public Long opslaanGroep(@RequestBody JsonGroepBijlages jsonGroepBijlages) {
        GroepBijlages groepBijlages = mapper.map(jsonGroepBijlages, GroepBijlages.class);

        bijlageService.opslaanGroepBijlages(groepBijlages);

        return groepBijlages.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alleGroepen/{soortentiteit}/{parentid}")
    @ResponseBody
    public OpvragenGroepBijlagesResponse alleGroepen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        logger.debug("alles JsonGroepBijlages voor soortEntiteit {} parentId {}", soortentiteit, parentid);

        List<GroepBijlages> domainEntiteiten = bijlageService.alleGroepenBijlages(SoortEntiteit.valueOf(soortentiteit), parentid);

        logger.debug("Opgehaald {} entiteiten", domainEntiteiten.size());

        List<JsonGroepBijlages> jsonEntiteiten = new ArrayList<>();

        for (GroepBijlages entiteit : domainEntiteiten) {
            logger.debug("map map {}", ReflectionToStringBuilder.toString(entiteit, ToStringStyle.SHORT_PREFIX_STYLE));
            JsonGroepBijlages jsonGroepBijlages = mapper.map(entiteit, JsonGroepBijlages.class);

            ReflectionToStringBuilder.toString(jsonEntiteiten, ToStringStyle.SHORT_PREFIX_STYLE);

            jsonEntiteiten.add(jsonGroepBijlages);
        }

        OpvragenGroepBijlagesResponse opvragenGroepBijlagesResponse = new OpvragenGroepBijlagesResponse();
        opvragenGroepBijlagesResponse.setBijlages(jsonEntiteiten);

        return opvragenGroepBijlagesResponse;
    }

}
