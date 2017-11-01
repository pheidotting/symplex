package nl.dias.web.mapper;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonGroepBijlages;
import nl.lakedigital.djfc.domain.response.GroepBijlages;

import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonToDtoGroepBijlageMapper implements Function<JsonGroepBijlages, GroepBijlages> {
    private IdentificatieClient identificatieClient;

    public JsonToDtoGroepBijlageMapper(IdentificatieClient identificatieClient) {
        this.identificatieClient = identificatieClient;
    }

    @Override
    public GroepBijlages apply(JsonGroepBijlages jsonGroepBijlages) {
        GroepBijlages groepBijlages = new GroepBijlages();

        Identificatie groepBijlageIdentificatie = identificatieClient.zoekIdentificatie("GROEPBIJLAGE", jsonGroepBijlages.getId());
        //zou niet voor mogen komen, maar GROEPBIJLAGE zit nog niet in de identificatie
        if (groepBijlageIdentificatie != null) {
            groepBijlages.setIdentificatie(groepBijlageIdentificatie.getIdentificatie());
        }

        groepBijlages.setNaam(jsonGroepBijlages.getNaam());
        groepBijlages.setBijlages(jsonGroepBijlages.getBijlages().stream().map(new JsonToDtoBijlageMapper(identificatieClient)).collect(Collectors.toList()));

        return groepBijlages;
    }
}
