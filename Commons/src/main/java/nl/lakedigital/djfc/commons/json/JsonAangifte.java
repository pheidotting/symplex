package nl.lakedigital.djfc.commons.json;

import java.util.ArrayList;
import java.util.List;

public class JsonAangifte {
    private Long id;
    private int jaar;
    private String datumAfgerond;
    private String uitstelTot;
    private String afgerondDoor;
    private String relatie;
    private List<JsonBijlage> bijlages;
    private String idDiv;
    private String idDivLink;
    private String className;
    private List<JsonOpmerking> opmerkingen;
    private String soortEntiteit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getJaar() {
        return jaar;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
    }

    public String getDatumAfgerond() {
        return datumAfgerond;
    }

    public void setDatumAfgerond(String datumAfgerond) {
        this.datumAfgerond = datumAfgerond;
    }

    public String getUitstelTot() {
        return uitstelTot;
    }

    public void setUitstelTot(String uitstelTot) {
        this.uitstelTot = uitstelTot;
    }

    public String getAfgerondDoor() {
        return afgerondDoor;
    }

    public void setAfgerondDoor(String afgerondDoor) {
        this.afgerondDoor = afgerondDoor;
    }

    public String getRelatie() {
        return relatie;
    }

    public void setRelatie(String relatie) {
        this.relatie = relatie;
    }

    public List<JsonBijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = new ArrayList<>();
        }
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

    public String getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(String soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public List<JsonOpmerking> getOpmerkingen() {
        return opmerkingen;
    }

    public void setOpmerkingen(List<JsonOpmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }
}
