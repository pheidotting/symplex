package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.opdracht.opdracht.VerwijderSchadeOpdracht;
import nl.lakedigital.djfc.service.VerwijderSchadeAfhandelenService;

import javax.inject.Inject;

public class VerwijderSchadeOpdrachtReciever extends AbstractReciever<VerwijderSchadeOpdracht> {
    @Inject
    private VerwijderSchadeAfhandelenService verwijderSchadeAfhandelenService;

    public VerwijderSchadeOpdrachtReciever() {
        super(VerwijderSchadeOpdracht.class);
    }

    @Override
    public void verwerkMessage(VerwijderSchadeOpdracht verwijderSchadeOpdracht, String berichtTekst) {
        verwijderSchadeAfhandelenService.verwerkInkomendeOpdracht(verwijderSchadeOpdracht, berichtTekst);
    }
}
