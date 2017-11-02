package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.domain.RekeningNummer;
import org.springframework.stereotype.Component;

@Component
public class RekeningNummerNaarJsonRekeningNummerMapper extends AbstractMapper<RekeningNummer, JsonRekeningNummer> implements JsonMapper {
    @Override
    public JsonRekeningNummer map(RekeningNummer rekeningNummer, Object parent, Object bestaandObject) {
        JsonRekeningNummer jsonRekeningNummer = new JsonRekeningNummer();
        jsonRekeningNummer.setId(rekeningNummer.getId());
        jsonRekeningNummer.setRekeningnummer(rekeningNummer.getRekeningnummer());
        jsonRekeningNummer.setBic(rekeningNummer.getBic());

        return jsonRekeningNummer;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof RekeningNummer;
    }
}
