package nl.dias.web.medewerker;

import com.codahale.metrics.Timer;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import nl.dias.service.PostcodeService;
import nl.lakedigital.djfc.client.polisadministratie.VerzekeringsMaatschappijClient;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonVerzekeringsMaatschappij;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/overig")
@Controller
@PropertySource("file:djfc.app.properties")
public class JsonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonController.class);

    @Inject
    private PostcodeService postcodeService;
    @Inject
    private MetricsService metricsService;
    @Inject
    private VerzekeringsMaatschappijClient verzekeringsMaatschappijClient;

    @RequestMapping(method = RequestMethod.GET, value = "/lijstVerzekeringsMaatschappijen", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Map<Long, String> lijstVerzekeringsMaatschappijen() {

        LOGGER.debug("ophalen lijst met VerzekeringsMaatschappijen");

        //        List<VerzekeringsMaatschappij> lijst = maatschappijService.alles();
        List<JsonVerzekeringsMaatschappij> lijst = verzekeringsMaatschappijClient.lijstVerzekeringsMaatschappijen();

        LOGGER.debug("Gevonden, " + lijst.size() + " VerzekeringsMaatschappijen");

        lijst.sort(new Comparator<JsonVerzekeringsMaatschappij>() {
            @Override
            public int compare(JsonVerzekeringsMaatschappij o1, JsonVerzekeringsMaatschappij o2) {
                return o1.getNaam().compareTo(o2.getNaam());
            }
        });

        Map<Long, String> ret = new HashMap<>();
        ret.put(0L, "Kies een maatschappij...");

        for (JsonVerzekeringsMaatschappij vm : lijst) {
            ret.put(vm.getId(), vm.getNaam());
        }

        LOGGER.debug("{}", ret);

        return ret;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/extraInfo", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public String extraInfo() {

        String omgeving = System.getProperty("omgeving");

        LOGGER.debug("omgeving " + omgeving);

        return omgeving;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ophalenAdresOpPostcode/{postcode}/{huisnummer}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonAdres ophalenAdresOpPostcode(@PathVariable("postcode") String postcode, @PathVariable("huisnummer") String huisnummer) {
        metricsService.addMetric("ophalenAdresOpPostcode", JsonController.class, null, null);

        String adres = "https://postcode-api.apiwise.nl/v2/addresses/?postcode=" + postcode + "&number=" + huisnummer;

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(adres);
        ClientResponse response = webResource.header("X-Api-Key", "FYEYGHHNFV3sZutux7LcX8ng8VizXWPk1HWxPPX9").accept("application/x-www-form-urlencoded; charset=UTF-8").get(ClientResponse.class);

        Timer.Context timer = metricsService.addTimerMetric("ophalenAdresOpPostcode", JsonController.class);

        String antwoord = response.getEntity(String.class);

        metricsService.stop(timer);

        LOGGER.debug("Antwoord van de postcode api: {}", antwoord);

        JsonAdres jsonAdres = postcodeService.extraHeerAdres(antwoord);
        jsonAdres.setPostcode(postcode);
        if (huisnummer != null) {
            jsonAdres.setHuisnummer(Long.valueOf(huisnummer));
        }

        return jsonAdres;
    }
}
