package nl.dias.service;

import nl.dias.domein.EmailCheck;
import nl.dias.domein.Relatie;
import nl.dias.repository.EmailCheckRepository;
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
    private EmailCheckRepository emailCheckRepository;
    @Mock
    private SlackService slackService;

    @Test
    public void checkEmailAdressenEersteKeer() {
        expect(emailCheckRepository.alles()).andReturn(newArrayList());

        Relatie relatie = new Relatie();
        relatie.setId(3L);
        relatie.setEmailadres("mail1");
        expect(gebruikerService.alleRelaties()).andReturn(newArrayList(relatie));

        Capture<EmailCheck> emailCheckCapture = newCapture();
        emailCheckRepository.opslaan(capture(emailCheckCapture));
        expectLastCall();

        replayAll();

        emailCheckService.checkEmailAdressen();

        verifyAll();

        EmailCheck emailCheck = emailCheckCapture.getValue();
        assertThat(emailCheck.getId(), is(nullValue()));
        assertThat(emailCheck.getMailadres(), is("mail1"));
        assertThat(emailCheck.getGebruiker(), is(3L));
    }

    @Test
    public void checkEmailAdressenNietsGewijzigd() {
        expect(emailCheckRepository.alles()).andReturn(newArrayList(new EmailCheck(3L, "mail1")));

        Relatie relatie = new Relatie();
        relatie.setId(3L);
        relatie.setEmailadres("mail1");
        expect(gebruikerService.alleRelaties()).andReturn(newArrayList(relatie));

        replayAll();

        emailCheckService.checkEmailAdressen();

        verifyAll();
    }

    @Test
    public void checkEmailAdressenAdresVerdwenen() {
        expect(emailCheckRepository.alles()).andReturn(newArrayList(new EmailCheck(3L, "mail1")));

        Relatie relatie = new Relatie();
        relatie.setId(3L);
        expect(gebruikerService.alleRelaties()).andReturn(newArrayList(relatie));

        slackService.stuurVerdwenenMailAdres("mail1", 3L);
        expectLastCall();

        Capture<EmailCheck> emailCheckCapture = newCapture();
        emailCheckRepository.verwijder(capture(emailCheckCapture));
        expectLastCall();

        replayAll();

        emailCheckService.checkEmailAdressen();

        verifyAll();

        EmailCheck emailCheck = emailCheckCapture.getValue();
        assertThat(emailCheck.getMailadres(), is("mail1"));
        assertThat(emailCheck.getGebruiker(), is(3L));
    }

    @Test
    public void checkEmailAdressenAdresGewijzigd() {
        expect(emailCheckRepository.alles()).andReturn(newArrayList(new EmailCheck(3L, "mail1")));

        Relatie relatie = new Relatie();
        relatie.setId(3L);
        relatie.setEmailadres("mail2");
        expect(gebruikerService.alleRelaties()).andReturn(newArrayList(relatie));

        slackService.stuurGewijzigdeMailAdres("mail2", 3L);
        expectLastCall();

        Capture<EmailCheck> emailCheckCapture = newCapture();
        emailCheckRepository.opslaan(capture(emailCheckCapture));
        expectLastCall();

        replayAll();

        emailCheckService.checkEmailAdressen();

        verifyAll();

        EmailCheck emailCheck = emailCheckCapture.getValue();
        assertThat(emailCheck.getMailadres(), is("mail2"));
        assertThat(emailCheck.getGebruiker(), is(3L));
    }

}