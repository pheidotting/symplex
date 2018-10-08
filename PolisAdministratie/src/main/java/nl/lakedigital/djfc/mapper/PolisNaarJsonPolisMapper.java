package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.domain.Bedrag;
import nl.lakedigital.djfc.domain.Polis;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class PolisNaarJsonPolisMapper extends AbstractMapper<Polis, JsonPolis> implements JsonMapper {
    @Inject
    private SchadeNaarJsonSchadeMapper schadeMapper;

    @Override
    public JsonPolis map(Polis polis, Object parent, Object bestaandOjbect) {
        String datePattern = "yyyy-MM-dd";

        JsonPolis jsonPolis = new JsonPolis();

        jsonPolis.setId(polis.getId());
        // polissen die al in het systeem staan hoeven net per se een status te
        // hebben
        if (polis.getStatus() != null) {
            jsonPolis.setStatus(polis.getStatus().getOmschrijving());
        }
        jsonPolis.setPolisNummer(polis.getPolisNummer());
        jsonPolis.setKenmerk(polis.getKenmerk());
        if (polis.getPremie() != null) {
            jsonPolis.setPremie(zetBedragOm(polis.getPremie()));
        }
        if (polis.getIngangsDatum() != null) {
            jsonPolis.setIngangsDatum(polis.getIngangsDatum().toString(datePattern));
        }
        if (polis.getEindDatum() != null) {
            jsonPolis.setEindDatum(polis.getEindDatum().toString(datePattern));
        }
        if (polis.getWijzigingsDatum() != null) {
            jsonPolis.setWijzigingsDatum(polis.getWijzigingsDatum().toString(datePattern));
        }
        if (polis.getProlongatieDatum() != null) {
            jsonPolis.setProlongatieDatum(polis.getProlongatieDatum().toString(datePattern));
        }
        if (polis.getBetaalfrequentie() != null) {
            jsonPolis.setBetaalfrequentie(polis.getBetaalfrequentie().getOmschrijving());
        }
        jsonPolis.setDekking(polis.getDekking());
        jsonPolis.setVerzekerdeZaak(polis.getVerzekerdeZaak());
        //        if (polis.getMaatschappij() != null) {
        //            jsonPolis.setMaatschappij(polis.getMaatschappij().toString());
        //        }
        jsonPolis.setSoort(polis.getClass().getSimpleName().replace("Verzekering", ""));
        //                    jsonPolis.setEntiteitId(polis.getPakket().getEntiteitId());
        //                    jsonPolis.setSoortEntiteit(polis.getPakket().getSoortEntiteit().toString());
        jsonPolis.setSchades(schadeMapper.mapAll(polis.getSchades()));
        jsonPolis.setOmschrijvingVerzekering(polis.getOmschrijvingVerzekering());

        return jsonPolis;
    }

    @Override
    public boolean isVoorMij(final Object object) {
        return object instanceof Polis;
    }

    public static String zetBedragOm(Bedrag bedrag) {
        String waarde;
        String[] x = bedrag.getBedrag().toString().split("\\.");
        if (x[1].length() == 1) {
            waarde = bedrag.getBedrag().toString() + "0";
        } else {
            waarde = bedrag.getBedrag().toString() + "";
        }
        return waarde;
    }
}
