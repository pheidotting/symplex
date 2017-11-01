//package nl.dias.dias_web.selenium;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import nl.dias.dias_web.grizzly.GrizzlyStart;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.server.SeleniumServer;
//
//import com.sun.grizzly.http.embed.GrizzlyWebServer;
//
//public abstract class AbstractSeleniumTest {
//    private GrizzlyWebServer gws;
//    private static SeleniumServer seleniumServer;
//    protected WebDriver driver;
//    protected List<File> schermprints;
//
//    public abstract void voerTestUit();
//
//    private boolean doorgaan() {
//        boolean doorgaan = true;
//        if (System.getenv("webtesten") != null) {
//            if (System.getenv("webtesten").equals("false")) {
//                doorgaan = false;
//            }
//        }
//
//        return doorgaan;
//    }
//
//    @Test
//    public void test() {
//        if (!doorgaan()) {
//            return;
//        }
//
//        voerTestUit();
//    }
//
//    @Before
//    public void init() throws Exception {
//        if (!doorgaan()) {
//            return;
//        }
//        schermprints = new ArrayList<>();
//
//        GrizzlyStart grizzlyStart = new GrizzlyStart();
//        grizzlyStart.setPoort(9999);
//
//        gws = grizzlyStart.getServer();
//
//        try {
//            gws.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//        }
//
//        AbstractSeleniumTest.seleniumServer = new SeleniumServer();
//        AbstractSeleniumTest.seleniumServer.start();
//
//        driver = new FirefoxDriver();
//    }
//
//    @After
//    public void afsluiten() {
//        if (!doorgaan()) {
//            return;
//        }
//        driver.close();
//        AbstractSeleniumTest.seleniumServer.stop();
//        gws.stop();
//    }
//
//    public void maakSchermprint() {
//        try {
//            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//
//            schermprints.add(scrFile);
//        } catch (Exception e) {
//
//        }
//    }
//}