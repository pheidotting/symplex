package nl.lakedigital.djfc.domain.response;

import nl.lakedigital.djfc.commons.json.Licentie;

import java.util.ArrayList;
import java.util.List;

public class Kantoor {
    private String identificatie;
    private String naam;
    private Long kvk;
    private String btwNummer;
    private String datumOprichting;
    private String rechtsvorm;
    private String soortKantoor;
    private String emailadres;
    private String afkorting;
    private Licentie licentie;
    private List<Medewerker> medewerkers;

    public Kantoor() {
    }

    public Kantoor(String identificatie, String naam, Long kvk, String btwNummer, String datumOprichting, String rechtsvorm, String soortKantoor, String emailadres, String afkorting, Licentie licentie) {
        this.identificatie = identificatie;
        this.naam = naam;
        this.kvk = kvk;
        this.btwNummer = btwNummer;
        this.datumOprichting = datumOprichting;
        this.rechtsvorm = rechtsvorm;
        this.soortKantoor = soortKantoor;
        this.emailadres = emailadres;
        this.afkorting = afkorting;
        this.licentie = licentie;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Long getKvk() {
        return kvk;
    }

    public void setKvk(Long kvk) {
        this.kvk = kvk;
    }

    public String getBtwNummer() {
        return btwNummer;
    }

    public void setBtwNummer(String btwNummer) {
        this.btwNummer = btwNummer;
    }

    public String getDatumOprichting() {
        return datumOprichting;
    }

    public void setDatumOprichting(String datumOprichting) {
        this.datumOprichting = datumOprichting;
    }

    public String getRechtsvorm() {
        return rechtsvorm;
    }

    public void setRechtsvorm(String rechtsvorm) {
        this.rechtsvorm = rechtsvorm;
    }

    public String getSoortKantoor() {
        return soortKantoor;
    }

    public void setSoortKantoor(String soortKantoor) {
        this.soortKantoor = soortKantoor;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public String getAfkorting() {
        return afkorting;
    }

    public void setAfkorting(String afkorting) {
        this.afkorting = afkorting;
    }

    public Licentie getLicentie() {
        return licentie;
    }

    public void setLicentie(Licentie licentie) {
        this.licentie = licentie;
    }

    public List<Medewerker> getMedewerkers() {
        if (medewerkers == null) {
            medewerkers = new ArrayList<>();
        }
        return medewerkers;
    }

    public void setMedewerkers(List<Medewerker> medewerkers) {
        this.medewerkers = medewerkers;
    }

    public void addMedewerker(Medewerker medewerker) {
        getMedewerkers().add(medewerker);
    }
}
