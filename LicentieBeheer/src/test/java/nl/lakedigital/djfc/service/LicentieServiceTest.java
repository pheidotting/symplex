package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.LicentieStatus;
import nl.lakedigital.djfc.domain.Trial;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class LicentieServiceTest extends EasyMockSupport {

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
}