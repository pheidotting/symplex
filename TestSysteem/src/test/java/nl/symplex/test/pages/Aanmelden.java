package nl.symplex.test.pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.*;

public class Aanmelden extends AbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(Aanmelden.class);

    // bedrijfsnaam
    // afkorting
    // foutmeldingAfkortingKomtVoor
    // voornaam
    // achternaam
    // emailadres
    // loginnaam
    // aanmeldButton
    public Inloggen aanmelden(String basisUrl, String bedrijfsnaam, String voornaam, String achternaam, String emailadres) {
        open(basisUrl + "aanmelden.html");

        setValue(LOGGER, $(By.id("bedrijfsnaam")), bedrijfsnaam);
        setValue(LOGGER, $(By.id("voornaam")), voornaam);
        setValue(LOGGER, $(By.id("achternaam")), achternaam);
        setValue(LOGGER, $(By.id("emailadres")), emailadres);
        click(LOGGER, $(By.id("aanmeldButton")).pressEnter());

        return page(Inloggen.class);
    }
}
