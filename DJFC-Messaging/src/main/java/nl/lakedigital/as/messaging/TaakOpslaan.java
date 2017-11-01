package nl.lakedigital.as.messaging;

import nl.lakedigital.as.messaging.domain.Taak;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TaakOpslaan extends AbstractMessage {
    private Taak taak;

    public Taak getTaak() {
        return taak;
    }

    public void setTaak(Taak taak) {
        this.taak = taak;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("taak", taak).toString();
    }
}
