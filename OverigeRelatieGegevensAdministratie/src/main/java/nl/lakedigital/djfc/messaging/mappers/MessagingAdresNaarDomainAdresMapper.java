package nl.lakedigital.djfc.messaging.mappers;

import nl.lakedigital.as.messaging.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.as.messaging.domain.Adres;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.service.AdresService;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.function.Function;

public class MessagingAdresNaarDomainAdresMapper implements Function<AbstracteEntiteitMetSoortEnId, nl.lakedigital.djfc.domain.Adres> {
    private DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
    private IdentificatieClient identificatieClient;
    private AdresService adresService;

    public MessagingAdresNaarDomainAdresMapper(IdentificatieClient identificatieClient, AdresService adresService) {
        this.identificatieClient = identificatieClient;
        this.adresService = adresService;
    }

    @Override
    public nl.lakedigital.djfc.domain.Adres apply(AbstracteEntiteitMetSoortEnId abstracteEntiteitMetSoortEnId) {
        Adres adr = (Adres) abstracteEntiteitMetSoortEnId;

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(adr.getIdentificatie());

        nl.lakedigital.djfc.domain.Adres adres = null;
        if (identificatie != null && identificatie.getEntiteitId() != null) {
            adres = adresService.lees(identificatie.getEntiteitId());
        } else {
            adres = new nl.lakedigital.djfc.domain.Adres();
        }

        adres.setHuisnummer(adr.getHuisnummer());
        adres.setPlaats(adr.getPlaats());
        adres.setPostcode(adr.getPostcode());
        adres.setSoortAdres(nl.lakedigital.djfc.domain.Adres.SoortAdres.valueOf(adr.getSoortAdres()));
        adres.setStraat(adr.getStraat());
        adres.setToevoeging(adr.getToevoeging());
        adres.setSoortEntiteit(SoortEntiteit.valueOf(adr.getSoortEntiteit().name()));
        adres.setEntiteitId(adr.getEntiteitId());

        return adres;
    }
}
