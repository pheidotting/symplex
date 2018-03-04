package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.Licentie;
import org.easymock.EasyMockSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class IdentificatieRepositoryTest extends EasyMockSupport {
    @Inject
    private IdentificatieRepository identificatieRepository;

    @Test
    public void testOpslaanEnZoeken() {
        String soortEntiteit = "POLIS";
        Long entiteitId = 4L;

        Licentie identificatie = new Licentie(soortEntiteit, entiteitId);

        identificatieRepository.opslaan(identificatie);

        Licentie id = identificatieRepository.zoek(soortEntiteit, entiteitId);
        assertThat(id, is(notNullValue()));
        assertThat(id.getIdentificatie(), is(notNullValue()));

        identificatieRepository.verwijder(id);

        Licentie id2 = identificatieRepository.zoek(soortEntiteit, entiteitId);
        assertThat(id2, is(nullValue()));
    }
}