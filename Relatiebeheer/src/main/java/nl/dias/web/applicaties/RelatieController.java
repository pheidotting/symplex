package nl.dias.web.applicaties;

import com.google.common.base.Function;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Relatie;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.RelatieMapper;
import nl.lakedigital.djfc.commons.json.JsonRelatie;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;

//@RequestMapping("/relatie")
//@Controller
public class RelatieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RelatieController.class);

    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private RelatieMapper relatieMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonRelatie lees(@PathVariable("id") Long id) {
        LOGGER.debug("Ophalen Relatie met id : " + id);

        JsonRelatie jsonRelatie = relatieMapper.mapNaarJson((Relatie) gebruikerService.lees(id));

        LOGGER.debug("Naar de front-end : " + jsonRelatie);

        return jsonRelatie;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoekOpEmailadres/{emailadres}/dummy", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonRelatie zoekOpEmailadres(@PathVariable("emailadres") String emailadres) {
        LOGGER.debug("Zoeken relatie met emailadres '{}'", emailadres);

        try {
            return relatieMapper.mapNaarJson((Relatie) gebruikerService.zoek(emailadres));
        } catch (NietGevondenException nre) {
            LOGGER.trace("{}", nre);
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoekOpNaam/{naam}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonRelatie> zoekOpNaam(@PathVariable("naam") String naam) {
        List<Gebruiker> gevondenGebruikers = gebruikerService.zoekOpNaam(naam);
        return relatieMapper.mapAllNaarJson(newArrayList(transform(gevondenGebruikers, new Function<Gebruiker, Relatie>() {
            @Override
            public Relatie apply(Gebruiker gebruiker) {
                return (Relatie) gebruiker;
            }
        })));
    }
}
