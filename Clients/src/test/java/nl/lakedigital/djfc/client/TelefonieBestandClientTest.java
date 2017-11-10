package nl.lakedigital.djfc.client;

import nl.lakedigital.djfc.client.oga.TelefonieBestandClient;
import nl.lakedigital.djfc.commons.json.JsonTelefonieBestand;
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.isA;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

public class TelefonieBestandClientTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelefonieBestandClient.class);
    @Test
    @Ignore
    public void testGetRecordingsAndVoicemails() {
        TelefonieBestandClient telefonieBestandClient = PowerMock.createPartialMock(TelefonieBestandClient.class, "uitvoerenGetString");

        try {
            PowerMock.expectPrivate(telefonieBestandClient, "uitvoerenGetString", eq("/rest/telefonie/recordings?telefoonnummers=1"), isA(Logger.class)).andReturn("{\"recordings\":[\"rg-8001-0614165929-20170102-115841-1483354721.74.wav\",\"out-0591377338-2912-20170102-185025-1483379425.187.wav\"]}");
        } catch (Exception e) {
            LOGGER.debug("{}", e);
            fail("fout bij expecten van private");
        }

        replay(telefonieBestandClient);

        Map<String, List<JsonTelefonieBestand>> result = telefonieBestandClient.getRecordingsAndVoicemails(newArrayList("1"));

        assertThat(result.get("recordings").size(), is(2));

        verify(telefonieBestandClient);
    }
}