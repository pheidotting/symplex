package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.PolisOpslaanRequest;
import nl.lakedigital.djfc.commons.domain.Opmerking;
import nl.lakedigital.djfc.commons.domain.Pakket;
import nl.lakedigital.djfc.commons.domain.Polis;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jms.Destination;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class PolisOpslaanRequestSender extends AbstractSender<PolisOpslaanRequest> {
    @Inject
    private Destination responseDestination;

    public PolisOpslaanRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public PolisOpslaanRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = PolisOpslaanRequest.class;

    }

    @Override
    protected Destination getReplyTo() {
        return responseDestination;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.PAKKET, SoortEntiteit.POLIS);
    }

    //    @Override
    public PolisOpslaanRequest maakMessage(Pakket jsonPakket1) {
        PolisOpslaanRequest polisOpslaanRequest = new PolisOpslaanRequest();
        polisOpslaanRequest.setPokketten(newArrayList(jsonPakket1).stream().map(jsonPakket -> {
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
            if (jsonPakket1.getOpmerkingen() != null && !jsonPakket1.getOpmerkingen().isEmpty()) {
                pakket.setOpmerkingen(jsonPakket1.getOpmerkingen().stream().map(jsonOpmerking -> {
                    Opmerking opmerking = new Opmerking();

                    opmerking.setMedewerker(jsonOpmerking.getMedewerkerId().toString());
                    opmerking.setOpmerking(jsonOpmerking.getOpmerking());
                    opmerking.setTijd(jsonOpmerking.getTijd());
                    opmerking.setIdentificatie(jsonOpmerking.getIdentificatie());

                    return opmerking;
                }).collect(Collectors.toList()));
            }

            return pakket;
        }).collect(Collectors.toList()));

        return polisOpslaanRequest;
    }

}
