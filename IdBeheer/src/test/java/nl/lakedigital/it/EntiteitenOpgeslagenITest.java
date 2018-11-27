package nl.lakedigital.it;

import nl.lakedigital.as.messaging.request.EntiteitenOpgeslagenRequest;
import nl.lakedigital.as.messaging.request.VerwijderEntiteitenRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-it.xml")
public class EntiteitenOpgeslagenITest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntiteitenOpgeslagenITest.class);
    @Inject
    private JmsTemplate entiteitenOpgeslagenRequestJmsTemplate;
    @Inject
    private JmsTemplate verwijderEntiteitenRequestJmsTemplate;
    private IdentificatieClient identificatieClient = new IdentificatieClient("http://localhost:7077/idbeheer");

    @Test
    public void testBerichtPerEntiteit() {
        List<SoortEntiteitEnEntiteitId> opgeslagenEntiteiten = maakLijst(1L, 5L);
        for (SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId : opgeslagenEntiteiten) {
            EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest = new EntiteitenOpgeslagenRequest();
            entiteitenOpgeslagenRequest.getSoortEntiteitEnEntiteitIds().add(soortEntiteitEnEntiteitId);

            verstuurEntiteitenOpgeslagenRequest(entiteitenOpgeslagenRequest);
        }

        //        controleer(opgeslagenEntiteiten);

        for (SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId : opgeslagenEntiteiten) {
            VerwijderEntiteitenRequest verwijderEntiteitenRequest = new VerwijderEntiteitenRequest();
            verwijderEntiteitenRequest.setEntiteitId(soortEntiteitEnEntiteitId.getEntiteitId());
            verwijderEntiteitenRequest.setSoortEntiteit(soortEntiteitEnEntiteitId.getSoortEntiteit());

            verstuurEntiteitenVerwijderdRequest(verwijderEntiteitenRequest);
        }

        //        controleerNietAanwezig(opgeslagenEntiteiten);
    }

    @Test
    public void testEenBericht() {
        List<SoortEntiteitEnEntiteitId> opgeslagenEntiteiten = maakLijst(51L, 55L);
        EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest = new EntiteitenOpgeslagenRequest();
        entiteitenOpgeslagenRequest.setSoortEntiteitEnEntiteitIds(opgeslagenEntiteiten);

        verstuurEntiteitenOpgeslagenRequest(entiteitenOpgeslagenRequest);

        //        controleer(entiteitenOpgeslagenRequest.getSoortEntiteitEnEntiteitIds());

        for (SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId : opgeslagenEntiteiten) {
            VerwijderEntiteitenRequest verwijderEntiteitenRequest = new VerwijderEntiteitenRequest();
            verwijderEntiteitenRequest.setEntiteitId(soortEntiteitEnEntiteitId.getEntiteitId());
            verwijderEntiteitenRequest.setSoortEntiteit(soortEntiteitEnEntiteitId.getSoortEntiteit());

            verstuurEntiteitenVerwijderdRequest(verwijderEntiteitenRequest);
        }

        //        controleerNietAanwezig(opgeslagenEntiteiten);
    }

    private List<SoortEntiteitEnEntiteitId> maakLijst(Long van, Long tot) {
        List<SoortEntiteitEnEntiteitId> lijst = new ArrayList();

        for (SoortEntiteit soortEntiteit : SoortEntiteit.values()) {
            for (Long i = van; i <= tot; i++) {
                EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest = new EntiteitenOpgeslagenRequest();
                SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = maakSoortEntiteitEnEntiteitId(soortEntiteit, i);

                entiteitenOpgeslagenRequest.getSoortEntiteitEnEntiteitIds().add(soortEntiteitEnEntiteitId);
                lijst.add(soortEntiteitEnEntiteitId);
            }
        }
        return lijst;
    }

    private void verstuurEntiteitenOpgeslagenRequest(EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest) {
        try {
            Thread.sleep(500);//NOSONAR
        } catch (InterruptedException e) {
            LOGGER.trace("{}", e);
            Thread.currentThread().interrupt();
        }
        AbstractSender entiteitenOpgeslagenRequestSender = new AbstractSender<EntiteitenOpgeslagenRequest, List<SoortEntiteitEnEntiteitId>>() {
            @Override
            public EntiteitenOpgeslagenRequest maakMessage(List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds) {

                EntiteitenOpgeslagenRequest request = new EntiteitenOpgeslagenRequest();

                request.setSoortEntiteitEnEntiteitIds(soortEntiteitEnEntiteitIds);

                return request;
            }
        };

        entiteitenOpgeslagenRequestSender.setJmsTemplate(entiteitenOpgeslagenRequestJmsTemplate);
        entiteitenOpgeslagenRequestSender.setClazz(EntiteitenOpgeslagenRequest.class);
        entiteitenOpgeslagenRequestSender.send(entiteitenOpgeslagenRequest);
    }

    private void verstuurEntiteitenVerwijderdRequest(VerwijderEntiteitenRequest verwijderEntiteitenRequest) {
        try {
            Thread.sleep(500);//NOSONAR
        } catch (InterruptedException e) {
            LOGGER.trace("{}", e);
            Thread.currentThread().interrupt();
        }
        AbstractSender entiteitenOpgeslagenRequestSender = new AbstractSender<VerwijderEntiteitenRequest, SoortEntiteitEnEntiteitId>() {
            @Override
            public VerwijderEntiteitenRequest maakMessage(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitIds) {

                VerwijderEntiteitenRequest request = new VerwijderEntiteitenRequest();

                request.setEntiteitId(soortEntiteitEnEntiteitIds.getEntiteitId());
                request.setSoortEntiteit(soortEntiteitEnEntiteitIds.getSoortEntiteit());

                return request;
            }
        };

        entiteitenOpgeslagenRequestSender.setJmsTemplate(verwijderEntiteitenRequestJmsTemplate);
        entiteitenOpgeslagenRequestSender.setClazz(VerwijderEntiteitenRequest.class);
        entiteitenOpgeslagenRequestSender.send(verwijderEntiteitenRequest);
    }

    private void controleer(List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds) {
        for (SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId1 : soortEntiteitEnEntiteitIds) {
            try {
                Thread.sleep(5000);//NOSONAR
            } catch (InterruptedException e) {
                LOGGER.trace("{}", e);
                Thread.currentThread().interrupt();
            }
            nl.lakedigital.djfc.commons.json.Identificatie gevonden = zoeken(soortEntiteitEnEntiteitId1);

            LOGGER.debug("Gevonden: {}", gevonden);

            if (gevonden != null) {
                assertThat(gevonden.getIdentificatie(), is(notNullValue()));
            } else {
                fail("gevonden is null, dit mag niet voorkomen");
            }
            try {
                Thread.sleep(500);//NOSONAR
            } catch (InterruptedException e) {
                LOGGER.trace("{}", e);
                Thread.currentThread().interrupt();
            }
        }
    }

    private void controleerNietAanwezig(List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds) {
        for (SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId1 : soortEntiteitEnEntiteitIds) {
            try {
                Thread.sleep(1000);//NOSONAR
            } catch (InterruptedException e) {
                LOGGER.trace("{}", e);
                Thread.currentThread().interrupt();
            }
            nl.lakedigital.djfc.commons.json.Identificatie gevonden = zoeken(soortEntiteitEnEntiteitId1);

            LOGGER.debug("Gevonden: {}", gevonden);

            assertThat(gevonden, is(nullValue()));
            try {
                Thread.sleep(100);//NOSONAR
            } catch (InterruptedException e) {
                LOGGER.trace("{}", e);
                Thread.currentThread().interrupt();
            }
        }
    }

    private nl.lakedigital.djfc.commons.json.Identificatie zoeken(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId1) {
        int aantal = 0;
        nl.lakedigital.djfc.commons.json.Identificatie gevonden = null;
        while (gevonden == null && ++aantal < 10) {
            gevonden = identificatieClient.zoekIdentificatie(soortEntiteitEnEntiteitId1.getSoortEntiteit().name(), soortEntiteitEnEntiteitId1.getEntiteitId());
            if (gevonden == null) {
                try {
                    Thread.sleep(1000);//NOSONAR
                } catch (InterruptedException e) {
                    LOGGER.trace("{}", e);
                    Thread.currentThread().interrupt();
                }
            }
        }

        return gevonden;
    }

    private SoortEntiteitEnEntiteitId maakSoortEntiteitEnEntiteitId(SoortEntiteit soortEntiteit, Long entiteitId) {
        SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
        soortEntiteitEnEntiteitId.setSoortEntiteit(soortEntiteit);
        soortEntiteitEnEntiteitId.setEntiteitId(entiteitId);

        return soortEntiteitEnEntiteitId;
    }
}
