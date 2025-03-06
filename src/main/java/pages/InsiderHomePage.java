package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InsiderHomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By companyMenu = By.xpath("//a[contains(text(),'Company')]");
    private By careersOption = By.xpath("//a[contains(text(),'Careers')]");

    public InsiderHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Insider ana sayfasını açan metod
    public void openHomePage() {
        driver.get("https://useinsider.com/");
    }

    // Company menüsünden career sayfasına gider
    public void goToCareersPage() {
        WebElement company = wait.until(ExpectedConditions.elementToBeClickable(companyMenu));
        company.click();

        WebElement careers = wait.until(ExpectedConditions.elementToBeClickable(careersOption));
        careers.click();
    }
}
