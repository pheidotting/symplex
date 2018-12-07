package nl.symplex.test.pages.beheren;

import nl.symplex.test.pages.AbstractPage;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;

public class Relatie extends AbstractPage {
    private final static Logger LOGGER = LoggerFactory.getLogger(Relatie.class);

    public void invullen(String voornaam, String roepnaam, String tussenvoegsel, String achternaam, String geboorteDatum, String overlijdensdatum, String geslacht, String burgerlijkeStaat, String emailadres) {
        setValue(LOGGER, $(By.id("voornaam")), voornaam);
        setValue(LOGGER, $(By.id("roepnaam")), roepnaam);
        setValue(LOGGER, $(By.id("tussenvoegsel")), tussenvoegsel);
        setValue(LOGGER, $(By.id("achternaam")), achternaam);
        setValue(LOGGER, $(By.id("geboorteDatum")), geboorteDatum, SoortVeld.DATUM);
        setValue(LOGGER, $(By.id("overlijdensdatum")), overlijdensdatum, SoortVeld.DATUM);
        setValue(LOGGER, $(By.id("geslacht")), geslacht);
        setValue(LOGGER, $(By.id("burgerlijkeStaat")), burgerlijkeStaat);
        setValue(LOGGER, $(By.id("emailadres")), emailadres);

    }
}
