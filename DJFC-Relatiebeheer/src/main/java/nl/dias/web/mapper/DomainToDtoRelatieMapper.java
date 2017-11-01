package nl.dias.web.mapper;

import nl.dias.domein.Relatie;

import java.util.function.Function;

public class DomainToDtoRelatieMapper implements Function<Relatie, nl.lakedigital.djfc.domain.response.Relatie> {
    @Override
    public nl.lakedigital.djfc.domain.response.Relatie apply(Relatie domain) {
        String datumFormaat = "yyyy-MM-dd";

        nl.lakedigital.djfc.domain.response.Relatie relatie = new nl.lakedigital.djfc.domain.response.Relatie();

        relatie.setAchternaam(domain.getAchternaam());
        relatie.setRoepnaam(domain.getRoepnaam());
        relatie.setVoornaam(domain.getVoornaam());
        relatie.setTussenvoegsel(domain.getTussenvoegsel());
        relatie.setBsn(domain.getBsn());
        if (domain.getGeboorteDatum() != null) {
            relatie.setGeboorteDatum(domain.getGeboorteDatum().toString(datumFormaat));
        }
        if (domain.getOverlijdensdatum() != null) {
            relatie.setOverlijdensdatum(domain.getOverlijdensdatum().toString(datumFormaat));
        }
        if (domain.getGeslacht() != null) {
            relatie.setGeslacht(domain.getGeslacht().getOmschrijving());
        }
        if (domain.getBurgerlijkeStaat() != null) {
            relatie.setBurgerlijkeStaat(domain.getBurgerlijkeStaat().getOmschrijving());
        }
        relatie.setEmailadres(domain.getEmailadres());


        return relatie;
    }
}
