package nl.dias.repository;

import nl.dias.domein.Kantoor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class KantoorRepositoryTest {
    @Inject
    private KantoorRepository kantoorRepository;

    @Test
    public void testZoekOpAfkorting() throws Exception {
        Kantoor kantoor1 = new Kantoor();
        kantoor1.setAfkorting("a");
        Kantoor kantoor2 = new Kantoor();
        kantoor2.setAfkorting("b");

        kantoorRepository.opslaanKantoor(kantoor1);
        kantoorRepository.opslaanKantoor(kantoor2);

        assertThat(kantoorRepository.zoekOpAfkorting("a").size(), is(1));
        assertThat(kantoorRepository.zoekOpAfkorting("a").get(0).getAfkorting(), is("a"));
        assertThat(kantoorRepository.zoekOpAfkorting("b").size(), is(1));
        assertThat(kantoorRepository.zoekOpAfkorting("b").get(0).getAfkorting(), is("b"));
        assertThat(kantoorRepository.zoekOpAfkorting("c").size(), is(0));
    }
}
