package nl.dias.web.servlet;

import nl.dias.domein.*;
import nl.dias.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.dias.repository.GebruikerRepository;
import nl.dias.service.BedrijfService;
import nl.dias.service.HypotheekService;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
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
    private GebruikerRepository gebruikerRepository;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private HypotheekService hypotheekService;
    @Inject
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);

        for (Relatie relatie : gebruikerRepository.alleRelaties()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(relatie.getId(), SoortEntiteit.RELATIE));

            for (Hypotheek hypotheek : hypotheekService.allesVanRelatie(relatie.getId())) {
                entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(hypotheek.getId(), SoortEntiteit.HYPOTHEEK));
            }
        }

        for (Medewerker medewerker : gebruikerRepository.alleMedewerkers()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(medewerker.getId(), SoortEntiteit.MEDEWERKER));
        }
        for (ContactPersoon contactPersoon : gebruikerRepository.alleContactPersonen()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(contactPersoon.getId(), SoortEntiteit.CONTACTPERSOON));
        }

        for (Bedrijf bedrijf : bedrijfService.alles()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(bedrijf.getId(), SoortEntiteit.BEDRIJF));
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
