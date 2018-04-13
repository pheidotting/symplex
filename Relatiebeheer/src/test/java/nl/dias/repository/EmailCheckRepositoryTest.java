package nl.dias.repository;

import nl.dias.domein.EmailCheck;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class EmailCheckRepositoryTest {

    @Inject
    private EmailCheckRepository emailCheckRepository;

    @Test
    public void testOpslaanLezenEnVerwijderen() {
        EmailCheck emailCheck1 = new EmailCheck(1L, "mail1");
        EmailCheck emailCheck2 = new EmailCheck(2L, "mail2");

        emailCheckRepository.opslaan(emailCheck1);
        emailCheckRepository.opslaan(emailCheck2);

        assertThat(emailCheckRepository.alles().size(), is(2));

        emailCheckRepository.verwijder(emailCheck1);

        assertThat(emailCheckRepository.alles().size(), is(1));
        assertThat(emailCheckRepository.alles().get(0).getMailadres(), is("mail2"));

        emailCheckRepository.verwijder(emailCheck2);

        assertThat(emailCheckRepository.alles().size(), is(0));
    }
}