package nl.dias.mapper;

import nl.dias.domein.BurgerlijkeStaat;
import nl.dias.domein.Geslacht;
import nl.dias.domein.Relatie;
import nl.dias.repository.GebruikerRepository;
import nl.dias.web.mapper.RelatieMapper;
import nl.lakedigital.djfc.commons.json.JsonRelatie;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Component
public class JsonRelatieNaarRelatieMapper extends AbstractMapper<JsonRelatie, Relatie> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RelatieMapper.class);

    @Inject
    private GebruikerRepository gebruikerRepository;

    @Override
    public Relatie map(JsonRelatie jsonRelatie, Object bestaandObject, Object bestaandOjbect) {
        String patternDatum = "dd-MM-yyyy";

        Relatie relatie = new Relatie();
        if (jsonRelatie.getId() != null) {
            relatie = (Relatie) gebruikerRepository.lees(jsonRelatie.getId());
        }
        relatie.setId(jsonRelatie.getId());
        relatie.setRoepnaam(jsonRelatie.getRoepnaam());
        try {
            relatie.setIdentificatie(jsonRelatie.getIdentificatie());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("fout", e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("fout", e);
        }
        relatie.setVoornaam(jsonRelatie.getVoornaam());
        relatie.setTussenvoegsel(jsonRelatie.getTussenvoegsel());
        relatie.setAchternaam(jsonRelatie.getAchternaam());
        if (jsonRelatie.getOverlijdensdatum() != null && !"".equals(jsonRelatie.getOverlijdensdatum())) {
            relatie.setOverlijdensdatum(LocalDate.parse(jsonRelatie.getOverlijdensdatum(), DateTimeFormat.forPattern(patternDatum)));
        }
        if (jsonRelatie.getGeboorteDatum() != null && !"".equals(jsonRelatie.getGeboorteDatum())) {
            relatie.setGeboorteDatum(LocalDate.parse(jsonRelatie.getGeboorteDatum(), DateTimeFormat.forPattern(patternDatum)));
        }
        relatie.setBsn(jsonRelatie.getBsn());
        relatie.setGeslacht(Geslacht.valueOf(jsonRelatie.getGeslacht().substring(0, 1)));

        for (BurgerlijkeStaat bs : BurgerlijkeStaat.values()) {
            if (bs.getOmschrijving().equals(jsonRelatie.getBurgerlijkeStaat())) {
                relatie.setBurgerlijkeStaat(bs);
            }
        }

        return relatie;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof JsonRelatie;
    }
}
