package nl.lakedigital.djfc.web.controller.mapper;

import nl.lakedigital.djfc.commons.domain.Taak;

import java.util.function.Function;
import java.util.stream.Collectors;

public class TaakMapper implements Function<Taak, nl.lakedigital.djfc.commons.json.Taak> {
    @Override
    public nl.lakedigital.djfc.commons.json.Taak apply(Taak in) {
        nl.lakedigital.djfc.commons.json.Taak uit = new nl.lakedigital.djfc.commons.json.Taak();

        uit.setId(in.getId());
        if (in.getDeadline() != null) {
            uit.setDeadline(in.getDeadline().toString());
        }
        uit.setEntiteitId(in.getEntiteitId());
        uit.setOmschrijving(in.getOmschrijving());
        uit.setSoortEntiteit(in.getSoortEntiteit().name());
        if (in.getTijdstipAfgehandeld() != null) {
            uit.setTijdstipAfgehandeld(in.getTijdstipAfgehandeld().toString());
        }
        if (in.getTijdstipCreatie() != null) {
            uit.setTijdstipCreatie(in.getTijdstipCreatie().toString());
        }
        uit.setTitel(in.getTitel());

        uit.setWijzigingTaaks(in.getWijzigingTaak().stream().map(new WijzigingTaakMapper()).collect(Collectors.toList()));

        return uit;
    }
}
