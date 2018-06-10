package nl.dias.web.mapper;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.Taak;

import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonToDtoTaakMapper implements Function<Taak, nl.lakedigital.djfc.domain.response.Taak> {
    private IdentificatieClient identificatieClient;

    public JsonToDtoTaakMapper(IdentificatieClient identificatieClient) {
        this.identificatieClient = identificatieClient;
    }

    @Override
    public nl.lakedigital.djfc.domain.response.Taak apply(Taak in) {
        nl.lakedigital.djfc.domain.response.Taak uit = new nl.lakedigital.djfc.domain.response.Taak();

        Identificatie identificatie = identificatieClient.zoekIdentificatie("TAAK", in.getId());
        uit.setIdentificatie(identificatie.getIdentificatie());
        uit.setDeadline(in.getDeadline());
        uit.setEntiteitId(in.getEntiteitId());
        uit.setOmschrijving(in.getOmschrijving());
        uit.setSoortEntiteit(in.getSoortEntiteit());
        if (in.getTijdstipAfgehandeld() != null) {
            uit.setTijdstipAfgehandeld(in.getTijdstipAfgehandeld());
        }
        if (in.getTijdstipCreatie() != null) {
            uit.setTijdstipCreatie(in.getTijdstipCreatie());
        }
        uit.setTitel(in.getTitel());

        uit.setWijzigingTaaks(in.getWijzigingTaaks().stream().map(new JsonToDtoWijzigingTaakMapper(identificatieClient)).collect(Collectors.toList()));

        return uit;
    }

}
