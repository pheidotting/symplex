package nl.dias.web.mapper;

import nl.dias.service.GebruikerService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.BijlageClient;
import nl.lakedigital.djfc.client.oga.GroepBijlagesClient;
import nl.lakedigital.djfc.client.oga.OpmerkingClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonSchade;
import nl.lakedigital.djfc.domain.response.Schade;

import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonToDtoSchadeMapper implements Function<JsonSchade, Schade> {
    private BijlageClient bijlageClient;
    private GroepBijlagesClient groepBijlagesClient;
    private OpmerkingClient opmerkingClient;
    private IdentificatieClient identificatieClient;
    private GebruikerService gebruikerService;

    public JsonToDtoSchadeMapper(BijlageClient bijlageClient, GroepBijlagesClient groepBijlagesClient, OpmerkingClient opmerkingClient, IdentificatieClient identificatieClient, GebruikerService gebruikerService) {
        this.bijlageClient = bijlageClient;
        this.groepBijlagesClient = groepBijlagesClient;
        this.opmerkingClient = opmerkingClient;
        this.identificatieClient = identificatieClient;
        this.gebruikerService = gebruikerService;
    }

    @Override
    public Schade apply(JsonSchade jsonSchade) {
        Schade schade = new Schade();

        Identificatie identificatie = identificatieClient.zoekIdentificatie("SCHADE", jsonSchade.getId());

        schade.setIdentificatie(identificatie.getIdentificatie());
        schade.setDatumAfgehandeld(jsonSchade.getDatumAfgehandeld());
        schade.setDatumTijdMelding(jsonSchade.getDatumTijdMelding());
        schade.setDatumTijdSchade(jsonSchade.getDatumTijdSchade());
        schade.setEigenRisico(jsonSchade.getEigenRisico());
        schade.setLocatie(jsonSchade.getLocatie());
        schade.setSchadeNummerMaatschappij(jsonSchade.getSchadeNummerMaatschappij());
        schade.setSchadeNummerTussenPersoon(jsonSchade.getSchadeNummerTussenPersoon());
        schade.setSoortSchade(jsonSchade.getSoortSchade());
        schade.setStatusSchade(jsonSchade.getStatusSchade());
        schade.setOmschrijving(jsonSchade.getOmschrijving());

        schade.setBijlages(bijlageClient.lijst("SCHADE", jsonSchade.getId()).stream().map(new JsonToDtoBijlageMapper(identificatieClient)).collect(Collectors.toList()));
        schade.setGroepBijlages(groepBijlagesClient.lijstGroepen("SCHADE", jsonSchade.getId()).stream().map(new JsonToDtoGroepBijlageMapper(identificatieClient)).collect(Collectors.toList()));
        schade.setOpmerkingen(opmerkingClient.lijst("SCHADE", jsonSchade.getId()).stream().map(new JsonToDtoOpmerkingMapper(identificatieClient, gebruikerService)).collect(Collectors.toList()));

        return schade;
    }
}
