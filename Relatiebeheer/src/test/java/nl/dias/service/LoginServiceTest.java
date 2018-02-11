package nl.dias.service;

import nl.dias.domein.LogIn;
import nl.dias.repository.LoginRepository;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class LoginServiceTest extends EasyMockSupport {
    @TestSubject
    private LoginService loginService = new LoginService();

    @Mock
    private LoginRepository loginRepository;

    @Test
    public void test() {
        String token = "abc";
        Long gebruiker = 3L;

        Capture<LogIn> logInCapture = newCapture();
        loginRepository.opslaan(capture(logInCapture));
        expectLastCall();
        loginRepository.opruimen();
        expectLastCall();

        replayAll();

        loginService.nieuwToken(gebruiker, token);

        LogIn logIn = logInCapture.getValue();
        assertThat(logIn.getGebruikerId(), is(gebruiker));
        assertThat(logIn.getToken(), is(token));
        assertThat(logIn.getExpireDatum(), is(notNullValue()));
    }
}