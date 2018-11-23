package nl.lakedigital.it;

import nl.lakedigital.djfc.client.polisadministratie.SchadeClient;
import nl.lakedigital.djfc.commons.json.JsonSchade;
import nl.lakedigital.djfc.domain.SoortSchade;
import nl.lakedigital.djfc.domain.StatusSchade;
import nl.lakedigital.djfc.repository.SchadeRepository;
import org.joda.time.LocalDateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-it.xml")
public class SchadeTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeTest.class);

    private final Long ingegelogdeGebruiker = 46L;
    private final String trackAndTraceId = UUID.randomUUID().toString();
    private String patternDatumTijd = "yyyy-MM-dd HH:mm";
    private String patternDatum = "yyyy-MM-dd";

    private SchadeClient schadeClient = new SchadeClient();

    @Inject
    private SchadeRepository schadeRepository;

    @Test
    public void test() {
        StatusSchade statusSchade = new StatusSchade();
        statusSchade.setIngebruik(true);
        statusSchade.setStatus("blabla");

        schadeRepository.opslaan(statusSchade);

        JsonSchade jsonSchade = new JsonSchade();
        jsonSchade.setDatumMelding(LocalDateTime.now().toString(patternDatumTijd));
        jsonSchade.setDatumSchade(LocalDateTime.now().toString(patternDatumTijd));
        jsonSchade.setSchadeNummerMaatschappij("12345");
        jsonSchade.setStatusSchade("blabla");

        jsonSchade.setId(schadeClient.opslaan(jsonSchade, ingegelogdeGebruiker, trackAndTraceId));

        controleerOpgeslagenSchade(jsonSchade);

        jsonSchade.setOmschrijving(UUID.randomUUID().toString());

        controleerOpgeslagenSchade(jsonSchade);

        jsonSchade.setLocatie("Elm Street");

        controleerOpgeslagenSchade(jsonSchade);

        jsonSchade.setEigenRisico("12345.6");

        controleerOpgeslagenSchade(jsonSchade);

        jsonSchade.setRelatie("12");

        controleerOpgeslagenSchade(jsonSchade);

        jsonSchade.setRelatie(null);

        controleerOpgeslagenSchade(jsonSchade);

        jsonSchade.setBedrijf(34L);

        controleerOpgeslagenSchade(jsonSchade);

        jsonSchade.setSoortSchade("soort schade");

        controleerOpgeslagenSchade(jsonSchade);

        jsonSchade.setSoortSchade(null);

        controleerOpgeslagenSchade(jsonSchade);

        SoortSchade soortSchade = new SoortSchade();
        soortSchade.setOmschrijving("soort");
        soortSchade.setIngebruik(true);
        schadeRepository.opslaan(soortSchade);

        jsonSchade.setSoortSchade("soort");

        controleerOpgeslagenSchade(jsonSchade);

        assertThat(schadeRepository.lees(jsonSchade.getId()).getSoortSchade(), is(soortSchade));

        schadeClient.verwijder(jsonSchade.getId(), ingegelogdeGebruiker, trackAndTraceId);
        try {
            schadeClient.lees(jsonSchade.getId().toString());
            fail("Error verwacht");
        } catch (RuntimeException nre) {
            LOGGER.error("{}", nre);
        }

        schadeRepository.verwijderStatusSchade(newArrayList(statusSchade));
        schadeRepository.verwijder(soortSchade);
    }

    private void controleerOpgeslagenSchade(JsonSchade schade) {
        schadeClient.opslaan(schade, ingegelogdeGebruiker, trackAndTraceId);

        JsonSchade s = schadeClient.lees(schade.getId().toString());

        assertThat(s, is(schade));
    }
}
