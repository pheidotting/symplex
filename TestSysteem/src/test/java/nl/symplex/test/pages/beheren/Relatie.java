package nl.symplex.test.pages.beheren;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import nl.symplex.test.pages.AbstractPage;
import nl.symplex.test.pages.Dashboard;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static nl.symplex.test.pages.AbstractPage.SoortVeld.DATUM;
import static nl.symplex.test.pages.AbstractPage.SoortVeld.DROPDOWN;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Relatie extends AbstractPage {
    private final static Logger LOGGER = LoggerFactory.getLogger(Relatie.class);

    public Relatie invullen(String voornaam, String roepnaam, String tussenvoegsel, String achternaam, String geboorteDatum, String overlijdensdatum, String geslacht, String burgerlijkeStaat, String emailadres) {
        setValue(LOGGER, $(By.id("voornaam")), voornaam);
        setValue(LOGGER, $(By.id("roepnaam")), roepnaam);
        setValue(LOGGER, $(By.id("tussenvoegsel")), tussenvoegsel);
        setValue(LOGGER, $(By.id("achternaam")), achternaam);
        setValue(LOGGER, $(By.id("geboorteDatum")), geboorteDatum, DATUM);
        setValue(LOGGER, $(By.id("overlijdensdatum")), overlijdensdatum, DATUM);
        setValue(LOGGER, $(By.id("geslacht")), geslacht, DROPDOWN);
        setValue(LOGGER, $(By.id("burgerlijkeStaat")), burgerlijkeStaat, DROPDOWN);
        setValue(LOGGER, $(By.id("emailadres")), emailadres);

        return this;
    }

    public Relatie controleer(String voornaam, String roepnaam, String tussenvoegsel, String achternaam, String geboorteDatum, String overlijdensdatum, String geslacht, String burgerlijkeStaat, String emailadres) {
        $(By.id("voornaam")).shouldBe(Condition.visible);

        assertThat($(By.id("voornaam")).getValue(), is(voornaam));
        assertThat($(By.id("roepnaam")).getValue(), is(roepnaam));
        assertThat($(By.id("tussenvoegsel")).getValue(), is(tussenvoegsel));
        assertThat($(By.id("achternaam")).getValue(), is(achternaam));
        assertThat($(By.id("geboorteDatum")).getValue(), is(geboorteDatum));
        assertThat($(By.id("overlijdensdatum")).getValue(), is(overlijdensdatum));
        assertThat($(By.id("geslacht")).getValue(), is(geslacht));
        assertThat($(By.id("burgerlijkeStaat")).getValue(), is(burgerlijkeStaat));
        assertThat($(By.id("emailadres")).getValue(), is(emailadres));

        Selenide.screenshot(bepaalBestandsNaam(LOGGER, $(By.id("emailadres"))));

        return this;
    }

    public Relatie voegRekeningToe(String nummer, String nummerOpgemaakt) {
        click(LOGGER, $(By.id("voegRekeningToe")), false);
        SelenideElement element = $(By.name("rekeningnummernummer"));

        setValue(LOGGER, element, nummer);
        element.sendKeys(Keys.TAB);
        assertThat(element.getValue(), is(nummerOpgemaakt));

        return this;
    }

    public Relatie voegTelefoonNummerToe(String nummer, String nummerOpgemaakt) {
        click(LOGGER, $(By.id("voegTelefoonNummerToe")), false);
        SelenideElement element = $(By.name("telnummer"));
        setValue(LOGGER, $(By.name("soorttelnummer")), "MOBIEL", DROPDOWN);

        setValue(LOGGER, element, nummer);
        element.sendKeys(Keys.TAB);
        assertThat(element.getValue(), is(nummerOpgemaakt));

        return this;
    }

    public Relatie voegAdresToe(String postcode, String postcodeOpgemaakt, String huisnummer, String straat, String plaats) {
        click(LOGGER, $(By.id("voegAdresToe")), false);
        SelenideElement postcodeElement = $(By.name("postcode"));
        SelenideElement huisnummerElement = $(By.name("huisnummer"));
        setValue(LOGGER, $(By.name("soortadres")), "WOONADRES", DROPDOWN);

        setValue(LOGGER, postcodeElement, postcode);
        setValue(LOGGER, huisnummerElement, huisnummer);
        huisnummerElement.sendKeys(Keys.TAB);

        assertThat(postcodeElement.getValue(), is(postcodeOpgemaakt));
        assertThat($(By.id("straat")).getValue(), is(straat));
        assertThat($(By.id("plaats")).getValue(), is(plaats));

        return this;
    }

    public Relatie voegOpmerkingToe(String opmerkingTekst) {
        click(LOGGER, $(By.id("voegOpmerkingToe")), false);

        setValue(LOGGER, $(By.name("nieuweOpmerking")), opmerkingTekst);

        return this;
    }

    public Relatie controleerRekening(String nummer) {
        SelenideElement element = $(By.name("rekeningnummernummer"));
        wachtOpAanwezigheid(element);

        assertThat(element.getValue(), is(nummer));

        return this;

    }

    public Relatie controleerTelefoonNummer(String nummer) {
        SelenideElement element = $(By.name("telnummer"));
        wachtOpAanwezigheid(element);

        assertThat(element.getValue(), is(nummer));

        return this;
    }

    public Relatie controleerAdres(String postcode, String huisnummer, String straat, String plaats) {
        SelenideElement postcodeElement = $(By.name("postcode"));
        wachtOpAanwezigheid(postcodeElement);

        assertThat(postcodeElement.getValue(), is(postcode));
        assertThat($(By.id("huisnummer")).getValue(), is(huisnummer));
        assertThat($(By.id("straat")).getValue(), is(straat));
        assertThat($(By.id("plaats")).getValue(), is(plaats));

        return this;
    }

    public Relatie controleerOpmerkgin(String opmerkingTekst) {
        //TODO
        assertThat($(By.id("tekstundefined")).getText(), is(opmerkingTekst));

        return this;
    }


    public Dashboard klikOpOpslaan() {
        click(LOGGER, $(By.id("opslaanRelatie")), true);

        return page(Dashboard.class);
    }
}
