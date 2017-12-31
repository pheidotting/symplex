package nl.dias.web.medewerker.mappers;

import nl.dias.web.mapper.HypotheekMapper;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonHypotheek;
import nl.lakedigital.djfc.domain.response.Hypotheek;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HypotheekNaarJsonHypotheekMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(HypotheekMapper.class);
    private static final String DATUM_FORMAAT = "dd-MM-yyyy";

    private IdentificatieClient identificatieClient;

    public HypotheekNaarJsonHypotheekMapper(IdentificatieClient identificatieClient) {
        this.identificatieClient = identificatieClient;
    }

    public JsonHypotheek mapNaarJson(Hypotheek hypotheek) {
        JsonHypotheek jsonHypotheek = new JsonHypotheek();

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(hypotheek.getIdentificatie());
        if (identificatie != null && identificatie.getEntiteitId() != null) {
            jsonHypotheek.setId(identificatie.getEntiteitId());
        }
        jsonHypotheek.setDuur(hypotheek.getDuur());
        jsonHypotheek.setDuurRenteVastePeriode(hypotheek.getDuurRenteVastePeriode());
        //        jsonHypotheek.setHypotheekPakket(hypotheek.getHypotheekPakket().getId());
        if (hypotheek.getEindDatum() != null) {
            jsonHypotheek.setEindDatum(hypotheek.getEindDatum());
        }
        if (hypotheek.getEindDatumRenteVastePeriode() != null) {
            jsonHypotheek.setEindDatumRenteVastePeriode(hypotheek.getEindDatumRenteVastePeriode());
        }
        if (hypotheek.getHypotheekBedrag() != null) {
            jsonHypotheek.setHypotheekBedrag(hypotheek.getHypotheekBedrag());
        }
        if (hypotheek.getHypotheekVorm() != null) {
            jsonHypotheek.setHypotheekVorm(hypotheek.getHypotheekVorm().toString());
        }
        if (hypotheek.getIngangsDatum() != null) {
            jsonHypotheek.setIngangsDatum(hypotheek.getIngangsDatum());
        }
        if (hypotheek.getIngangsDatumRenteVastePeriode() != null) {
            jsonHypotheek.setIngangsDatumRenteVastePeriode(hypotheek.getIngangsDatumRenteVastePeriode());
        }
        if (hypotheek.getKoopsom() != null) {
            jsonHypotheek.setKoopsom(hypotheek.getKoopsom());
        }
        if (hypotheek.getMarktWaarde() != null) {
            jsonHypotheek.setMarktWaarde(hypotheek.getMarktWaarde());
        }
        jsonHypotheek.setOmschrijving(hypotheek.getOmschrijving());
        if (hypotheek.getOnderpand() != null) {
            jsonHypotheek.setOnderpand(hypotheek.getOnderpand());
        }
        if (hypotheek.getRente() != null) {
            jsonHypotheek.setRente(hypotheek.getRente());
        }
        if (hypotheek.getTaxatieDatum() != null) {
            jsonHypotheek.setTaxatieDatum(hypotheek.getTaxatieDatum());
        }
        if (hypotheek.getVrijeVerkoopWaarde() != null) {
            jsonHypotheek.setVrijeVerkoopWaarde(hypotheek.getVrijeVerkoopWaarde());
        }
        if (hypotheek.getWaardeNaVerbouwing() != null) {
            jsonHypotheek.setWaardeNaVerbouwing(hypotheek.getWaardeNaVerbouwing());
        }
        if (hypotheek.getWaardeVoorVerbouwing() != null) {
            jsonHypotheek.setWaardeVoorVerbouwing(hypotheek.getWaardeVoorVerbouwing());
        }
        if (hypotheek.getWozWaarde() != null) {
            jsonHypotheek.setWozWaarde(hypotheek.getWozWaarde());
        }

        jsonHypotheek.setLeningNummer(hypotheek.getLeningNummer());
        jsonHypotheek.setBank(hypotheek.getBank());
        if (hypotheek.getBoxI() != null) {
            jsonHypotheek.setBoxI(hypotheek.getBoxI());
        }
        if (hypotheek.getBoxIII() != null) {
            jsonHypotheek.setBoxIII(hypotheek.getBoxIII());
        }

        LOGGER.debug("In  : " + ReflectionToStringBuilder.toString(hypotheek));
        LOGGER.debug("Uit : " + ReflectionToStringBuilder.toString(jsonHypotheek));

        return jsonHypotheek;
    }

}
