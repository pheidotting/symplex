package nl.lakedigital.djfc.commons.domain.response;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Hypotheek {
    private Long hypotheekVorm;
    private String omschrijving;
    private String hypotheekBedrag;
    private String rente;
    private String marktWaarde;
    private String onderpand;
    private String koopsom;
    private String vrijeVerkoopWaarde;
    private String taxatieDatum;
    private String wozWaarde;
    private String waardeVoorVerbouwing;
    private String waardeNaVerbouwing;
    private String ingangsDatum;
    private String eindDatum;
    private Long duur;
    private String ingangsDatumRenteVastePeriode;
    private String eindDatumRenteVastePeriode;
    private Long duurRenteVastePeriode;
    private List<Opmerking> opmerkingen;
    private List<Bijlage> bijlages;
    private String leningNummer;
    private String bank;
    private Long gekoppeldeHypotheek;
    private String boxI;
    private String boxIII;
    private Long hypotheekPakket;
    private String identificatie;
    private String soortEntiteit;
    private Long entiteitId;
    private String parentIdentificatie;
    private List<Taak> taken;

    public Long getHypotheekVorm() {
        return hypotheekVorm;
    }

    public void setHypotheekVorm(Long hypotheekVorm) {
        this.hypotheekVorm = hypotheekVorm;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getHypotheekBedrag() {
        return hypotheekBedrag;
    }

    public void setHypotheekBedrag(String hypotheekBedrag) {
        this.hypotheekBedrag = hypotheekBedrag;
    }

    public String getRente() {
        return rente;
    }

    public void setRente(String rente) {
        this.rente = rente;
    }

    public String getMarktWaarde() {
        return marktWaarde;
    }

    public void setMarktWaarde(String marktWaarde) {
        this.marktWaarde = marktWaarde;
    }

    public String getOnderpand() {
        return onderpand;
    }

    public void setOnderpand(String onderpand) {
        this.onderpand = onderpand;
    }

    public String getKoopsom() {
        return koopsom;
    }

    public void setKoopsom(String koopsom) {
        this.koopsom = koopsom;
    }

    public String getVrijeVerkoopWaarde() {
        return vrijeVerkoopWaarde;
    }

    public void setVrijeVerkoopWaarde(String vrijeVerkoopWaarde) {
        this.vrijeVerkoopWaarde = vrijeVerkoopWaarde;
    }

    public String getTaxatieDatum() {
        return taxatieDatum;
    }

    public void setTaxatieDatum(String taxatieDatum) {
        this.taxatieDatum = taxatieDatum;
    }

    public String getWozWaarde() {
        return wozWaarde;
    }

    public void setWozWaarde(String wozWaarde) {
        this.wozWaarde = wozWaarde;
    }

    public String getWaardeVoorVerbouwing() {
        return waardeVoorVerbouwing;
    }

    public void setWaardeVoorVerbouwing(String waardeVoorVerbouwing) {
        this.waardeVoorVerbouwing = waardeVoorVerbouwing;
    }

    public String getWaardeNaVerbouwing() {
        return waardeNaVerbouwing;
    }

    public void setWaardeNaVerbouwing(String waardeNaVerbouwing) {
        this.waardeNaVerbouwing = waardeNaVerbouwing;
    }

    public String getIngangsDatum() {
        return ingangsDatum;
    }

    public void setIngangsDatum(String ingangsDatum) {
        this.ingangsDatum = ingangsDatum;
    }

    public String getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(String eindDatum) {
        this.eindDatum = eindDatum;
    }

    public Long getDuur() {
        return duur;
    }

    public void setDuur(Long duur) {
        this.duur = duur;
    }

    public String getIngangsDatumRenteVastePeriode() {
        return ingangsDatumRenteVastePeriode;
    }

    public void setIngangsDatumRenteVastePeriode(String ingangsDatumRenteVastePeriode) {
        this.ingangsDatumRenteVastePeriode = ingangsDatumRenteVastePeriode;
    }

    public String getEindDatumRenteVastePeriode() {
        return eindDatumRenteVastePeriode;
    }

    public void setEindDatumRenteVastePeriode(String eindDatumRenteVastePeriode) {
        this.eindDatumRenteVastePeriode = eindDatumRenteVastePeriode;
    }

    public Long getDuurRenteVastePeriode() {
        return duurRenteVastePeriode;
    }

    public void setDuurRenteVastePeriode(Long duurRenteVastePeriode) {
        this.duurRenteVastePeriode = duurRenteVastePeriode;
    }

    public List<Opmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = new ArrayList<>();
        }
        return opmerkingen;
    }

    public void setOpmerkingen(List<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public List<Bijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = new ArrayList<>();
        }
        return bijlages;
    }

    public void setBijlages(List<Bijlage> bijlages) {
        this.bijlages = bijlages;
    }

    public String getLeningNummer() {
        return leningNummer;
    }

    public void setLeningNummer(String leningNummer) {
        this.leningNummer = leningNummer;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Long getGekoppeldeHypotheek() {
        return gekoppeldeHypotheek;
    }

    public void setGekoppeldeHypotheek(Long gekoppeldeHypotheek) {
        this.gekoppeldeHypotheek = gekoppeldeHypotheek;
    }

    public String getBoxI() {
        return boxI;
    }

    public void setBoxI(String boxI) {
        this.boxI = boxI;
    }

    public String getBoxIII() {
        return boxIII;
    }

    public void setBoxIII(String boxIII) {
        this.boxIII = boxIII;
    }

    public Long getHypotheekPakket() {
        return hypotheekPakket;
    }

    public void setHypotheekPakket(Long hypotheekPakket) {
        this.hypotheekPakket = hypotheekPakket;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(String soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }

    public String getParentIdentificatie() {
        return parentIdentificatie;
    }

    public void setParentIdentificatie(String parentIdentificatie) {
        this.parentIdentificatie = parentIdentificatie;
    }

    public List<Taak> getTaken() {
        if (taken == null) {
            taken = newArrayList();
        }
        return taken;
    }

    public void setTaken(List<Taak> taken) {
        this.taken = taken;
    }
}
