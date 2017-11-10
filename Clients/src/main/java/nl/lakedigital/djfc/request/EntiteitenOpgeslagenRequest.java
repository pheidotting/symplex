package nl.lakedigital.djfc.request;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class EntiteitenOpgeslagenRequest {
    private List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds;

    public List<SoortEntiteitEnEntiteitId> getSoortEntiteitEnEntiteitIds() {
        if (soortEntiteitEnEntiteitIds == null) {
            soortEntiteitEnEntiteitIds = new ArrayList<>();
        }

        return soortEntiteitEnEntiteitIds;
    }

    public void setSoortEntiteitEnEntiteitIds(List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds) {
        this.soortEntiteitEnEntiteitIds = soortEntiteitEnEntiteitIds;
    }
}
