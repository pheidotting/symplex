package nl.dias.domein;

public enum BurgerlijkeStaat {
    O("Ongehuwd"), G("Gehuwd GVG"), H("Gehuwd HV"), T("Thuiswonend"), S("Samenwonend"), C("Samenlevingscontract"),W("Weduwe/Weduwnaar");

    private String omschrijving;

    private BurgerlijkeStaat(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }


}
