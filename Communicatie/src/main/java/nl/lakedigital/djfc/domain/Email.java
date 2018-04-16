package nl.lakedigital.djfc.domain;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Audited
@Entity
@Table(name = "COMMUNICATIEPRODUCT")
public abstract class Email extends CommunicatieProduct{
    @Column(name = "NAAMONTVANGER")
    private String naamOntvanger;
    @Column(name = "EMAILONTVANGER")
    private String emailOntvanger;
    @Column(name = "NAAMVERZENDER")
    private String naamVerzender;
    @Column(name = "EMAILVERZENDER")
    private String emailVerzender;

    public String getNaamOntvanger() {
        return naamOntvanger;
    }

    public void setNaamOntvanger(String naamOntvanger) {
        this.naamOntvanger = naamOntvanger;
    }

    public String getEmailOntvanger() {
        return emailOntvanger;
    }

    public void setEmailOntvanger(String emailOntvanger) {
        this.emailOntvanger = emailOntvanger;
    }

    public String getNaamVerzender() {
        return naamVerzender;
    }

    public void setNaamVerzender(String naamVerzender) {
        this.naamVerzender = naamVerzender;
    }

    public String getEmailVerzender() {
        return emailVerzender;
    }

    public void setEmailVerzender(String emailVerzender) {
        this.emailVerzender = emailVerzender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Email)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Email email = (Email) o;
        return Objects.equals(getNaamOntvanger(), email.getNaamOntvanger()) && Objects.equals(getEmailOntvanger(), email.getEmailOntvanger()) && Objects.equals(getNaamVerzender(), email.getNaamVerzender()) && Objects.equals(getEmailVerzender(), email.getEmailVerzender());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getNaamOntvanger(), getEmailOntvanger(), getNaamVerzender(), getEmailVerzender());
    }
}
