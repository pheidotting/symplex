package nl.symplex.test.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;

public class Inloggen extends AbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(Inloggen.class);

    public void login(String username, String wachtwoord) {
        setValue(LOGGER, $("#identificatie"), username);
        setValue(LOGGER, $("#wachtwoord"), wachtwoord).pressEnter();
    }

    public void wijzigwachtwoord(String nieuwWachtwoord) {
        setValue(LOGGER, $("#nieuwWachtwoord"), nieuwWachtwoord);
        setValue(LOGGER, $("#nieuwWachtwoordNogmaals"), nieuwWachtwoord).pressEnter();
    }
}
