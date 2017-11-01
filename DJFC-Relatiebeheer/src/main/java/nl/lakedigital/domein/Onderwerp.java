package nl.lakedigital.domein;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Audited
@MappedSuperclass
public abstract class Onderwerp implements Serializable {
    private static final long serialVersionUID = 6395324676484643680L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IDENTIFICATIE")
    private String identificatie;
    @Column(name = "WACHTWOORD")
    private String wachtwoord;
    @Column(name = "SALT")
    private String salt;

    public String genereerNieuwWachtwoord() {
        this.wachtwoord = UUID.randomUUID().toString().replace("-", "");
        return this.wachtwoord;
    }

    public void setHashWachtwoord(String wachtwoord) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.wachtwoord = hash(wachtwoord + getSalt());
    }

    public String hash(String tekst) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-512");
        // Change this to "UTF-16" if needed
        md.update(tekst.getBytes("UTF-8"));

        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        return bigInt.toString(16);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.identificatie = identificatie;
        this.setSalt(null);
        this.getSalt();
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
        this.setSalt(null);
    }

    public String getSalt() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (this.salt == null || "".equals(this.salt)) {
            if (this.identificatie != null && !"".equals(this.identificatie)) {
                this.salt = hash(this.identificatie);
            } else {
                String zout = UUID.randomUUID().toString();
                this.salt = hash(zout);
            }
        }
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nnl.lakedigital.loginsystem.inloggen.domein.Onderwerp [id=");
        builder.append(id);
        builder.append(", identificatie=");
        builder.append(identificatie);
        builder.append("\nwachtwoord=");
        builder.append(wachtwoord);
        builder.append("\nsalt=");
        builder.append(salt);
        builder.append("]");
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((identificatie == null) ? 0 : identificatie.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Onderwerp other = (Onderwerp) obj;
        return new EqualsBuilder().append(identificatie, other.identificatie).isEquals();
    }
}
