package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class JobsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    //menülerin ve iş ilanları XPath'leri
    private By locationDropdown = By.xpath("//span[@id='select2-filter-by-location-container']");
    private By departmentDropdown = By.xpath("//span[@id='select2-filter-by-department-container']");
    private By jobList = By.xpath("//div[contains(@class, 'position-list-item-wrapper')]");
    private By jobTitle = By.xpath(".//p[contains(@class, 'position-title')]");
    private By jobDepartment = By.xpath(".//span[contains(@class, 'position-department')]");
    private By jobLocation = By.xpath(".//div[contains(@class, 'position-location')]");

    public JobsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    // sayfanın yüklenmesini bekler
    public void waitForPageToBeReady() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    // filtlerin yüklenmesini bekler
    public void waitForPageLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(locationDropdown));
        wait.until(ExpectedConditions.presenceOfElementLocated(departmentDropdown));
    }

    // açılır menüden istenilen değeri bulur ve seçer
    private void selectFromCustomDropdown(By dropdown, String optionText) {
        try {
            Thread.sleep(10000); // Dropdown açılmadan önce 10 saniye bekleme
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        dropdownElement.click();

        By dropdownOptions = By.xpath("//li[contains(@class,'select2-results__option')]");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownOptions));

        List<WebElement> options = driver.findElements(dropdownOptions);
        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(optionText.trim())) {
                option.click();
                return;
            }
        }
        throw new NoSuchElementException("Seçenek bulunamadı: " + optionText);
    }

    // iş ilanlarını filtreler
    public void filterJobs(String location, String department) {
        waitForPageToBeReady();
        waitForPageLoad();

        selectFromCustomDropdown(locationDropdown, location);
        selectFromCustomDropdown(departmentDropdown, department);

        // URL değişimini beklemeyi kaldırır, iş ilanlarının yüklenmesini bekler
        wait.until(ExpectedConditions.presenceOfElementLocated(jobList));

        System.out.println("✅ Filtreleme tamamlandı, iş ilanları yüklendi!");
    }

    //filtrelenmiş iş ilanlarının doğruluğunu kontrol eder
    public boolean verifyJobListings(String expectedLocation, String expectedDepartment) {
        List<WebElement> jobs = driver.findElements(jobList);

        if (jobs.isEmpty()) {
            System.out.println("❌ Hiç iş ilanı bulunamadı!");
            return false;
        }

        System.out.println("✅ " + jobs.size() + " adet iş ilanı bulundu.");

        for (WebElement job : jobs) {
            try {
                //jobList öğesinin içindeki bileşenleri bulur
                String titleText = job.findElement(jobTitle).getText().trim();
                String departmentText = job.findElement(jobDepartment).getText().trim();
                String locationText = job.findElement(jobLocation).getText().trim();

                System.out.println("🔍 Kontrol Edilen İş:");
                System.out.println("   - Pozisyon: " + titleText);
                System.out.println("   - Departman: " + departmentText);
                System.out.println("   - Konum: " + locationText);

                //kontrol
                if (!titleText.contains("Quality Assurance")) {
                    System.out.println("❌ Pozisyon adı 'Quality Assurance' içermiyor!");
                    return false;
                }
                if (!departmentText.equalsIgnoreCase(expectedDepartment)) {
                    System.out.println("❌ Departman '" + expectedDepartment + "' bekleniyordu, ama '" + departmentText + "' bulundu!");
                    return false;
                }
                if (!locationText.contains(expectedLocation)) {
                    System.out.println("❌ Konum '" + expectedLocation + "' bekleniyordu, ama '" + locationText + "' bulundu!");
                    return false;
                }

            } catch (NoSuchElementException e) {
                System.out.println("❌ Bir iş ilanında gerekli bilgiler eksik!");
                return false;
            }
        }

        System.out.println("✅ Tüm iş ilanları beklendiği gibi!");
        return true;
    }

    //"View Role" butonuna tıklayarak iş ilanı detay sayfasına gider
    public void clickViewRole(int index) {
        List<WebElement> jobElements = driver.findElements(jobList);

        if (jobElements.isEmpty()) {
            throw new NoSuchElementException("❌ Görüntülenecek iş ilanı bulunamadı!");
        }

        Actions actions = new Actions(driver);
        actions.moveToElement(jobElements.get(index)).perform(); // Mouse hover yap

        By viewRoleButton = By.xpath("(//a[contains(text(),'View Role')])[" + (index + 1) + "]");
        WebElement viewRole = wait.until(ExpectedConditions.elementToBeClickable(viewRoleButton));
        viewRole.click();

        System.out.println("✅ 'View Role' butonuna tıklandı!");
        wait.until(ExpectedConditions.urlContains("lever.co"));
    }

}