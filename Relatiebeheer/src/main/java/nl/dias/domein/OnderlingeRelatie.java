package nl.dias.domein;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ONDERLINGERELATIES")
public class OnderlingeRelatie implements Serializable {
    private static final long serialVersionUID = -8731485363183283190L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false, targetEntity = Relatie.class)
    @JoinColumn(name = "RELATIE")
    private Relatie relatie;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false, targetEntity = Relatie.class)
    @JoinColumn(name = "RELATIE_MET")
    private Relatie relatieMet;

    @Column(name = "RELATIESOORT", length = 3)
    @Enumerated(EnumType.STRING)
    private OnderlingeRelatieSoort onderlingeRelatieSoort;

    public OnderlingeRelatie() {
    }

    public OnderlingeRelatie(Relatie relatie, Relatie relatieMet, OnderlingeRelatieSoort soort) {
        this.relatie = relatie;
        this.onderlingeRelatieSoort = soort;
        setRelatieMet(relatieMet, true);
    }

    public OnderlingeRelatie(Relatie relatie, Relatie relatieMet, boolean terug, OnderlingeRelatieSoort soort) {
        this.relatie = relatie;
        this.onderlingeRelatieSoort = soort;
        setRelatieMet(relatieMet, terug);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }

    public Relatie getRelatieMet() {
        return relatieMet;
    }

    public void setRelatieMet(Relatie relatieMet, boolean terug) {
        this.relatieMet = relatieMet;
        if (terug) {
            this.relatieMet.getOnderlingeRelaties().add(new OnderlingeRelatie(this.relatieMet, relatie, false, OnderlingeRelatieSoort.getTegenGesteld(onderlingeRelatieSoort)));
        }
    }

    public OnderlingeRelatieSoort getOnderlingeRelatieSoort() {
        return onderlingeRelatieSoort;
    }

    public void setOnderlingeRelatieSoort(OnderlingeRelatieSoort onderlingeRelatieSoort) {
        this.onderlingeRelatieSoort = onderlingeRelatieSoort;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OnderlingeRelatie [id=");
        builder.append(id);
        builder.append(", relatie=");
        if (relatie != null) {
            builder.append(relatie.getId());
        } else {
            builder.append("null");
        }
        builder.append(", relatieMet=");
        if (relatieMet != null) {
            builder.append(relatieMet.getId());
        } else {
            builder.append("null");
        }
        builder.append(", onderlingeRelatieSoort=");
        builder.append(onderlingeRelatieSoort);
        builder.append("]");
        return builder.toString();
    }
}
