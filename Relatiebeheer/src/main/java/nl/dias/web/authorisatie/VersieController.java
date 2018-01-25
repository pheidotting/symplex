package nl.dias.web.authorisatie;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Versie;
import nl.dias.domein.VersieGelezen;
import nl.dias.repository.GebruikerRepository;
import nl.dias.repository.VersieRepository;
import nl.dias.web.medewerker.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequestMapping("versies")
@Controller
public class VersieController extends AbstractController {
    private final static Logger LOGGER = LoggerFactory.getLogger(VersieController.class);

    @Inject
    private VersieRepository versieRepository;
    @Inject
    private GebruikerRepository gebruikerRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/nieuweversie", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void nieuweversie(@RequestBody String versieinfo) {
        int pos = versieinfo.indexOf(" ");
        String versieNummer = versieinfo.substring(0, pos);
        String releasenotes = versieinfo.substring(pos + 1);

        Versie versie = new Versie(versieNummer, releasenotes);
        versieRepository.opslaan(versie);

        List<Long> gebruikerIds = gebruikerRepository.alleGebruikersDieKunnenInloggen().stream().map(new Function<Gebruiker, Long>() {
            @Override
            public Long apply(Gebruiker gebruiker) {
                return gebruiker.getId();
            }
        }).collect(Collectors.toList());

        for (Long gebruikerId : gebruikerIds) {
            versieRepository.opslaan(new VersieGelezen(versie.getId(), gebruikerId));
        }
    }

    @SendTo("/pushNieuweVersie")
    public Map<String, String> pushNieuweVersie(HttpServletRequest httpServletRequest) {
        Long gebruikerId = getIngelogdeGebruiker(httpServletRequest).getId();

        Map<String, String> result = new HashMap<>();
        for (Versie v : versieRepository.getOngelezenVersies(gebruikerId)) {
            result.put(v.getVersie(), v.getReleasenotes());
        }
        return result;
    }

}
