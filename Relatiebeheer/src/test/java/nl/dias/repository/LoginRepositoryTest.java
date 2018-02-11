package nl.dias.repository;

import nl.dias.domein.LogIn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class LoginRepositoryTest {
    @Inject
    private LoginRepository loginRepository;

    @Test
    public void test() {
        LogIn logIn = new LogIn();
        logIn.setGebruikerId(2L);
        logIn.setToken("ab");

        loginRepository.opslaan(logIn);

        loginRepository.getTransaction();
        assertThat(loginRepository.getSession().createQuery("select i from LogIn i").list().size(), is(1));
        loginRepository.getTransaction().commit();

        loginRepository.opruimen();
    }

    @Test
    public void testGetIngelogdeGebruikers() {
        Long gebruikerId1 = 1L;
        Long gebruikerId2 = 2L;
        Long gebruikerId3 = 3L;

        LogIn logIn1 = new LogIn();
        logIn1.setGebruikerId(gebruikerId1);
        logIn1.setToken("ab");

        LogIn logIn2 = new LogIn();
        logIn2.setGebruikerId(gebruikerId2);
        logIn2.setToken("abc");

        LogIn logIn3 = new LogIn();
        logIn3.setGebruikerId(gebruikerId3);
        logIn3.setToken("abcd");

        LogIn logIn4 = new LogIn();
        logIn4.setGebruikerId(gebruikerId2);
        logIn4.setToken("abcde");

        loginRepository.opslaan(logIn1);
        loginRepository.opslaan(logIn2);
        loginRepository.opslaan(logIn3);
        loginRepository.opslaan(logIn4);

        assertThat(loginRepository.getIngelogdeGebruikers(), is(newArrayList(gebruikerId1, gebruikerId2, gebruikerId3)));
    }
}