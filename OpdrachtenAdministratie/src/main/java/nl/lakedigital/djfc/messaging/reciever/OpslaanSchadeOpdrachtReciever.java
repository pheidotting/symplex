package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanSchadeOpdracht;
import nl.lakedigital.djfc.service.OpslaanSchadeAfhandelenService;

import javax.inject.Inject;

public class OpslaanSchadeOpdrachtReciever extends AbstractReciever<OpslaanSchadeOpdracht> {
    @Inject
    private OpslaanSchadeAfhandelenService opslaanSchadeAfhandelenService;

    public OpslaanSchadeOpdrachtReciever() {
        super(OpslaanSchadeOpdracht.class);
    }

    @Override
    public void verwerkMessage(OpslaanSchadeOpdracht opslaanSchadeOpdracht, String berichtTekst) {
        opslaanSchadeAfhandelenService.verwerkInkomendeOpdracht(opslaanSchadeOpdracht, berichtTekst);
    }
}
