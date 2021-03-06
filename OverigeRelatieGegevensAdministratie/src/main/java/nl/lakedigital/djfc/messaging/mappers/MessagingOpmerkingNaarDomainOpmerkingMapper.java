package nl.lakedigital.djfc.messaging.mappers;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.djfc.commons.domain.Opmerking;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.service.OpmerkingService;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.function.Function;

public class MessagingOpmerkingNaarDomainOpmerkingMapper implements Function<AbstracteEntiteitMetSoortEnId, nl.lakedigital.djfc.domain.Opmerking> {
    private DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
    private IdentificatieClient identificatieClient;
    private OpmerkingService opmerkingService;
    private Long ingelogdeGebruiker;

    public MessagingOpmerkingNaarDomainOpmerkingMapper(IdentificatieClient identificatieClient, OpmerkingService opmerkingService, Long ingelogdeGebruiker) {
        this.identificatieClient = identificatieClient;
        this.opmerkingService = opmerkingService;
        this.ingelogdeGebruiker = ingelogdeGebruiker;
    }

    @Override
    public nl.lakedigital.djfc.domain.Opmerking apply(AbstracteEntiteitMetSoortEnId abstracteEntiteitMetSoortEnId) {
        Opmerking opm = (Opmerking) abstracteEntiteitMetSoortEnId;

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(opm.getIdentificatie());

        nl.lakedigital.djfc.domain.Opmerking opmerking;
        if (identificatie != null && identificatie.getEntiteitId() != null) {
            opmerking = opmerkingService.lees(identificatie.getEntiteitId());
        } else {
            opmerking = new nl.lakedigital.djfc.domain.Opmerking();
            opmerking.setMedewerker(ingelogdeGebruiker);
            if (opm.getTijd() == null) {
                opmerking.setTijd(LocalDateTime.now());
            } else {
                opmerking.setTijd(LocalDateTime.parse(opm.getTijd(), dateTimeFormatter));
            }
            opmerking.setSoortEntiteit(SoortEntiteit.valueOf(opm.getSoortEntiteit().name()));
            opmerking.setEntiteitId(opm.getEntiteitId());
        }

        opmerking.setOpmerking(opm.getOpmerking());

        return opmerking;
    }
}
