package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.entities.*;
import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanRelatieOpdracht;
import nl.lakedigital.djfc.domain.uitgaand.UitgaandeOpdracht;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class OpslaanRelatieAfhandelenServiceTest extends EasyMockSupport {

    @TestSubject
    private OpslaanRelatieAfhandelenService opslaanRelatieAfhandelenService = new OpslaanRelatieAfhandelenService();

    @Test
    public void testMetAlleenEenRelatie() {
        OpslaanRelatieOpdracht opslaanRelatieOpdracht = new OpslaanRelatieOpdracht();

        Relatie relatie = new Relatie();
        opslaanRelatieOpdracht.setRelatie(relatie);

        replayAll();

        Set<UitgaandeOpdracht> uitgaandeOpdrachten = opslaanRelatieAfhandelenService.genereerUitgaandeOpdrachten(opslaanRelatieOpdracht);

        verifyAll();

        assertThat(uitgaandeOpdrachten.size(), is(1));
    }

    @Test
    public void testVolledigMetNieuweRelatie() {
        OpslaanRelatieOpdracht opslaanRelatieOpdracht = new OpslaanRelatieOpdracht();

        Relatie relatie = new Relatie();
        relatie.setAchternaam("Achternaam");
        opslaanRelatieOpdracht.setRelatie(relatie);

        Adres adres = new Adres();
        adres.setStraat("Straatnaam");
        opslaanRelatieOpdracht.getAdressen().add(adres);

        Opmerking opmerking = new Opmerking();
        opmerking.setOpmerking("bla");
        opslaanRelatieOpdracht.getOpmerkingen().add(opmerking);

        RekeningNummer rekeningNummer = new RekeningNummer();
        rekeningNummer.setRekeningnummer("rekeningnummer");
        opslaanRelatieOpdracht.getRekeningNummers().add(rekeningNummer);

        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setTelefoonnummer("0000000000");
        opslaanRelatieOpdracht.getTelefoonnummers().add(telefoonnummer);

        replayAll();

        Set<UitgaandeOpdracht> uitgaandeOpdrachten = opslaanRelatieAfhandelenService.genereerUitgaandeOpdrachten(opslaanRelatieOpdracht);

        verifyAll();

        assertThat(uitgaandeOpdrachten.size(), is(5));

        UitgaandeOpdracht opslaanRelatieUitgaandeOpdracht = uitgaandeOpdrachten.stream().filter(new Predicate<UitgaandeOpdracht>() {
            @Override
            public boolean test(UitgaandeOpdracht uitgaandeOpdracht) {
                return uitgaandeOpdracht.getBericht().contains("<opslaanRelatieRequest>");
            }
        }).collect(Collectors.toList()).get(0);

        uitgaandeOpdrachten.stream().forEach(uitgaandeOpdracht -> {
            if (uitgaandeOpdracht.getBericht().contains("adres") || uitgaandeOpdracht.getBericht().contains("opmerking") || uitgaandeOpdracht.getBericht().contains("rekeningNummer") || uitgaandeOpdracht.getBericht().contains("telefoonnummer")) {
                assertThat(uitgaandeOpdracht.getWachtenOp(), is(opslaanRelatieUitgaandeOpdracht));
            }
        });
    }

    @Test
    public void testVolledigMetBestaandeRelatie() {
        OpslaanRelatieOpdracht opslaanRelatieOpdracht = new OpslaanRelatieOpdracht();

        Relatie relatie = new Relatie();
        relatie.setId(3L);
        relatie.setAchternaam("Achternaam");
        opslaanRelatieOpdracht.setRelatie(relatie);

        Adres adres = new Adres();
        adres.setStraat("Straatnaam");
        opslaanRelatieOpdracht.getAdressen().add(adres);

        Opmerking opmerking = new Opmerking();
        opmerking.setOpmerking("bla");
        opslaanRelatieOpdracht.getOpmerkingen().add(opmerking);

        RekeningNummer rekeningNummer = new RekeningNummer();
        rekeningNummer.setRekeningnummer("rekeningnummer");
        opslaanRelatieOpdracht.getRekeningNummers().add(rekeningNummer);

        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setTelefoonnummer("0000000000");
        opslaanRelatieOpdracht.getTelefoonnummers().add(telefoonnummer);

        replayAll();

        Set<UitgaandeOpdracht> uitgaandeOpdrachten = opslaanRelatieAfhandelenService.genereerUitgaandeOpdrachten(opslaanRelatieOpdracht);

        verifyAll();

        assertThat(uitgaandeOpdrachten.size(), is(5));

        uitgaandeOpdrachten.stream().forEach(uitgaandeOpdracht -> {
            if (uitgaandeOpdracht.getBericht().contains("adres") || uitgaandeOpdracht.getBericht().contains("opmerking") || uitgaandeOpdracht.getBericht().contains("rekeningNummer") || uitgaandeOpdracht.getBericht().contains("telefoonnummer")) {
                assertThat(uitgaandeOpdracht.getWachtenOp(), is(nullValue()));
            }
        });
    }

}