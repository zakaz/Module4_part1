package seleniumTestsPackage;

import baseClassPackage.DataProviderClass;
import org.apache.log4j.Logger;
import baseClassPackage.BaseSeleniumClass;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Created by Zakir_Mustafin on 1/30/2017.
 */
public class TestWebPage_1 extends BaseSeleniumClass {

//    @BeforeTest
//    public void beforeTest(){
//        driver = new ChromeDriver();
//    }
//
//    @AfterTest
//    public void afterTest(){
//        driver.quit();
//    }


    @Test(groups = "firstGroup", dataProviderClass = DataProviderClass.class, dataProvider = "dataForOpeningWepPage")
    public void openWebPage(String baseUrl) throws InterruptedException {
        logger = Logger.getLogger("Test-1. Open Web Page");
        driver.get(baseUrl);
        String expectedTitle = "Яндекс.Почта — бесплатная электронная почта";
        String actualTitle = driver.getTitle();
        wait.until(ExpectedConditions.titleIs(expectedTitle));
        logger.info("We are Expected title - '" + expectedTitle + "'. And found - '" + actualTitle + "'.");
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test(groups = "firstGroup", dependsOnMethods = "openWebPage", alwaysRun = true, dataProviderClass = DataProviderClass.class, dataProvider = "loginToTheMailBox")
    public void enterTheMail(String myLogin, String myPass) {
        logger = Logger.getLogger("Test-2. Enter login and Pass");
//        driver.navigate().refresh();
        WebElement loginField = driver.findElement(By.xpath("//*[@id=\"nb-1\"]/span/input"));
        loginField.sendKeys(myLogin);
        WebElement passField = driver.findElement(By.xpath("//*[@id=\"nb-2\"]/span/input"));
        passField.sendKeys(myPass);
        passField.sendKeys(Keys.ENTER);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Входящие']")));
        String actualWord = element.getText();
//        Thread.sleep(2000);
//        boolean expectedWord = true;
//        boolean actualWord = driver.getPageSource().contains("Входящие");
//        Thread.sleep(2000);
        logger.info("We are Expected word on page - 'Входящие'. And found - '" + actualWord + "'.");
        Assert.assertEquals(actualWord, "Входящие");
    }

    @Test(dependsOnMethods = "enterTheMail", dataProviderClass = DataProviderClass.class, dataProvider = "writeTheLetter")
    public void createNewMail(String mailAdress, String letterBody) throws InterruptedException {
        logger = Logger.getLogger("Test-3. Create new mail");
        driver.findElement(By.linkText("Написать")).click();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"nb-20\"]")));

        driver.findElement(By.cssSelector(".js-compose-field.mail-Bubbles.mail-Bubbles-Sms_hidden")).sendKeys(mailAdress);
//        String letterTheme = String.valueOf(System.currentTimeMillis());
        driver.findElement(By.cssSelector(".mail-Compose-Field-Input-Controller.js-compose-field.js-editor-tabfocus-prev")).sendKeys(themeOfSendingLetter);
        driver.findElement(By.xpath("//*[@id=\"cke_1_contents\"]/div")).sendKeys(letterBody);
        driver.findElement(By.xpath("//*[@title=\"Закрыть\"]")).click();
//        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Сохранить и перейти']")));
        driver.findElement(By.xpath("//span[text()='Сохранить и перейти']")).click();
//        WebElement.sendKeys(Keys.ENTER);
        driver.findElement(By.linkText("Черновики")).click();
        wait.until(ExpectedConditions.titleIs("Черновики — Яндекс.Почта"));
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + themeOfSendingLetter + "']")));
        String actualTheme = element.getText();
        logger.info("We are checking if our letter with '" +themeOfSendingLetter+ "' theme, saved in Draft folder. And found - '" + actualTheme + "'.");
        Assert.assertEquals(actualTheme, themeOfSendingLetter);

        String actualAdress = driver.findElement(By.xpath("//span[text()='" +mailAdress+ "']")).getText();
        logger.info("Expecting adress is '" +mailAdress+ "'. And found on WebPage is '" + actualAdress + "'.");
        Assert.assertEquals(actualAdress, mailAdress);

        String actualLetterBody = driver.findElement(By.xpath("//span[text()='" +letterBody+ "']")).getText();
        logger.info("Expecting letter text is '" +letterBody+ "'. And found on WebPage is '" + actualLetterBody + "'.");
        Assert.assertEquals(actualLetterBody, letterBody);
//        Assert.assertTrue(isDraft);
    }

    @Test(dependsOnMethods = "createNewMail", expectedExceptions = NoSuchElementException.class)
    public void sendMail(){
        logger = Logger.getLogger("Test-4. Send mail");
        driver.findElement(By.xpath("//span[contains(@title, '" +themeOfSendingLetter+ "')]")).click();
//        themeOfSendingLetter = driver.findElement(By.xpath("//input[contains(@name, 'subj')]")).getText();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[1]/span/span/button[contains(@title, '')]")));
        element.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mail-Done-Title.js-title-info")));
        driver.findElement(By.linkText("Черновики")).click();
        wait.until(ExpectedConditions.titleIs("Черновики — Яндекс.Почта"));
        logger.info("Checking that letter with '" +themeOfSendingLetter+ "' theme, disapered.");
        driver.findElement(By.xpath("//span[text()='" + themeOfSendingLetter + "']"));
    }

    @Test(dependsOnMethods = "sendMail")
    public void checkingSentFolder(){
        logger = Logger.getLogger("Test-5. Checking 'Sent' folder");
        driver.findElement(By.xpath("//a[contains(@href, '#sent')]")).click();
        wait.until(ExpectedConditions.titleIs("Отправленные — Яндекс.Почта"));

        String actualTheme = driver.findElement(By.xpath("//span[contains(@title, '" +themeOfSendingLetter+ "')]")).getText();
        logger.info("In SENT folder should be letter with theme '" +themeOfSendingLetter+ "'. Found letter with theme '" + actualTheme + "'.");
        Assert.assertEquals(actualTheme, themeOfSendingLetter);
    }

    @Test(dependsOnMethods = "checkingSentFolder")
    public void logOut(){
        logger = Logger.getLogger("Test-6. Logging out");
        driver.findElement(By.cssSelector(".mail-User-Picture.js-user-picture")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("b-user-dropdown-content")));
        driver.findElement(By.linkText("Выход")).click();
        String actualTitle = driver.getTitle();
        logger.info("Title should be Яндекс. Found title '" + actualTitle + "'.");
        Assert.assertEquals(actualTitle, "Яндекс");
    }
}