package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.request.taak.NieuweTaakRequest;
import nl.lakedigital.djfc.commons.json.Taak;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class NieuweTaakRequestSender extends AbstractSender<NieuweTaakRequest, Taak> {
    private final static Logger LOGGER = LoggerFactory.getLogger(NieuweTaakRequestSender.class);

    public NieuweTaakRequestSender(JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = NieuweTaakRequest.class;
    }

    @Override
    public NieuweTaakRequest maakMessage(Taak taak) {
        NieuweTaakRequest nieuweTaakRequest = new NieuweTaakRequest();

        nieuweTaakRequest.setEntiteitId(taak.getEntiteitId());
        nieuweTaakRequest.setSoortEntiteit(SoortEntiteit.valueOf(taak.getSoortEntiteit()));
        nieuweTaakRequest.setOmschrijving(taak.getOmschrijving());
        if (taak.getDeadline() != null && !"".equals(taak.getDeadline())) {
            nieuweTaakRequest.setDeadline(LocalDateTime.parse(taak.getDeadline()));
        }
        nieuweTaakRequest.setTitel(taak.getTitel());
        nieuweTaakRequest.setToegewezenAan(taak.getToegewezenAan());

        return nieuweTaakRequest;
    }

    public void send(Taak taak) {
        super.send(taak, LOGGER);
    }
}
