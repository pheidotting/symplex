package nl.dias.web.medewerker;

import com.codahale.metrics.Timer;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.service.SchadeService;
import nl.dias.web.mapper.SchadeMapper;
import nl.lakedigital.djfc.commons.json.BedrijfZoekResultaat;
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
    @Inject
    private SchadeMapper schadeMapper;
    @Inject
    private BedrijfService bedrijfService;

    @RequestMapping(method = RequestMethod.GET, value = "/dashboard", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Dashboard getDashboard(HttpServletRequest httpServletRequest) {
        Timer.Context timer = metricsService.addTimerMetric("dashboard", DashboardController.class);

        zetSessieWaarden(httpServletRequest);
        Kantoor kantoor = ((Medewerker) getIngelogdeGebruiker(httpServletRequest)).getKantoor();

        Dashboard dashboard = new Dashboard();

        List<RelatieZoekResultaat> relaties = gebruikerService.alleRelaties(kantoor, true).stream().map(new Function<Relatie, RelatieZoekResultaat>() {
            @Override
            public RelatieZoekResultaat apply(Relatie relatie) {
                RelatieZoekResultaat relatieZoekResultaat = new RelatieZoekResultaat();
                relatieZoekResultaat.setAchternaam(relatie.getAchternaam());
                if (relatie.getGeboorteDatum() != null) {
                    relatieZoekResultaat.setGeboortedatum(relatie.getGeboorteDatum().toString("yyyy-MM-dd"));
                }
                relatieZoekResultaat.setRoepnaam(relatie.getRoepnaam());
                relatieZoekResultaat.setTussenvoegsel(relatie.getTussenvoegsel());
                relatieZoekResultaat.setVoornaam(relatie.getVoornaam());

                return relatieZoekResultaat;
            }
        }).collect(Collectors.toList());

        dashboard.setRelaties(relaties);

        dashboard.setOpenSchades(schadeMapper.mapAllNaarJson(schadeService.alleOpenSchade(kantoor)));

        dashboard.setBedrijven(bedrijfService.alles().stream()//
                .filter(bedrijf -> {
                    if (getIngelogdeGebruiker(httpServletRequest) != null) {
                        return bedrijf.getKantoor() == ((Medewerker) getIngelogdeGebruiker(httpServletRequest)).getKantoor().getId();
                    }
                    return false;
                }).map(bedrijf -> {
                    BedrijfZoekResultaat bedrijfZoekResultaat = new BedrijfZoekResultaat();
                    bedrijfZoekResultaat.setId(bedrijf.getId());
                    bedrijfZoekResultaat.setNaam(bedrijf.getNaam());

                    return bedrijfZoekResultaat;
                }).collect(Collectors.toList()));

        metricsService.stop(timer);

        return dashboard;
    }
}
