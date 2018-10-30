package nl.lakedigital.as.messaging.opdracht.opdracht;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.djfc.commons.domain.Opmerking;
import nl.lakedigital.djfc.commons.domain.Schade;
import nl.lakedigital.djfc.commons.domain.Taak;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "opslaanSchadeOpdracht")
public class OpslaanSchadeOpdracht extends AbstractMessage implements MetOpmerkingen, MetTaken {
    private Schade schade;
    private List<Opmerking> opmerkingen;
    private List<Taak> taken;

    public Schade getSchade() {
        return schade;
    }

    public void setSchade(Schade schade) {
        this.schade = schade;
    }

    public List<Opmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = new ArrayList<>();
        }
        return opmerkingen;
    }

    public void setOpmerkingen(List<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    @Override
    public List<Taak> getTaken() {
        if (taken == null) {
            taken = new ArrayList<>();
        }
        return taken;
    }

    @Override
    public void setTaken(List<Taak> taken) {
        this.taken = taken;
    }
}
