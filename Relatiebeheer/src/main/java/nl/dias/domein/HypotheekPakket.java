package nl.dias.domein;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Audited
@Entity
@Table(name = "HYPOTHEEKPAKKET")
@NamedQueries({@NamedQuery(name = "HypotheekPakket.allesVanRelatie", query = "select h from HypotheekPakket h where h.relatie = :relatie and size(h.hypotheken) >= 2")})
public class HypotheekPakket implements Serializable {
    private static final long serialVersionUID = -2386437329178396939L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;

    @Column(name = "RELATIE")
    //    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Relatie.class)
    protected Long relatie;

    @OneToMany(targetEntity = Hypotheek.class, mappedBy = "hypotheekPakket", fetch = FetchType.EAGER)
    private Set<Hypotheek> hypotheken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie.getId();
    }

    public Set<Hypotheek> getHypotheken() {
        if (hypotheken == null) {
            hypotheken = new HashSet<>();
        }
        return hypotheken;
    }

    public void setHypotheken(Set<Hypotheek> hypotheken) {
        this.hypotheken = hypotheken;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("HypotheekPakket [id=");
        builder.append(id);
        builder.append(", relatie=");
        builder.append(relatie);
        builder.append("]");
        return builder.toString();
    }

}
