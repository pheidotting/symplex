package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.TelefonieBestand;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.TestSubject;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class TelefonieBestandServiceTest extends EasyMockSupport {
    @TestSubject
    private TelefonieBestandService telefonieBestandService = new TelefonieBestandService();

    @Test
    public void testMaakTelefonieBestandInkomendBestandExternal1() throws Exception {
        String file = "external-2901-0611710077-20170113-131904-1484309944.1401.wav";

        TelefonieBestand verwacht = new TelefonieBestand();
        verwacht.setTijdstip(new LocalDateTime(2017, 1, 13, 13, 19, 4));
        verwacht.setBestandsnaam(file);
        verwacht.setTelefoonnummer("0611710077");

        assertThat(telefonieBestandService.maakTelefonieBestand(file), is(verwacht));
    }

    @Test
    public void testMaakTelefonieBestandUitgaandbestand() throws Exception {
        String file = "out-0302780222-2904-20170106-121930-1483701570.658.wav";

        TelefonieBestand verwacht = new TelefonieBestand();
        verwacht.setTijdstip(new LocalDateTime(2017, 1, 6, 12, 19, 30));
        verwacht.setBestandsnaam(file);
        verwacht.setTelefoonnummer("0302780222");

        assertThat(telefonieBestandService.maakTelefonieBestand(file), is(verwacht));
    }

    @Test
    public void testMaakTelefonieBestandUitgaandbestand1() throws Exception {
        String file = "out--2903-20170103-134840-1483447719.317.wav";

        assertThat(telefonieBestandService.maakTelefonieBestand(file), is(nullValue()));
    }

    @Test
    public void testMaakTelefonieBestandUitgaandbestand2() throws Exception {
        String file = "out-030-2780222-2904-20170103-134319-1483447399.315.wav";

        TelefonieBestand verwacht = new TelefonieBestand();
        verwacht.setTijdstip(new LocalDateTime(2017, 1, 3, 13, 43, 19));
        verwacht.setBestandsnaam(file);
        verwacht.setTelefoonnummer("0302780222");

        assertThat(telefonieBestandService.maakTelefonieBestand(file), is(verwacht));
    }

    @Test
    public void testMaakTelefonieBestandUitgaandbestand3() throws Exception {
        String file = "out-08000240721-2912-20170102-144855-1483364935.127.wav";

        TelefonieBestand verwacht = new TelefonieBestand();
        verwacht.setTijdstip(new LocalDateTime(2017, 1, 2, 14, 48, 55));
        verwacht.setBestandsnaam(file);
        verwacht.setTelefoonnummer("0800024072");

        assertThat(telefonieBestandService.maakTelefonieBestand(file), is(verwacht));
    }

    @Test
    public void testMaakTelefonieBestandUitgaandbestand4() throws Exception {
        String file = "out-08000543-2901-20170130-141151-1485781911.2442.wav";

        TelefonieBestand verwacht = new TelefonieBestand();
        verwacht.setTijdstip(new LocalDateTime(2017, 1, 30, 14, 11, 51));
        verwacht.setBestandsnaam(file);
        verwacht.setTelefoonnummer("08000543");

        assertThat(telefonieBestandService.maakTelefonieBestand(file), is(verwacht));
    }

    @Test
    public void testMaakTelefonieBestandUitgaandbestand5() throws Exception {
        String file = "out-088299042-2904-20170103-160602-1483455962.373.wav";

        TelefonieBestand verwacht = new TelefonieBestand();
        verwacht.setTijdstip(new LocalDateTime(2017, 1, 3, 16, 6, 2));
        verwacht.setBestandsnaam(file);
        verwacht.setTelefoonnummer("088299042");

        assertThat(telefonieBestandService.maakTelefonieBestand(file), is(verwacht));
    }

    @Test
    public void testMaakTelefonieBestandUitgaandbestand6() throws Exception {
        String file = "out--2903-20170103-134840-1483447719.317.wav";

        assertThat(telefonieBestandService.maakTelefonieBestand(file), is(nullValue()));
    }

    @Test
    public void testMaakTelefonieBestandInkomendBestand2() throws Exception {
        String file = "rg-8001-0561451395-20170113-161919-1484320759.1426.wav";

        TelefonieBestand verwacht = new TelefonieBestand();
        verwacht.setTijdstip(new LocalDateTime(2017, 1, 13, 16, 19, 19));
        verwacht.setBestandsnaam(file);
        verwacht.setTelefoonnummer("0561451395");

        assertThat(telefonieBestandService.maakTelefonieBestand(file), is(verwacht));
    }
}