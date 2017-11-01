package nl.lakedigital.as.messaging.response;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.domain.Bedrijf;
import nl.lakedigital.as.messaging.domain.Persoon;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "opvragenPersoonResponse")
public class OpvragenPersoonSOfBedrijfsGegevensResponse extends AbstractMessage {
    private Persoon persoon;
    private Bedrijf bedrijf;

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(Persoon persoon) {
        this.persoon = persoon;
    }

    public Bedrijf getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("persoon", persoon).append("bedrijf", bedrijf).toString();
    }
}
