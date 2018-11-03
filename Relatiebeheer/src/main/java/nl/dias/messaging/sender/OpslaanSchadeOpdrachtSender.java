package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanSchadeOpdracht;
import nl.lakedigital.djfc.commons.domain.Opmerking;
import nl.lakedigital.djfc.commons.domain.Schade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class OpslaanSchadeOpdrachtSender extends AbstractSender<OpslaanSchadeOpdracht, nl.lakedigital.djfc.commons.domain.response.Schade> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpslaanSchadeOpdrachtSender.class);

    public OpslaanSchadeOpdrachtSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public OpslaanSchadeOpdrachtSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = OpslaanSchadeOpdracht.class;
    }

    @Override
    public OpslaanSchadeOpdracht maakMessage(nl.lakedigital.djfc.commons.domain.response.Schade jsonSchade) {
        OpslaanSchadeOpdracht opslaanSchadeOpdracht = new OpslaanSchadeOpdracht();
        Schade schade = new Schade();
        //        schade.setId(jsonSchade.getId());
        schade.setIdentificatie(jsonSchade.getIdentificatie());
        schade.setDatumAfgehandeld(jsonSchade.getDatumAfgehandeld());
        schade.setDatumMelding(jsonSchade.getDatumMelding());
        schade.setDatumSchade(jsonSchade.getDatumSchade());
        schade.setEigenRisico(jsonSchade.getEigenRisico());
        schade.setLocatie(jsonSchade.getLocatie());
        schade.setOmschrijving(jsonSchade.getOmschrijving());
        schade.setSchadeNummerMaatschappij(jsonSchade.getSchadeNummerMaatschappij());
        schade.setSchadeNummerTussenPersoon(jsonSchade.getSchadeNummerTussenPersoon());
        schade.setSoortSchade(jsonSchade.getSoortSchade());
        schade.setStatusSchade(jsonSchade.getStatusSchade());

        opslaanSchadeOpdracht.setSchade(schade);

        if (jsonSchade.getOpmerkingen() != null && !jsonSchade.getOpmerkingen().isEmpty()) {
            opslaanSchadeOpdracht.setOpmerkingen(jsonSchade.getOpmerkingen().stream().map(jsonOpmerking -> {
                Opmerking opmerking = new Opmerking();

                opmerking.setMedewerker(jsonOpmerking.getMedewerkerId().toString());
                opmerking.setOpmerking(jsonOpmerking.getOpmerking());
                opmerking.setTijd(jsonOpmerking.getTijd());
                opmerking.setIdentificatie(jsonOpmerking.getIdentificatie());

                return opmerking;
            }).collect(Collectors.toList()));
        }

        return opslaanSchadeOpdracht;
    }

    public void send(AbstractMessage abstractMessage) {
        super.send(abstractMessage, LOGGER);
    }

    public void send(nl.lakedigital.djfc.commons.domain.response.Schade jsonSchade) {
        super.send(jsonSchade, LOGGER);
    }
}
