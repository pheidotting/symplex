package nl.symplex.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.lakedigital.djfc.commons.json.Inloggen;
import nl.lakedigital.djfc.domain.response.Adres;
import nl.lakedigital.djfc.domain.response.Hypotheek;
import nl.lakedigital.djfc.domain.response.Opmerking;
import nl.lakedigital.djfc.domain.response.Relatie;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import nl.symplex.test.builders.AdresBuilder;
import nl.symplex.test.builders.HypotheekBuilder;
import nl.symplex.test.builders.OpmerkingBuilder;
import nl.symplex.test.builders.RelatieBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.UUID;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class TestSymplex {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestSymplex.class);

    private String host = "tst-diasii";

    protected String GEBRUIKER_OPSLAAN = null;
    protected String HYPOTHEEK_OPSLAAN = null;
    protected String GEBRUIKER_VERWIJDEREN = null;
    protected String RELATIE_LEZEN = null;
    protected String INLOGGEN = null;

    private String jwt;
    private String relatieId;

    @Before
    public void init() {
        if (System.getProperty("os.name").equals("Mac OS X")) {
            host = "localhost";
        }

        GEBRUIKER_OPSLAAN = "http://" + host + ":8080/dejonge/rest/medewerker/gebruiker/opslaan";
        HYPOTHEEK_OPSLAAN = "http://" + host + ":8080/dejonge/rest/medewerker/hypotheek/opslaan";
        GEBRUIKER_VERWIJDEREN = "http://" + host + ":8080/dejonge/rest/medewerker/gebruiker/verwijderen/";
        RELATIE_LEZEN = "http://" + host + ":8080/dejonge/rest/medewerker/relatie/lees";
        INLOGGEN = "http://" + host + ":8080/dejonge/rest/authorisatie/authorisatie/inloggen";

        inloggen();
        System.out.println("Ingelogd, opgehaalde JWT : {}" + jwt);
    }

    @Test
    public void testSymplex() throws InterruptedException {
        Relatie relatie = new RelatieBuilder().defaultRelatie().build();

        Adres adres = new AdresBuilder().defaultAdres().build();
        relatie.getAdressen().add(adres);

        System.out.println("Doe post met : ");
        System.out.println(ReflectionToStringBuilder.toString(relatie));
        this.relatieId = doePost(relatie, GEBRUIKER_OPSLAAN, UUID.randomUUID().toString());

        Thread.sleep(10000);
        System.out.println("RelatieId ontvangen : ");
        System.out.println(this.relatieId);

        relatie.setIdentificatie(this.relatieId);
        Relatie relatieOpgeslagen = (Relatie) fromJson(doeGet(RELATIE_LEZEN + "/" + this.relatieId), Relatie.class);
        System.out.println(ReflectionToStringBuilder.toString(relatieOpgeslagen));
        System.out.println(ReflectionToStringBuilder.toString(relatie));

        assertThat(relatieOpgeslagen, equalTo(relatie));

        Opmerking opmerking = new OpmerkingBuilder().metTekst().build();
        Hypotheek hypotheek = new HypotheekBuilder().defaultHypotheek().metRelatie(this.relatieId).metOpmerking(opmerking).build();
        doePost(hypotheek, HYPOTHEEK_OPSLAAN, UUID.randomUUID().toString());

        Thread.sleep(10000);

        relatieOpgeslagen = (Relatie) fromJson(doeGet(RELATIE_LEZEN + "/" + this.relatieId), Relatie.class);
        assertThat(relatieOpgeslagen.getHypotheken().size(), is(1));
        assertThat(relatieOpgeslagen.getHypotheken().get(0).getIdentificatie(), is(notNullValue()));
    }

    @After
    public void opruimen() {
        doePost(null, GEBRUIKER_VERWIJDEREN + this.relatieId, UUID.randomUUID().toString());
    }

    protected String doePost(Object entiteit, String url, String trackAndTraceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("trackAndTraceId", trackAndTraceId);
        headers.set(AUTHORIZATION, jwt);

        RestTemplate restTemplate = new RestTemplate();

        LOGGER.debug("Aanroepen {}", url);
        System.out.println("Aanroepen {}" + url);
        HttpEntity<String> entity = null;
        if (entiteit != null) {
            entity = new HttpEntity<>(toJson(entiteit), headers);
        } else {
            entity = new HttpEntity<>(headers);
        }
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

    protected Object fromJson(String in, Class objClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(in, objClass);
        } catch (IOException e) {
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
}
