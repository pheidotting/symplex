package nl.lakedigital.djfc.messaging.mappers;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.domain.RekeningNummer;
import nl.lakedigital.djfc.service.RekeningNummerService;

import java.util.function.Function;

public class MessagingRekeningNummerNaarDomainRekeningNummerMapper implements Function<AbstracteEntiteitMetSoortEnId, RekeningNummer> {
    private IdentificatieClient identificatieClient;
    private RekeningNummerService rekeningNummerService;

    public MessagingRekeningNummerNaarDomainRekeningNummerMapper(IdentificatieClient identificatieClient, RekeningNummerService rekeningNummerService) {
        this.identificatieClient = identificatieClient;
        this.rekeningNummerService = rekeningNummerService;
    }

    @Override
    public RekeningNummer apply(AbstracteEntiteitMetSoortEnId abstracteEntiteitMetSoortEnId) {
        nl.lakedigital.djfc.commons.domain.RekeningNummer rek = (nl.lakedigital.djfc.commons.domain.RekeningNummer) abstracteEntiteitMetSoortEnId;

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(rek.getIdentificatie());

        RekeningNummer rekeningNummer;
        if (identificatie != null && identificatie.getEntiteitId() != null) {
            rekeningNummer = rekeningNummerService.lees(identificatie.getEntiteitId());
        } else {
            rekeningNummer = new RekeningNummer();
        }

        rekeningNummer.setBic(rek.getBic());
        rekeningNummer.setRekeningnummer(rek.getRekeningnummer());
        rekeningNummer.setSoortEntiteit(SoortEntiteit.valueOf(rek.getSoortEntiteit().name()));
        rekeningNummer.setEntiteitId(rek.getEntiteitId());

        return rekeningNummer;
    }
}
