package nl.dias.web.medewerker.mappers;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.response.Adres;

import java.util.function.Function;

public class DomainAdresNaarMessagingAdresMapper implements Function<Adres, nl.lakedigital.as.messaging.domain.Adres> {
    private Long bedrijfId;

    public DomainAdresNaarMessagingAdresMapper(Long bedrijfId) {
        this.bedrijfId = bedrijfId;
    }

    @Override
    public nl.lakedigital.as.messaging.domain.Adres apply(Adres adres) {
        nl.lakedigital.as.messaging.domain.Adres jsonAdres = new nl.lakedigital.as.messaging.domain.Adres(SoortEntiteit.BEDRIJF, bedrijfId, null, adres.getStraat(), adres.getHuisnummer(), adres.getToevoeging(), adres.getPostcode(), adres.getPlaats(), adres.getSoortAdres());
        jsonAdres.setIdentificatie(adres.getIdentificatie());

        return jsonAdres;
    }
}
