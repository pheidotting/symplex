package nl.dias.domein;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Audited
@Entity
@Table(name = "BEDRIJF")
@NamedQueries({
        //        @NamedQuery(name = "Bedrijf.allesBijRelatie", query = "select b from Bedrijf b where b.relatie = :relatie"),
        @NamedQuery(name = "Bedrijf.zoekOpNaam", query = "select b from Bedrijf b where b.naam like :zoekTerm"), @NamedQuery(name = "Bedrijf.alles", query = "select b from Bedrijf b")})
public class Bedrijf implements Serializable {
    private static final long serialVersionUID = 4611123664803995245L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAAM")
    private String naam;

    @Column(name = "KVK", length = 8)
    private String kvk;

    @Column(name = "RECHTSVORM")
    private String rechtsvorm;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "INTERNETADRES")
    private String internetadres;

    @Column(name = "HOEDANIGHEID")
    private String hoedanigheid;

    @Column(name = "CAOVERPLICHTINGEN")
    private String cAoVerplichtingen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getKvk() {
        return kvk;
    }

    public void setKvk(String kvk) {
        this.kvk = kvk;
    }

    public String getRechtsvorm() {
        return rechtsvorm;
    }

    public void setRechtsvorm(String rechtsvorm) {
        this.rechtsvorm = rechtsvorm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInternetadres() {
        return internetadres;
    }

    public void setInternetadres(String internetadres) {
        this.internetadres = internetadres;
    }

    public String getHoedanigheid() {
        return hoedanigheid;
    }

    public void setHoedanigheid(String hoedanigheid) {
        this.hoedanigheid = hoedanigheid;
    }

    public String getcAoVerplichtingen() {
        return cAoVerplichtingen;
    }

    public void setcAoVerplichtingen(String cAoVerplichtingen) {
        this.cAoVerplichtingen = cAoVerplichtingen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Bedrijf)) {
            return false;
        }

        Bedrijf bedrijf = (Bedrijf) o;

        return new EqualsBuilder().append(getId(), bedrijf.getId()).append(getNaam(), bedrijf.getNaam()).append(getKvk(), bedrijf.getKvk()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getNaam()).append(getKvk()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("naam", naam).append("kvk", kvk).toString();
    }
}
