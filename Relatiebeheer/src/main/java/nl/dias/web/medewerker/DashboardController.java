package nl.dias.web.medewerker;

import com.codahale.metrics.Timer;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.service.GebruikerService;
import nl.dias.service.SchadeService;
import nl.lakedigital.djfc.commons.json.Dashboard;
import nl.lakedigital.djfc.commons.json.RelatieZoekResultaat;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class DashboardController extends AbstractController {

    @Inject
    private MetricsService metricsService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private SchadeService schadeService;

    @RequestMapping(method = RequestMethod.GET, value = "/dashboard", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Dashboard getDashboard(HttpServletRequest httpServletRequest) {
        Timer.Context timer = metricsService.addTimerMetric("dashboard", DashboardController.class);

        zetSessieWaarden(httpServletRequest);

        Dashboard dashboard = new Dashboard();

        List<RelatieZoekResultaat> relaties = gebruikerService.alleRelaties(((Medewerker) getIngelogdeGebruiker(httpServletRequest)).getKantoor()).stream().map(new Function<Relatie, RelatieZoekResultaat>() {
            @Override
            public RelatieZoekResultaat apply(Relatie relatie) {
                RelatieZoekResultaat relatieZoekResultaat = new RelatieZoekResultaat();
                relatieZoekResultaat.setAchternaam(relatie.getAchternaam());
                relatieZoekResultaat.setGeboortedatum(relatie.getGeboorteDatum().toString("yyyy-MM-dd"));
                relatieZoekResultaat.setRoepnaam(relatie.getRoepnaam());
                relatieZoekResultaat.setTussenvoegsel(relatie.getTussenvoegsel());
                relatieZoekResultaat.setVoornaam(relatie.getVoornaam());

                return relatieZoekResultaat;
            }
        }).collect(Collectors.toList());

        dashboard.setRelaties(relaties);


        metricsService.stop(timer);

        return dashboard;
    }
}
