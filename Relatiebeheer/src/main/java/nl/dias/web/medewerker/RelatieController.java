package nl.dias.web.medewerker;

import nl.dias.domein.Schade;
import nl.dias.domein.polis.Polis;
import nl.dias.service.GebruikerService;
import nl.dias.service.PolisService;
import nl.dias.service.RelatieService;
import nl.dias.service.SchadeService;
import nl.dias.web.mapper.*;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.*;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.commons.json.JsonTelefonieBestand;
import nl.lakedigital.djfc.domain.response.Relatie;
import nl.lakedigital.djfc.domain.response.Telefoongesprek;
import nl.lakedigital.djfc.domain.response.TelefoonnummerMetGesprekken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
@RequestMapping("relatie")
public class RelatieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RelatieController.class);

    @Inject
    private AdresClient adresClient;
    @Inject
    private BijlageClient bijlageClient;
    @Inject
    private GroepBijlagesClient groepBijlagesClient;
    @Inject
    private OpmerkingClient opmerkingClient;
    @Inject
    private RekeningClient rekeningClient;
    @Inject
    private TelefoonnummerClient telefoonnummerClient;
    @Inject
    private TelefonieBestandClient telefonieBestandClient;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private RelatieService relatieService;
    @Inject
    private PolisClient polisClient;
    @Inject
    private PolisService polisService;
    @Inject
    private PolisMapper polisMapper;
    @Inject
    private SchadeService schadeService;
    @Inject
    private SchadeMapper schadeMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Relatie leesRelatie(@PathVariable("id") String identificatie) {
        LOGGER.debug("Ophalen Relatie met ID {}", identificatie);

        nl.dias.domein.Relatie relatieDomain = relatieService.zoekRelatie(identificatie);

        Relatie relatie = new DomainToDtoRelatieMapper().apply(relatieDomain);
        relatie.setIdentificatie(identificatieClient.zoekIdentificatie("RELATIE", relatieDomain.getId()).getIdentificatie());

        relatie.setAdressen(adresClient.lijst("RELATIE", relatieDomain.getId()).stream().map(new JsonToDtoAdresMapper(identificatieClient)).collect(Collectors.toList()));
        relatie.setBijlages(bijlageClient.lijst("RELATIE", relatieDomain.getId()).stream().map(new JsonToDtoBijlageMapper(identificatieClient)).collect(Collectors.toList()));
        relatie.setGroepBijlages(groepBijlagesClient.lijstGroepen("RELATIE", relatieDomain.getId()).stream().map(new JsonToDtoGroepBijlageMapper(identificatieClient)).collect(Collectors.toList()));
        relatie.setRekeningNummers(rekeningClient.lijst("RELATIE", relatieDomain.getId()).stream().map(new JsonToDtoRekeningNummerMapper(identificatieClient)).collect(Collectors.toList()));
        relatie.setTelefoonnummers(telefoonnummerClient.lijst("RELATIE", relatieDomain.getId()).stream().map(new JsonToDtoTelefoonnummerMapper(identificatieClient)).collect(Collectors.toList()));
        relatie.setOpmerkingen(opmerkingClient.lijst("RELATIE", relatieDomain.getId()).stream().map(new JsonToDtoOpmerkingMapper(identificatieClient, gebruikerService)).collect(Collectors.toList()));

        List<Polis> polissen = polisService.allePolissenBijRelatie(relatieDomain.getId());
        List<JsonPolis> jsonPolissen = polisMapper.mapAllNaarJson(polissen);

        List<Schade> schades = schadeService.alleSchadesBijRelatie(relatieDomain.getId());
        LOGGER.debug("Schade gevonden :");
        schades.stream().forEach(ss -> LOGGER.debug("Schade : {} - {} - {}", ss.getId(), ss.getSchadeNummerMaatschappij(), ss.getOmschrijving()));
        List<JsonPolis> jsonPolisList = jsonPolissen.stream().map(new Function<JsonPolis, JsonPolis>() {
            @Override
            public JsonPolis apply(JsonPolis jsonPolis) {
                List<Schade> schadesBijPolis = schades.stream().filter(new Predicate<Schade>() {
                    @Override
                    public boolean test(Schade schade) {
                        return schade.getPolis() == jsonPolis.getId();
                    }
                }).collect(Collectors.toList());

                jsonPolis.setSchades(schadeMapper.mapAllNaarJson(schadesBijPolis));

                return jsonPolis;
            }
        }).collect(Collectors.toList());

        relatie.setPolissen(jsonPolisList.stream().map(new JsonToDtoPolisMapper(bijlageClient, groepBijlagesClient, opmerkingClient, identificatieClient, gebruikerService)).collect(Collectors.toList()));
        //        relatie.setPolissen(polisClient.lijst(String.valueOf(relatieDomain.getId())).stream().map(new JsonToDtoPolisMapper(bijlageClient, groepBijlagesClient, opmerkingClient, identificatieClient, gebruikerService)).collect(Collectors.toList()));

        List<String> telefoonnummers = relatie.getTelefoonnummers().stream().map(telefoonnummer -> telefoonnummer.getTelefoonnummer()).collect(Collectors.toList());

        if (!telefoonnummers.isEmpty()) {
            Map<String, List<JsonTelefonieBestand>> telefonieResult = telefonieBestandClient.getRecordingsAndVoicemails(telefoonnummers);
            for (String nummer : telefonieResult.keySet()) {
                TelefoonnummerMetGesprekken telefoonnummerMetGesprekken = new TelefoonnummerMetGesprekken();
                telefoonnummerMetGesprekken.setTelefoonnummer(nummer);
                telefoonnummerMetGesprekken.setTelefoongesprekken(telefonieResult.get(nummer).stream().map(s -> {
                    Telefoongesprek telefoongesprek = new Telefoongesprek();
                    telefoongesprek.setBestandsnaam(s.getBestandsnaam());
                    telefoongesprek.setTijdstip(s.getTijdstip());
                    telefoongesprek.setTelefoonnummer(s.getTelefoonnummer());

                    return telefoongesprek;
                }).collect(Collectors.toList()));

                relatie.getTelefoonnummerMetGesprekkens().add(telefoonnummerMetGesprekken);
            }
        }

        return relatie;
    }
}
