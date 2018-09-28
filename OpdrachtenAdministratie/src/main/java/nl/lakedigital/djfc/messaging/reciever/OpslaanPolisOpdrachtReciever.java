package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanPolisOpdracht;
import nl.lakedigital.djfc.service.OpslaanPolisAfhandelenService;

import javax.inject.Inject;

public class OpslaanPolisOpdrachtReciever extends AbstractReciever<OpslaanPolisOpdracht> {
    @Inject
    private OpslaanPolisAfhandelenService opslaanPolisAfhandelenService;

    public OpslaanPolisOpdrachtReciever() {
        super(OpslaanPolisOpdracht.class);
    }

    @Override
    public void verwerkMessage(OpslaanPolisOpdracht opslaanPolisOpdracht, String berichtTekst) {
        opslaanPolisAfhandelenService.verwerkInkomendeOpdracht(opslaanPolisOpdracht, berichtTekst);
    }
}
