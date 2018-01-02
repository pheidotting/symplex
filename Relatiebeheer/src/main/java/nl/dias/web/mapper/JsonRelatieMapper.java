package nl.dias.web.mapper;

import nl.dias.domein.BurgerlijkeStaat;
import nl.dias.domein.Geslacht;
import nl.dias.domein.OnderlingeRelatie;
import nl.dias.domein.Relatie;
import nl.dias.service.GebruikerService;
import nl.lakedigital.djfc.commons.json.JsonOnderlingeRelatie;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Component
public class JsonRelatieMapper extends Mapper<Relatie, nl.lakedigital.djfc.domain.response.Relatie> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonRelatieMapper.class);

    @Inject
    private GebruikerService gebruikerService;

    @Override
    public Relatie mapVanJson(nl.lakedigital.djfc.domain.response.Relatie jsonRelatie) {
        String patternDatum = "yyyy-MM-dd";

        Relatie relatie = new Relatie();
        if (jsonRelatie.getId() != null) {
            relatie = (Relatie) gebruikerService.lees(jsonRelatie.getId());
        }
        relatie.setId(jsonRelatie.getId());
        relatie.setRoepnaam(jsonRelatie.getRoepnaam());
        try {
            relatie.setIdentificatie(jsonRelatie.getIdentificatie());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
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
        if (jsonRelatie.getGeslacht() != null) {
            relatie.setGeslacht(Geslacht.valueOf(jsonRelatie.getGeslacht().substring(0, 1)));
        }

        for (BurgerlijkeStaat bs : BurgerlijkeStaat.values()) {
            if (bs.getOmschrijving().equals(jsonRelatie.getBurgerlijkeStaat())) {
                relatie.setBurgerlijkeStaat(bs);
            }
        }
        relatie.setEmailadres(jsonRelatie.getEmailadres());

        return relatie;
    }

    @Override
    public nl.lakedigital.djfc.domain.response.Relatie mapNaarJson(Relatie relatie) {
        LOGGER.debug("Map naar JSON : {}", ReflectionToStringBuilder.toString(relatie));

        nl.lakedigital.djfc.domain.response.Relatie jsonRelatie = new nl.lakedigital.djfc.domain.response.Relatie();

        jsonRelatie.setId(relatie.getId());
        jsonRelatie.setRoepnaam(relatie.getRoepnaam());
        jsonRelatie.setIdentificatie(relatie.getIdentificatie());
        jsonRelatie.setVoornaam(relatie.getVoornaam());
        if (relatie.getTussenvoegsel() != null) {
            jsonRelatie.setTussenvoegsel(relatie.getTussenvoegsel());
        } else {
            jsonRelatie.setTussenvoegsel("");
        }
        jsonRelatie.setAchternaam(relatie.getAchternaam());
        jsonRelatie.setBsn(relatie.getBsn());
        if (relatie.getGeboorteDatum() != null) {
            jsonRelatie.setGeboorteDatum(relatie.getGeboorteDatum().toString("dd-MM-yyyy"));
        } else {
            jsonRelatie.setGeboorteDatum("");
        }
        if (relatie.getOverlijdensdatum() != null) {
            jsonRelatie.setOverlijdensdatum(relatie.getOverlijdensdatum().toString("dd-MM-yyyy"));
        }
        if (relatie.getGeslacht() != null) {
            jsonRelatie.setGeslacht(relatie.getGeslacht().getOmschrijving());
        }
        if (relatie.getBurgerlijkeStaat() != null) {
            jsonRelatie.setBurgerlijkeStaat(relatie.getBurgerlijkeStaat().getOmschrijving());
        }

        jsonRelatie.setEmailadres(relatie.getIdentificatie());

        LOGGER.debug("Gemapt naar JSON : {}", ReflectionToStringBuilder.toString(jsonRelatie));

        return jsonRelatie;
    }

    private JsonOnderlingeRelatie jsonOnderlingeRelatie(OnderlingeRelatie onderlingeRelatie) {
        JsonOnderlingeRelatie jsonOnderlingeRelatie = new JsonOnderlingeRelatie();

        jsonOnderlingeRelatie.setIdRelatieMet(onderlingeRelatie.getRelatieMet().getId());

        StringBuilder naam = new StringBuilder();
        if (onderlingeRelatie.getRelatieMet().getRoepnaam() != null) {
            naam.append(onderlingeRelatie.getRelatieMet().getRoepnaam());
        } else {
            naam.append(onderlingeRelatie.getRelatieMet().getVoornaam());
        }
        naam.append(" ");
        if (onderlingeRelatie.getRelatieMet().getTussenvoegsel() != null) {
            naam.append(onderlingeRelatie.getRelatieMet().getTussenvoegsel());
            naam.append(" ");
        }
        naam.append(onderlingeRelatie.getRelatieMet().getAchternaam());

        jsonOnderlingeRelatie.setRelatieMet(naam.toString());
        jsonOnderlingeRelatie.setSoortRelatie(onderlingeRelatie.getOnderlingeRelatieSoort().getOmschrijving());

        return jsonOnderlingeRelatie;
    }
}
