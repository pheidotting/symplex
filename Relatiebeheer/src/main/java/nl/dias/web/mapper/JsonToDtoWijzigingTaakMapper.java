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
        if (in != null && in.getToegewezenAan() != null && !"null".equals(in.getToegewezenAan())) {
            Identificatie identificatieM = identificatieClient.zoekIdentificatie("MEDEWERKER", Long.valueOf(in.getToegewezenAan()));
            uit.setToegewezenAan(identificatieM.getIdentificatie());
        } else {
            uit.setToegewezenAan("");
        }
        uit.setIdentificatie(identificatie.getIdentificatie());
        uit.setTaakStatus(in.getTaakStatus());
        uit.setTijdstip(in.getTijdstip());

        return uit;
    }


}
