package testcases;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddCustomerTest extends TestBase {

    private String btnAddCustomer;
    private String lblFirstname;
    private String lblLastname;
    private String lblPostcode;
    private String btnAddCustomerAction;

    /*    String validators         */
    private String idCustomerAdd;

    @BeforeTest
    private void loadSelectors() {
        btnAddCustomer = getPropertyOR("btnAddCustomer");
        lblFirstname = getPropertyOR("lblFirstname");
        lblLastname = getPropertyOR("lblLastname");
        lblPostcode = getPropertyOR("lblPostcode");
        btnAddCustomerAction = getPropertyOR("btnAddCustomerAction");
    }

    @Test//(dataProvider = "excel")
    public void addCustomer() {
        log.info("Inside addCustomber test");
        driver.clickElement(By.cssSelector(btnAddCustomer));
        driver.sendKeys(By.cssSelector(lblFirstname),"Agustin");
        driver.sendKeys(By.cssSelector(lblLastname),"Automation");
        driver.sendKeys(By.cssSelector(lblPostcode),"7600");
        driver.clickElement(By.cssSelector(btnAddCustomerAction));
        /* Verify alert and take id form string */
        idCustomerAdd = driver.acceptAlert();
        if ( idCustomerAdd != null && idCustomerAdd.contains("Customer added successfully")){
            /* Split text and save idCustomer */
            String[] parts = idCustomerAdd.split(":");
            idCustomerAdd = parts[1];
            Assert.assertTrue(isNumeric(idCustomerAdd));
            log.info("Customer add correctly with id: " + idCustomerAdd);
        } else {
            Assert.fail("Fail to take idCustomer or error in message");
        }
    }



    /*
    @DataProvider(name = "excel")
    public Object[][] getDataFromExcel() {


        return new Object[][];
    } */

}
