package nl.lakedigital.djfc.domain.response;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class TelefoonnummerMetGesprekken {
    private String telefoonnummer;
    private List<Telefoongesprek> telefoongesprekken = newArrayList();

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public List<Telefoongesprek> getTelefoongesprekken() {
        return telefoongesprekken;
    }

    public void setTelefoongesprekken(List<Telefoongesprek> telefoongesprekken) {
        this.telefoongesprekken = telefoongesprekken;
    }
}
