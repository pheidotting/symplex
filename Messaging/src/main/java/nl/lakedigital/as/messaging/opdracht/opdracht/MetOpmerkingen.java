package nl.lakedigital.as.messaging.opdracht.opdracht;

import nl.lakedigital.djfc.commons.domain.Opmerking;

import java.util.List;

public interface MetOpmerkingen {
    List<Opmerking> getOpmerkingen();

    void setOpmerkingen(List<Opmerking> opmerkingen);
}
