package nl.lakedigital.as.messaging.opdracht.opdracht;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.entities.Relatie;
import nl.lakedigital.djfc.commons.domain.Adres;
import nl.lakedigital.djfc.commons.domain.Opmerking;
import nl.lakedigital.djfc.commons.domain.RekeningNummer;
import nl.lakedigital.djfc.commons.domain.Telefoonnummer;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@XmlRootElement(name = "opslaanRelatieOpdracht")
public class OpslaanRelatieOpdracht extends AbstractMessage {
    private Relatie relatie;

    private List<Adres> adressen = newArrayList();
    private List<RekeningNummer> rekeningNummers = newArrayList();
    private List<Telefoonnummer> telefoonnummers = newArrayList();
    private List<Opmerking> opmerkingen = newArrayList();
    //    private List<Taak> taken;

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }

    public List<Adres> getAdressen() {
        return adressen;
    }

    public void setAdressen(List<Adres> adressen) {
        this.adressen = adressen;
    }

    public List<RekeningNummer> getRekeningNummers() {
        return rekeningNummers;
    }

    public void setRekeningNummers(List<RekeningNummer> rekeningNummers) {
        this.rekeningNummers = rekeningNummers;
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
}
