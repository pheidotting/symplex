package nl.lakedigital.as.messaging.request;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "controleerLicentieResponse")
public class ControleerLicentieResponse extends AbstractMessage {
    private Long kantoorId;
    private String soortLicentie;
    private int aantalDagenNog;

    public ControleerLicentieResponse() {
    }

    public ControleerLicentieResponse(Long kantoorId, String soortLicentie, int aantalDagenNog) {
        this.kantoorId = kantoorId;
        this.soortLicentie = soortLicentie;
        this.aantalDagenNog = aantalDagenNog;
    }

    public Long getKantoorId() {
        return kantoorId;
    }

    public void setKantoorId(Long kantoorId) {
        this.kantoorId = kantoorId;
    }

    public String getSoortLicentie() {
        return soortLicentie;
    }

    public void setSoortLicentie(String soortLicentie) {
        this.soortLicentie = soortLicentie;
    }

    public int getAantalDagenNog() {
        return aantalDagenNog;
    }

    public void setAantalDagenNog(int aantalDagenNog) {
        this.aantalDagenNog = aantalDagenNog;
    }
}
