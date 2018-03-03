package nl.dias.web.medewerker;

import com.codahale.metrics.Timer;
import nl.dias.domein.Bijlage;
import nl.dias.domein.Gebruiker;
import nl.dias.mapper.BijlageNaarJsonBijlageMapper;
import nl.dias.service.BijlageService;
import nl.dias.web.SoortEntiteit;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.BijlageClient;
import nl.lakedigital.djfc.client.oga.GroepBijlagesClient;
import nl.lakedigital.djfc.commons.json.*;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.request.EntiteitenOpgeslagenRequest;
import nl.lakedigital.djfc.request.SoortEntiteitEnEntiteitId;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/bijlage")
@Controller
public class BijlageController extends AbstractController {
    private final static Logger LOGGER = LoggerFactory.getLogger(BijlageController.class);

    @Inject
    private BijlageClient bijlageClient;
    @Inject
    private GroepBijlagesClient groepBijlagesClient;
    @Inject
    private BijlageService bijlageService;
    @Inject
    private BijlageNaarJsonBijlageMapper bijlageNaarJsonBijlageMapper;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private MetricsService metricsService;

    @RequestMapping(method = RequestMethod.POST, value = "/wijzigOmschrijvingBijlage", produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @ResponseBody
    public void wijzigOmschrijvingBijlage(@RequestBody WijzigenOmschrijvingBijlage wijzigenOmschrijvingBijlage, HttpServletRequest httpServletRequest) {
        bijlageClient.wijzigOmschrijvingBijlage(wijzigenOmschrijvingBijlage, getIngelogdeGebruiker(httpServletRequest).getId(), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @ResponseBody
    public void opslaan(@RequestBody List<JsonBijlage> jsonEntiteiten, HttpServletRequest httpServletRequest) {
        bijlageClient.opslaan(jsonEntiteiten, getIngelogdeGebruiker(httpServletRequest).getId(), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaanBijlage", produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @ResponseBody
    public void opslaanBijlage(JsonBijlage jsonBijlage, HttpServletRequest httpServletRequest) {
        bijlageClient.opslaan(jsonBijlage, getIngelogdeGebruiker(httpServletRequest).getId(), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/genereerBestandsnaam", produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @ResponseBody
    public String genereerBestandsnaam() {
        return bijlageClient.genereerBestandsnaam();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUploadPad", produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @ResponseBody
    public String getUploadPad() {
        return bijlageClient.getUploadPad();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}", produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonBijlage> alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        List<JsonBijlage> jsonEntiteiten = bijlageClient.lijst(soortentiteit, parentid);

        return jsonEntiteiten;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{soortentiteit}/{parentid}", produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijderen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid, HttpServletRequest httpServletRequest) {
        bijlageClient.verwijder(soortentiteit, parentid, getIngelogdeGebruiker(httpServletRequest).getId(), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijder/{id}", produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijderen(@PathVariable("id") String identificatieCode, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(identificatieCode);

        metricsService.addMetric("downloadBijlage" + identificatie.getSoortEntiteit(), BijlageController.class, null, null);

        bijlageClient.verwijder(identificatie.getEntiteitId(), getIngelogdeGebruiker(httpServletRequest).getId(), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekTerm}", produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonBijlage> zoeken(@PathVariable("zoekTerm") String zoekTerm) {
        List<JsonBijlage> result = bijlageClient.zoeken(zoekTerm);

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(@RequestParam("id") String identificatieString) throws IOException {
        Timer.Context timer = metricsService.addTimerMetric("download", BijlageController.class);

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(identificatieString);

        metricsService.addMetric("downloadBijlage" + identificatie.getSoortEntiteit(), BijlageController.class, null, null);

        LOGGER.debug("Ophalen bijlage met id {}", identificatie.getEntiteitId());

        JsonBijlage bijlage = bijlageClient.lees(identificatie.getEntiteitId());

        File file = new File(bijlageClient.getUploadPad() + "/" + bijlage.getS3Identificatie());

        HttpHeaders headers = new HttpHeaders();
        MediaType header;
        String[] parts = bijlage.getBestandsNaam().split("\\.");
        String extensie = parts[parts.length - 1];

        switch (extensie) {
            case "jpg":
            case "jpeg":
                header = MediaType.IMAGE_JPEG;
                break;
            case "png":
                header = MediaType.IMAGE_PNG;
                break;
            default:
                header = MediaType.parseMediaType("application/pdf");
        }

        headers.setContentType(header);
        headers.add("content-disposition", "inline;filename=" + bijlage.getBestandsNaam());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(Files.readAllBytes(Paths.get(file.getAbsolutePath())), headers, HttpStatus.OK);

        metricsService.stop(timer);

        return response;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadBijlage", produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @ResponseBody
    public UploadBijlageResponse uploadBijlage(@RequestParam("bijlageFile") MultipartFile fileDetail, @FormParam("identificatie") String identificatie, @FormParam("soortEntiteit") String soortEntiteit, HttpServletRequest httpServletRequest) {
        UploadBijlageResponse response = new UploadBijlageResponse();

        LOGGER.info("uploaden bestand voor {} met id {}", soortEntiteit, identificatie);

        LOGGER.debug("{}", ReflectionToStringBuilder.toString(fileDetail));

        Identificatie identificatie1 = identificatieClient.zoekIdentificatieCode(identificatie);
        Long id = identificatie1.getEntiteitId();
        metricsService.addMetric("bijlageUploaden", BijlageController.class, identificatie1.getSoortEntiteit(), null);

        LOGGER.debug("Opgehaalde identificatie {}", ReflectionToStringBuilder.toString(id));

        List<JsonBijlage> bijlages = uploaden(fileDetail, soortEntiteit, id, httpServletRequest);

        if (bijlages.size() > 1) {
            JsonGroepBijlages groepBijlages = new JsonGroepBijlages();
            groepBijlages.setBijlages(bijlages);
            if (fileDetail != null) {
                groepBijlages.setNaam(fileDetail.getOriginalFilename().replace(".zip", ""));
            }
            String groepId = groepBijlagesClient.opslaan(groepBijlages, getIngelogdeGebruiker(httpServletRequest).getId(), getTrackAndTraceId(httpServletRequest));

            groepBijlages.setId(Long.valueOf(groepId));

            response.setGroepBijlages(groepBijlages);
        } else {
            response.setBijlage(bijlages.get(0));
        }

        for (JsonBijlage bijlage : bijlages) {
            LOGGER.debug("Geupload {}", ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));
        }

        return response;
    }

    private List<JsonBijlage> uploaden(MultipartFile fileDetail, String soortEntiteit, Long entiteitId, HttpServletRequest httpServletRequest) {

        List<Bijlage> bijlages;
        List<JsonBijlage> jsonBijlages = new ArrayList<>();

        if (fileDetail != null && fileDetail.getName() != null) {

            LOGGER.debug("Naar BijlageService");
            bijlages = bijlageService.uploaden(fileDetail, getUploadPad());

            LOGGER.debug("request {}", httpServletRequest);
            Gebruiker ingelogdeGebruiker = getIngelogdeGebruiker(httpServletRequest);
            String trackAndTraceId = getTrackAndTraceId(httpServletRequest);
            LOGGER.debug("trackAndTraceId {}", trackAndTraceId);

            for (Bijlage bijlage : bijlages) {
                bijlage.setSoortBijlage(SoortEntiteit.valueOf(soortEntiteit.toUpperCase()));
                bijlage.setEntiteitId(entiteitId);
                LOGGER.debug(ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));
                JsonBijlage jsonBijlage = bijlageNaarJsonBijlageMapper.map(bijlage, null, null);

                String id = bijlageClient.opslaan(jsonBijlage, ingelogdeGebruiker.getId(), trackAndTraceId);

                EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest = new EntiteitenOpgeslagenRequest();
                SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
                soortEntiteitEnEntiteitId.setEntiteitId(Long.valueOf(id));
                soortEntiteitEnEntiteitId.setSoortEntiteit(nl.lakedigital.djfc.request.SoortEntiteit.BIJLAGE);
                entiteitenOpgeslagenRequest.getSoortEntiteitEnEntiteitIds().add(soortEntiteitEnEntiteitId);
                Identificatie identificatie = identificatieClient.opslaan(entiteitenOpgeslagenRequest);

                jsonBijlage.setIdentificatie(identificatie.getIdentificatie());
                jsonBijlage.setId(Long.valueOf(id));

                jsonBijlages.add(jsonBijlage);
            }
        }
        return jsonBijlages;
    }
}
