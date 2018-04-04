package nl.lakedigital.djfc.domain;

import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@Audited
@Entity
@Table(name = "COMMUNICATIEPRODUCT")
public class IngaandeEmail extends Email {
    public IngaandeEmail() {
        OngelezenIndicatie ongelezenIndicatie = new OngelezenIndicatie();
        ongelezenIndicatie.setIngaandeEmail(this);
        setOngelezenIndicatie(ongelezenIndicatie);

        setExtraInformatie(new ExtraInformatie());
        extraInformatie.setIngaandeEmail(this);
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "ingaandeEmail", orphanRemoval = true)
    private OngelezenIndicatie ongelezenIndicatie;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "ingaandeEmail", orphanRemoval = true)
    private ExtraInformatie extraInformatie;

    public OngelezenIndicatie getOngelezenIndicatie() {
        return ongelezenIndicatie;
    }

    public void setOngelezenIndicatie(OngelezenIndicatie ongelezenIndicatie) {
        this.ongelezenIndicatie = ongelezenIndicatie;
    }

    public ExtraInformatie getExtraInformatie() {
        return extraInformatie;
    }

    public void setExtraInformatie(ExtraInformatie extraInformatie) {
        this.extraInformatie = extraInformatie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngaandeEmail)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        IngaandeEmail that = (IngaandeEmail) o;
        return Objects.equals(getOngelezenIndicatie(), that.getOngelezenIndicatie()) && Objects.equals(getExtraInformatie(), that.getExtraInformatie());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getOngelezenIndicatie(), getExtraInformatie());
    }
}
