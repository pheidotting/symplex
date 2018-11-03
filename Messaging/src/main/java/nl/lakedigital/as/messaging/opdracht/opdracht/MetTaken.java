package nl.lakedigital.as.messaging.opdracht.opdracht;

import nl.lakedigital.djfc.commons.domain.Taak;

import java.util.List;

public interface MetTaken {
    List<Taak> getTaken();

    void setTaken(List<Taak> taken);
}
