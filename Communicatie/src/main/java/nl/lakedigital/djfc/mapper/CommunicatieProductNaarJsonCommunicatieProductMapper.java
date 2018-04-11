package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonCommunicatieProduct;
import nl.lakedigital.djfc.commons.json.JsonUitgaandeBrief;
import nl.lakedigital.djfc.commons.json.JsonUitgaandeEmail;
import nl.lakedigital.djfc.domain.CommunicatieProduct;
import nl.lakedigital.djfc.domain.UitgaandeBrief;
import nl.lakedigital.djfc.domain.UitgaandeEmail;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommunicatieProductNaarJsonCommunicatieProductMapper extends AbstractMapper<CommunicatieProduct, JsonCommunicatieProduct> implements JsonMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunicatieProductNaarJsonCommunicatieProductMapper.class);
    private final String datumTijdFormaat = "yyyy-MM-dd HH:mm";

    //    @Inject
    private AdresClient adresClient;

    @Override
    public JsonCommunicatieProduct map(CommunicatieProduct communicatieProduct, Object parent, Object bestaandObject) {
        JsonCommunicatieProduct jsonCommunicatieProduct = null;

        LOGGER.debug(ReflectionToStringBuilder.toString(communicatieProduct, ToStringStyle.SHORT_PREFIX_STYLE));

        if (communicatieProduct instanceof UitgaandeBrief) {
            jsonCommunicatieProduct = mapUitgaandeBrief((UitgaandeBrief) communicatieProduct);
        } else if (communicatieProduct instanceof UitgaandeEmail) {
            jsonCommunicatieProduct = mapUitgaandeEmail((UitgaandeEmail) communicatieProduct);
        }

        if (jsonCommunicatieProduct != null) {
            jsonCommunicatieProduct.setId(communicatieProduct.getId());
            if (communicatieProduct.getDatumTijdCreatie() != null) {
                jsonCommunicatieProduct.setDatumTijdCreatie(communicatieProduct.getDatumTijdCreatie().toString(datumTijdFormaat));
            }
            if (communicatieProduct.getDatumTijdVerzending() != null) {
                jsonCommunicatieProduct.setDatumTijdVerzending(communicatieProduct.getDatumTijdVerzending().toString(datumTijdFormaat));
            }
            jsonCommunicatieProduct.setSoortEntiteit(communicatieProduct.getSoortEntiteit());
            jsonCommunicatieProduct.setEntiteitId(communicatieProduct.getEntiteitId());
            jsonCommunicatieProduct.setTekst(communicatieProduct.getTekst());
            if (communicatieProduct.getAntwoordOp() != null) {
                jsonCommunicatieProduct.setAntwoordOp(communicatieProduct.getAntwoordOp().getId());
            }
        }

        return jsonCommunicatieProduct;
    }

    private JsonUitgaandeBrief mapUitgaandeBrief(UitgaandeBrief uitgaandeBrief) {
        JsonUitgaandeBrief json = new JsonUitgaandeBrief();

        List<JsonAdres> adressen=adresClient.lijst("COMMUNICATIEPRODUCT",uitgaandeBrief.getId());

        if(!adressen.isEmpty()){
            JsonAdres adres=adressen.get(0);

            json.setHuisnummer(adres.getHuisnummer());
            json.setStraat(adres.getStraat());
            json.setToevoeging(adres.getToevoeging());
            json.setPostcode(adres.getPostcode());
            json.setPlaats(adres.getPlaats());
        }

        return json;
    }

    private JsonUitgaandeEmail mapUitgaandeEmail(UitgaandeEmail uitgaandeEmail) {
        JsonUitgaandeEmail json = new JsonUitgaandeEmail();

        json.setOnderwerp(uitgaandeEmail.getOnderwerp());
        if(uitgaandeEmail.getEmailadres()!=null){
            json.setEmailadres(uitgaandeEmail.getEmailadres().getEmailadres());
        }

        return json;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof CommunicatieProduct;
    }
}
