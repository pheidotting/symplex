package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.request.taak.OpslaanTaakRequest;
import nl.lakedigital.djfc.commons.json.Taak;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class OpslaanTaakRequestSender extends AbstractSender<OpslaanTaakRequest, Taak> {
    private final static Logger LOGGER = LoggerFactory.getLogger(OpslaanTaakRequestSender.class);

    public OpslaanTaakRequestSender() {
    }

    public OpslaanTaakRequestSender(JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = OpslaanTaakRequest.class;
    }

    @Override
    public OpslaanTaakRequest maakMessage(Taak taak) {
        OpslaanTaakRequest opslaanTaakRequest = new OpslaanTaakRequest();

        opslaanTaakRequest.setEntiteitId(taak.getEntiteitId());
        opslaanTaakRequest.setSoortEntiteit(SoortEntiteit.valueOf(taak.getSoortEntiteit()));
        opslaanTaakRequest.setOmschrijving(taak.getOmschrijving());
        if (taak.getDeadline() != null && !"".equals(taak.getDeadline())) {
            opslaanTaakRequest.setDeadline(LocalDateTime.parse(taak.getDeadline()));
        }
        opslaanTaakRequest.setTitel(taak.getTitel());
        opslaanTaakRequest.setToegewezenAan(taak.getToegewezenAan());
        opslaanTaakRequest.setIdentificatie(taak.getIdentificatie());

        return opslaanTaakRequest;
    }

    public void send(Taak taak) {
        super.send(taak, LOGGER);
    }
}
