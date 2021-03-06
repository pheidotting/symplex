package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.request.taak.OpslaanTaakRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.Taak;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class OpslaanTaakRequestSender extends AbstractSender<OpslaanTaakRequest, Taak> {
    private final static Logger LOGGER = LoggerFactory.getLogger(OpslaanTaakRequestSender.class);

    @Inject
    private IdentificatieClient identificatieClient;

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
            opslaanTaakRequest.setDeadline(LocalDate.parse(taak.getDeadline()));
        }
        opslaanTaakRequest.setTitel(taak.getTitel());
        opslaanTaakRequest.setToegewezenAan(taak.getToegewezenAan());

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(taak.getIdentificatie());

        opslaanTaakRequest.setId(identificatie.getEntiteitId());

        return opslaanTaakRequest;
    }

    public void send(Taak taak) {
        super.send(taak, LOGGER);
    }

    public void send(OpslaanTaakRequest opslaanTaakRequest) {
        super.send(opslaanTaakRequest, LOGGER);
    }
}
