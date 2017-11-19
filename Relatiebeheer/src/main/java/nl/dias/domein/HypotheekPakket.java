package nl.dias.domein;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Audited
@Entity
@Table(name = "HYPOTHEEKPAKKET")
@NamedQueries({@NamedQuery(name = "HypotheekPakket.allesVanRelatie", query = "select h from HypotheekPakket h where h.relatie = :relatie and size(h.hypotheken) >= 2")})
public class HypotheekPakket implements PersistenceObject, Serializable {
    private static final long serialVersionUID = -2386437329178396939L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;

    @JoinColumn(name = "RELATIE")
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = true, targetEntity = Relatie.class)
    protected Relatie relatie;

    @OneToMany(targetEntity = Hypotheek.class, mappedBy = "hypotheekPakket", fetch = FetchType.EAGER)
    private Set<Hypotheek> hypotheken;

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
        builder.append(relatie.getId());
        builder.append("]");
        return builder.toString();
    }

}
