//package nl.lakedigital.as.messaging.domain;
//
//import javax.xml.bind.annotation.XmlRootElement;
//import java.util.List;
//
//@XmlRootElement(name = "telefoonnummer")
//public class Telefoonnummer extends AbstracteEntiteitMetSoortEnId {
//    private String telefoonnummer;
//    private String soort;
//    private String omschrijving;
//    private List<String> errors;
//
//    public Telefoonnummer() {
//    }
//
//    public Telefoonnummer(SoortEntiteit soortEntiteit, Long entiteitId, String identificatie, String telefoonnummer, String soort, String omschrijving, List<String> errors) {
//        super(soortEntiteit, entiteitId, identificatie);
//        this.telefoonnummer = telefoonnummer;
//        this.soort = soort;
//        this.omschrijving = omschrijving;
//        this.errors = errors;
//    }
//
//    public String getTelefoonnummer() {
//        return telefoonnummer;
//    }
//
//    public void setTelefoonnummer(String telefoonnummer) {
//        this.telefoonnummer = telefoonnummer;
//    }
//
//    public String getSoort() {
//        return soort;
//    }
//
//    public void setSoort(String soort) {
//        this.soort = soort;
//    }
//
//    public String getOmschrijving() {
//        return omschrijving;
//    }
//
//    public void setOmschrijving(String omschrijving) {
//        this.omschrijving = omschrijving;
//    }
//
//    public List<String> getErrors() {
//        return errors;
//    }
//
//    public void setErrors(List<String> errors) {
//        this.errors = errors;
//    }
//}