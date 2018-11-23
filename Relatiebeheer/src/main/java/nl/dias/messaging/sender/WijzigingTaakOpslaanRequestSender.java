package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.request.taak.WijzigingTaakOpslaanRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.WijzigingTaakOpslaan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.inject.Inject;
import java.util.ArrayList;

public class WijzigingTaakOpslaanRequestSender extends AbstractSender<WijzigingTaakOpslaanRequest, WijzigingTaakOpslaan> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WijzigingTaakOpslaanRequestSender.class);

    @Inject
    private IdentificatieClient identificatieClient;

    public WijzigingTaakOpslaanRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public WijzigingTaakOpslaanRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = WijzigingTaakOpslaanRequest.class;
    }

    @Override
    public WijzigingTaakOpslaanRequest maakMessage(WijzigingTaakOpslaan wijzigingTaakOpslaan) {
        WijzigingTaakOpslaanRequest wijzigingTaakOpslaanRequest = new WijzigingTaakOpslaanRequest();

        Long taakId = identificatieClient.zoekIdentificatieCode(wijzigingTaakOpslaan.getTaak()).getEntiteitId();
        if (wijzigingTaakOpslaan.getToegewezenAan() != null && !"null".equals(wijzigingTaakOpslaan.getToegewezenAan()) && !"".equals(wijzigingTaakOpslaan.getToegewezenAan())) {
            Long medewerkerId = identificatieClient.zoekIdentificatieCode(wijzigingTaakOpslaan.getToegewezenAan()).getEntiteitId();
            wijzigingTaakOpslaanRequest.setToegewezenAan(medewerkerId);
        }

        wijzigingTaakOpslaanRequest.setTaak(taakId);
        wijzigingTaakOpslaanRequest.setTaakStatus(wijzigingTaakOpslaan.getTaakStatus());
        wijzigingTaakOpslaanRequest.setOpmerking(wijzigingTaakOpslaan.getOpmerking());

        return wijzigingTaakOpslaanRequest;
    }

    public void send(WijzigingTaakOpslaan wijzigingTaakOpslaan) {
        super.send(wijzigingTaakOpslaan, LOGGER);
    }
}
