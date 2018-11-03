//package nl.lakedigital.as.messaging.entities;
//
//
//import nl.lakedigital.as.messaging.domain.Opmerking;
//
//import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlType;
//import java.util.ArrayList;
//import java.util.List;
//
//@XmlRootElement(name = "pakket")
//@XmlType(namespace = "entities")
//public class Pakket {
//    private Long id;
//    private String identificatie;
//    private String polisNummer;
//    private String soortEntiteit;
//    private Long entiteitId;
//    private Long maatschappij;
//    private String parentIdentificatie;
//    private List<Polis> polissen;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getIdentificatie() {
//        return identificatie;
//    }
//
//    public void setIdentificatie(String identificatie) {
//        this.identificatie = identificatie;
//    }
//
//    public String getPolisNummer() {
//        return polisNummer;
//    }
//
//    public void setPolisNummer(String polisNummer) {
//        this.polisNummer = polisNummer;
//    }
//
//    public String getSoortEntiteit() {
//        return soortEntiteit;
//    }
//
//    public void setSoortEntiteit(String soortEntiteit) {
//        this.soortEntiteit = soortEntiteit;
//    }
//
//    public Long getEntiteitId() {
//        return entiteitId;
//    }
//
//    public void setEntiteitId(Long entiteitId) {
//        this.entiteitId = entiteitId;
//    }
//
//    public Long getMaatschappij() {
//        return maatschappij;
//    }
//
//    public void setMaatschappij(Long maatschappij) {
//        this.maatschappij = maatschappij;
//    }
//
//    public String getParentIdentificatie() {
//        return parentIdentificatie;
//    }
//
//    public void setParentIdentificatie(String parentIdentificatie) {
//        this.parentIdentificatie = parentIdentificatie;
//    }
//
//    public List<Polis> getPolissen() {
//        if (polissen == null) {
//            polissen = new ArrayList<>();
//        }
//        return polissen;
//    }
//
//    public void setPolissen(List<Polis> polissen) {
//        this.polissen = polissen;
//    }
//}
