package nl.dias.web.mapper;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.domain.response.RekeningNummer;

import java.util.function.Function;

public class JsonToDtoRekeningNummerMapper implements Function<JsonRekeningNummer, RekeningNummer> {
    private IdentificatieClient identificatieClient;

    public JsonToDtoRekeningNummerMapper(IdentificatieClient identificatieClient) {
        this.identificatieClient = identificatieClient;
    }

    @Override
    public RekeningNummer apply(JsonRekeningNummer jsonRekeningNummer) {
        RekeningNummer rekeningNummer = new RekeningNummer();

        Identificatie rekeningNummerIdentificatie = identificatieClient.zoekIdentificatie("REKENINGNUMMER", jsonRekeningNummer.getId());

        rekeningNummer.setIdentificatie(rekeningNummerIdentificatie.getIdentificatie());
        rekeningNummer.setBic(jsonRekeningNummer.getBic());
        rekeningNummer.setRekeningnummer(jsonRekeningNummer.getRekeningnummer());

        return rekeningNummer;
    }
}
