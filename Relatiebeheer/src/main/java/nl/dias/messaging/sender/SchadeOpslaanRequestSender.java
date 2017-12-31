package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.domain.Opmerking;
import nl.lakedigital.as.messaging.domain.Schade;
import nl.lakedigital.as.messaging.request.SchadeOpslaanRequest;
import nl.lakedigital.djfc.commons.json.JsonSchade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class SchadeOpslaanRequestSender extends AbstractSender<SchadeOpslaanRequest, JsonSchade> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeOpslaanRequestSender.class);

    public SchadeOpslaanRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public SchadeOpslaanRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = SchadeOpslaanRequest.class;
    }

    @Override
    public SchadeOpslaanRequest maakMessage(JsonSchade jsonSchade) {
        SchadeOpslaanRequest schadeOpslaanRequest = new SchadeOpslaanRequest();

        schadeOpslaanRequest.setSchades(newArrayList(jsonSchade).stream().map(jsonSchade1 -> {
            Schade schade = new Schade();

            schade.setId(jsonSchade1.getId());
            schade.setIdentificatie(jsonSchade1.getIdentificatie());
            schade.setDatumAfgehandeld(jsonSchade1.getDatumAfgehandeld());
            schade.setDatumTijdMelding(jsonSchade1.getDatumTijdMelding());
            schade.setDatumTijdSchade(jsonSchade1.getDatumTijdSchade());
            schade.setEigenRisico(jsonSchade1.getEigenRisico());
            schade.setLocatie(jsonSchade1.getLocatie());
            schade.setOmschrijving(jsonSchade1.getOmschrijving());
            schade.setSchadeNummerMaatschappij(jsonSchade1.getSchadeNummerMaatschappij());
            schade.setSchadeNummerTussenPersoon(jsonSchade1.getSchadeNummerTussenPersoon());
            schade.setSoortSchade(jsonSchade1.getSoortSchade());
            schade.setPolis(jsonSchade1.getPolis());
            schade.setStatusSchade(jsonSchade1.getStatusSchade());

            if (jsonSchade1.getOpmerkingen() != null && !jsonSchade1.getOpmerkingen().isEmpty()) {
                schade.setOpmerkingen(jsonSchade1.getOpmerkingen().stream().map(jsonOpmerking -> {
                    Opmerking opmerking = new Opmerking();

                    opmerking.setMedewerker(jsonOpmerking.getMedewerkerId());
                    opmerking.setTekst(jsonOpmerking.getOpmerking());
                    opmerking.setTijdstip(jsonOpmerking.getTijd());
                    opmerking.setIdentificatie(jsonOpmerking.getIdentificatie());

                    return opmerking;
                }).collect(Collectors.toList()));
            }

            return schade;
        }).collect(Collectors.toList()));

        return schadeOpslaanRequest;
    }
}
