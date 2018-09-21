package nl.lakedigital.djfc.web.servlet;

import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.Bijlage;
import nl.lakedigital.djfc.domain.GroepBijlages;
import nl.lakedigital.djfc.domain.Telefoonnummer;
import nl.lakedigital.djfc.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.lakedigital.djfc.service.*;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class AanmeldenEntiteitenBijIdentificatieServlet implements ServletContextListener {

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

        for (nl.lakedigital.djfc.domain.Adres adres : adresService.alles()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(adres.getId(), SoortEntiteit.ADRES));
        }
        for (Bijlage bijlage : bijlageService.alles()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(bijlage.getId(), SoortEntiteit.BIJLAGE));
        }
        for (GroepBijlages groepBijlages : bijlageService.alleGroepenBijlages()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(groepBijlages.getId(), SoortEntiteit.GROEPBIJLAGES));
        }
        for (nl.lakedigital.djfc.domain.Opmerking opmerking : opmerkingService.alles()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(opmerking.getId(), SoortEntiteit.OPMERKING));
        }
        for (nl.lakedigital.djfc.domain.RekeningNummer rekeningNummer : rekeningNummerService.alles()) {
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
        //Gebruiken we niet
    }
}
