package nl.symplex.test;

import com.codeborne.selenide.junit.ScreenShooter;
import nl.symplex.test.pages.Aanmelden;
import nl.symplex.test.pages.Inloggen;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.open;

public class AanmeldenTest extends AbstractTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(Inloggen.class);

    @Rule
    public ScreenShooter makeScreenshotOnFailure = ScreenShooter.failedTests();

    @Test
    @Ignore
    public void aanmelden() {
        System.out.println("Start test");
        System.out.println(basisUrl);
        try {
            Inloggen inloggen = new Aanmelden().aanmelden(basisUrl, "Fa. List Bedrog", "henkie", "jansen", "henk@heidotting.nl");

            inloggen.login("flb.henkie", getWachtwoord());
            inloggen.wijzigwachtwoord("aabbccddeeffgghh");
        } finally {
            open(basisUrlRest + "rest/medewerker/kantoor/verwijderen/flb");
        }
    }

}
