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
        String afkorting = getAfkorting();

        setValue(LOGGER, $(By.id("emailadres")), emailadres).pressEnter();

        //Zou eigenlijk niet nodig hoeven zijn, maar PhantomJS heeft hier wat moeite mee anders
        open(basisUrl + "inloggen.html");

        Inloggen inloggen = page(Inloggen.class);
        inloggen.setAfkorting(afkorting);
        return inloggen;
    }

    public String getAfkorting() {
        return $(By.id("afkorting")).getValue();
    }
}
