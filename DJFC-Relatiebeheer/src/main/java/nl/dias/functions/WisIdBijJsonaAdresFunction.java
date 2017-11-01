package nl.dias.functions;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.JsonAdres;

import java.util.function.Function;

public class WisIdBijJsonaAdresFunction implements Function<JsonAdres, JsonAdres> {
    private IdentificatieClient identificatieClient;

    public WisIdBijJsonaAdresFunction(IdentificatieClient identificatieClient) {
        this.identificatieClient = identificatieClient;
    }

    @Override
    public JsonAdres apply(JsonAdres adres) {
        String identificatie = identificatieClient.zoekIdentificatie("ADRES", adres.getId()).getIdentificatie();

        adres.setEntiteitId(null);
        adres.setSoortEntiteit(null);
        adres.setId(null);
        adres.setIdentificatie(identificatie);

        return adres;
    }
}
