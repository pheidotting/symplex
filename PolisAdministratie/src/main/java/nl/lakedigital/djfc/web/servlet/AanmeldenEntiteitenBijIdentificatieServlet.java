package nl.lakedigital.djfc.web.servlet;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.domain.Schade;
import nl.lakedigital.djfc.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.lakedigital.djfc.repository.PolisRepository;
import nl.lakedigital.djfc.repository.SchadeRepository;
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
    private PolisRepository polisRepository;
    @Inject
    private SchadeRepository schadeRepository;
    @Inject
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);

        for (Polis polis : polisRepository.alles()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(polis.getId(), SoortEntiteit.POLIS));
        }

        for (Schade schade : schadeRepository.alles()) {
            entiteitenOpgeslagenRequestSender.send(maakSoortEntiteitEnEntiteitId(schade.getId(), SoortEntiteit.SCHADE));
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
