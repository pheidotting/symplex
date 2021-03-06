package nl.dias.mapper;

import nl.dias.domein.Kantoor;
import nl.dias.domein.Rechtsvorm;
import nl.dias.repository.KantoorRepository;
import nl.lakedigital.djfc.commons.json.JsonKantoor;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class JsonKantoorNaarKantoorMapper extends AbstractMapper<JsonKantoor, Kantoor> {
    @Inject
    private KantoorRepository kantoorRepository;

    @Override
    public Kantoor map(JsonKantoor jsonKantoor, Object parent, Object bestaandObject) {
        Kantoor kantoor;
        if (jsonKantoor.getId() != null) {
            kantoor = kantoorRepository.lees(jsonKantoor.getId());
        } else {
            kantoor = new Kantoor();
        }

        kantoor.setNaam(jsonKantoor.getNaam());
        kantoor.setKvk(kantoor.getKvk());
        kantoor.setRechtsvorm(Rechtsvorm.valueOf(jsonKantoor.getRechtsvorm()));
        kantoor.setEmailadres(jsonKantoor.getEmailadres());
        kantoor.setAfkorting(jsonKantoor.getAfkorting());

        return kantoor;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof JsonKantoor;
    }
}
