package nl.lakedigital.djfc.messaging.mappers;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.djfc.commons.domain.Adres;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import nl.lakedigital.djfc.service.AdresService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class MessagingAdresNaarDomainAdresMapper implements Function<AbstracteEntiteitMetSoortEnId, nl.lakedigital.djfc.domain.Adres> {
    private final static Logger LOGGER = LoggerFactory.getLogger(MessagingAdresNaarDomainAdresMapper.class);

    private IdentificatieClient identificatieClient;
    private AdresService adresService;

    public MessagingAdresNaarDomainAdresMapper(IdentificatieClient identificatieClient, AdresService adresService) {
        this.identificatieClient = identificatieClient;
        this.adresService = adresService;
    }

    @Override
    public nl.lakedigital.djfc.domain.Adres apply(AbstracteEntiteitMetSoortEnId abstracteEntiteitMetSoortEnId) {
        LOGGER.trace(ReflectionToStringBuilder.toString(abstracteEntiteitMetSoortEnId));

        Adres adr = (Adres) abstracteEntiteitMetSoortEnId;
        LOGGER.trace(ReflectionToStringBuilder.toString(adr));

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(adr.getIdentificatie());

        nl.lakedigital.djfc.domain.Adres adres;
        if (identificatie != null && identificatie.getEntiteitId() != null) {
            adres = adresService.lees(identificatie.getEntiteitId());
        } else {
            adres = new nl.lakedigital.djfc.domain.Adres();
        }

        LOGGER.trace(ReflectionToStringBuilder.toString(adres));

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
