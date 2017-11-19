package nl.dias.messaging.sender;

import nl.dias.domein.Bedrag;
import nl.lakedigital.as.messaging.domain.Opmerking;
import nl.lakedigital.as.messaging.domain.Polis;
import nl.lakedigital.as.messaging.request.PolisOpslaanRequest;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class PolisOpslaanRequestSender extends AbstractSender<PolisOpslaanRequest, JsonPolis> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisOpslaanRequestSender.class);

    public PolisOpslaanRequestSender() {
        this.jmsTemplates = new ArrayList<>();
        this.LOGGER_ = LOGGER;
    }

    public PolisOpslaanRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.LOGGER_ = LOGGER;
        this.clazz = PolisOpslaanRequest.class;
    }

    @Override
    public PolisOpslaanRequest maakMessage(JsonPolis polis) {
        PolisOpslaanRequest polisOpslaanRequest = new PolisOpslaanRequest();
        polisOpslaanRequest.setPolissen(newArrayList(polis).stream().map(jsonPolis -> {
            Polis polis1 = new Polis();

            //            polis1.setId(polis1.getId());
            polis1.setIdentificatie(jsonPolis.getIdentificatie());
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
            if (jsonPolis.getMaatschappij() != null) {
                polis1.setMaatschappij(jsonPolis.getMaatschappij());
            }
            polis1.setSoort(jsonPolis.getSoort());
            polis1.setSoortEntiteit(jsonPolis.getSoortEntiteit());
            polis1.setEntiteitId(jsonPolis.getEntiteitId());
            polis1.setOmschrijvingVerzekering(jsonPolis.getOmschrijvingVerzekering());
            polis1.setMaatschappij(jsonPolis.getMaatschappij());
            polis1.setStatus(jsonPolis.getStatus());

            if (jsonPolis.getOpmerkingen() != null && !jsonPolis.getOpmerkingen().isEmpty()) {
                polis1.setOpmerkingen(jsonPolis.getOpmerkingen().stream().map(jsonOpmerking -> {
                    Opmerking opmerking = new Opmerking();

                    opmerking.setMedewerker(jsonOpmerking.getMedewerkerId());
                    opmerking.setTekst(jsonOpmerking.getOpmerking());
                    opmerking.setTijdstip(jsonOpmerking.getTijd());
                    opmerking.setIdentificatie(jsonOpmerking.getIdentificatie());

                    return opmerking;
                }).collect(Collectors.toList()));
            }

            return polis1;
        }).collect(Collectors.toList()));

        return polisOpslaanRequest;
    }

    protected static String zetBedragOm(Bedrag bedrag) {
        String waarde;
        String[] x = bedrag.getBedrag().toString().split("\\.");
        if (x[1].length() == 1) {
            waarde = bedrag.getBedrag().toString() + "0";
        } else {
            waarde = bedrag.getBedrag().toString() + "";
        }
        return waarde;
    }

}
