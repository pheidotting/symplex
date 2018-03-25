package nl.symplex.test.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.joda.time.LocalDateTime;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;


public class AbstractPage {
    private void wachtff() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }
    protected SelenideElement setValue(Logger LOGGER, SelenideElement element, String value) {
        wachtff();
        element.setValue(value);
        wachtff();
        Selenide.screenshot(bepaalBestandsNaam(LOGGER, element));

        return element;
    }

    protected SelenideElement click(Logger LOGGER, SelenideElement element) {
        Selenide.screenshot(bepaalBestandsNaam(LOGGER, element));
        wachtff();
        element.click();
        wachtff();
        Selenide.screenshot(bepaalBestandsNaam(LOGGER, element));

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
