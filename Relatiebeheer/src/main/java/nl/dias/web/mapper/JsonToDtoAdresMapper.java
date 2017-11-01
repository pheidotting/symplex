package nl.dias.web.mapper;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.domain.response.Adres;

import java.util.function.Function;

public class JsonToDtoAdresMapper implements Function<JsonAdres, Adres> {
    private IdentificatieClient identificatieClient;

    public JsonToDtoAdresMapper(IdentificatieClient identificatieClient) {
        this.identificatieClient = identificatieClient;
    }

    @Override
    public Adres apply(JsonAdres jsonAdres) {
        Adres adres = new Adres();

        Identificatie adresIdentificatie = identificatieClient.zoekIdentificatie("ADRES", jsonAdres.getId());

        adres.setHuisnummer(jsonAdres.getHuisnummer());
        adres.setIdentificatie(adresIdentificatie.getIdentificatie());
        adres.setHuisnummer(jsonAdres.getHuisnummer());
        adres.setPlaats(jsonAdres.getPlaats());
        adres.setPostcode(jsonAdres.getPostcode());
        adres.setStraat(jsonAdres.getStraat());
        adres.setToevoeging(jsonAdres.getToevoeging());
        adres.setSoortAdres(jsonAdres.getSoortAdres());
        return adres;
    }
}
