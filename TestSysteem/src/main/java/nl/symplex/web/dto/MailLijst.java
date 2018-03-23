package nl.symplex.web.dto;

import java.util.ArrayList;
import java.util.List;

public class MailLijst {
    private List<Mail> lijst;

    public MailLijst() {
    }

    public List<Mail> getLijst() {
        if (lijst == null) {
            lijst = new ArrayList<>();
        }
        return lijst;
    }

    public void addMail(Mail mail) {
        getLijst().add(mail);
    }

    public void setLijst(List<Mail> lijst) {
        this.lijst = lijst;
    }
}
