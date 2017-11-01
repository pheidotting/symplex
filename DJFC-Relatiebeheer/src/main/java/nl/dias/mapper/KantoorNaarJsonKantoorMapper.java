package nl.dias.mapper;

import nl.dias.domein.Kantoor;
import nl.lakedigital.djfc.commons.json.JsonKantoor;
import org.springframework.stereotype.Component;

@Component
public class KantoorNaarJsonKantoorMapper extends AbstractMapper<Kantoor, JsonKantoor> {
    @Override
    public JsonKantoor map(Kantoor kantoor, Object parent, Object bestaandObject) {
        JsonKantoor jsonKantoor = new JsonKantoor();

        jsonKantoor.setId(kantoor.getId());
        jsonKantoor.setNaam(kantoor.getNaam());
        jsonKantoor.setKvk(kantoor.getKvk());
        jsonKantoor.setBtwNummer(kantoor.getBtwNummer());
        jsonKantoor.setDatumOprichting(kantoor.getDatumOprichting().toString("dd-MM-yyyy"));
        jsonKantoor.setRechtsvorm(kantoor.getRechtsvorm().getOmschrijving());
        jsonKantoor.setSoortKantoor(kantoor.getSoortKantoor().getOmschrijving());
        jsonKantoor.setEmailadres(kantoor.getEmailadres());
        jsonKantoor.setAfkorting(kantoor.getAfkorting());

        return jsonKantoor;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof Kantoor;
    }
}
