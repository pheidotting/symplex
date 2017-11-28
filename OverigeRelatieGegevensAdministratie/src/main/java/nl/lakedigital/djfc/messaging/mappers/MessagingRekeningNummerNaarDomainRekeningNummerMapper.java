package nl.lakedigital.djfc.messaging.mappers;

import nl.lakedigital.as.messaging.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.as.messaging.domain.RekeningNummer;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.service.RekeningNummerService;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.function.Function;

public class MessagingRekeningNummerNaarDomainRekeningNummerMapper implements Function<AbstracteEntiteitMetSoortEnId, nl.lakedigital.djfc.domain.RekeningNummer> {
    private DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
    private IdentificatieClient identificatieClient;
    private RekeningNummerService rekeningNummerService;

    public MessagingRekeningNummerNaarDomainRekeningNummerMapper(IdentificatieClient identificatieClient, RekeningNummerService rekeningNummerService) {
        this.identificatieClient = identificatieClient;
        this.rekeningNummerService = rekeningNummerService;
    }

    @Override
    public nl.lakedigital.djfc.domain.RekeningNummer apply(AbstracteEntiteitMetSoortEnId abstracteEntiteitMetSoortEnId) {
        RekeningNummer rek = (RekeningNummer) abstracteEntiteitMetSoortEnId;

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(rek.getIdentificatie());

        nl.lakedigital.djfc.domain.RekeningNummer rekeningNummer = null;
        if (identificatie != null && identificatie.getEntiteitId() != null) {
            rekeningNummer = rekeningNummerService.lees(identificatie.getEntiteitId());
        } else {
            rekeningNummer = new nl.lakedigital.djfc.domain.RekeningNummer();
        }

        rekeningNummer.setBic(rek.getBic());
        rekeningNummer.setRekeningnummer(rek.getRekeningnummer());
        rekeningNummer.setSoortEntiteit(SoortEntiteit.valueOf(rek.getSoortEntiteit().name()));
        rekeningNummer.setEntiteitId(rek.getEntiteitId());

        return rekeningNummer;
    }
}
