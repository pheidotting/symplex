package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.opdracht.opdracht.MetOpmerkingen;
import nl.lakedigital.as.messaging.opdracht.opdracht.MetTaken;
import nl.lakedigital.djfc.commons.domain.Opmerking;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import nl.lakedigital.djfc.commons.domain.inkomend.InkomendeOpdracht;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.mapper.MessagingOpmerkingToUitgaandeOpdrachtMapper;
import nl.lakedigital.djfc.mapper.MessagingTaakToUitgaandeOpdrachtMapper;
import nl.lakedigital.djfc.repository.InkomendeOpdrachtRepository;
import nl.lakedigital.djfc.repository.UitgaandeOpdrachtRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AfhandelenInkomendeOpdrachtService<T extends AbstractMessage, U extends AbstractMessage> {
    private final static Logger LOGGER = LoggerFactory.getLogger(AfhandelenInkomendeOpdrachtService.class);
    @Inject
    private InkomendeOpdrachtRepository inkomendeOpdrachtRepository;
    @Inject
    protected UitgaandeOpdrachtRepository uitgaandeOpdrachtRepository;
    @Inject
    protected VerstuurUitgaandeOpdrachtenService verstuurUitgaandeOpdrachtenService;

    protected abstract UitgaandeOpdracht genereerUitgaandeOpdrachten(T opdracht);

    public abstract void verwerkTerugkoppeling(U response);

    protected abstract SoortOpdracht getSoortOpdracht();

    protected abstract UitgaandeOpdracht bepaalUitgaandeOpdrachtWachtenOp(T opdracht, UitgaandeOpdracht uitgaandeOpdracht);

    protected abstract SoortEntiteitEnEntiteitId getSoortEntiteitEnEntiteitId(T message);

    public void verwerkInkomendeOpdracht(T message, String berichtTekst) {
        InkomendeOpdracht inkomendeOpdracht = new InkomendeOpdracht(getSoortOpdracht(), message.getTrackAndTraceId(), berichtTekst);
        inkomendeOpdracht.getUitgaandeOpdrachten().add(genereerUitgaandeOpdrachten(message));

        UitgaandeOpdracht uitgaandeOpdracht = bepaalUitgaandeOpdrachtWachtenOp(message, inkomendeOpdracht.getUitgaandeOpdrachten().iterator().next());

        SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = getSoortEntiteitEnEntiteitId(message);
        if (soortEntiteitEnEntiteitId.getEntiteitId() == null) {
            soortEntiteitEnEntiteitId.setEntiteitId(0L);
        }
        if (message instanceof MetOpmerkingen) {

            List<Opmerking> opmerkingen = ((MetOpmerkingen) message).getOpmerkingen();
            MessagingOpmerkingToUitgaandeOpdrachtMapper mapper = new MessagingOpmerkingToUitgaandeOpdrachtMapper(uitgaandeOpdracht, soortEntiteitEnEntiteitId);
            opmerkingen.stream().forEach(mapper);
            inkomendeOpdracht.getUitgaandeOpdrachten().add(mapper.finish());
        }
        if (message instanceof MetTaken) {
            inkomendeOpdracht.getUitgaandeOpdrachten().addAll(((MetTaken) message).getTaken().stream().map(new MessagingTaakToUitgaandeOpdrachtMapper(uitgaandeOpdracht, soortEntiteitEnEntiteitId)).collect(Collectors.toList()));
        }

        inkomendeOpdracht.getUitgaandeOpdrachten().stream().forEach(uitgaandeOpdracht1 -> uitgaandeOpdracht1.setInkomendeOpdracht(inkomendeOpdracht));

        inkomendeOpdrachtRepository.opslaan(inkomendeOpdracht);

        verstuurUitgaandeOpdrachtenService.verstuurUitgaandeOpdrachten();
    }
}
