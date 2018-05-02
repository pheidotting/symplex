package nl.dias.service;

import com.google.common.util.concurrent.RateLimiter;
import com.ullink.slack.simpleslackapi.SlackSession;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.EmailCheck;
import nl.dias.domein.Relatie;
import nl.dias.repository.EmailCheckRepository;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class EmailCheckServiceTest extends EasyMockSupport {
    @TestSubject
    private EmailCheckService emailCheckService = new EmailCheckService();

    @Mock
    private GebruikerService gebruikerService;
    @Mock
    private BedrijfService bedrijfService;
    @Mock
    private EmailCheckRepository emailCheckRepository;
    @Mock
    private SlackService slackService;

    @Test
    public void checkEmailAdressenEersteKeer() {
        String channelName = "channelName";
        RateLimiter rateLimiter = RateLimiter.create(1);
        SlackSession session = null;

        expect(emailCheckRepository.alles()).andReturn(newArrayList());

        Relatie relatie = new Relatie();
        relatie.setId(3L);
        relatie.setEmailadres("mail1");
        expect(gebruikerService.alleRelaties()).andReturn(newArrayList(relatie));
        Bedrijf bedrijf = new Bedrijf();
        bedrijf.setId(4L);
        bedrijf.setEmail("mail2");
        expect(bedrijfService.alles()).andReturn(newArrayList(bedrijf));

        Capture<EmailCheck> emailCheckCaptureRelatie = newCapture();
        emailCheckRepository.opslaan(capture(emailCheckCaptureRelatie));
        expectLastCall();
        Capture<EmailCheck> emailCheckCaptureBedrijf = newCapture();
        emailCheckRepository.opslaan(capture(emailCheckCaptureBedrijf));
        expectLastCall();

        slackService.stuurBericht("mail1", 3L, SoortEntiteit.RELATIE, SlackService.Soort.NIEUW, session, channelName, rateLimiter);
        expectLastCall();
        slackService.stuurBericht("mail2", 4L, SoortEntiteit.BEDRIJF, SlackService.Soort.NIEUW, session, channelName, rateLimiter);
        expectLastCall();

        replayAll();

        emailCheckService.checkEmailAdressen(session, channelName, rateLimiter);

        verifyAll();

        EmailCheck emailCheckRelatie = emailCheckCaptureRelatie.getValue();
        assertThat(emailCheckRelatie.getId(), is(nullValue()));
        assertThat(emailCheckRelatie.getMailadres(), is("mail1"));
        assertThat(emailCheckRelatie.getGebruiker(), is(3L));

        EmailCheck emailCheckBedrijf = emailCheckCaptureBedrijf.getValue();
        assertThat(emailCheckBedrijf.getId(), is(nullValue()));
        assertThat(emailCheckBedrijf.getMailadres(), is("mail2"));
        assertThat(emailCheckBedrijf.getGebruiker(), is(4L));
    }

    @Test
    public void checkEmailAdressenNietsGewijzigd() {
        String channelName = "channelName";
        RateLimiter rateLimiter = RateLimiter.create(1);
        SlackSession session = null;

        expect(emailCheckRepository.alles()).andReturn(newArrayList(new EmailCheck(3L, "mail1")));

        Relatie relatie = new Relatie();
        relatie.setId(3L);
        relatie.setEmailadres("mail1");
        expect(gebruikerService.alleRelaties()).andReturn(newArrayList(relatie));
        expect(bedrijfService.alles()).andReturn(newArrayList());

        replayAll();

        emailCheckService.checkEmailAdressen(session, channelName, rateLimiter);

        verifyAll();
    }

    @Test
    public void checkEmailAdressenAdresVerdwenen() {
        String channelName = "channelName";
        RateLimiter rateLimiter = RateLimiter.create(1);
        SlackSession session = null;

        expect(emailCheckRepository.alles()).andReturn(newArrayList(new EmailCheck(3L, "mail1")));

        Relatie relatie = new Relatie();
        relatie.setId(3L);
        expect(gebruikerService.alleRelaties()).andReturn(newArrayList(relatie));
        expect(bedrijfService.alles()).andReturn(newArrayList());

        slackService.stuurBericht("mail1", 3L, SoortEntiteit.RELATIE, SlackService.Soort.VERWIJDERD, session, channelName, rateLimiter);
        expectLastCall();

        Capture<EmailCheck> emailCheckCapture = newCapture();
        emailCheckRepository.verwijder(capture(emailCheckCapture));
        expectLastCall();

        replayAll();

        emailCheckService.checkEmailAdressen(session, channelName, rateLimiter);

        verifyAll();

        EmailCheck emailCheck = emailCheckCapture.getValue();
        assertThat(emailCheck.getMailadres(), is("mail1"));
        assertThat(emailCheck.getGebruiker(), is(3L));
    }

    @Test
    public void checkEmailAdressenAdresGewijzigd() {
        String channelName = "channelName";
        RateLimiter rateLimiter = RateLimiter.create(1);
        SlackSession session = null;

        expect(emailCheckRepository.alles()).andReturn(newArrayList(new EmailCheck(3L, "mail1")));

        Relatie relatie = new Relatie();
        relatie.setId(3L);
        relatie.setEmailadres("mail2");
        expect(gebruikerService.alleRelaties()).andReturn(newArrayList(relatie));
        expect(bedrijfService.alles()).andReturn(newArrayList());

        slackService.stuurBericht("mail2", 3L, SoortEntiteit.RELATIE, SlackService.Soort.GEWIJZIGD, session, channelName, rateLimiter);
        expectLastCall();

        Capture<EmailCheck> emailCheckCapture = newCapture();
        emailCheckRepository.opslaan(capture(emailCheckCapture));
        expectLastCall();

        replayAll();

        emailCheckService.checkEmailAdressen(session, channelName, rateLimiter);

        verifyAll();

        EmailCheck emailCheck = emailCheckCapture.getValue();
        assertThat(emailCheck.getMailadres(), is("mail2"));
        assertThat(emailCheck.getGebruiker(), is(3L));
    }
}

