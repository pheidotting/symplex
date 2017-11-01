//package nl.dias.dias_web.hulp;
//
//import java.util.NoSuchElementException;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.StaleElementReferenceException;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//
//public class Checks {
//    public static void checkOfVeldNietVoorkomt(WebElement element) {
//        try {
//            element.sendKeys(" ");
//            throw new StaleElementReferenceException("er had een exception moeten komen");
//        } catch (StaleElementReferenceException e) {
//        }
//    }
//
//    public static void checkOfVeldNietVoorkomt(String naam, WebDriver driver) {
//        boolean gevonden = false;
//
//        WebElement element = null;
//        try {
//            element = driver.findElement(By.id(naam));
//            gevonden = true;
//        } catch (NoSuchElementException e1) {
//            gevonden = false;
//        }
//
//        if (gevonden) {
//            // dan kunnen we nog ff kijken of hij misschien onzichtbaar is..
//            try {
//                element.sendKeys(" ");
//            } catch (StaleElementReferenceException e) {
//                gevonden = false;
//            }
//        }
//
//        if (gevonden) {
//            throw new StaleElementReferenceException("Het element " + naam + " komt voor op het scherm");
//        }
//    }
//
//    public static void checkOfVeldWelVoorkomt(WebElement element) {
//        try {
//            element.sendKeys(" ");
//        } catch (StaleElementReferenceException e) {
//            throw new StaleElementReferenceException("er had geen exception mogen komen");
//        }
//    }
//}
