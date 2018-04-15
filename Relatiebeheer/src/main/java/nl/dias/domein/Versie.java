package nl.dias.domein;

import javax.persistence.*;

@Entity
@Table(name = "VERSIES")
public class Versie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VERSIE")
    private String versie;

    @Column(name = "RELEASENOTES")
    private String releasenotes;

    public Versie() {
        super();
    }

    public Versie(String versie, String releasenotes) {
        this.versie = versie;
        this.releasenotes = releasenotes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
