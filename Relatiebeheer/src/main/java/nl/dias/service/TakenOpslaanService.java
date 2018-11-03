package nl.dias.service;

import nl.dias.messaging.sender.OpslaanTaakRequestSender;
import nl.lakedigital.as.messaging.request.taak.OpslaanTaakRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.response.Taak;
import nl.lakedigital.djfc.commons.json.Identificatie;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class TakenOpslaanService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TakenOpslaanService.class);

    @Inject
    private OpslaanTaakRequestSender opslaanTaakRequestSender;
    @Inject
    private IdentificatieClient identificatieClient;

    public void opslaan(List<Taak> taken, Long entiteitId, SoortEntiteit soortEntiteit) {
        LOGGER.info("Opslaan {} taken", taken.size());
        taken.stream().filter(taak -> taak.getIdentificatie() == null && taak.getTitel() != null && !"".equals(taak.getTitel()))//
                .forEach(taak -> {
                    OpslaanTaakRequest opslaanTaakRequest = new OpslaanTaakRequest();
                    opslaanTaakRequest.setTitel(taak.getTitel());
                    opslaanTaakRequest.setOmschrijving(taak.getOmschrijving());
                    if (taak.getDeadline() != null && !"".equals(taak.getDeadline())) {
                        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("YYYY-MM-dd");
                        opslaanTaakRequest.setDeadline(LocalDate.parse(taak.getDeadline(), dateTimeFormatter));
                    }
                    if (taak.getToegewezenAan() != null && !"".equals(taak.getToegewezenAan())) {
                        Identificatie identificatieMedewerker = identificatieClient.zoekIdentificatieCode(taak.getToegewezenAan());
                        opslaanTaakRequest.setToegewezenAan(identificatieMedewerker.getEntiteitId());
                    }
                    opslaanTaakRequest.setSoortEntiteit(soortEntiteit);
                    opslaanTaakRequest.setEntiteitId(entiteitId);
                    opslaanTaakRequestSender.send(opslaanTaakRequest);
                });

    }
}
