package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.djfc.domain.SoortOpdracht;
import nl.lakedigital.djfc.domain.inkomend.InkomendeOpdracht;
import nl.lakedigital.djfc.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.repository.InkomendeOpdrachtRepository;

import javax.inject.Inject;
import java.util.Set;

public abstract class AfhandelenInkomendeOpdrachtService<T extends AbstractMessage> {
    @Inject
    private InkomendeOpdrachtRepository inkomendeOpdrachtRepository;
    @Inject
    private VerstuurUitgaandeOpdrachtenService verstuurUitgaandeOpdrachtenService;

    protected abstract Set<UitgaandeOpdracht> genereerUitgaandeOpdrachten(T opdracht);

    protected abstract SoortOpdracht getSoortOpdracht();

    public void verwerkInkomendeOpdracht(T message, String berichtTekst) {
        InkomendeOpdracht inkomendeOpdracht = new InkomendeOpdracht(getSoortOpdracht(), message.getTrackAndTraceId(), berichtTekst);
        inkomendeOpdracht.setUitgaandeOpdrachten(genereerUitgaandeOpdrachten(message));

        inkomendeOpdrachtRepository.opslaan(inkomendeOpdracht);

        verstuurUitgaandeOpdrachtenService.verstuurUitgaandeOpdrachten();
    }
}
