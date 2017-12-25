package nl.dias.web.medewerker;

import nl.dias.domein.Hypotheek;
import nl.dias.domein.Schade;
import nl.dias.domein.polis.Polis;
import nl.dias.service.*;
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
    @Inject
    private HypotheekService hypotheekService;

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Relatie leesRelatie(@PathVariable("id") String identificatie) {
        LOGGER.debug("Ophalen Relatie met ID {}", identificatie);

        Relatie relatie = null;
        //        try{
        nl.dias.domein.Relatie relatieDomain = relatieService.zoekRelatie(identificatie);

        relatie = new DomainToDtoRelatieMapper().apply(relatieDomain);
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
        List<JsonPolis> jsonPolisList = jsonPolissen.stream().map(jsonPolis -> {
            List<Schade> schadesBijPolis = schades.stream().filter(schade -> schade.getPolis() == jsonPolis.getId()).collect(Collectors.toList());

            jsonPolis.setSchades(schadeMapper.mapAllNaarJson(schadesBijPolis));

            return jsonPolis;
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
        //        }catch (Exception e){
        //            LOGGER.error("{}",e);
        //            throw  e;
        //        }

        List<Hypotheek> hypotheken = hypotheekService.allesVanRelatie(relatieDomain.getId());
        relatie.setHypotheken(hypotheken.stream().map(new Function<Hypotheek, nl.lakedigital.djfc.domain.response.Hypotheek>() {
            @Override
            public nl.lakedigital.djfc.domain.response.Hypotheek apply(Hypotheek hypotheek) {
                final String DATUM_FORMAAT = "dd-MM-yyyy";

                nl.lakedigital.djfc.domain.response.Hypotheek jsonHypotheek = new nl.lakedigital.djfc.domain.response.Hypotheek();

                jsonHypotheek.setIdentificatie(identificatieClient.zoekIdentificatie("HYPOTHEEK", hypotheek.getId()).getIdentificatie());
                jsonHypotheek.setDuur(hypotheek.getDuur());
                jsonHypotheek.setDuurRenteVastePeriode(hypotheek.getDuurRenteVastePeriode());
                jsonHypotheek.setHypotheekPakket(hypotheek.getHypotheekPakket().getId());
                if (hypotheek.getEindDatum() != null) {
                    jsonHypotheek.setEindDatum(hypotheek.getEindDatum().toString(DATUM_FORMAAT));
                }
                if (hypotheek.getEindDatumRenteVastePeriode() != null) {
                    jsonHypotheek.setEindDatumRenteVastePeriode(hypotheek.getEindDatumRenteVastePeriode().toString(DATUM_FORMAAT));
                }
                if (hypotheek.getHypotheekBedrag() != null) {
                    jsonHypotheek.setHypotheekBedrag(hypotheek.getHypotheekBedrag().getBedrag().toString());
                }
                if (hypotheek.getHypotheekVorm() != null) {
                    jsonHypotheek.setHypotheekVorm(hypotheek.getHypotheekVorm().getId());
                }
                if (hypotheek.getIngangsDatum() != null) {
                    jsonHypotheek.setIngangsDatum(hypotheek.getIngangsDatum().toString(DATUM_FORMAAT));
                }
                if (hypotheek.getIngangsDatumRenteVastePeriode() != null) {
                    jsonHypotheek.setIngangsDatumRenteVastePeriode(hypotheek.getIngangsDatumRenteVastePeriode().toString(DATUM_FORMAAT));
                }
                if (hypotheek.getKoopsom() != null) {
                    jsonHypotheek.setKoopsom(hypotheek.getKoopsom().getBedrag().toString());
                }
                if (hypotheek.getMarktWaarde() != null) {
                    jsonHypotheek.setMarktWaarde(hypotheek.getMarktWaarde().getBedrag().toString());
                }
                jsonHypotheek.setOmschrijving(hypotheek.getOmschrijving());
                if (hypotheek.getOnderpand() != null) {
                    jsonHypotheek.setOnderpand(hypotheek.getOnderpand());
                }
                if (hypotheek.getRente() != null) {
                    jsonHypotheek.setRente(hypotheek.getRente().toString());
                }
                if (hypotheek.getTaxatieDatum() != null) {
                    jsonHypotheek.setTaxatieDatum(hypotheek.getTaxatieDatum().toString(DATUM_FORMAAT));
                }
                if (hypotheek.getVrijeVerkoopWaarde() != null) {
                    jsonHypotheek.setVrijeVerkoopWaarde(hypotheek.getVrijeVerkoopWaarde().getBedrag().toString());
                }
                if (hypotheek.getWaardeNaVerbouwing() != null) {
                    jsonHypotheek.setWaardeNaVerbouwing(hypotheek.getWaardeNaVerbouwing().getBedrag().toString());
                }
                if (hypotheek.getWaardeVoorVerbouwing() != null) {
                    jsonHypotheek.setWaardeVoorVerbouwing(hypotheek.getWaardeVoorVerbouwing().getBedrag().toString());
                }
                if (hypotheek.getWozWaarde() != null) {
                    jsonHypotheek.setWozWaarde(hypotheek.getWozWaarde().getBedrag().toString());
                }

                jsonHypotheek.setLeningNummer(hypotheek.getLeningNummer());
                jsonHypotheek.setBank(hypotheek.getBank());
                if (hypotheek.getBoxI() != null) {
                    jsonHypotheek.setBoxI(hypotheek.getBoxI().getBedrag().toString());
                }
                if (hypotheek.getBoxIII() != null) {
                    jsonHypotheek.setBoxIII(hypotheek.getBoxIII().getBedrag().toString());
                }

                jsonHypotheek.setOpmerkingen(opmerkingClient.lijst("HYPOTHEEK", hypotheek.getId()).stream().map(new JsonToDtoOpmerkingMapper(identificatieClient, gebruikerService)).collect(Collectors.toList()));


                return jsonHypotheek;
            }
        }).collect(Collectors.toList()));

        return relatie;
    }
}
