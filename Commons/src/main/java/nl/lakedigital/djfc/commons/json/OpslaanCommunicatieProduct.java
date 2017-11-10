package nl.lakedigital.djfc.commons.json;

public class OpslaanCommunicatieProduct {
    public enum SoortCommunicatieProduct {EMAIL, BRIEF}

    private Long id;
    private SoortCommunicatieProduct soortCommunicatieProduct;
    private String soortentiteit;
    private Long parentid;
    private String onderwerp;
    private String tekst;
    private Long medewerker;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SoortCommunicatieProduct getSoortCommunicatieProduct() {
        return soortCommunicatieProduct;
    }

    public void setSoortCommunicatieProduct(SoortCommunicatieProduct soortCommunicatieProduct) {
        this.soortCommunicatieProduct = soortCommunicatieProduct;
    }

    public String getSoortentiteit() {
        return soortentiteit;
    }

    public void setSoortentiteit(String soortentiteit) {
        this.soortentiteit = soortentiteit;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getOnderwerp() {
        return onderwerp;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Long getMedewerker() {
        return medewerker;
    }

    public void setMedewerker(Long medewerker) {
        this.medewerker = medewerker;
    }
}
