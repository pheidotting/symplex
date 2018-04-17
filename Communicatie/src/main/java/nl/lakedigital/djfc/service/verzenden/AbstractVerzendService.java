package nl.lakedigital.djfc.service.verzenden;

import nl.lakedigital.djfc.domain.CommunicatieProduct;

public interface AbstractVerzendService {
    void verzend(CommunicatieProduct communicatieProduct);

    boolean isVoorMij(CommunicatieProduct communicatieProduct);
}
