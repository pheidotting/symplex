package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.domain.RekeningNummer;
import nl.lakedigital.djfc.service.RekeningNummerService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class JsonRekeningNummerNaarRekeningNummerMapper extends AbstractMapper<JsonRekeningNummer, RekeningNummer> implements JsonMapper {
    @Inject
    private RekeningNummerService rekeningNummerService;

    @Override
    public RekeningNummer map(JsonRekeningNummer jsonRekeningNummer, Object parent, Object bestaandOjbect) {
        RekeningNummer rekeningNummer = new RekeningNummer();
        if (jsonRekeningNummer.getId() != null) {
            rekeningNummer = rekeningNummerService.lees(jsonRekeningNummer.getId());
        }
        rekeningNummer.setRekeningnummer(jsonRekeningNummer.getRekeningnummer());
        rekeningNummer.setBic("".equals(jsonRekeningNummer.getBic()) ? null : jsonRekeningNummer.getBic());

        return rekeningNummer;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof JsonRekeningNummer;
    }
}
