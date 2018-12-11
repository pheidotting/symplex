package nl.symplex.test.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import nl.symplex.test.pages.beheren.Zoekscherm;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class Dashboard extends AbstractPage {
    private final static Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);

    SelenideElement nieuweRelatie = $(By.id("nieuweRelatie"));
    SelenideElement nieuwBedrijf = $(By.id("nieuwBedrijf"));

    public Dashboard klikOpNieuweRelatie() {
        click(LOGGER, nieuweRelatie, true);

        return this;
    }

    public Dashboard benIkOpDashboard() {
        nieuweRelatie.shouldBe(Condition.visible);
        nieuwBedrijf.shouldBe(Condition.visible);

        return this;
    }

    public Zoekscherm zoekOpNaam(String naam) {
        setValue(LOGGER, $(By.id("zoekveldenNaam")), naam);
        click(LOGGER, $(By.id("zoekknop")), true);

        return page(Zoekscherm.class);
    }

}
