package testcases;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {

    private String btnBankManagerLogin;

    @BeforeClass
    private void loadSelectors() {
        btnBankManagerLogin = getPropertyOR("btnBankManagerLogin");
    }

    @Test
    public void loginAsBankManager() throws InterruptedException {
        log.info("Inside login as bank manager test");
        log.info("Locator test: " + btnBankManagerLogin);
      //  Webdriver.findElement(By.cssSelector(btnBankManagerLogin)).click();
        driver.clickElement(By.cssSelector(btnBankManagerLogin));
        log.info("Loggin as bank manager success");
    }


}