package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CareersPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By seeAllQAJobsButton = By.xpath("//*[@id='page-head']//a[contains(text(),'See all QA jobs')]");
    private By closePopupButton = By.xpath("//button[contains(text(),'Close') or contains(text(),'×')]");
    private By cookieBanner = By.id("wt-cli-accept-all-btn"); // Çerez onayı butonu

    public CareersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Sayfadaki popup veya cookie banner'ı kapatır
    public void closePopups() {
        try {
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(closePopupButton));
            popup.click();
        } catch (Exception e) {
            System.out.println("Popup bulunamadı, devam ediliyor...");
        }

        try {
            WebElement cookie = wait.until(ExpectedConditions.visibilityOfElementLocated(cookieBanner));
            cookie.click();
        } catch (Exception e) {
            System.out.println("Çerez banner'ı bulunamadı, devam ediliyor...");
        }
    }

    public void openQAPage() {
        driver.get("https://useinsider.com/careers/quality-assurance/");
        closePopups();
    }

    public void clickSeeAllQAJobs() {
        closePopups(); // Butona tıklamadan önce popup varsa kapat
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(seeAllQAJobsButton));

        // Eğer buton görünmüyorsa, sayfayı kaydır
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);

        button.click();
    }
}
