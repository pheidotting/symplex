package nl.lakedigital.djfc.commons.json;

import java.io.Serializable;


public final class OnderlingeRelatieJson implements Serializable {
    private static final long serialVersionUID = -5853743296641464125L;

    private Long id;
    private Long idNaar;
    private String soort;
    private String metWie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdNaar() {
        return idNaar;
    }

    public void setIdNaar(Long idNaar) {
        this.idNaar = idNaar;
    }

    public String getSoort() {
        return soort;
    }

    public void setSoort(String soort) {
        this.soort = soort;
    }

    public String getMetWie() {
        return metWie;
    }

    public void setMetWie(String metWie) {
        this.metWie = metWie;
    }
}
