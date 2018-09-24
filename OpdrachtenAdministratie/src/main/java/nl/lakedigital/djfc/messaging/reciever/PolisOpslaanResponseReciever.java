package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.response.PolisOpslaanResponse;
import nl.lakedigital.djfc.service.OpslaanPolisAfhandelenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class PolisOpslaanResponseReciever extends AbstractReciever<PolisOpslaanResponse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(PolisOpslaanResponseReciever.class);

    @Inject
    private OpslaanPolisAfhandelenService opslaanPolisAfhandelenService;

    public PolisOpslaanResponseReciever() {
        super(PolisOpslaanResponse.class);
    }

    @Override
    public void verwerkMessage(PolisOpslaanResponse opslaanPolisOpdracht, String berichtTekst) {
        opslaanPolisAfhandelenService.verwerkTerugkoppeling(opslaanPolisOpdracht);
    }
}
