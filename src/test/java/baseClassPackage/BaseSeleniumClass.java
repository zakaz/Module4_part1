package baseClassPackage;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

/**
 * Created by Zakir_Mustafin on 1/30/2017.
 */
public class BaseSeleniumClass {
    protected Logger logger;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String themeOfSendingLetter = null;



    @BeforeSuite
    public void beforeSuite() { System.out.println("Before suite");
//        logger = Logger.getLogger("new logger");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\zakir_mustafin@epam.com\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        themeOfSendingLetter = String.valueOf(System.currentTimeMillis());

    }

    @BeforeClass(groups = "firstGroup")
    public void beforeClass() { System.out.println("Before class");

    }

    @AfterSuite
    public void afterSuite() { System.out.println("After suite");
        driver.quit();
    }

    @AfterClass
    public void afterClass() { System.out.println("After class");

    }

}
