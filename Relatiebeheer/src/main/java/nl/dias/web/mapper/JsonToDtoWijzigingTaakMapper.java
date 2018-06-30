package nl.dias.web.mapper;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.WijzigingTaak;

import java.util.function.Function;

public class JsonToDtoWijzigingTaakMapper implements Function<WijzigingTaak, nl.lakedigital.djfc.domain.response.WijzigingTaak> {
    private IdentificatieClient identificatieClient;

    public JsonToDtoWijzigingTaakMapper(IdentificatieClient identificatieClient) {
        this.identificatieClient = identificatieClient;
    }

    @Override
    public nl.lakedigital.djfc.domain.response.WijzigingTaak apply(WijzigingTaak in) {
        nl.lakedigital.djfc.domain.response.WijzigingTaak uit = new nl.lakedigital.djfc.domain.response.WijzigingTaak();

        Identificatie identificatie = identificatieClient.zoekIdentificatie("WIJZIGINGTAAK", in.getId());
        uit.setIdentificatie(identificatie.getIdentificatie());
        uit.setTaakStatus(in.getTaakStatus());
        uit.setTijdstip(in.getTijdstip().toString());
        uit.setToegewezenAan(Long.valueOf(in.getToegewezenAan()));

        return uit;
    }


}
