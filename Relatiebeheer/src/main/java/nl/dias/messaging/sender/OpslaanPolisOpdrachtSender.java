package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanPolisOpdracht;
import nl.lakedigital.djfc.commons.domain.Opmerking;
import nl.lakedigital.djfc.commons.domain.Pakket;
import nl.lakedigital.djfc.commons.domain.Polis;
import nl.lakedigital.djfc.commons.json.JsonPakket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OpslaanPolisOpdrachtSender extends AbstractSender<OpslaanPolisOpdracht, JsonPakket> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpslaanPolisOpdrachtSender.class);

    public OpslaanPolisOpdrachtSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public OpslaanPolisOpdrachtSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = OpslaanPolisOpdracht.class;
    }

    @Override
    public OpslaanPolisOpdracht maakMessage(JsonPakket jsonPakket) {
        OpslaanPolisOpdracht opslaanPolisOpdracht = new OpslaanPolisOpdracht();
        Pakket pakket = new Pakket();
        pakket.setMaatschappij(jsonPakket.getMaatschappij());
        pakket.setSoortEntiteit(jsonPakket.getSoortEntiteit());
        pakket.setPolisNummer(jsonPakket.getPolisNummer());
        pakket.setEntiteitId(jsonPakket.getEntiteitId());
        pakket.setId(jsonPakket.getId());

        List<Polis> polisList = jsonPakket.getPolissen().stream().map(jsonPolis -> {
            Polis polis1 = new Polis();

            polis1.setId(jsonPolis.getId());
            //            polis1.setIdentificatie(jsonPolis.getIdentificatie());
            // polissen die al in het systeem staan hoeven net per se een status te
            // hebben
            if (polis1.getStatus() != null) {
                polis1.setStatus(jsonPolis.getStatus());
            }
            polis1.setPolisNummer(jsonPolis.getPolisNummer());
            polis1.setKenmerk(jsonPolis.getKenmerk());
            if (jsonPolis.getPremie() != null) {
                polis1.setPremie(jsonPolis.getPremie());
            }
            polis1.setBetaalfrequentie(jsonPolis.getBetaalfrequentie());
            polis1.setIngangsDatum(jsonPolis.getIngangsDatum());
            polis1.setEindDatum(jsonPolis.getEindDatum());
            polis1.setWijzigingsDatum(jsonPolis.getWijzigingsDatum());
            polis1.setProlongatieDatum(jsonPolis.getProlongatieDatum());
            polis1.setBetaalfrequentie(jsonPolis.getBetaalfrequentie());
            polis1.setDekking(jsonPolis.getDekking());
            polis1.setVerzekerdeZaak(jsonPolis.getVerzekerdeZaak());
            polis1.setSoort(jsonPolis.getSoort());
            polis1.setOmschrijvingVerzekering(jsonPolis.getOmschrijvingVerzekering());
            polis1.setStatus(jsonPolis.getStatus());


            return polis1;
        }).collect(Collectors.toList());

        pakket.setPolissen(polisList);
        opslaanPolisOpdracht.setPakket(pakket);

        if (jsonPakket.getOpmerkingen() != null && !jsonPakket.getOpmerkingen().isEmpty()) {
            opslaanPolisOpdracht.setOpmerkingen(jsonPakket.getOpmerkingen().stream().map(jsonOpmerking -> {
                Opmerking opmerking = new Opmerking();

                opmerking.setMedewerker(jsonOpmerking.getMedewerkerId().toString());
                opmerking.setOpmerking(jsonOpmerking.getOpmerking());
                opmerking.setTijd(jsonOpmerking.getTijd());
                opmerking.setIdentificatie(jsonOpmerking.getIdentificatie());

                return opmerking;
            }).collect(Collectors.toList()));
        }

        return opslaanPolisOpdracht;
    }

    public void send(AbstractMessage abstractMessage) {
        super.send(abstractMessage, LOGGER);
    }

    public void send(JsonPakket jsonPakket) {
        super.send(jsonPakket, LOGGER);
    }
}
