package nl.dias.domein;

import com.google.common.collect.Sets;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Audited
@Entity
@Table(name = "BEDRIJF")
@NamedQueries({
        //        @NamedQuery(name = "Bedrijf.allesBijRelatie", query = "select b from Bedrijf b where b.relatie = :relatie"),
        @NamedQuery(name = "Bedrijf.zoekOpNaam", query = "select b from Bedrijf b where b.naam like :zoekTerm"), @NamedQuery(name = "Bedrijf.alles", query = "select b from Bedrijf b")})
public class Bedrijf implements Serializable, PersistenceObject {
    private static final long serialVersionUID = 4611123664803995245L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "RELATIE")
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Relatie.class)
    private Relatie relatie;

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

    @NotAudited
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "bedrijf")
    private Set<JaarCijfers> jaarCijfers;

    @NotAudited
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "bedrijf")
    private Set<RisicoAnalyse> risicoAnalyses;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
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

    public Set<JaarCijfers> getJaarCijfers() {
        if (jaarCijfers == null) {
            jaarCijfers = Sets.newHashSet();
        }
        return jaarCijfers;
    }

    public void setJaarCijfers(Set<JaarCijfers> jaarCijfers) {
        this.jaarCijfers = jaarCijfers;
    }

    public Set<RisicoAnalyse> getRisicoAnalyses() {
        if (risicoAnalyses == null) {
            risicoAnalyses = new HashSet<>();
        }
        return risicoAnalyses;
    }

    public void setRisicoAnalyses(Set<RisicoAnalyse> risicoAnalyses) {
        this.risicoAnalyses = risicoAnalyses;
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

        return new EqualsBuilder().append(getId(), bedrijf.getId()).append(getRelatie(), bedrijf.getRelatie()).append(getNaam(), bedrijf.getNaam()).append(getKvk(), bedrijf.getKvk()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getRelatie()).append(getNaam()).append(getKvk()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("relatie", relatie).append("naam", naam).append("kvk", kvk).toString();
    }
}
