package nl.dias.web.mapper;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.domain.response.Telefoonnummer;

import java.util.function.Function;

public class JsonToDtoTelefoonnummerMapper implements Function<JsonTelefoonnummer, Telefoonnummer> {
    private IdentificatieClient identificatieClient;

    public JsonToDtoTelefoonnummerMapper(IdentificatieClient identificatieClient) {
        this.identificatieClient = identificatieClient;
    }

    @Override
    public Telefoonnummer apply(JsonTelefoonnummer jsonTelefoonnummer) {
        Telefoonnummer telefoonnummer = new Telefoonnummer();

        Identificatie telefoonnummerIdentificatie = identificatieClient.zoekIdentificatie("TELEFOONNUMMER", jsonTelefoonnummer.getId());

        telefoonnummer.setIdentificatie(telefoonnummerIdentificatie.getIdentificatie());
        telefoonnummer.setTelefoonnummer(jsonTelefoonnummer.getTelefoonnummer());
        telefoonnummer.setSoort(jsonTelefoonnummer.getSoort());
        telefoonnummer.setOmschrijving(jsonTelefoonnummer.getOmschrijving());

        return telefoonnummer;
    }
}
