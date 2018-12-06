package nl.symplex.test.pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;

public class Dashboard extends AbstractPage {
    private final static Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);

    public void klikOpNieuweRelatie() {
        click(LOGGER, $(By.id("nieuweRelatie")), true);
    }
}
