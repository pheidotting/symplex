package nl.lakedigital.djfc.commons.domain.response;

import java.util.ArrayList;
import java.util.List;

public class Bedrijf {
    private Long id;
    private String identificatie;
    private String naam;
    private String kvk;
    private String rechtsvorm;
    private String email;
    private String internetadres;
    private String hoedanigheid;
    private String cAoVerplichtingen;
    private Long kantoor;

    private List<Adres> adressen = new ArrayList<>();
    private List<Bijlage> bijlages = new ArrayList<>();
    private List<GroepBijlages> groepBijlages = new ArrayList<>();
    private List<Telefoonnummer> telefoonnummers = new ArrayList<>();
    private List<Opmerking> opmerkingen = new ArrayList<>();
    private List<TelefoonnummerMetGesprekken> telefoonnummerMetGesprekkens = new ArrayList<>();
    private List<Pakket> pakketten;
    private List<ContactPersoon> contactPersoons = new ArrayList<>();
    private List<Taak> taken = new ArrayList<>();

    private Belastingzaken belastingzaken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getKvk() {
        return kvk;
    }

    public void setKvk(String kvk) {
        this.kvk = kvk;
    }

    public String getRechtsvorm() {
        return rechtsvorm;
    }

    public void setRechtsvorm(String rechtsvorm) {
        this.rechtsvorm = rechtsvorm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInternetadres() {
        return internetadres;
    }

    public void setInternetadres(String internetadres) {
        this.internetadres = internetadres;
    }

    public String getHoedanigheid() {
        return hoedanigheid;
    }

    public void setHoedanigheid(String hoedanigheid) {
        this.hoedanigheid = hoedanigheid;
    }

    public String getcAoVerplichtingen() {
        return cAoVerplichtingen;
    }

    public void setcAoVerplichtingen(String cAoVerplichtingen) {
        this.cAoVerplichtingen = cAoVerplichtingen;
    }

    public Long getKantoor() {
        return kantoor;
    }

    public void setKantoor(Long kantoor) {
        this.kantoor = kantoor;
    }

    public List<Adres> getAdressen() {
        return adressen;
    }

    public void setAdressen(List<Adres> adressen) {
        this.adressen = adressen;
    }

    public List<Bijlage> getBijlages() {
        return bijlages;
    }

    public void setBijlages(List<Bijlage> bijlages) {
        this.bijlages = bijlages;
    }

    public List<GroepBijlages> getGroepBijlages() {
        return groepBijlages;
    }

    public void setGroepBijlages(List<GroepBijlages> groepBijlages) {
        this.groepBijlages = groepBijlages;
    }

    public List<Telefoonnummer> getTelefoonnummers() {
        return telefoonnummers;
    }

    public void setTelefoonnummers(List<Telefoonnummer> telefoonnummers) {
        this.telefoonnummers = telefoonnummers;
    }

    public List<Opmerking> getOpmerkingen() {
        return opmerkingen;
    }

    public void setOpmerkingen(List<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public List<TelefoonnummerMetGesprekken> getTelefoonnummerMetGesprekkens() {
        return telefoonnummerMetGesprekkens;
    }

    public void setTelefoonnummerMetGesprekkens(List<TelefoonnummerMetGesprekken> telefoonnummerMetGesprekkens) {
        this.telefoonnummerMetGesprekkens = telefoonnummerMetGesprekkens;
    }

    public List<Pakket> getPakketten() {
        if (pakketten == null) {
            pakketten = new ArrayList<>();
        }
        return pakketten;
    }

    public void setPakketten(List<Pakket> pakketten) {
        this.pakketten = pakketten;
    }

    public List<ContactPersoon> getContactPersoons() {
        return contactPersoons;
    }

    public void setContactPersoons(List<ContactPersoon> contactPersoons) {
        this.contactPersoons = contactPersoons;
    }

    public Belastingzaken getBelastingzaken() {
        return belastingzaken;
    }

    public void setBelastingzaken(Belastingzaken belastingzaken) {
        this.belastingzaken = belastingzaken;
    }

    public List<Taak> getTaken() {
        return taken;
    }

    public void setTaken(List<Taak> taken) {
        this.taken = taken;
    }
}
