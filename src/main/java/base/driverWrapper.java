package base;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.testng.Assert;

public class driverWrapper {

    /* Wrapper class */

    private static WebDriver wdriver;
    private static Logger log;

    public driverWrapper(WebDriver driver, Logger log) {
        this.wdriver = driver;
        this.log = log;
    }

    public void clickElement(By locator) {
        try {
            wdriver.findElement(locator).click();
        } catch (NoSuchElementException nse) {
            Assert.fail("Error when try to localize the element: " + locator);
        } catch (WebDriverException we) {
            Assert.fail("Error when try to click on element: " + locator);
        } catch (Exception e) {

        }
    }

    public void sendKeys( By locator, String textSend){
        try{
            wdriver.findElement(locator).sendKeys(textSend);
        } catch (IllegalArgumentException iae) {
            log.error("Locator is null");
        } catch (WebDriverException we ) {
            log.error("WebDriver exception: " + we.getCause() );
            log.info("Pruebaaaaaaaaaa");
        }
    }

    /*  Take text from alert and accept him */
    public String acceptAlert(){
        String aux = null;
        try{
            aux = wdriver.switchTo().alert().getText();
            wdriver.switchTo().alert().accept();
        } catch ( NoAlertPresentException nape ) {
            log.error("No alert present to accept "  + nape.getCause());
        } catch ( Exception e ) {
            log.error("Generic exception,  No alert present to accept "  + e.getCause());
        }
        return aux;
    }

}
