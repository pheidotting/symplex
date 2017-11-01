package nl.lakedigital.as.messaging;

import nl.lakedigital.as.messaging.request.*;
import nl.lakedigital.as.messaging.response.PolisOpslaanResponse;
import nl.lakedigital.as.messaging.response.SchadeOpslaanResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({//
        BeindigenPolisRequest.class,//
        TaakOpslaan.class,//
        OpvragenPersoonSOfBedrijfsGegevensRequest.class,//
        OpvragenPersoonSOfBedrijfsGegevensRequest.class,//
        PolisOpslaanRequest.class,//
        PolisOpslaanResponse.class,//
        PolisVerwijderenRequest.class, //
        VerwijderBedrijvenRequest.class, //
        VerwijderEntiteitenRequest.class,//
        VerwijderRelatiesRequest.class,//
        OpslaanEntiteitenRequest.class,//
        SchadeOpslaanRequest.class,//
        SchadeOpslaanResponse.class,//
        EntiteitenOpgeslagenRequest.class
})
public abstract class AbstractMessage {
    private String trackAndTraceId;
    private Long ingelogdeGebruiker;
    private AbstractMessage antwoordOp;

    public String getTrackAndTraceId() {
        return trackAndTraceId;
    }

    public void setTrackAndTraceId(String trackAndTraceId) {
        this.trackAndTraceId = trackAndTraceId;
    }

    public Long getIngelogdeGebruiker() {
        return ingelogdeGebruiker;
    }

    public void setIngelogdeGebruiker(Long ingelogdeGebruiker) {
        this.ingelogdeGebruiker = ingelogdeGebruiker;
    }

    public AbstractMessage getAntwoordOp() {
        return antwoordOp;
    }

    public void setAntwoordOp(AbstractMessage antwoordOp) {
        this.antwoordOp = antwoordOp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("trackAndTraceId", trackAndTraceId).append("ingelogdeGebruiker", ingelogdeGebruiker).append("antwoordOp", antwoordOp).toString();
    }
}
