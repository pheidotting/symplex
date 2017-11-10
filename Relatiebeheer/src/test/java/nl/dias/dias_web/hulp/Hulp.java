//package nl.dias.dias_web.hulp;
//
//import org.joda.time.LocalDateTime;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.Select;
//
//public class Hulp {
//    private static final int timeout = 1000;
//    public static final int zoekTimeOut = 25;
//
//    private Hulp() {
//    }
//
//    public static boolean doeCheckMetTimeOut(boolean check) {
//        LocalDateTime timeOut = new LocalDateTime().plusSeconds(zoekTimeOut);
//        while (!check && LocalDateTime.now().isBefore(timeOut)) {
//            wachtFf();
//            return true;
//        }
//        return false;
//    }
//
//    public static void vulVeld(WebElement element, String waarde) {
//        LocalDateTime timeOut = new LocalDateTime().plusSeconds(zoekTimeOut);
//        System.out.println(timeout);
//        System.out.println(LocalDateTime.now());
//        while (!element.isDisplayed() && LocalDateTime.now().isBefore(timeOut)) {
//            wachtFf();
//            System.out.println(LocalDateTime.now());
//        }
//        if (waarde != null && !waarde.equals("") && !waarde.equals(element.getText()) && !waarde.equals(getText(element))) {
//            do {
//                element.clear();
//                element.sendKeys(waarde);
//                wachtFf();
//            } while (!Hulp.getText(element).equals(waarde));
//        }
//        element.sendKeys(Keys.TAB);
//    }
//
//    public static void klikEnWacht(WebElement element) {
//        Hulp.klikEnWacht(element, timeout);
//    }
//
//    public static void wachtOpElement(WebElement element) {
//        LocalDateTime timeOut = new LocalDateTime().plusSeconds(zoekTimeOut);
//        while ((element == null || !element.isDisplayed()) && LocalDateTime.now().isBefore(timeOut)) {
//            wachtFf(timeout);
//        }
//        if (!element.isDisplayed()) {
//            throw new NoSuchElementException(element + " niet gevonden");
//        }
//    }
//
//    public static void klikEnWacht(WebElement element, int timeout) {
//        wachtFf(timeout);
//        LocalDateTime timeOut = new LocalDateTime().plusSeconds(zoekTimeOut);
//        try {
//            while (!element.isDisplayed() && LocalDateTime.now().isBefore(timeOut)) {
//                wachtFf(timeout);
//            }
//            element.click();
//        } catch (NoSuchElementException e) {
//            try {
//                element.click();
//            } catch (NoSuchElementException e1) {
//                // doeCheckMetTimeOut(element.isDisplayed());
//            }
//        }
//    }
//
//    public static void naarAdres(WebDriver driver, String adres) {
//        driver.get(adres);
//
//        wachtFf();
//    }
//
//    public static String getText(WebElement element) {
//        return element.getAttribute("value");
//    }
//
//    public static void wachtFf() {
//        wachtFf(timeout);
//    }
//
//    public static void wachtFf(int timeout) {
//        try {
//            Thread.sleep(timeout);
//        } catch (InterruptedException e) {
//        }
//    }
//
//    public static boolean waardeUitCheckbox(WebElement element) {
//        return "true".equals(element.getAttribute("checked"));
//    }
//
//    public static void selecteerUitSelectieBox(WebElement element, String waarde) {
//        Select select = new Select(element);
//        doeCheckMetTimeOut(element.isDisplayed());
//        try {
//            select.selectByValue(waarde);
//        } catch (NoSuchElementException e) {
//            select.selectByVisibleText(waarde);
//        }
//        wachtFf();
//    }
//
//    public static boolean controleerVeld(WebElement element, String verwacht) {
//        boolean ok = false;
//
//        try {
//            ok = getText(element).equals(verwacht);
//            LocalDateTime timeOut = new LocalDateTime().plusSeconds(zoekTimeOut);
//            while ((!ok) && LocalDateTime.now().isBefore(timeOut)) {
//                wachtFf(timeout);
//                ok = getText(element).equals(verwacht);
//            }
//            // if (!ok) {
//            // Hulp.wachtFf();
//            // ok = getText(element).equals(verwacht);
//            // }
//        } catch (NullPointerException e) {
//            ok = element.getText().equals(verwacht);
//            LocalDateTime timeOut = new LocalDateTime().plusSeconds(zoekTimeOut);
//            while ((!ok) && LocalDateTime.now().isBefore(timeOut)) {
//                wachtFf(timeout);
//                ok = element.getText().equals(verwacht);
//            }
//            // if (!ok) {
//            // Hulp.wachtFf();
//            // ok = element.getText().equals(verwacht);
//            // }
//        }
//
//        return ok;
//    }
//}