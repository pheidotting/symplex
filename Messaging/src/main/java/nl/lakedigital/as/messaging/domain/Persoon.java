package nl.lakedigital.as.messaging.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class Persoon implements BedrijfOfPersoon {
    private String voornaam;
    private String tussenvoegsel;
    private String achternaam;

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Persoon)) {
            return false;
        }
        Persoon persoon = (Persoon) o;
        return Objects.equals(getVoornaam(), persoon.getVoornaam()) &&
                Objects.equals(getTussenvoegsel(), persoon.getTussenvoegsel()) &&
                Objects.equals(getAchternaam(), persoon.getAchternaam());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoornaam(), getTussenvoegsel(), getAchternaam());
    }
}
