package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanRelatieOpdracht;
import nl.lakedigital.djfc.service.OpslaanRelatieAfhandelenService;

import javax.inject.Inject;

public class OpslaanRelatieOpdrachtReciever extends AbstractReciever<OpslaanRelatieOpdracht> {
    @Inject
    private OpslaanRelatieAfhandelenService opslaanRelatieAfhandelenService;

    public OpslaanRelatieOpdrachtReciever() {
        super(OpslaanRelatieOpdracht.class);
    }

    @Override
    public void verwerkMessage(OpslaanRelatieOpdracht opslaanRelatieOpdracht, String berichtTekst) {
        opslaanRelatieAfhandelenService.verwerkInkomendeOpdracht(opslaanRelatieOpdracht, berichtTekst);
    }
}
