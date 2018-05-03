package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.LicentieResponse;
import nl.lakedigital.djfc.domain.Goud;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.LicentieService;
import org.easymock.*;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class LicentieControllerTest extends EasyMockSupport {
    @TestSubject
    private LicentieController licentieController = new LicentieController();

    @Mock(MockType.NICE)
    private MetricsService metricsService;
    @Mock
    private LicentieService licentieService;
    @Mock(MockType.NICE)
    private HttpServletRequest httpServletRequest;

    @Test
    public void actievelicentie() {
        Long kantoorId = 12L;

        Goud goud = new Goud();
        LocalDate einddatum = LocalDate.now().plusDays(10);
        expect(licentieService.actieveLicentie(kantoorId)).andReturn(goud);
        expect(licentieService.actieveLicentie(goud)).andReturn(einddatum);

        replayAll();

        LicentieResponse licentieResponse = licentieController.actievelicentie(kantoorId, httpServletRequest);

        verifyAll();

        assertThat(licentieResponse.getLicenties().size(), is(1));
        assertThat(licentieResponse.getLicenties().get(0).getEinddatum(), is(einddatum.toString("yyyy-MM-dd")));
        assertThat(licentieResponse.getLicenties().get(0).getSoort(), is("goud"));
    }
}