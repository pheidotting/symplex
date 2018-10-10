package nl.heidotting.inloggen.service;

import nl.lakedigital.domein.Onderwerp;

import javax.persistence.*;

@Entity
@Table
@NamedQueries({@NamedQuery(name = "OnderwerpZoeken", query = "SELECT o FROM TestOnderwerp o where o.identificatie = :identificatie"), @NamedQuery(name = "OnderwerpAlles", query = "SELECT o FROM TestOnderwerp o")})
public class TestOnderwerp extends Onderwerp {
    private static final long serialVersionUID = -1084719759101017592L;

    @Column
    private String voorNaam;
    @Column
    private String achterNaam;

    public String getVoorNaam() {
        return voorNaam;
    }

    public void setVoorNaam(String voorNaam) {
        this.voorNaam = voorNaam;
    }

    public String getAchterNaam() {
        return achterNaam;
    }

    public void setAchterNaam(String achterNaam) {
        this.achterNaam = achterNaam;
    }
}