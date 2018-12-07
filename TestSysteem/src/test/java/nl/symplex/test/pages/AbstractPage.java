package nl.symplex.test.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.joda.time.LocalDateTime;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;


public class AbstractPage {
    public enum SoortVeld {INPUT, DATUM, DROPDOWN}
    private void wachtff() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }

    private void wachtOpAanwezigheid(SelenideElement element) {
        while (!element.isDisplayed()) {
            wachtff();
        }
    }

    protected SelenideElement setValue(Logger LOGGER, SelenideElement element, String value) {
        return setValue(LOGGER, element, value, SoortVeld.INPUT);
    }

    protected SelenideElement setValue(Logger LOGGER, SelenideElement element, String value, SoortVeld soortVeld) {
        if (value != null && !"".equals(value)) {
            wachtOpAanwezigheid(element);
            if (soortVeld.equals(SoortVeld.DATUM)) {
                element.sendKeys(value);
            } else if (soortVeld.equals(SoortVeld.DROPDOWN)) {
                //                Select
                element.sendKeys(value);
            } else {
                element.setValue(value);
            }
            Selenide.screenshot(bepaalBestandsNaam(LOGGER, element));

        }
        return element;
    }

    protected SelenideElement click(Logger LOGGER, SelenideElement element, boolean gaatWeg) {
        Selenide.screenshot(bepaalBestandsNaam(LOGGER, element));
        wachtOpAanwezigheid(element);
        element.click();
        if (!gaatWeg) {
            Selenide.screenshot(bepaalBestandsNaam(LOGGER, element));
        }

        return element;
    }

    private String bepaalBestandsNaam(Logger LOGGER, SelenideElement element) {
        String packa = this.getClass().getPackage().toString();
        String loggerNaam = LOGGER.getName();
        String tijdstip = LocalDateTime.now().toString("HH:mm:ss");

        String idOfNaam = element.getAttribute("id");
        if (idOfNaam == null || "".equals(idOfNaam)) {
            idOfNaam = element.getAttribute("name");
        }

        try {
            return tijdstip + " " + loggerNaam.replace(packa + ".", "") + " " + idOfNaam;
        } catch (NoSuchElementException nsee) {
            return tijdstip + " " + loggerNaam.replace(packa + ".", "");
        }
    }
}
