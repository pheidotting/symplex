package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanRelatieOpdracht;
import nl.lakedigital.djfc.service.OpslaanRelatieAfhandelenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class OpslaanPolisOpdrachtReciever extends AbstractReciever<OpslaanRelatieOpdracht> {
    private final static Logger LOGGER = LoggerFactory.getLogger(OpslaanPolisOpdrachtReciever.class);

    @Inject
    private OpslaanRelatieAfhandelenService opslaanRelatieAfhandelenService;

    public OpslaanPolisOpdrachtReciever() {
        super(OpslaanRelatieOpdracht.class);
    }

    @Override
    public void verwerkMessage(OpslaanRelatieOpdracht opslaanRelatieOpdracht, String berichtTekst) {
        opslaanRelatieAfhandelenService.verwerkInkomendeOpdracht(opslaanRelatieOpdracht, berichtTekst);
    }
}
