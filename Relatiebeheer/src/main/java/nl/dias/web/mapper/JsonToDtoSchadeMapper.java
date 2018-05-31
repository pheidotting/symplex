package nl.dias.web.mapper;

import nl.dias.service.GebruikerService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.BijlageClient;
import nl.lakedigital.djfc.client.oga.GroepBijlagesClient;
import nl.lakedigital.djfc.client.oga.OpmerkingClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.domain.response.Schade;

import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonToDtoSchadeMapper implements Function<nl.dias.domein.Schade, Schade> {
    private BijlageClient bijlageClient;
    private GroepBijlagesClient groepBijlagesClient;
    private OpmerkingClient opmerkingClient;
    private IdentificatieClient identificatieClient;
    private GebruikerService gebruikerService;
    private String patternDatum = "yyyy-MM-dd";

    public JsonToDtoSchadeMapper(BijlageClient bijlageClient, GroepBijlagesClient groepBijlagesClient, OpmerkingClient opmerkingClient, IdentificatieClient identificatieClient, GebruikerService gebruikerService) {
        this.bijlageClient = bijlageClient;
        this.groepBijlagesClient = groepBijlagesClient;
        this.opmerkingClient = opmerkingClient;
        this.identificatieClient = identificatieClient;
        this.gebruikerService = gebruikerService;
    }

    @Override
    public Schade apply(nl.dias.domein.Schade domein) {
        Schade schade = new Schade();

        Identificatie identificatie = identificatieClient.zoekIdentificatie("SCHADE", domein.getId());

        schade.setIdentificatie(identificatie.getIdentificatie());
        if (domein.getDatumAfgehandeld() != null) {
            schade.setDatumAfgehandeld(domein.getDatumAfgehandeld().toString(patternDatum));
        }
        schade.setDatumMelding(domein.getDatumMelding().toString(patternDatum));
        schade.setDatumSchade(domein.getDatumSchade().toString(patternDatum));
        if (domein.getEigenRisico() != null) {
            schade.setEigenRisico(domein.getEigenRisico().getBedrag().toString());
        }
        schade.setLocatie(domein.getLocatie());
        schade.setOmschrijving(domein.getOmschrijving());
        schade.setSchadeNummerMaatschappij(domein.getSchadeNummerMaatschappij());
        schade.setSchadeNummerTussenPersoon(domein.getSchadeNummerTussenPersoon());
        if (domein.getSoortSchade() != null) {
            schade.setSoortSchade(domein.getSoortSchade().getOmschrijving());
        } else {
            schade.setSoortSchade(domein.getSoortSchadeOngedefinieerd());
        }
        if (domein.getStatusSchade() != null) {
            schade.setStatusSchade(domein.getStatusSchade().getStatus());
        }

        schade.setBijlages(bijlageClient.lijst("SCHADE", domein.getId()).stream().map(new JsonToDtoBijlageMapper(identificatieClient)).collect(Collectors.toList()));
        schade.setGroepBijlages(groepBijlagesClient.lijstGroepen("SCHADE", domein.getId()).stream().map(new JsonToDtoGroepBijlageMapper(identificatieClient)).collect(Collectors.toList()));
        schade.setOpmerkingen(opmerkingClient.lijst("SCHADE", domein.getId()).stream().map(new JsonToDtoOpmerkingMapper(identificatieClient, gebruikerService)).collect(Collectors.toList()));

        return schade;
    }
}
