package nl.dias.web.mapper;

import com.ibm.icu.math.BigDecimal;
import nl.dias.domein.Hypotheek;
import nl.dias.domein.HypotheekPakket;
import nl.lakedigital.djfc.commons.json.JsonHypotheekPakket;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class HypotheekPakketMapper extends Mapper<HypotheekPakket, JsonHypotheekPakket> {
    @Inject
    private HypotheekMapper hypotheekMapper;

    @Override
    public HypotheekPakket mapVanJson(JsonHypotheekPakket json) {
        return null;
    }

    @Override
    public JsonHypotheekPakket mapNaarJson(HypotheekPakket object) {
        JsonHypotheekPakket pakket = new JsonHypotheekPakket();

        pakket.setId(object.getId());
        pakket.setHypotheken(hypotheekMapper.mapAllNaarJson(object.getHypotheken()));

        BigDecimal totaalBedrag = BigDecimal.ZERO;
        for (Hypotheek h : object.getHypotheken()) {
            BigDecimal toeTeVoegen = new BigDecimal(h.getHypotheekBedrag().getBedrag());
            totaalBedrag = totaalBedrag.add(toeTeVoegen);
        }
        pakket.setTotaalBedrag(totaalBedrag.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        return pakket;
    }

    public void setHypotheekMapper(HypotheekMapper hypotheekMapper) {
        this.hypotheekMapper = hypotheekMapper;
    }
}
