package nl.dias.web.medewerker;

import nl.lakedigital.djfc.client.oga.TelefonieBestandClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequestMapping("/telefonie")
@Controller
@Configuration
@PropertySource(value = "file:djfc.app.properties", ignoreResourceNotFound = true)
@PropertySource(value = "djfc.app.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:dev/djfc.app.properties", ignoreResourceNotFound = true)
public class TelefonieController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelefonieController.class);

    @Value("${voicemailspad}")
    private String voicemailspad;
    private String recordingspad = "/data/recordings";
    @Inject
    private TelefonieBestandClient telefonieBestandClient;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download/{bestandsnaam}", produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @ResponseBody
    @Produces("application/wav")
    public ResponseEntity<byte[]> getFile(@PathVariable("bestandsnaam") String bn) throws IOException {
        String bestandsnaam = bn.replace(".a", "");
        File file = new File(recordingspad + File.separator + bestandsnaam);

        LOGGER.debug("Telefoniebestand downloaden {}", recordingspad + File.separator + bestandsnaam);

        if (file.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/wav"));
            headers.add("content-disposition", "inline;filename=" + bestandsnaam);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(Files.readAllBytes(Paths.get(file.getAbsolutePath())), headers, HttpStatus.OK);
        } else {
            LOGGER.trace("Bestand niet gevonden : {}", bestandsnaam);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/wav"));
            headers.add("content-disposition", "inline;filename=" + bestandsnaam);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
    }

}
