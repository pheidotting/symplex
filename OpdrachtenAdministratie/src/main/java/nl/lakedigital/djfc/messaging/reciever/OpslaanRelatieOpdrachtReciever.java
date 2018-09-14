package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanRelatieOpdracht;
import nl.lakedigital.djfc.service.OpslaanRelatieAfhandelenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class OpslaanRelatieOpdrachtReciever extends AbstractReciever<OpslaanRelatieOpdracht> {
    private final static Logger LOGGER = LoggerFactory.getLogger(OpslaanRelatieOpdrachtReciever.class);

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
