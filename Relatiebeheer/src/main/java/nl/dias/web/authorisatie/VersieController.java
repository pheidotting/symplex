package nl.dias.web.authorisatie;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Versie;
import nl.dias.domein.VersieGelezen;
import nl.dias.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.dias.messaging.sender.NieuweVersieRequestSender;
import nl.dias.repository.GebruikerRepository;
import nl.dias.repository.VersieRepository;
import nl.dias.web.medewerker.AbstractController;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.as.messaging.request.communicatie.NieuweVersieRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

@RequestMapping("versies")
@Controller
public class VersieController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersieController.class);

    @Inject
    private VersieRepository versieRepository;
    @Inject
    private GebruikerRepository gebruikerRepository;
    @Inject
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private MetricsService metricsService;
    @Inject
    private NieuweVersieRequestSender nieuweVersieRequestSender;

    @RequestMapping(method = RequestMethod.POST, value = "/nieuweversie", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void nieuweversie(@RequestBody String vi) {
        LOGGER.debug(vi);
        String versieinfo = vi;
        if (versieinfo.trim().toLowerCase().startsWith("versie")) {
            versieinfo = versieinfo.substring(6).trim();

            int pos = versieinfo.indexOf(' ');
            String versieNummer = versieinfo.substring(0, pos);
            String releasenotes = versieinfo.substring(pos + 1);
            releasenotes = releasenotes.replace(" - ", "\n - ");

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

            SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
            soortEntiteitEnEntiteitId.setEntiteitId(versie.getId());
            soortEntiteitEnEntiteitId.setSoortEntiteit(SoortEntiteit.VERSIE);

            entiteitenOpgeslagenRequestSender.send(newArrayList(soortEntiteitEnEntiteitId));

            NieuweVersieRequest nieuweVersieRequest = new NieuweVersieRequest();
            nieuweVersieRequest.setVersie(versieNummer);
            nieuweVersieRequest.setReleasenotes(releasenotes);
            nieuweVersieRequest.addGeadresseerde(null, "patrick@heidotting.nl", "Patrick", null, "Heidotting");
            nieuweVersieRequest.addGeadresseerde(null, "bene@dejongefinancieelconsult.nl", "Bene", "de", "Jonge");

            nieuweVersieRequestSender.send(nieuweVersieRequest);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/checkNieuweversie", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Map<String, String> checkNieuweversie(HttpServletRequest httpServletRequest) {
        Map<String, String> result = new HashMap<>();

        if (getIngelogdeGebruiker(httpServletRequest) != null) {
            Long gebruikerId = getIngelogdeGebruiker(httpServletRequest).getId();

            for (Versie v : versieRepository.getOngelezenVersies(gebruikerId)) {
                result.put("Versie " + v.getVersie(), identificatieClient.zoekIdentificatie("VERSIE", v.getId()).getIdentificatie());
            }
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/leesVersie/{identificatie}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public nl.lakedigital.djfc.domain.response.Versie leesVersie(@PathVariable("identificatie") String identificatieCode, HttpServletRequest httpServletRequest) {
        metricsService.addMetric("leesVersie", VersieController.class, null, null);

        Long id = identificatieClient.zoekIdentificatieCode(identificatieCode).getEntiteitId();

        Versie versie = versieRepository.lees(id);

        return new nl.lakedigital.djfc.domain.response.Versie(versie.getVersie(), versie.getReleasenotes());
    }

}
