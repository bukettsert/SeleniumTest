package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.InsiderHomePage;
import pages.JobsPage;

import java.util.Set;

public class CareersTest extends BaseTest {

    @Test
    public void testJobFilters() {
        InsiderHomePage homePage = new InsiderHomePage(driver);
        CareersPage careersPage = new CareersPage(driver);
        JobsPage jobsPage = new JobsPage(driver);

        // Insider ana sayfasını aç
        homePage.openHomePage();
        Assert.assertTrue(driver.getCurrentUrl().contains("useinsider.com"), "❌ Insider ana sayfası açılamadı!");

        // Kariyer sayfasına git
        homePage.goToCareersPage();
        Assert.assertTrue(driver.getCurrentUrl().contains("careers"), "❌ Kariyer sayfası açılamadı!");

        // QA Jobs sayfasına git
        careersPage.openQAPage();
        Assert.assertTrue(driver.getCurrentUrl().contains("quality-assurance"), "❌ QA Jobs sayfası açılamadı!");

        // "See all QA jobs" butonuna tıkla
        careersPage.clickSeeAllQAJobs();

        //Filtreleme işlemini başlatır ve ilanların listelendiğinden emin olur
        jobsPage.filterJobs("Istanbul, Turkiye", "Quality Assurance");

        // 3. İlanların doğruluğunu kontrol et
        boolean isJobListValid = jobsPage.verifyJobListings("Istanbul, Turkiye", "Quality Assurance");

        //"View Role" butonuna tıkla
        jobsPage.clickViewRole(0);

        // "View Role" butonuna tıklandıktan sonra yeni sekmenin açıldığını doğrula ve sekmeyi kaydet
        String originalWindow = driver.getWindowHandle();

        // Tüm açık sekmeleri al
        Set<String> windowHandles = driver.getWindowHandles();

        // Yeni sekmeye geç
        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // URL kontrolü yap
        String expectedUrlPart = "jobs.lever.co/useinsider/";
        String currentUrl = driver.getCurrentUrl();

        System.out.println("🔍 Yeni Sekmedeki URL: " + currentUrl);

        // URL'nin beklenen bölümü içerdiğini doğrulamaya çalışıyorum ama bu aşamada takıldım
        // lever sayfasına gittiği halde url kontrolü yaptıramıyorum

        Assert.assertTrue(currentUrl.contains(expectedUrlPart),
                "❌ Lever başvuru formuna yönlendirilmedi! Mevcut URL: " + currentUrl);

        System.out.println("✅ Lever başvuru formuna başarıyla yönlendirildi!");

    }
}