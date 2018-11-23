package nl.dias.web.medewerker.mappers;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.response.Taak;
import nl.lakedigital.djfc.commons.json.Identificatie;

import java.util.function.Function;

public class JsonTaakNaarOpdrachtMapper implements Function<Taak, nl.lakedigital.djfc.commons.domain.Taak> {
    private IdentificatieClient identificatieClient;
    private Long entiteitId;
    private SoortEntiteit soortEntiteit;

    public JsonTaakNaarOpdrachtMapper(IdentificatieClient identificatieClient, Long entiteitId, SoortEntiteit soortEntiteit) {
        this.identificatieClient = identificatieClient;
        this.entiteitId = entiteitId;
        this.soortEntiteit = soortEntiteit;
    }

    @Override
    public nl.lakedigital.djfc.commons.domain.Taak apply(Taak taakIn) {
        nl.lakedigital.djfc.commons.domain.Taak taak = new nl.lakedigital.djfc.commons.domain.Taak();
        taak.setTitel(taakIn.getTitel());
        taak.setOmschrijving(taakIn.getOmschrijving());
        taak.setDeadline(taakIn.getDeadline());
        if (taakIn.getToegewezenAan() != null && !"".equals(taakIn.getToegewezenAan())) {
            Identificatie identificatieMedewerker = identificatieClient.zoekIdentificatieCode(taakIn.getToegewezenAan());
            taak.setToegewezenAan(identificatieMedewerker.getEntiteitId());
        }
        taak.setSoortEntiteit(soortEntiteit.name());
        taak.setEntiteitId(entiteitId);

        return taak;
    }
}
