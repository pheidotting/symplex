package nl.lakedigital.djfc.service.verzenden;

import nl.lakedigital.djfc.domain.CommunicatieProduct;
import nl.lakedigital.djfc.domain.UitgaandeBrief;
import nl.lakedigital.djfc.repository.CommunicatieProductRepository;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BriefVerzendService implements AbstractVerzendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BriefVerzendService.class);

    @Inject
    private CommunicatieProductRepository communicatieProductRepository;

    @Override
    public boolean isVoorMij(CommunicatieProduct communicatieProduct) {
        return communicatieProduct instanceof UitgaandeBrief;
    }

    @Override
    public void verzend(CommunicatieProduct communicatieProduct) {
        List<UitgaandeBrief> uitgaandeBriefs = communicatieProductRepository.leesOnverzondenBrieven();

        for (UitgaandeBrief uitgaandeBrief : uitgaandeBriefs) {
            LOGGER.debug("Tijdstip verzending zetten bij Uitgaande brief met id {}", uitgaandeBrief.getId());
            uitgaandeBrief.setDatumTijdVerzending(LocalDateTime.now());

            communicatieProductRepository.opslaan(uitgaandeBrief);

        }
    }
}
