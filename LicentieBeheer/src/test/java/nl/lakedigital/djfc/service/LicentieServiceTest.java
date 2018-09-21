package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.domain.Kantoor;
import nl.lakedigital.djfc.commons.domain.*;
import nl.lakedigital.djfc.repository.LicentieRepository;
import org.easymock.*;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(EasyMockRunner.class)
public class LicentieServiceTest extends EasyMockSupport {
    @TestSubject
    private LicentieService licentieService = new LicentieService();

    @Mock
    private LicentieRepository licentieRepository;

    @Test
    public void bepaalLicentieStatusTrial() {
        Trial trial = new Trial();
        trial.setAantalDagen(2);

        assertThat(LicentieService.bepaalLicentieStatus(trial), is(LicentieStatus.TRIAL));
    }

    @Test
    public void bepaalLicentieStatusTrialAfgelopen() {
        Trial trial = new Trial();
        trial.setAantalDagen(-1);

        assertThat(LicentieService.bepaalLicentieStatus(trial), is(LicentieStatus.GEBLOKKEERD));
    }

    @Test
    public void actieveLicentie() {
        Trial trial = new Trial();
        trial.setStartDatum(new LocalDate(2018, 3, 2));
        trial.setAantalDagen(3);
        Long kantoorId = 5L;

        expect(licentieRepository.alleLicenties(kantoorId)).andReturn(newArrayList(trial));

        replayAll();

        assertThat(licentieService.actieveLicentie(kantoorId), is(trial));

        verifyAll();
    }

    @Test
    public void actieveLicentieLocalDate() {
        Trial trial = new Trial();
        trial.setStartDatum(new LocalDate(2018, 3, 2));
        trial.setAantalDagen(3);

        assertThat(licentieService.actieveLicentie(trial), is(new LocalDate(2018, 3, 5)));
    }

    @Test
    public void bepaalPrijs() {
        String brons = "brons";
        String zilver = "zilver";
        String goud = "goud";
        String administratiekantoor = "administratiekantoor";
        String ietsanders = "ietsanders";

        assertThat(licentieService.bepaalPrijs(brons), is(5.00));
        assertThat(licentieService.bepaalPrijs(zilver), is(10.00));
        assertThat(licentieService.bepaalPrijs(goud), is(20.00));
        assertThat(licentieService.bepaalPrijs(administratiekantoor), is(15.00));
        assertThat(licentieService.bepaalPrijs(ietsanders), is(15.00));
    }

    @Test
    public void maakTrialAan() {
        Kantoor kantoor = new Kantoor();
        kantoor.setId(5L);

        Capture<Licentie> licentieCapture = newCapture();
        licentieRepository.opslaan(capture(licentieCapture));
        expectLastCall();

        replayAll();

        licentieService.maakTrialAan(kantoor);

        verifyAll();

        Licentie licentie = licentieCapture.getValue();
        assertTrue(licentie instanceof Trial);
        assertThat(licentie.getKantoor(), is(5L));
        assertThat(licentie.getAantalDagen(), is(30));
        assertThat(licentie.getStartDatum(), is(LocalDate.now()));
    }

    @Test
    public void nieuweLicentieBrons() {
        String soort = "brons";
        Long kantoorId = 6L;

        Capture<Licentie> licentieCapture = newCapture();
        licentieRepository.opslaan(capture(licentieCapture));
        expectLastCall();

        replayAll();

        licentieService.nieuweLicentie(soort, kantoorId);

        verifyAll();

        Licentie licentie = licentieCapture.getValue();
        assertTrue(licentie instanceof Brons);
        assertThat(licentie.getKantoor(), is(kantoorId));
        assertThat(licentie.getAantalDagen(), is(365));
        assertThat(licentie.getStartDatum(), is(LocalDate.now()));
    }

    @Test
    public void nieuweLicentieZilver() {
        String soort = "zilver";
        Long kantoorId = 7L;

        Capture<Licentie> licentieCapture = newCapture();
        licentieRepository.opslaan(capture(licentieCapture));
        expectLastCall();

        replayAll();

        licentieService.nieuweLicentie(soort, kantoorId);

        verifyAll();

        Licentie licentie = licentieCapture.getValue();
        assertTrue(licentie instanceof Zilver);
        assertThat(licentie.getKantoor(), is(kantoorId));
        assertThat(licentie.getAantalDagen(), is(365));
        assertThat(licentie.getStartDatum(), is(LocalDate.now()));
    }

    @Test
    public void nieuweLicentieGoud() {
        String soort = "goud";
        Long kantoorId = 8L;

        Capture<Licentie> licentieCapture = newCapture();
        licentieRepository.opslaan(capture(licentieCapture));
        expectLastCall();

        replayAll();

        licentieService.nieuweLicentie(soort, kantoorId);

        verifyAll();

        Licentie licentie = licentieCapture.getValue();
        assertTrue(licentie instanceof Goud);
        assertThat(licentie.getKantoor(), is(kantoorId));
        assertThat(licentie.getAantalDagen(), is(365));
        assertThat(licentie.getStartDatum(), is(LocalDate.now()));
    }

    @Test
    public void nieuweLicentieAdministratieKantoor() {
        String soort = "administratiekantoor";
        Long kantoorId = 9L;

        Capture<Licentie> licentieCapture = newCapture();
        licentieRepository.opslaan(capture(licentieCapture));
        expectLastCall();

        replayAll();

        licentieService.nieuweLicentie(soort, kantoorId);

        verifyAll();

        Licentie licentie = licentieCapture.getValue();
        assertTrue(licentie instanceof AdministratieKantoor);
        assertThat(licentie.getKantoor(), is(kantoorId));
        assertThat(licentie.getAantalDagen(), is(365));
        assertThat(licentie.getStartDatum(), is(LocalDate.now()));
    }

    @Test
    public void nieuweLicentieAnders() {
        String soort = "anders";
        Long kantoorId = 10L;

        Capture<Licentie> licentieCapture = newCapture();
        licentieRepository.opslaan(capture(licentieCapture));
        expectLastCall();

        replayAll();

        licentieService.nieuweLicentie(soort, kantoorId);

        verifyAll();

        Licentie licentie = licentieCapture.getValue();
        assertTrue(licentie instanceof AdministratieKantoor);
        assertThat(licentie.getKantoor(), is(kantoorId));
        assertThat(licentie.getAantalDagen(), is(365));
        assertThat(licentie.getStartDatum(), is(LocalDate.now()));
    }
}