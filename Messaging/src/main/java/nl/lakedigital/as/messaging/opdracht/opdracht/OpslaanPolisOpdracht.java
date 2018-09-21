package nl.lakedigital.as.messaging.opdracht.opdracht;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.djfc.commons.domain.Opmerking;
import nl.lakedigital.djfc.commons.domain.Pakket;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "opslaanPolisOpdracht")
public class OpslaanPolisOpdracht extends AbstractMessage {
    private Pakket pakket;
    private List<Opmerking> opmerkingen;

    public Pakket getPakket() {
        return pakket;
    }

    public void setPakket(Pakket pakket) {
        this.pakket = pakket;
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

}
