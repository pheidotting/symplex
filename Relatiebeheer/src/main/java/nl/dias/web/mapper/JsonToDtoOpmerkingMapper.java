package nl.dias.web.mapper;

import nl.dias.domein.Medewerker;
import nl.dias.service.GebruikerService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonOpmerking;
import nl.lakedigital.djfc.domain.response.Opmerking;

import java.util.function.Function;

public class JsonToDtoOpmerkingMapper implements Function<JsonOpmerking, Opmerking> {
    private IdentificatieClient identificatieClient;
    private GebruikerService gebruikerService;

    public JsonToDtoOpmerkingMapper(IdentificatieClient identificatieClient, GebruikerService gebruikerService) {
        this.identificatieClient = identificatieClient;
        this.gebruikerService = gebruikerService;
    }

    @Override
    public Opmerking apply(JsonOpmerking jsonOpmerking) {
        Opmerking opmerking = new Opmerking();

        Identificatie opmerkingIdentificatie = identificatieClient.zoekIdentificatie("OPMERKING", jsonOpmerking.getId());

        opmerking.setIdentificatie(opmerkingIdentificatie.getIdentificatie());
        opmerking.setTijd(jsonOpmerking.getTijd());
        opmerking.setOpmerking(jsonOpmerking.getOpmerking());
        opmerking.setMedewerkerId(jsonOpmerking.getMedewerkerId());

        Medewerker medewerker = (Medewerker) gebruikerService.lees(jsonOpmerking.getMedewerkerId());
        StringBuilder sb = new StringBuilder();
        sb.append(medewerker.getVoornaam());
        sb.append(" ");
        if (medewerker.getTussenvoegsel() != null) {
            sb.append(medewerker.getTussenvoegsel());
            sb.append(" ");
        }
        sb.append(medewerker.getAchternaam());
        opmerking.setMedewerker(sb.toString());

        return opmerking;
    }

}
