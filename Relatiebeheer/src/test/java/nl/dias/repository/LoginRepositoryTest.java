package nl.dias.repository;

import nl.dias.domein.LogIn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

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
}