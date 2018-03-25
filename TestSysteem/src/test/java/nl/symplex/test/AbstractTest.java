package nl.symplex.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import nl.symplex.web.dto.Mail;
import nl.symplex.web.dto.MailLijst;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

import static com.codeborne.selenide.Selenide.assertNoJavascriptErrors;
import static com.codeborne.selenide.Selenide.close;

public abstract class AbstractTest {
    private static Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);

    public static Long timeOut = 5000L;
    protected XmlMapper mapper = new XmlMapper();

    protected String basisUrl;
    protected String basisUrlRest;

    protected Lorem lorem;

    protected boolean opServer = false;
    protected boolean uitvoeren = false;

    @Before
    public void setup() {
        lorem = LoremIpsum.getInstance();
        LOGGER.debug("OS waar we op draaien : {}", System.getProperty("os.name"));
        basisUrl = "http://localhost:8080/";

        Configuration.reportsFolder = "target/screenshots";

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver");
        String os = System.getProperty("os.name").equals("Mac OS X") ? "" : "-linux";
        System.setProperty("phantomjs.binary.path", "src/test/resources/phantomjs" + os);


        if (!System.getProperty("os.name").equals("Mac OS X")) {
            uitvoeren = true;
            opServer = true;
            basisUrl = "http://tst-diasii:8080/";
            setupPhantomJSDriver();
            WebDriverRunner.setWebDriver(new PhantomJSDriver());
            WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(1920, 1080));
            timeOut = 30000L;
        } else {
            uitvoeren = true;
            setupPhantomJSDriver();
            //            setupChromeDriver();
            //            basisUrl = "http://localhost:8080/";
            basisUrl = "http://tst-diasii:8080/";
        }
        if (uitvoeren) {
            basisUrlRest = basisUrl.replace("djfc/", "") + "dejonge/";

            LOGGER.debug("basisUrlRest {}", basisUrlRest);


            //            LOGGER.info("Naar de inlogpagina {}index.html#inloggen", basisUrl);
            //            open(basisUrl + "inloggen.html");
        }
    }

    @After
    public void afsluiten() {
        if (uitvoeren) {
            assertNoJavascriptErrors();
            close();

        }
    }


    protected String getStringOnMillis() {
        String s = String.valueOf(System.currentTimeMillis());
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return s;
    }


    private void setupPhantomJSDriver() {
        WebDriverRunner.setWebDriver(new PhantomJSDriver());
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(1920, 1080));
    }

    private void setupChromeDriver() {
        WebDriverRunner.setWebDriver(new ChromeDriver());
    }

    protected void setValue(Logger LOGGER, SelenideElement element, String value) {
        element.setValue(value);
        Selenide.screenshot(bepaalBestandsNaam(LOGGER, element));
    }

    private String bepaalBestandsNaam(Logger LOGGER, SelenideElement element) {
        String packa = this.getClass().getPackage().toString();
        String loggerNaam = "package " + LOGGER.getName();
        String tijdstip = LocalDateTime.now().toString();//"HH:mm:ss");

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

    protected String getWachtwoord() {
        MailLijst mailLijst = new MailLijst();
        while (mailLijst == null || mailLijst.getLijst() == null || mailLijst.getLijst().size() == 0) {
            mailLijst = getMailLijst("http://localhost:8080/test/rest/mails");
        }

        Mail mail = mailLijst.getLijst().get(0);

        String message = mail.getMessage();
        int pos = message.indexOf("Nieuw wachtwoord : <span>");
        message = message.substring(pos + "Nieuw wachtwoord : <span>".length());
        pos = message.indexOf("</span>");
        return message.substring(0, pos);
    }

    protected MailLijst getMailLijst(String uri) {
        URL url;
        try {
            url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            connection.setReadTimeout(20000);
            connection.setConnectTimeout(20000);

            InputStream xml = connection.getInputStream();
            MailLijst response = mapper.readValue(xml, MailLijst.class);

            connection.disconnect();

            return response;
        } catch (IOException e) {
            LOGGER.error("Fout bij omzetten xml {}", e.getStackTrace());
        }

        return null;
    }
}