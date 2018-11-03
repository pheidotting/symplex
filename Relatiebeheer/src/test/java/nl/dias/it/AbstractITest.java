package nl.dias.it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.lakedigital.djfc.commons.json.Inloggen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.UUID;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

public class AbstractITest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractITest.class);

    protected final String GEBRUIKER_OPSLAAN = "http://localhost:7075/dejonge/rest/medewerker/gebruiker/opslaan";
    protected final String RELATIE_LEZEN = "http://localhost:7075/dejonge/rest/medewerker/relatie/lees";
    protected final String INLOGGEN = "http://localhost:7075/dejonge/rest/authorisatie/authorisatie/inloggen";

    private String jwt;

    protected <T> T getMessageFromTemplate(JmsTemplate jmsTemplate, Class<T> clazz) throws JAXBException, JMSException {
        Message m = jmsTemplate.receive();
        TextMessage message = (TextMessage) m;
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        T ontvangenObject = (T) jaxbUnmarshaller.unmarshal(new StringReader(message.getText()));

        m.acknowledge();

        return ontvangenObject;
    }

    protected String doePost(Object entiteit, String url, String trackAndTraceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("trackAndTraceId", trackAndTraceId);
        headers.set(AUTHORIZATION, jwt);

        HttpEntity<String> entity = new HttpEntity<>(toJson(entiteit), headers);

        RestTemplate restTemplate = new RestTemplate();

        LOGGER.debug("Aanroepen {}", url);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        jwt = response.getHeaders().get(AUTHORIZATION).get(0);

        return response.getBody();
    }

    protected void inloggen() {
        String trackAndTraceId = UUID.randomUUID().toString();
        doePost(new Inloggen("djfc.bene", "bene"), INLOGGEN, trackAndTraceId);
    }

    protected String toJson(Object in) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(in);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String doeGet(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTHORIZATION, jwt);

        HttpEntity entity = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();

        LOGGER.debug("Aanroepen {}", url);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    protected Stub stubIdentificatieZoekOpSoortEnId(String response) {
        return new Stub("/rest/identificatie/zoeken/RELATIE/([0-9]*)", response, false);
    }

    protected Stub stubIdentificatieZoekenOpCode(String id, String response) {
        return new Stub("/rest/identificatie/zoekenOpCode/" + id, response, true);
    }

    protected Stub stubZoekAlleAdressen(String response) {
        return new Stub("/rest/adres/alles%2FRELATIE%2F([0-9]*)", response, false);
    }

    protected Stub stubZoekAlleBijlages(String response) {
        return new Stub("/rest/bijlage/alles/RELATIE/([0-9]*)", response, false);
    }

    protected Stub stubGroepenBijlage(String response) {
        return new Stub("/rest/bijlage/alleGroepen/RELATIE/([0-9]*)", response, false);
    }

    protected Stub stubRekeningnummer(String response) {
        return new Stub("/rest/rekeningnummer/alles%2FRELATIE%2F([0-9]*)", response, false);
    }

    protected Stub stubTelefoonnummer(String response) {
        return new Stub("/rest/telefoonnummer/alles%2FRELATIE%2F([0-9]*)", response, false);
    }

    protected Stub stubOpmerking(String response) {
        return new Stub("/rest/opmerking/alles%2FRELATIE%2F([0-9]*)", response, false);
    }

    protected Stub stubBelastingzakenBijlage(String response) {
        return new Stub("/rest/bijlage/alles/BELASTINGZAKEN/([0-9]*)", response, false);
    }

    protected Stub stubBelastingzakenGroepenBijlage(String response) {
        return new Stub("/rest/bijlage/alleGroepen/BELASTINGZAKEN/([0-9]*)", response, false);
    }

    protected Stub stubPolis(String response) {
        return new Stub("/rest/polis/lijst/([0-9]*)", response, false);
    }
}
