package nl.symplex.test;

import com.codeborne.selenide.junit.ScreenShooter;
import nl.symplex.test.pages.Aanmelden;
import nl.symplex.test.pages.Dashboard;
import nl.symplex.test.pages.Inloggen;
import nl.symplex.test.pages.beheren.Relatie;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.open;

@Ignore
public class AanmeldenTest extends AbstractTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(Inloggen.class);

    @Rule
    public ScreenShooter makeScreenshotOnFailure = ScreenShooter.failedTests();

    @Test
    public void aanmelden() {
        String afkorting = null;
        try {
            String voornaam = voornaam();
            Inloggen inloggen = new Aanmelden().aanmelden(basisUrl, bedrijfsnaam(), voornaam, achternaam(), email());

            afkorting = inloggen.getAfkorting();

            inloggen.login(inloggen.getAfkorting() + "." + voornaam.toLowerCase(), getWachtwoord());
            inloggen.wijzigwachtwoord("aabbccddeeffgghh");

            new Dashboard().klikOpNieuweRelatie();

            new Relatie().invullen(voornaam(), voornaam(), "", achternaam(), "06-09-1979", "", "Man", "Ongehuwd", email());
        } finally {
            open(basisUrlRest + "rest/medewerker/kantoor/verwijderen/" + afkorting);
        }
    }

}
