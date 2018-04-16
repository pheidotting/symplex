package nl.lakedigital.it;

import com.google.common.base.Stopwatch;
import nl.lakedigital.as.messaging.domain.Polis;
import nl.lakedigital.as.messaging.request.EntiteitenOpgeslagenRequest;
import nl.lakedigital.as.messaging.request.VerwijderEntiteitenRequest;
import nl.lakedigital.djfc.client.LeesFoutException;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.domain.particulier.AanhangerParticulierVerzekering;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Stopwatch.createStarted;
import static com.google.common.collect.Lists.newArrayList;
import static nl.lakedigital.djfc.domain.SoortEntiteit.RELATIE;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-it.xml")
public class PolisTest extends AbstractITest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisTest.class);

    private final Long ingegelogdeGebruiker = 46L;
    private final String trackAndTraceId = UUID.randomUUID().toString();

    private PolisClient polisClient = new PolisClient("http://localhost:7072/pa");

    @Inject
    private JmsTemplate entiteitenOpgeslagenRequestTemplate;
    @Inject
    private JmsTemplate entiteitenVerwijderdRequestTemplate;

    @Inject
    private List<nl.lakedigital.djfc.domain.Polis> polissen;

    private List<EntiteitenOpgeslagenRequest> entiteitenOpgeslagenRequests = newArrayList();
    private List<VerwijderEntiteitenRequest> verwijderEntiteitenRequests = newArrayList();

    @Test
    public void voertestenuit() throws JAXBException, JMSException {
        //                for (nl.lakedigital.djfc.domain.Polis p : polissen) {
        //                    if (!p.getSchermNaam().equals("ArbeidsongeschiktheidParticulier")) {
        //                        test(p.getSchermNaam());
        //                        wachtff(10000);
        //                    }
        //                }
        test(new AanhangerParticulierVerzekering().getSchermNaam());
    }

    public void test(String soort) throws JAXBException, JMSException {
        LOGGER.debug("# # # # # # # # # # # # # # # # # # # # # # #");
        LOGGER.debug("{}", soort);
        LOGGER.debug("# # # # # # # # # # # # # # # # # # # # # # #");
        LOGGER.debug("#");
        Polis polis = maakPolis(soort);

        sendPolisOpslaanRequestMessage(ingegelogdeGebruiker, trackAndTraceId, polis);

        ophalenEntiteitenOpgeslagenRequests();
        EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest = entiteitenOpgeslagenRequests.get(entiteitenOpgeslagenRequests.size() - 1);

        Long id = entiteitenOpgeslagenRequest.getSoortEntiteitEnEntiteitIds().get(0).getEntiteitId();
        polis.setId(id);

        controleerOpgeslagenPolis(id, polis);

        muteerPolis(polis);

        sendPolisOpslaanRequestMessage(ingegelogdeGebruiker, trackAndTraceId, polis);

        ophalenEntiteitenOpgeslagenRequests();

        controleerOpgeslagenPolis(id, polis);

        sendPolisVerwijderenRequestMessage(ingegelogdeGebruiker, trackAndTraceId, polis.getId());

        //        ophalenVerwijderEntiteitenRequests();

        wachtff(10000);
        controleerOpgeslagenPolis(id, new Polis());

    }

    private void ophalenEntiteitenOpgeslagenRequests() throws JAXBException, JMSException {
        int entiteitenOpgeslagenRequestsSize = entiteitenOpgeslagenRequests.size();
        Stopwatch stopwatch = createStarted();
        while (entiteitenOpgeslagenRequestsSize == entiteitenOpgeslagenRequests.size() && stopwatch.elapsed(TimeUnit.SECONDS) < 60) {
            entiteitenOpgeslagenRequests = getMessageFromTemplate(entiteitenOpgeslagenRequestTemplate, EntiteitenOpgeslagenRequest.class);
        }
    }

    private void ophalenVerwijderEntiteitenRequests() throws JAXBException, JMSException {
        int verwijderEntiteitenRequestsSize = verwijderEntiteitenRequests.size();
        Stopwatch stopwatch = createStarted();
        while (verwijderEntiteitenRequestsSize == verwijderEntiteitenRequests.size() && stopwatch.elapsed(TimeUnit.SECONDS) < 60) {
            verwijderEntiteitenRequests = getMessageFromTemplate(entiteitenVerwijderdRequestTemplate, VerwijderEntiteitenRequest.class);
        }
    }

    private Polis maakPolis(String soort) {
        Polis polis = new Polis();
        polis.setSoort(soort);
        polis.setPolisNummer(UUID.randomUUID().toString().replace("-", "").substring(0, 25));
        polis.setMaatschappij("123");
        polis.setSoortEntiteit(RELATIE.name());
        polis.setEntiteitId(3L);

        return polis;
    }

    private void muteerPolis(Polis polis) {
        polis.setDekking("dekkingdekking");
        polis.setBetaalfrequentie("Maand");
        polis.setIngangsDatum("2017-02-01");
    }

    private void controleerOpgeslagenPolis(Long id, Polis polis) {
        LOGGER.debug("Ophalen Polis met id {}", id);
        JsonPolis p = ophalenPolis(id);

        try {
            assertThat(p.getStatus(), is(polis.getStatus()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getStatus(), is(polis.getStatus()));
        }
        try {
            assertThat(p.getPolisNummer(), is(polis.getPolisNummer()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getPolisNummer(), is(polis.getPolisNummer()));
        }
        try {
            assertThat(p.getKenmerk(), is(polis.getKenmerk()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getKenmerk(), is(polis.getKenmerk()));
        }
        try {
            assertThat(p.getIngangsDatum(), is(polis.getIngangsDatum()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getIngangsDatum(), is(polis.getIngangsDatum()));
        }
        try {
            assertThat(p.getEindDatum(), is(polis.getEindDatum()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getEindDatum(), is(polis.getEindDatum()));
        }
        try {
            assertThat(p.getPremie(), is(polis.getPremie()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getPremie(), is(polis.getPremie()));
        }
        try {
            assertThat(p.getWijzigingsDatum(), is(polis.getWijzigingsDatum()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getWijzigingsDatum(), is(polis.getWijzigingsDatum()));
        }
        try {
            assertThat(p.getProlongatieDatum(), is(polis.getProlongatieDatum()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getProlongatieDatum(), is(polis.getProlongatieDatum()));
        }
        try {
            assertThat(p.getBetaalfrequentie(), is(polis.getBetaalfrequentie()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getBetaalfrequentie(), is(polis.getBetaalfrequentie()));
        }
        try {
            assertThat(p.getDekking(), is(polis.getDekking()));
        } catch (AssertionError ae) {
            wachtff(30000);
            p = ophalenPolis(id);
            assertThat(p.getDekking(), is(polis.getDekking()));
        }
        try {
            assertThat(p.getVerzekerdeZaak(), is(polis.getVerzekerdeZaak()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getVerzekerdeZaak(), is(polis.getVerzekerdeZaak()));
        }
        try {
            assertThat(p.getMaatschappij(), is(polis.getMaatschappij()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getMaatschappij(), is(polis.getMaatschappij()));
        }
        try {
            assertThat(p.getSoort(), is(polis.getSoort()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getSoort(), is(polis.getSoort()));
        }
        try {
            assertThat(p.getOmschrijvingVerzekering(), is(polis.getOmschrijvingVerzekering()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getOmschrijvingVerzekering(), is(polis.getOmschrijvingVerzekering()));
        }
        try {
            assertThat(p.getSoortEntiteit(), is(polis.getSoortEntiteit()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getSoortEntiteit(), is(polis.getSoortEntiteit()));
        }
        try {
            assertThat(p.getEntiteitId(), is(polis.getEntiteitId()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getEntiteitId(), is(polis.getEntiteitId()));
        }
        try {
            assertThat(p.getIdentificatie(), is(polis.getIdentificatie()));
        } catch (AssertionError ae) {
            p = ophalenPolis(id);
            assertThat(p.getIdentificatie(), is(polis.getIdentificatie()));
        }
    }

    private JsonPolis ophalenPolis(Long id) {
        JsonPolis p = null;
        Stopwatch stopwatch = createStarted();
        while (p == null && stopwatch.elapsed(TimeUnit.SECONDS) < 60) {
            try {
                p = polisClient.lees(id.toString());
            } catch (LeesFoutException lfe) {
                wachtff();
                p = polisClient.lees(id.toString());
            }
        }

        return p;
    }

    private void wachtff() {
        wachtff(500);
    }

    private void wachtff(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
