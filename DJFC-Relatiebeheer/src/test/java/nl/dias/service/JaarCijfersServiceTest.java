package nl.dias.service;

import com.google.common.collect.Lists;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.JaarCijfers;
import nl.dias.repository.JaarCijfersRepository;
import org.easymock.*;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class JaarCijfersServiceTest extends EasyMockSupport {
    @TestSubject
    private JaarCijfersService jaarCijfersService = new JaarCijfersService();

    @Mock
    private JaarCijfersRepository jaarCijfersRepository;
    @Mock
    private BedrijfService bedrijfService;

    @Test
    public void testLees() {
        JaarCijfers jaarCijfers = new JaarCijfers();
        Long id = 7L;

        expect(jaarCijfersRepository.lees(id)).andReturn(jaarCijfers);

        replayAll();

        assertThat(jaarCijfersService.lees(id), is(jaarCijfers));

        verifyAll();
    }

    @Test
    public void testAllesMetHuidigJaar() {
        Long bedrijsId = 55L;
        Bedrijf bedrijf = new Bedrijf();

        expect(bedrijfService.lees(bedrijsId)).andReturn(bedrijf);

        JaarCijfers jaarCijfersVorigJaar = new JaarCijfers();
        jaarCijfersVorigJaar.setJaar(Long.valueOf(LocalDate.now().minusYears(1).getYear()));
        JaarCijfers jaarCijfersHuidigJaar = new JaarCijfers();
        jaarCijfersHuidigJaar.setJaar(Long.valueOf(LocalDate.now().getYear()));

        expect(jaarCijfersRepository.allesBijBedrijf(bedrijf)).andReturn(Lists.newArrayList(jaarCijfersVorigJaar, jaarCijfersHuidigJaar));

        replayAll();

        assertEquals(Lists.newArrayList(jaarCijfersVorigJaar, jaarCijfersHuidigJaar), jaarCijfersService.alles(bedrijsId));

        verifyAll();
    }

    @Test
    public void testAllesMetHuidigJaarZonderVorigJaar() {
        Long bedrijsId = 55L;
        Bedrijf bedrijf = new Bedrijf();

        expect(bedrijfService.lees(bedrijsId)).andReturn(bedrijf);

        JaarCijfers jaarCijfersVorigJaar = new JaarCijfers();
        jaarCijfersVorigJaar.setJaar(Long.valueOf(LocalDate.now().minusYears(1).getYear()));
        jaarCijfersVorigJaar.setBedrijf(bedrijf);
        JaarCijfers jaarCijfersHuidigJaar = new JaarCijfers();
        jaarCijfersHuidigJaar.setJaar(Long.valueOf(LocalDate.now().getYear()));

        expect(jaarCijfersRepository.allesBijBedrijf(bedrijf)).andReturn(Lists.newArrayList(jaarCijfersHuidigJaar));
        jaarCijfersRepository.opslaan(jaarCijfersVorigJaar);
        expectLastCall();

        replayAll();

        assertEquals(Lists.newArrayList(jaarCijfersHuidigJaar, jaarCijfersVorigJaar), jaarCijfersService.alles(bedrijsId));

        verifyAll();
    }

    @Test
    public void testAllesZonderHuidigJaar() {
        Long bedrijsId = 55L;
        Bedrijf bedrijf = new Bedrijf();

        expect(bedrijfService.lees(bedrijsId)).andReturn(bedrijf);

        JaarCijfers jaarCijfersVorigJaar = new JaarCijfers();
        jaarCijfersVorigJaar.setJaar(Long.valueOf(LocalDate.now().minusYears(1).getYear()));
        JaarCijfers jaarCijfersHuidigJaar = new JaarCijfers();
        jaarCijfersHuidigJaar.setJaar(Long.valueOf(LocalDate.now().getYear()));
        jaarCijfersHuidigJaar.setBedrijf(bedrijf);
        JaarCijfers jaarCijfersVolgendJaar = new JaarCijfers();
        jaarCijfersVolgendJaar.setJaar(Long.valueOf(LocalDate.now().plusYears(1).getYear()));
        jaarCijfersVolgendJaar.setBedrijf(bedrijf);

        expect(jaarCijfersRepository.allesBijBedrijf(bedrijf)).andReturn(Lists.newArrayList(jaarCijfersVorigJaar));

        Capture<JaarCijfers> jaarCijfersCapture = newCapture();

        jaarCijfersRepository.opslaan(capture(jaarCijfersCapture));
        expectLastCall();

        replayAll();

        assertEquals(Lists.newArrayList(jaarCijfersVorigJaar, jaarCijfersHuidigJaar), jaarCijfersService.alles(bedrijsId));

        JaarCijfers jaarCijfers = jaarCijfersCapture.getValue();
        assertEquals(new Long(LocalDate.now().getYear()), jaarCijfers.getJaar());

        verifyAll();
    }
}