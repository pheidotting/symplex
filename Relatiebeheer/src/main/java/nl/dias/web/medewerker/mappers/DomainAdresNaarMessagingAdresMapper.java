package nl.dias.web.medewerker.mappers;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.response.Adres;

import java.util.function.Function;

public class DomainAdresNaarMessagingAdresMapper implements Function<Adres, nl.lakedigital.as.messaging.domain.Adres> {
    private Long entiteitId;
    private SoortEntiteit soortEntiteit;

    public DomainAdresNaarMessagingAdresMapper(Long entiteitId, SoortEntiteit soortEntiteit) {
        this.entiteitId = entiteitId;
        this.soortEntiteit = soortEntiteit;
    }

    @Override
    public nl.lakedigital.as.messaging.domain.Adres apply(Adres adres) {
        nl.lakedigital.as.messaging.domain.Adres jsonAdres = new nl.lakedigital.as.messaging.domain.Adres(soortEntiteit, entiteitId, null, adres.getStraat(), adres.getHuisnummer(), adres.getToevoeging(), adres.getPostcode(), adres.getPlaats(), adres.getSoortAdres());
        jsonAdres.setIdentificatie(adres.getIdentificatie());

        return jsonAdres;
    }
}
