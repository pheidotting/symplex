package nl.lakedigital.djfc.commons.json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({//
        @JsonSubTypes.Type(value = JsonIngaandeBrief.class, name = "JsonIngaandeBrief"),//
        @JsonSubTypes.Type(value = JsonUitgaandeBrief.class, name = "JsonUitgaandeBrief"), //
        @JsonSubTypes.Type(value = JsonIngaandeEmail.class, name = "JsonIngaandeEmail"), //
        @JsonSubTypes.Type(value = JsonUitgaandeEmail.class, name = "JsonUitgaandeEmail")//
})
public abstract class JsonCommunicatieProduct {
    private Long id;
    private String datumTijdCreatie;
    private String datumTijdVerzending;
    private SoortEntiteit soortEntiteit;
    private Long entiteitId;
    private String tekst;
    private Long antwoordOp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumTijdCreatie() {
        return datumTijdCreatie;
    }

    public void setDatumTijdCreatie(String datumTijdCreatie) {
        this.datumTijdCreatie = datumTijdCreatie;
    }

    public String getDatumTijdVerzending() {
        return datumTijdVerzending;
    }

    public void setDatumTijdVerzending(String datumTijdVerzending) {
        this.datumTijdVerzending = datumTijdVerzending;
    }

    public SoortEntiteit getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(SoortEntiteit soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Long getAntwoordOp() {
        return antwoordOp;
    }

    public void setAntwoordOp(Long antwoordOp) {
        this.antwoordOp = antwoordOp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JsonCommunicatieProduct)) {
            return false;
        }

        JsonCommunicatieProduct that = (JsonCommunicatieProduct) o;

        return new EqualsBuilder().append(getId(), that.getId()).append(getDatumTijdVerzending(), that.getDatumTijdVerzending()).append(getSoortEntiteit(), that.getSoortEntiteit()).append(getEntiteitId(), that.getEntiteitId()).append(getTekst(), that.getTekst()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getDatumTijdVerzending()).append(getSoortEntiteit()).append(getEntiteitId()).append(getTekst()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("datumTijdCreatie", datumTijdCreatie).append("datumTijdVerzending", datumTijdVerzending).append("soortEntiteit", soortEntiteit).append("entiteitId", entiteitId).append("tekst", tekst).toString();
    }
}
