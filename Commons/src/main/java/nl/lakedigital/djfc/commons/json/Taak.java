package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Taak {
    private String omschrijving;
    private boolean afgerond;
    private String soortEntiteit;
    private Long entiteitId;
    private Long todoistId;
    private List<Notitie> notities = new ArrayList<>();

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public boolean isAfgerond() {
        return afgerond;
    }

    public void setAfgerond(boolean afgerond) {
        this.afgerond = afgerond;
    }

    public String getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(String soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }

    public Long getTodoistId() {
        return todoistId;
    }

    public void setTodoistId(Long todoistId) {
        this.todoistId = todoistId;
    }

    public List<Notitie> getNotities() {
        return notities;
    }

    public void setNotities(List<Notitie> notities) {
        this.notities = notities;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("omschrijving", omschrijving).append("afgerond", afgerond).append("soortEntiteit", soortEntiteit).append("entiteitId", entiteitId).append("todoistId", todoistId).append("notities", notities).toString();
    }
}
