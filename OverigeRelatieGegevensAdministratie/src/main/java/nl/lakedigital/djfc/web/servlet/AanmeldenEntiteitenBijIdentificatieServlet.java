package nl.lakedigital.djfc.web.servlet;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.domain.*;
import nl.lakedigital.djfc.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.lakedigital.djfc.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class AanmeldenEntiteitenBijIdentificatieServlet implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AanmeldenEntiteitenBijIdentificatieServlet.class);

    @Inject
    private AdresService adresService;
    @Inject
    private BijlageService bijlageService;
    @Inject
    private OpmerkingService opmerkingService;
    @Inject
    private RekeningNummerService rekeningNummerService;
    @Inject
    private TelefoonnummerService telefoonnummerService;
    @Inject
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);

        for (Adres adres : adresService.alles()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(adres.getId(), SoortEntiteit.ADRES));
        }
        for (Bijlage bijlage : bijlageService.alles()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(bijlage.getId(), SoortEntiteit.BIJLAGE));
        }
        for (GroepBijlages groepBijlages : bijlageService.alleGroepenBijlages()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(groepBijlages.getId(), SoortEntiteit.GROEPBIJLAGES));
        }
        for (Opmerking opmerking : opmerkingService.alles()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(opmerking.getId(), SoortEntiteit.OPMERKING));
        }
        for (RekeningNummer rekeningNummer : rekeningNummerService.alles()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(rekeningNummer.getId(), SoortEntiteit.REKENINGNUMMER));
        }
        for (Telefoonnummer telefoonnummer : telefoonnummerService.alles()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(telefoonnummer.getId(), SoortEntiteit.TELEFOONNUMMER));
        }
    }

    private List<SoortEntiteitEnEntiteitId> maakSoortEntiteitEnEntiteitId(Long id, SoortEntiteit soort) {
        SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
        soortEntiteitEnEntiteitId.setEntiteitId(id);
        soortEntiteitEnEntiteitId.setSoortEntiteit(soort);

        return newArrayList(soortEntiteitEnEntiteitId);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
