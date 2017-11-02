package nl.lakedigital.djfc.repository;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import nl.lakedigital.djfc.domain.Identificatie;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class IdentificatieRepositoryTest extends EasyMockSupport {
@Inject
    private IdentificatieRepository identificatieRepository;

    @Test
    public void testOpslaanEnZoeken(){
        String soortEntiteit = "POLIS";
        Long entiteitId = 4L;

        Identificatie identificatie=new Identificatie(soortEntiteit,entiteitId);

        identificatieRepository.opslaan(identificatie);

        Identificatie id = identificatieRepository.zoek(soortEntiteit,entiteitId);
        assertThat(id, is(notNullValue()));
        assertThat(id.getIdentificatie(),is(notNullValue()));

        identificatieRepository.verwijder(id);

        Identificatie id2=identificatieRepository.zoek(soortEntiteit,entiteitId);
        assertThat(id2, is(nullValue()));
    }
}