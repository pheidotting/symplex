package nl.dias.web.mapper;

import nl.dias.domein.Bedrijf;

import java.util.function.Function;

/**
 * Created by patrickheidotting on 28-04-17.
 */
public class BedrijfToDtoBedrijfMapper implements Function<Bedrijf, nl.lakedigital.djfc.domain.response.Bedrijf> {
    @Override
    public nl.lakedigital.djfc.domain.response.Bedrijf apply(Bedrijf bedrijf) {
        nl.lakedigital.djfc.domain.response.Bedrijf jsonBedrijf = new nl.lakedigital.djfc.domain.response.Bedrijf();


        jsonBedrijf.setKvk(bedrijf.getKvk());
        jsonBedrijf.setNaam(bedrijf.getNaam());
        jsonBedrijf.setcAoVerplichtingen(bedrijf.getcAoVerplichtingen());
        jsonBedrijf.setEmail(bedrijf.getEmail());
        jsonBedrijf.setHoedanigheid(bedrijf.getHoedanigheid());
        jsonBedrijf.setRechtsvorm(bedrijf.getRechtsvorm());
        jsonBedrijf.setInternetadres(bedrijf.getInternetadres());

        return jsonBedrijf;
    }
}
