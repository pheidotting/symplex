package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.response.Response;
import nl.lakedigital.djfc.service.AfhandelenInkomendeOpdrachtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class ResponseReciever extends AbstractReciever<Response> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseReciever.class);

    @Inject
    private List<AfhandelenInkomendeOpdrachtService> afhandelenInkomendeOpdrachtServices;

    public ResponseReciever() {
        super(Response.class);
    }

    @Override
    public void verwerkMessage(Response response, String berichtTekst) {
        Optional<AfhandelenInkomendeOpdrachtService> afhandelenInkomendeOpdrachtServiceOption = afhandelenInkomendeOpdrachtServices.stream().filter(afhandelenInkomendeOpdrachtService -> afhandelenInkomendeOpdrachtService.getSoortEntiteiten().contains(response.getHoofdSoortEntiteit())).findFirst();

        if (afhandelenInkomendeOpdrachtServiceOption.isPresent()) {
            afhandelenInkomendeOpdrachtServiceOption.get().verwerkTerugkoppeling(response);
        } else {
            LOGGER.error("Geen service gevonden voor entiteit {}", response.getHoofdSoortEntiteit());
        }
    }
}
