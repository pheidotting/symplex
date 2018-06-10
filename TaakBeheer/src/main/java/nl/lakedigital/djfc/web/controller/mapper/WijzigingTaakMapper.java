package nl.lakedigital.djfc.web.controller.mapper;

import nl.lakedigital.djfc.domain.WijzigingTaak;

import java.util.function.Function;

public class WijzigingTaakMapper implements Function<WijzigingTaak, nl.lakedigital.djfc.commons.json.WijzigingTaak> {

    @Override
    public nl.lakedigital.djfc.commons.json.WijzigingTaak apply(WijzigingTaak in) {
        nl.lakedigital.djfc.commons.json.WijzigingTaak uit = new nl.lakedigital.djfc.commons.json.WijzigingTaak();

        uit.setId(in.getId());
        uit.setTaakStatus(in.getTaakStatus().name());
        uit.setTijdstip(in.getTijdstip().toString());
        uit.setToegewezenAan(in.getToegewezenAan());

        return uit;
    }
}
