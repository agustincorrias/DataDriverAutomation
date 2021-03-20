package base;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utilities.ExcelReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class TestBase extends Operations {
    /*
     *   WebDriver -
     *   Wrapper -
     *   Properties -
     *   Logs -
     *   Extent Reports
     *   DB
     *   Excel -
     *   Mail
     *   ReportNG
     *   Jenkins
     */

    public static WebDriver Webdriver;
    public static Properties config = new Properties();
    public static Properties objectRepository = new Properties();
    public static FileInputStream fis;
    public static ExcelReader excelReader;
    public static driverWrapper driver;

    /* Logger */
    public static Logger log = Logger.getLogger("LOGGER");

    /* Wrapper class */


    /* Properties paths */
    private String userDir = System.getProperty("user.dir");
    private String configPath = userDir + "\\src\\test\\resources\\properties\\config.properties";
    private String objectReposityPath = userDir + "\\src\\test\\resources\\properties\\or.properties";

    /* Executables paths */
    private String chromeDriver = userDir + "\\src\\test\\resources\\executables\\chromedriver.exe";
    private String firefoxDriver = userDir + "\\src\\test\\resources\\executables\\geckodriver.exe";
    private String internetExplorerDriver = userDir + "\\src\\test\\resources\\executables\\IEDriverServer.exe";

    /* Excel path */
    private String excelFile = userDir + "\\src\\test\\resources\\excel\\testdata.xlsx";

    /* URL Path */
    private String urlSite;

    /* Config variables */
    private Long implicitWait;


    @BeforeSuite
    public void setUp() {
        BasicConfigurator.configure();
        if (driver == null) {
            /*  Cargamos el archivo de config.properties */
            try {
                log.info("Load config.properties");
                fis = new FileInputStream(configPath);
                config.load(fis);
            } catch (FileNotFoundException fe) {
                log.error("FileNotFoundException - Method:setUp - Class:TestBase " + fe.getCause());
            } catch (IOException ioe) {
                log.error("IOException - Method:setUp - Class:TestBase " + ioe.getCause());
            } catch (SecurityException se) {
                log.error("SecurityException - Method:setUp - Class:TestBase " + se.getCause());
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error("IOException - Method:setUp - Class:TestBase " + e.getCause());
                }
            }
            /*  Cargamos el archivo de objectRepository.properties */
            try {
                log.info("Load objectRepository.properties");
                fis = new FileInputStream(objectReposityPath);
                objectRepository.load(fis);
            } catch (FileNotFoundException fe) {
                log.error("FileNotFoundException - Method:setUp - Class:TestBase " + fe.getCause());
            } catch (IOException ioe) {
                log.error("IOException - Method:setUp - Class:TestBase " + ioe.getCause());
            } catch (SecurityException se) {
                log.error("SecurityException - Method:setUp - Class:TestBase " + se.getCause());
            }
            /* Verificamos la propiedad del config y definimos el tipo de navegador en el driver*/
            switch (config.getProperty("browser")) {
                case "chrome": {
                    log.info("Launching chrome driver");
                    System.setProperty("webdriver.chrome.driver", chromeDriver);
                    Webdriver = new ChromeDriver();
                    break;
                }
                case "firefox": {
                    log.info("Launching firefox driver");
                    System.setProperty("webdriver.gecko.driver", firefoxDriver);
                    Webdriver = new FirefoxDriver();
                    break;
                }
                case "iexplorer": {
                    log.info("Launching ie driver");
                    System.setProperty("webdriver.ie.driver", internetExplorerDriver);
                    Webdriver = new InternetExplorerDriver();
                    break;
                }
                default: {
                    log.error("broswer property is empty");
                    Assert.fail("Can't load browser property, verify config.properties");
                }
            }

            urlSite = config.getProperty("testsiteurl");
            /* Verify can load correctly property */
            if (urlSite == null) {
                log.error("urlSite property is empty");
                Assert.fail("Can't load site URL property, verify config.properties");
            }
            Webdriver.get(urlSite);
            /* Try lo maximaze window */
            Assert.assertTrue(tryMaximazeWindow(Webdriver));

            implicitWait = Long.parseLong(config.getProperty("implicitwait"));
            if (implicitWait == null) {
                log.error("implicit wait property is empty");
                Assert.fail("Can't load implicit wait verify config.properties");
            }
            /* Setup implicit wait */
            Webdriver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);

            /* Seteamos driver con el wrapper */
            driver = new driverWrapper(Webdriver,log);
            System.out.println("CLASSSSSSSSSSSSSSSS" + driver.getClass());

            /* Setup excel reader */
            try {
                excelReader = new ExcelReader(excelFile);
            } catch ( NullPointerException ne ) {
                log.error("NullPointerException to open excel"  + ne.getCause());
            } catch ( Exception e ){
                log.error("Exception to open excel"  + e.getCause());
            }
        }
    }

    @AfterSuite
    public void tearDown() {

      //  driver.quit();

        try {
            fis.close(); /* Close object repository */
        } catch (IOException e) {
            log.error("IOException - Method:tearDown - Class:TestBase " + e.getCause());
        }
    }


    private boolean tryMaximazeWindow(WebDriver driver) {
        for (int i = 0; i <= 10; i++) {
            try {
                driver.manage().window().maximize();
            } catch (WebDriverException we) {
                return true;
            }
            /* If can maximize window, should be quit */
            break;
        }
        return true;
    }

    public String getPropertyOR(String element) {
        String aux = objectRepository.getProperty(element);
        if (aux == null) {
            log.error("Can`t load " + element + " from ObjectReposity");
        }
        return aux;
    }

}
