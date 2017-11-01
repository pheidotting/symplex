package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class JsonHypotheek implements Comparable<JsonHypotheek> {
    private Long id;
    private Long relatie;
    private String hypotheekVorm;
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
    private List<String> errors;
    private List<JsonOpmerking> opmerkingen;
    private List<JsonBijlage> bijlages;
    private String leningNummer;
    private String bank;
    private Long gekoppeldeHypotheek;
    private String boxI;
    private String boxIII;
    private Long hypotheekPakket;
    // moet vanuit de schermkant
    private String idDiv;
    private String idDivLink;
    private String className;
    private String titel;
    private List<String> soortenHypotheek;
    private String hypotheekVormOpgemaakt;
    private String soortEntiteit;
    private JsonOpmerkingenModel opmerkingenModel;
    private String readOnly;
    private String notReadOnly;

    public String getHypotheekVormOpgemaakt() {
        return hypotheekVormOpgemaakt;
    }

    public void setHypotheekVormOpgemaakt(String hypotheekVormOpgemaakt) {
        this.hypotheekVormOpgemaakt = hypotheekVormOpgemaakt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRelatie() {
        return relatie;
    }

    public void setRelatie(Long relatie) {
        this.relatie = relatie;
    }

    public String getHypotheekVorm() {
        return hypotheekVorm;
    }

    public void setHypotheekVorm(String hypotheekVorm) {
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

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<JsonOpmerking> getOpmerkingen() {
        return opmerkingen;
    }

    public void setOpmerkingen(List<JsonOpmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public List<JsonBijlage> getBijlages() {
        return bijlages;
    }

    public void setBijlages(List<JsonBijlage> bijlages) {
        this.bijlages = bijlages;
    }

    public String getIdDiv() {
        return idDiv;
    }

    public void setIdDiv(String idDiv) {
        this.idDiv = idDiv;
    }

    public String getIdDivLink() {
        return idDivLink;
    }

    public void setIdDivLink(String idDivLink) {
        this.idDivLink = idDivLink;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public List<String> getSoortenHypotheek() {
        return soortenHypotheek;
    }

    public void setSoortenHypotheek(List<String> soortenHypotheek) {
        this.soortenHypotheek = soortenHypotheek;
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

    public String getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(String soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public JsonOpmerkingenModel getOpmerkingenModel() {
        return opmerkingenModel;
    }

    public void setOpmerkingenModel(JsonOpmerkingenModel opmerkingenModel) {
        this.opmerkingenModel = opmerkingenModel;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }

    public String getNotReadOnly() {
        return notReadOnly;
    }

    public void setNotReadOnly(String notReadOnly) {
        this.notReadOnly = notReadOnly;
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof JsonHypotheek)) {
            return false;
        }
        JsonHypotheek rhs = (JsonHypotheek) object;
        return new EqualsBuilder().append(this.taxatieDatum, rhs.taxatieDatum).append(this.koopsom, rhs.koopsom).append(this.relatie, rhs.relatie)
                .append(this.waardeVoorVerbouwing, rhs.waardeVoorVerbouwing).append(this.id, rhs.id).append(this.vrijeVerkoopWaarde, rhs.vrijeVerkoopWaarde)
                .append(this.duurRenteVastePeriode, rhs.duurRenteVastePeriode).append(this.leningNummer, rhs.leningNummer).append(this.bank, rhs.bank).append(this.ingangsDatum, rhs.ingangsDatum)
                .append(this.marktWaarde, rhs.marktWaarde).append(this.waardeNaVerbouwing, rhs.waardeNaVerbouwing).append(this.omschrijving, rhs.omschrijving).append(this.eindDatum, rhs.eindDatum)
                .append(this.hypotheekBedrag, rhs.hypotheekBedrag).append(this.rente, rhs.rente).append(this.hypotheekVorm, rhs.hypotheekVorm).append(this.duur, rhs.duur)
                .append(this.onderpand, rhs.onderpand).append(this.ingangsDatumRenteVastePeriode, rhs.ingangsDatumRenteVastePeriode).append(this.wozWaarde, rhs.wozWaarde)
                .append(this.eindDatumRenteVastePeriode, rhs.eindDatumRenteVastePeriode).isEquals();
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.taxatieDatum).append(this.koopsom).append(this.relatie).append(this.waardeVoorVerbouwing).append(this.id).append(this.vrijeVerkoopWaarde)
                .append(this.duurRenteVastePeriode).append(this.leningNummer).append(this.bank).append(this.ingangsDatum).append(this.marktWaarde).append(this.waardeNaVerbouwing)
                .append(this.omschrijving).append(this.eindDatum).append(this.hypotheekBedrag).append(this.rente).append(this.hypotheekVorm).append(this.duur).append(this.onderpand)
                .append(this.ingangsDatumRenteVastePeriode).append(this.wozWaarde).append(this.eindDatumRenteVastePeriode).toHashCode();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("\ntaxatieDatum", this.taxatieDatum).append("koopsom", this.koopsom).append("relatie", this.relatie)
                .append("waardeVoorVerbouwing", this.waardeVoorVerbouwing).append("id", this.id).append("vrijeVerkoopWaarde", this.vrijeVerkoopWaarde)
                .append("duurRenteVastePeriode", this.duurRenteVastePeriode).append("leningNummer", this.leningNummer).append("bijlages", this.bijlages).append("bank", this.bank)
                .append("ingangsDatum", this.ingangsDatum).append("marktWaarde", this.marktWaarde).append("waardeNaVerbouwing", this.waardeNaVerbouwing).append("omschrijving", this.omschrijving)
                .append("eindDatum", this.eindDatum).append("hypotheekBedrag", this.hypotheekBedrag).append("rente", this.rente).append("hypotheekVorm", this.hypotheekVorm).append("duur", this.duur)
                .append("onderpand", this.onderpand).append("ingangsDatumRenteVastePeriode", this.ingangsDatumRenteVastePeriode).append("wozWaarde", this.wozWaarde)
                .append("eindDatumRenteVastePeriode", this.eindDatumRenteVastePeriode).toString();
    }

    @Override
    public int compareTo(JsonHypotheek o) {
        return leningNummer.compareTo(o.leningNummer);
    }

}
