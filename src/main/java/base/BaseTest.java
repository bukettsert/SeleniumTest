package base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            captureScreenshot(result.getName());
        }
        if (driver != null) {
            driver.quit();
        }
    }

    public void captureScreenshot(String testName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File destinationFile = new File("screenshots/" + testName + "_" + timestamp + ".png");

        try {
            FileUtils.copyFile(screenshot, destinationFile);
            System.out.println("📸 Ekran görüntüsü kaydedildi: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("❌ Ekran görüntüsü kaydedilemedi: " + e.getMessage());
        }
    }
}
