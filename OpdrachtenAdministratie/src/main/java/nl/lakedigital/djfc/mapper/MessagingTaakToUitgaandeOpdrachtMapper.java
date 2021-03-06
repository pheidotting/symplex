package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.as.messaging.request.taak.OpslaanTaakRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.Taak;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MessagingTaakToUitgaandeOpdrachtMapper extends AbstractMapper<OpslaanTaakRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingTaakToUitgaandeOpdrachtMapper.class);
    private UitgaandeOpdracht uitgaandeOpdrachtWachtenOp;
    private SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId;

    public MessagingTaakToUitgaandeOpdrachtMapper(UitgaandeOpdracht uitgaandeOpdrachtWachtenOp, SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
        super(OpslaanEntiteitenRequest.class);
        this.uitgaandeOpdrachtWachtenOp = uitgaandeOpdrachtWachtenOp;
        this.soortEntiteitEnEntiteitId = soortEntiteitEnEntiteitId;

    }

    public List<UitgaandeOpdracht> apply(Taak taak) {
        List<UitgaandeOpdracht> uitgaandeOpdrachten = new ArrayList<>();

        if ((taak.getTitel() != null && !"".equals(taak.getTitel())) || (taak.getOmschrijving() != null && !"".equals(taak.getOmschrijving()))) {
            OpslaanTaakRequest opslaanTaakRequest = new OpslaanTaakRequest();
            opslaanTaakRequest.setEntiteitId(this.soortEntiteitEnEntiteitId.getEntiteitId());
            opslaanTaakRequest.setSoortEntiteit(this.soortEntiteitEnEntiteitId.getSoortEntiteit());

            opslaanTaakRequest.setTitel(taak.getTitel());
            opslaanTaakRequest.setOmschrijving(taak.getOmschrijving());
            if (taak.getDeadline() != null && !"".equals(taak.getDeadline())) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-YYYY");
                try {
                    opslaanTaakRequest.setDeadline(LocalDate.parse(taak.getDeadline(), dateTimeFormatter));
                } catch (java.lang.IllegalArgumentException e) {
                    dateTimeFormatter = DateTimeFormat.forPattern("YYYY-MM-dd");
                    opslaanTaakRequest.setDeadline(LocalDate.parse(taak.getDeadline(), dateTimeFormatter));

                    LOGGER.trace("{}", e);
                }
            }
            opslaanTaakRequest.setToegewezenAan(taak.getToegewezenAan());

            UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();
            uitgaandeOpdracht.setSoortEntiteit(SoortEntiteit.TAAK);
            opslaanTaakRequest.setHoofdSoortEntiteit(uitgaandeOpdracht.getSoortEntiteit());
            setMDC(opslaanTaakRequest, uitgaandeOpdracht);

            uitgaandeOpdracht.setBericht(marshall(opslaanTaakRequest));
            uitgaandeOpdracht.setWachtenOp(uitgaandeOpdrachtWachtenOp);

            uitgaandeOpdrachten.add(uitgaandeOpdracht);
            uitgaandeOpdrachten.add(new EntiteitenOpgeslagenMapper().maakEntiteitenOpgeslagenRequest(uitgaandeOpdracht));
        }

        return uitgaandeOpdrachten;
    }
}
