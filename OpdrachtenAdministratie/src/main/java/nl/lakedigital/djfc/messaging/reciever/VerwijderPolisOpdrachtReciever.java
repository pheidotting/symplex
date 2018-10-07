package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.opdracht.opdracht.VerwijderPolisOpdracht;
import nl.lakedigital.djfc.service.VerwijderPolisAfhandelenService;

import javax.inject.Inject;

public class VerwijderPolisOpdrachtReciever extends AbstractReciever<VerwijderPolisOpdracht> {
    @Inject
    private VerwijderPolisAfhandelenService verwijderPolisAfhandelenService;

    public VerwijderPolisOpdrachtReciever() {
        super(VerwijderPolisOpdracht.class);
    }

    @Override
    public void verwerkMessage(VerwijderPolisOpdracht verwijderPolisOpdracht, String berichtTekst) {
        verwijderPolisAfhandelenService.verwerkInkomendeOpdracht(verwijderPolisOpdracht, berichtTekst);
    }
}
