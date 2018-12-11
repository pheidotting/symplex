package nl.symplex.test.pages.beheren;

import com.codeborne.selenide.Condition;
import nl.symplex.test.pages.AbstractPage;
import nl.symplex.test.pages.Dashboard;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class Zoekscherm extends AbstractPage {
    private final static Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);

    public Relatie klikEerste() {
        $(By.id("resultatenH3")).shouldBe(Condition.visible);
        click(LOGGER, $(By.name("overzichtNaam")), true);

        return page(Relatie.class);
    }
}
