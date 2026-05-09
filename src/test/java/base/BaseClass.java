package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

    public static WebDriver driver;
    public static WebDriverWait wait;

    public void setupBrowser() {

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.manage().window().maximize();

        driver.get("https://demoqa.com/");
    }

    public void closeBrowser() {

        if (driver != null) {

            driver.quit();
        }
    }
}