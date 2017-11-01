package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Notitie {
    private String tekst;
    private String tijdstip;
    private String medewerker;
    private Long todoistId;

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public String getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(String tijdstip) {
        this.tijdstip = tijdstip;
    }

    public String getMedewerker() {
        return medewerker;
    }

    public void setMedewerker(String medewerker) {
        this.medewerker = medewerker;
    }

    public Long getTodoistId() {
        return todoistId;
    }

    public void setTodoistId(Long todoistId) {
        this.todoistId = todoistId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("tekst", tekst).append("tijdstip", tijdstip).append("medewerker", medewerker).append("todoistId", todoistId).toString();
    }
}
