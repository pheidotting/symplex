package nl.lakedigital.djfc.domain;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
}
