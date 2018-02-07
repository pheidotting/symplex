package nl.dias.web.mapper;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Hypotheek;
import nl.lakedigital.djfc.commons.json.JsonHypotheek;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class HypotheekMapper extends Mapper<Hypotheek, JsonHypotheek> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HypotheekMapper.class);
    private static final String DATUM_FORMAAT = "yyyy-MM-dd";

    @Override
    public Hypotheek mapVanJson(JsonHypotheek jsonHypotheek) {
        return mapVanJson(jsonHypotheek, new Hypotheek());
    }

    public Hypotheek mapVanJson(JsonHypotheek jsonHypotheek, Hypotheek hypotheek) {

        LocalDate eindDatum = null;
        if (jsonHypotheek.getEindDatum() != null && !"".equals(jsonHypotheek.getEindDatum())) {
            eindDatum = LocalDate.parse(jsonHypotheek.getEindDatum(), DateTimeFormat.forPattern(DATUM_FORMAAT));
        }
        LocalDate eindDatumRenteVastePeriode = null;
        if (jsonHypotheek.getEindDatumRenteVastePeriode() != null && !"".equals(jsonHypotheek.getEindDatumRenteVastePeriode())) {
            eindDatumRenteVastePeriode = LocalDate.parse(jsonHypotheek.getEindDatumRenteVastePeriode(), DateTimeFormat.forPattern(DATUM_FORMAAT));
        }
        LocalDate ingangsDatum = null;
        if (jsonHypotheek.getIngangsDatum() != null && !"".equals(jsonHypotheek.getIngangsDatum())) {
            ingangsDatum = LocalDate.parse(jsonHypotheek.getIngangsDatum(), DateTimeFormat.forPattern(DATUM_FORMAAT));
        }
        LocalDate ingangsDatumRenteVastePeriode = null;
        if (jsonHypotheek.getIngangsDatumRenteVastePeriode() != null && !"".equals(jsonHypotheek.getIngangsDatumRenteVastePeriode())) {
            ingangsDatumRenteVastePeriode = LocalDate.parse(jsonHypotheek.getIngangsDatumRenteVastePeriode(), DateTimeFormat.forPattern(DATUM_FORMAAT));
        }
        LocalDate taxatieDatum = null;
        if (jsonHypotheek.getTaxatieDatum() != null && !"".equals(jsonHypotheek.getTaxatieDatum())) {
            taxatieDatum = LocalDate.parse(jsonHypotheek.getTaxatieDatum(), DateTimeFormat.forPattern(DATUM_FORMAAT));
        }

        // Hypotheek hypotheek = new Hypotheek();

        hypotheek.setId(jsonHypotheek.getId());
        hypotheek.setDuur(jsonHypotheek.getDuur());
        hypotheek.setDuurRenteVastePeriode(jsonHypotheek.getDuurRenteVastePeriode());
        hypotheek.setEindDatum(eindDatum);
        hypotheek.setEindDatumRenteVastePeriode(eindDatumRenteVastePeriode);
        if (StringUtils.isNotBlank(jsonHypotheek.getHypotheekBedrag())) {
            hypotheek.setHypotheekBedrag(new Bedrag(jsonHypotheek.getHypotheekBedrag()));
        }
        hypotheek.setIngangsDatum(ingangsDatum);
        hypotheek.setIngangsDatumRenteVastePeriode(ingangsDatumRenteVastePeriode);
        if (StringUtils.isNotBlank(jsonHypotheek.getKoopsom())) {
            hypotheek.setKoopsom(new Bedrag(jsonHypotheek.getKoopsom()));
        }
        if (StringUtils.isNotBlank(jsonHypotheek.getMarktWaarde())) {
            hypotheek.setMarktWaarde(new Bedrag(jsonHypotheek.getMarktWaarde()));
        }
        hypotheek.setOmschrijving(jsonHypotheek.getOmschrijving());
        if (StringUtils.isNotBlank(jsonHypotheek.getOnderpand())) {
            hypotheek.setOnderpand(jsonHypotheek.getOnderpand());
        }
        if (jsonHypotheek.getRente() != null) {
            hypotheek.setRente(new BigDecimal(jsonHypotheek.getRente()));
        }
        hypotheek.setTaxatieDatum(taxatieDatum);
        if (StringUtils.isNotBlank(jsonHypotheek.getVrijeVerkoopWaarde())) {
            hypotheek.setVrijeVerkoopWaarde(new Bedrag(jsonHypotheek.getVrijeVerkoopWaarde()));
        }
        if (StringUtils.isNotBlank(jsonHypotheek.getWaardeNaVerbouwing())) {
            hypotheek.setWaardeNaVerbouwing(new Bedrag(jsonHypotheek.getWaardeNaVerbouwing()));
        }
        if (StringUtils.isNotBlank(jsonHypotheek.getWaardeVoorVerbouwing())) {
            hypotheek.setWaardeVoorVerbouwing(new Bedrag(jsonHypotheek.getWaardeVoorVerbouwing()));
        }
        if (StringUtils.isNotBlank(jsonHypotheek.getWozWaarde())) {
            hypotheek.setWozWaarde(new Bedrag(jsonHypotheek.getWozWaarde()));
        }
        hypotheek.setLeningNummer(jsonHypotheek.getLeningNummer());
        hypotheek.setBank(jsonHypotheek.getBank());
        if (StringUtils.isNotBlank(jsonHypotheek.getBoxI())) {
            hypotheek.setBoxI(new Bedrag(jsonHypotheek.getBoxI()));
        }
        if (StringUtils.isNotBlank(jsonHypotheek.getBoxIII())) {
            hypotheek.setBoxIII(new Bedrag(jsonHypotheek.getBoxIII()));
        }

        return hypotheek;
    }

    @Override
    public JsonHypotheek mapNaarJson(Hypotheek hypotheek) {
        JsonHypotheek jsonHypotheek = new JsonHypotheek();

        jsonHypotheek.setId(hypotheek.getId());
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
            jsonHypotheek.setHypotheekVorm(hypotheek.getHypotheekVorm().getId().toString());
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
        jsonHypotheek.setRelatie(hypotheek.getRelatie());
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

        LOGGER.debug("In  : " + ReflectionToStringBuilder.toString(hypotheek));
        LOGGER.debug("Uit : " + ReflectionToStringBuilder.toString(jsonHypotheek));

        return jsonHypotheek;
    }
}
