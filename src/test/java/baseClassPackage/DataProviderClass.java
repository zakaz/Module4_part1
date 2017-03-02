package baseClassPackage;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;

/**
 * Created by Zakir_Mustafin on 1/30/2017.
 */
public class DataProviderClass {
    @DataProvider(name = "dataForOpeningWepPage")
    public static Object[][] dataForWeb(){
        return new Object[][]{
                {"https://mail.yandex.ru/"}
        };
    }

    @DataProvider(name = "loginToTheMailBox")
    public static Object[][] dataForWeb1(){
        return new Object[][]{
                {"samsamitch1@yandex.ru", "TestNG_password"}
        };
    }

    @DataProvider(name = "writeTheLetter")
    public static Object[][] dataForWeb2(){
        return new Object[][]{
                {"samsamitch1@yandex.ru", "Мама мыла раму!!!"}
        };
    }
}
