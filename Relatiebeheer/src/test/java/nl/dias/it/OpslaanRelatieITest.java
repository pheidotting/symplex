package nl.dias.it;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.as.messaging.request.EntiteitenOpgeslagenRequest;
import nl.lakedigital.djfc.domain.response.Relatie;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
import java.util.UUID;
import java.util.function.Predicate;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-it.xml")
public class OpslaanRelatieITest extends AbstractITest {
    private final static Logger LOGGER = LoggerFactory.getLogger(OpslaanRelatieITest.class);

    @Inject
    private JmsTemplate entiteitenOpgeslagenRequestTemplate;

    private WireMockServer wireMockServer;

    @Before
    public void init() {
        wireMockServer = new WireMockServer(8089);
        configureFor("localhost", 8089);

        wireMockServer.start();

        inloggen();
    }

    @After
    public void cleanup() {
        wireMockServer.stop();
    }

    @Test
    public void testOpslaanNieuweRelatieMetAlleenVoorEnAchternaam() throws JAXBException, JMSException {
        String uuid = UUID.randomUUID().toString();

        Relatie relatie = new Relatie();

        relatie.setAchternaam("Parker");
        relatie.setVoornaam("Peter");

        test(relatie, uuid);
    }

    @Test
    @Ignore
    public void testOpslaanNieuweRelatieVolledigGevuld() throws JMSException, JAXBException {
        String uuid = UUID.randomUUID().toString();

        Relatie relatie = new Relatie();

        relatie.setAchternaam("Wayne");
        relatie.setVoornaam("Bruce");
        relatie.setTussenvoegsel("van");
        relatie.setGeboorteDatum("1979-09-06");
        relatie.setEmailadres("a@b.c");
        relatie.setRoepnaam("Batman");
        relatie.setBsn("123456789");
        relatie.setGeslacht("Man");
        relatie.setBurgerlijkeStaat("Ongehuwd");

        Relatie result = test(relatie, uuid);

        assertThat(result.getTussenvoegsel(), is(relatie.getTussenvoegsel()));
        assertThat(result.getGeboorteDatum(), is(relatie.getGeboorteDatum()));
        assertThat(result.getEmailadres(), is(nullValue()));
        assertThat(result.getRoepnaam(), is(relatie.getRoepnaam()));
        assertThat(result.getBsn(), is(relatie.getBsn()));
        assertThat(result.getOverlijdensdatum(), is(relatie.getOverlijdensdatum()));
        assertThat(result.getGeslacht(), is(relatie.getGeslacht()));
        assertThat(result.getBurgerlijkeStaat(), is(relatie.getBurgerlijkeStaat()));

        result.setOverlijdensdatum("2017-07-02");

        Relatie result2 = test(result, uuid);

        assertThat(result2.getTussenvoegsel(), is(result.getTussenvoegsel()));
        assertThat(result2.getGeboorteDatum(), is(result.getGeboorteDatum()));
        //        assertThat(result2.getEmailadres(), is(result.getOverlijdensdatum()));
        assertThat(result2.getRoepnaam(), is(result.getRoepnaam()));
        assertThat(result2.getBsn(), is(result.getBsn()));
        assertThat(result2.getOverlijdensdatum(), is(result.getOverlijdensdatum()));
        assertThat(result2.getGeslacht(), is(result.getGeslacht()));
        assertThat(result2.getBurgerlijkeStaat(), is(result.getBurgerlijkeStaat()));
    }

    private Relatie test(Relatie relatie, String uuid) throws JAXBException, JMSException {
        Stub stubIdentificatieZoekOpSoortEnId = stubIdentificatieZoekOpSoortEnId(zoekIdentificatieResponse(uuid, null));

        String id = doePost(relatie, GEBRUIKER_OPSLAAN, UUID.randomUUID().toString());

        assertThat(id, is(uuid));

        EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest = getMessageFromTemplate(entiteitenOpgeslagenRequestTemplate, EntiteitenOpgeslagenRequest.class);
        SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = entiteitenOpgeslagenRequest.getSoortEntiteitEnEntiteitIds().stream().findFirst().filter(new Predicate<SoortEntiteitEnEntiteitId>() {
            @Override
            public boolean test(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
                return soortEntiteitEnEntiteitId.getSoortEntiteit() == SoortEntiteit.RELATIE;
            }
        }).orElse(null);

        String relatieId = soortEntiteitEnEntiteitId.getEntiteitId().toString();

        Stub stubIdentificatieZoekenOpCode = stubIdentificatieZoekenOpCode(id, zoekIdentificatieResponse(uuid, relatieId));
        Stub stubZoekAlleAdressen = stubZoekAlleAdressen(adressenResponse());
        Stub stubZoekAlleBijlages = stubZoekAlleBijlages(bijlageResponse());
        Stub stubRekeningnummer = stubRekeningnummer(rekeningnummerResponse());
        Stub stubTelefoonnummer = stubTelefoonnummer(telefoonnummerResponse());
        Stub stubOpmerking = stubOpmerking(opmerkingResponse());

        String rel = doeGet(RELATIE_LEZEN + "/" + id);

        Relatie result = new Gson().fromJson(rel, Relatie.class);

        stubIdentificatieZoekOpSoortEnId.verifyStub(relatieId);
        stubIdentificatieZoekenOpCode.verifyStub(relatieId);
        stubZoekAlleAdressen.verifyStub(relatieId);
        stubZoekAlleBijlages.verifyStub(relatieId);
        stubRekeningnummer.verifyStub(relatieId);
        stubTelefoonnummer.verifyStub(relatieId);
        stubOpmerking.verifyStub(relatieId);

        assertThat(result.getIdentificatie(), is(uuid));
        assertThat(result.getAchternaam(), is(relatie.getAchternaam()));
        assertThat(result.getVoornaam(), is(relatie.getVoornaam()));

        return result;
    }

    //
    private String zoekIdentificatieResponse(String identificatieS, String id) {
        return "<ZoekIdentificatieResponse><identificaties><identificaties><id>63</id><identificatie>" + identificatieS + "</identificatie><soortEntiteit>RELATIE</soortEntiteit><entiteitId>" + id + "</entiteitId></identificaties></identificaties></ZoekIdentificatieResponse>";
    }

    private String adressenResponse() {
        return "<OpvragenAdressenResponse></OpvragenAdressenResponse>";
    }

    private String bijlageResponse() {
        return "<OpvragenBijlagesResponse></OpvragenBijlagesResponse>";
    }

    private String groepenBijlageResponse() {
        return "<OpvragenGroepBijlagesResponse></OpvragenGroepBijlagesResponse>";
    }

    private String rekeningnummerResponse() {
        return "<OpvragenRekeningNummersResponse></OpvragenRekeningNummersResponse>";
    }

    private String telefoonnummerResponse() {
        return "<OpvragenTelefoonnummersResponse></OpvragenTelefoonnummersResponse>";
    }

    private String opmerkingResponse() {
        return "<OpvragenOpmerkingenResponse></OpvragenOpmerkingenResponse>";
    }

    private String polisResponse() {
        return "<OpvragenPolissenResponse></OpvragenPolissenResponse>";
    }

    private String telefonieResponse() {
        return "<Map/>";
    }
}
