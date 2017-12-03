package nl.dias.domein;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Audited
@Entity
@Table(name = "STATUSSCHADE")
@NamedQueries({@NamedQuery(name = "StatusSchade.zoekOpSoort", query = "select s from StatusSchade s where s.status =:status and s.ingebruik = '1'")})
public class StatusSchade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "STATUS", length = 250, nullable = false, unique = true)
    private String status;

    @Column(name = "INGEBRUIK")
    private boolean ingebruik;

    public StatusSchade() {
        ingebruik = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIngebruik() {
        return ingebruik;
    }

    public void setIngebruik(boolean ingebruik) {
        this.ingebruik = ingebruik;
    }

    @Override
    public String toString() {
        return status;
    }
}
