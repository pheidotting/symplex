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

            inloggen.login(inloggen.getAfkorting().toLowerCase() + "." + voornaam.toLowerCase(), getWachtwoord());
            inloggen.wijzigwachtwoord("aabbccddeeffgghh");

            Dashboard dashboard = new Dashboard().benIkOpDashboard().klikOpNieuweRelatie();

            String voornaamRelatie = voornaam();
            String roepnaamRelatie = voornaam();
            String tussenvoegselRelatie = "";
            String achternaamRelatie = achternaam();
            String gedatumRelatie = "06-09-1979";
            String overlijdensdatumRelatie = "";
            String geslachtRelatie = "Man";
            String burgerlijkestaatRelatie = "Gehuwd GVG";
            String emailRelatie = email();

            new Relatie().invullen(voornaamRelatie, roepnaamRelatie, tussenvoegselRelatie, achternaamRelatie, gedatumRelatie, overlijdensdatumRelatie, geslachtRelatie, burgerlijkestaatRelatie, emailRelatie).klikOpOpslaan();

            String opmerkingTekst = lorem.getParagraphs(1, 5);
            Relatie relatie = dashboard.benIkOpDashboard().zoekOpNaam(achternaamRelatie).klikEerste();
            relatie.controleer(voornaamRelatie, roepnaamRelatie, tussenvoegselRelatie, achternaamRelatie, "1979-09-06", overlijdensdatumRelatie, geslachtRelatie, burgerlijkestaatRelatie, emailRelatie);
            relatie.voegRekeningToe("nl39ingb0651969395", "NL39 INGB 0651 9693 95").voegTelefoonNummerToe("0123456789", "0123 - 45 67 89");
            relatie.voegAdresToe("7891tn", "7891 TN", "26", "Boogschutter", "KLAZIENAVEEN");
            relatie.voegOpmerkingToe(opmerkingTekst);
            relatie.klikOpOpslaan().benIkOpDashboard();

            relatie = dashboard.benIkOpDashboard().zoekOpNaam(achternaamRelatie).klikEerste();
            relatie.controleer(voornaamRelatie, roepnaamRelatie, tussenvoegselRelatie, achternaamRelatie, "1979-09-06", overlijdensdatumRelatie, geslachtRelatie, burgerlijkestaatRelatie, emailRelatie);
            relatie.controleerRekening("NL39 INGB 0651 9693 95").controleerTelefoonNummer("0123 - 45 67 89").controleerAdres("7891 TN", "26", "Boogschutter", "KLAZIENAVEEN").controleerOpmerkgin(opmerkingTekst);
        } finally {
            open(basisUrlRest + "rest/medewerker/kantoor/verwijderen/" + afkorting);
        }
    }

}
