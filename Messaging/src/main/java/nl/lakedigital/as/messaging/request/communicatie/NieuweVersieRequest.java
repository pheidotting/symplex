package nl.lakedigital.as.messaging.request.communicatie;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "nieuweVersieRequest")
public class NieuweVersieRequest extends AbstractCommunicatieRequest {
    private String versie;
    private String releasenotes;

    public NieuweVersieRequest() {
    }

    public NieuweVersieRequest(Long gebruikerId, String email, String voornaam, String tussenvoegsel, String achternaam, String versie, String releasenotes) {
        super(gebruikerId, email, voornaam, tussenvoegsel, achternaam, null, null);

        this.versie = versie;
        this.releasenotes = releasenotes;
    }

    public String getVersie() {
        return versie;
    }

    public void setVersie(String versie) {
        this.versie = versie;
    }

    public String getReleasenotes() {
        return releasenotes;
    }

    public void setReleasenotes(String releasenotes) {
        this.releasenotes = releasenotes;
    }
}
