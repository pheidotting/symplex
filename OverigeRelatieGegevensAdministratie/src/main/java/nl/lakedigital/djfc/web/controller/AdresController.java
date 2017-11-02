package nl.lakedigital.djfc.web.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.xml.OpvragenAdressenResponse;
import nl.lakedigital.djfc.domain.Adres;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.AdresService;
import nl.lakedigital.djfc.service.PostcodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@RequestMapping("/adres")
@Controller
public class AdresController extends AbstractController<Adres, JsonAdres> {
    @Inject
    private AdresService adresService;
    @Inject
    private PostcodeService postcodeService;

    public AdresController() {
        super(Adres.class, JsonAdres.class);
    }

    @Override
    public AbstractService getService() {
        return adresService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenAdressenResponse alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        logger.debug("alles soortEntiteit {} parentId {}", soortentiteit, parentid);

        List<Adres> domainEntiteiten = getService().alles(SoortEntiteit.valueOf(soortentiteit), parentid);
        OpvragenAdressenResponse opvragenAdressenResponse = new OpvragenAdressenResponse();

        for (Adres entiteit : domainEntiteiten) {
            opvragenAdressenResponse.getAdressen().add(mapper.map(entiteit, JsonAdres.class));
        }

        return opvragenAdressenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekTerm}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenAdressenResponse zoeken(@PathVariable("zoekTerm") String zoekTerm) {
        logger.debug("Zoeken met zoekterm {}", zoekTerm, Adres.class);

        OpvragenAdressenResponse opvragenAdressenResponse = new OpvragenAdressenResponse();
        List<Adres> opgehaald = getService().zoeken(zoekTerm);
        for (Adres d : opgehaald) {
            opvragenAdressenResponse.getAdressen().add(mapper.map(d, JsonAdres.class));
        }

        return opvragenAdressenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoekOpAdres/{zoekTerm}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenAdressenResponse zoekOpAdres(@PathVariable("zoekTerm") String zoekTerm) {
        logger.debug("Zoek op adres {}, {}", zoekTerm);

        OpvragenAdressenResponse opvragenAdressenResponse = new OpvragenAdressenResponse();
        List<Adres> opgehaald = adresService.zoekOpAdres(zoekTerm);
        for (Adres d : opgehaald) {
            opvragenAdressenResponse.getAdressen().add(mapper.map(d, JsonAdres.class));
        }

        return opvragenAdressenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoekOpPostcode/{zoekTerm}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenAdressenResponse zoekOpPostcode(@PathVariable("zoekTerm") String zoekTerm) {
        logger.debug("Zoek op adres {}", zoekTerm);

        OpvragenAdressenResponse opvragenAdressenResponse = new OpvragenAdressenResponse();
        List<Adres> opgehaald = adresService.zoekOpPostcode(zoekTerm);
        for (Adres d : opgehaald) {
            opvragenAdressenResponse.getAdressen().add(mapper.map(d, JsonAdres.class));
        }

        return opvragenAdressenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoekOpPlaats/{zoekTerm}", produces = MediaType.APPLICATION_XML)
    @ResponseBody
    public OpvragenAdressenResponse zoekOpPlaats(@PathVariable("zoekTerm") String zoekTerm) {
        logger.debug("Zoek op adres {}", zoekTerm);

        OpvragenAdressenResponse opvragenAdressenResponse = new OpvragenAdressenResponse();
        List<Adres> opgehaald = adresService.zoekOpPlaats(zoekTerm);
        for (Adres d : opgehaald) {
            opvragenAdressenResponse.getAdressen().add(mapper.map(d, JsonAdres.class));
        }

        return opvragenAdressenResponse;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public void opslaan(@RequestBody List<JsonAdres> jsonEntiteiten, HttpServletRequest httpServletRequest) {
        logger.info("Opslaan lijst met {} entiteiten", jsonEntiteiten.size());

        zetSessieWaarden(httpServletRequest);

        goOpslaan(jsonEntiteiten);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}")
    @ResponseBody
    public OpvragenAdressenResponse lees(@PathVariable Long id) {
        OpvragenAdressenResponse opvragenAdressenResponse = new OpvragenAdressenResponse();

        opvragenAdressenResponse.setAdressen(newArrayList(mapper.map(adresService.lees(id), JsonAdres.class)));

        return opvragenAdressenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alleAdressenBijLijstMetEntiteiten")
    @ResponseBody
    public OpvragenAdressenResponse alleAdressenBijLijstMetEntiteiten(@RequestParam("soortEntiteit") String soortEntiteit, @RequestParam("lijst") List<Long> ids) {
        OpvragenAdressenResponse opvragenAdressenResponse = new OpvragenAdressenResponse();

        List<Adres> adressen = adresService.alleAdressenBijLijstMetEntiteiten(ids, SoortEntiteit.valueOf(soortEntiteit));

        if (!adressen.isEmpty()) {
            opvragenAdressenResponse.setAdressen(newArrayList());
            for (Adres adres : adressen) {
                opvragenAdressenResponse.getAdressen().add(mapper.map(adres, JsonAdres.class));
            }
        }

        return opvragenAdressenResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ophalenAdresOpPostcode/{postcode}/{huisnummer}/{toggle}")
    @ResponseBody
    public OpvragenAdressenResponse ophalenAdresOpPostcode(@PathVariable("postcode") String postcode, @PathVariable("huisnummer") String huisnummer, @PathVariable("toggle") boolean toggle) {
        logger.debug("Toggle is {}", toggle);
        OpvragenAdressenResponse opvragenAdressenResponse = new OpvragenAdressenResponse();
        if (!toggle) {
            String adres = "https://postcode-api.apiwise.nl/v2/addresses/?postcode=" + postcode + "&number=" + huisnummer;

            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            Client client = Client.create(clientConfig);
            WebResource webResource = client.resource(adres);
            ClientResponse response = webResource.header("X-Api-Key", "FYEYGHHNFV3sZutux7LcX8ng8VizXWPk1HWxPPX9").accept("application/x-www-form-urlencoded; charset=UTF-8").get(ClientResponse.class);

            String antwoord = response.getEntity(String.class);
            logger.debug("Antwoord van de postcode api: {}", antwoord);

            JsonAdres jsonAdres = postcodeService.extraHeerAdres(antwoord);
            jsonAdres.setPostcode(postcode);
            if (huisnummer != null) {
                jsonAdres.setHuisnummer(Long.valueOf(huisnummer));
            }

            opvragenAdressenResponse.getAdressen().add(jsonAdres);
        } else {
            JsonAdres adres = new JsonAdres();

            if ("7891tn".equalsIgnoreCase(postcode)) {
                adres.setPostcode("7891TN");
                adres.setHuisnummer(Long.valueOf(huisnummer));
                adres.setStraat("Boogschuttert");
                adres.setPlaats("Klazienaveen");
            } else if ("7894ab".equalsIgnoreCase(postcode)) {
                adres.setPostcode("7894AB");
                adres.setHuisnummer(Long.valueOf(huisnummer));
                adres.setStraat("Eemsladnweg");
                adres.setPlaats("Zwartemeer");
            } else if ("7891rb".equalsIgnoreCase(postcode)) {
                adres.setPostcode("7891RB");
                adres.setHuisnummer(Long.valueOf(huisnummer));
                adres.setStraat("Herdersstraaat");
                adres.setPlaats("Klazienaveen");
            }

            opvragenAdressenResponse.getAdressen().add(adres);
        }

        return opvragenAdressenResponse;
    }
}
