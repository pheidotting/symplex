package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.LicentieStatus;
import nl.lakedigital.djfc.domain.Trial;
import nl.lakedigital.djfc.repository.LicentieRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
}