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

import java.util.function.Function;

public class MessagingTaakToUitgaandeOpdrachtMapper extends AbstractMapper<OpslaanTaakRequest> implements Function<Taak, UitgaandeOpdracht> {
    private final static Logger LOGGER = LoggerFactory.getLogger(MessagingTaakToUitgaandeOpdrachtMapper.class);
    private UitgaandeOpdracht uitgaandeOpdrachtWachtenOp;
    private SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId;

    public MessagingTaakToUitgaandeOpdrachtMapper(UitgaandeOpdracht uitgaandeOpdrachtWachtenOp, SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
        super(OpslaanEntiteitenRequest.class);
        this.uitgaandeOpdrachtWachtenOp = uitgaandeOpdrachtWachtenOp;
        this.soortEntiteitEnEntiteitId = soortEntiteitEnEntiteitId;

    }

    @Override
    public UitgaandeOpdracht apply(Taak taak) {
        OpslaanTaakRequest opslaanTaakRequest = new OpslaanTaakRequest();
        setMDC(opslaanTaakRequest);
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
            }
        }
        opslaanTaakRequest.setToegewezenAan(taak.getToegewezenAan());

        UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();
        uitgaandeOpdracht.setSoortEntiteit(SoortEntiteit.TAAK);

        uitgaandeOpdracht.setBericht(marshall(opslaanTaakRequest));
        uitgaandeOpdracht.setWachtenOp(uitgaandeOpdrachtWachtenOp);

        return uitgaandeOpdracht;
    }
}
