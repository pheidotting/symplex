package nl.symplex.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.lakedigital.djfc.commons.json.Inloggen;
import nl.lakedigital.djfc.domain.response.Adres;
import nl.lakedigital.djfc.domain.response.Relatie;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

public class TestSymplex {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestSymplex.class);

    protected final String GEBRUIKER_OPSLAAN = "http://tst-diasii:8080/dejonge/rest/medewerker/gebruiker/opslaan";
    protected final String GEBRUIKER_VERWIJDEREN = "http://tst-diasii:8080/dejonge/rest/medewerker/gebruiker/verwijderen/";
    protected final String RELATIE_LEZEN = "http://tst-diasii:8080/dejonge/rest/medewerker/relatie/lees";
    protected final String INLOGGEN = "http://tst-diasii:8080/dejonge/rest/authorisatie/authorisatie/inloggen";

    private String jwt;

    @Before
    public void init() {
        inloggen();
        System.out.println("Ingelogd, opgehaalde JWT : {}" + jwt);
    }

    @Test
    public void testSymplex() {
        Relatie relatie = new Relatie();
        relatie.setAchternaam("Achternaam");
        relatie.setTussenvoegsel("Tussenvoegsel");
        relatie.setVoornaam("Voornaam");
        relatie.setBsn("bsn");
        relatie.setBurgerlijkeStaat("Ongehuwd");
        relatie.setEmailadres("info@symplex.nl");
        relatie.setGeboorteDatum("1979-09-06");
        relatie.setGeslacht("M");
        relatie.setRoepnaam("Henk");

        Adres adres = maakAdres("Boogschutter", 26L, "7891TN", "Klazienaveen", "WOONADRES");
        relatie.getAdressen().add(adres);

        String result = doePost(relatie, GEBRUIKER_OPSLAAN, UUID.randomUUID().toString());

        System.out.println(result);

        doePost(null, GEBRUIKER_VERWIJDEREN + result, UUID.randomUUID().toString());
    }

    public Adres maakAdres(String straatnaam, Long huisnummer, String postcode, String plaats, String soortAdres) {
        Adres adres = new Adres();
        adres.setHuisnummer(huisnummer);
        adres.setStraat(straatnaam);
        adres.setPostcode(postcode);
        adres.setPlaats(plaats);
        adres.setSoortAdres(soortAdres);

        return adres;
    }

    protected String doePost(Object entiteit, String url, String trackAndTraceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("trackAndTraceId", trackAndTraceId);
        headers.set(AUTHORIZATION, jwt);

        RestTemplate restTemplate = new RestTemplate();

        LOGGER.debug("Aanroepen {}", url);
        HttpEntity<String> entity = null;
        if (entiteit != null) {
            System.out.println("Met entiteit");
            entity = new HttpEntity<>(toJson(entiteit), headers);
        } else {
            System.out.println("Zonder entiteit");
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
