package nl.dias.web.mapper;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonBijlage;
import nl.lakedigital.djfc.domain.response.Bijlage;

import java.util.function.Function;

public class JsonToDtoBijlageMapper implements Function<JsonBijlage, Bijlage> {
    private IdentificatieClient identificatieClient;

    public JsonToDtoBijlageMapper(IdentificatieClient identificatieClient) {
        this.identificatieClient = identificatieClient;
    }

    @Override
    public Bijlage apply(JsonBijlage jsonBijlage) {
        Bijlage bijlage = new Bijlage();

        Identificatie bijlageIdentificatie = identificatieClient.zoekIdentificatie("BIJLAGE", jsonBijlage.getId());

        bijlage.setIdentificatie(bijlageIdentificatie.getIdentificatie());
        bijlage.setDatumUpload(jsonBijlage.getDatumUpload());
        bijlage.setOmschrijving(jsonBijlage.getOmschrijving());
        bijlage.setBestandsNaam(jsonBijlage.getBestandsNaam());

        return bijlage;
    }
}
