package nl.lakedigital.djfc.service.verzenden;

import nl.lakedigital.djfc.domain.CommunicatieProduct;
import nl.lakedigital.djfc.service.CommunicatieProductService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
public class VerzendService {
    @Inject
    private List<AbstractVerzendService> verzendServiceList;
    @Inject
    private CommunicatieProductService communicatieProductService;

    public void verzend(){
        List<CommunicatieProduct> communicatieProducten=communicatieProductService.leesOnverzondenCommunicatieProducten();

        for(final CommunicatieProduct communicatieProduct:communicatieProducten){
            Optional<AbstractVerzendService> optionalAbstractVerzendService = verzendServiceList.stream().filter(abstractVerzendService -> abstractVerzendService.isVoorMij(communicatieProduct)).findFirst();

            if (optionalAbstractVerzendService.isPresent()) {
                optionalAbstractVerzendService.get().verzend(communicatieProduct);
            }
        }

        communicatieProductService.opslaan(communicatieProducten);
    }
}
