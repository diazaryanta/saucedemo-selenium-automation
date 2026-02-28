package saucedemo.home;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import saucedemo.HomePage;
import saucedemo.LoginPage;
import saucedemo.CartPage;
import saucedemo.CheckoutPage;
import saucedemo.OverviewPage;
import saucedemo.core.BaseTest;
import saucedemo.core.DriverManager;
import saucedemo.core.TestListener;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

@Listeners(TestListener.class)
public class SauceDemoE2ETest extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private OverviewPage overviewPage;

    @BeforeClass(groups = {"smoke"})
    public void setupPages() {
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().manage().deleteAllCookies();
        DriverManager.getDriver().get("https://www.saucedemo.com/");

        loginPage = new LoginPage(DriverManager.getDriver());
        homePage = new HomePage(DriverManager.getDriver());
        cartPage = new CartPage(DriverManager.getDriver());
        checkoutPage = new CheckoutPage(DriverManager.getDriver());
        overviewPage = new OverviewPage(DriverManager.getDriver());
    }

    @Test(priority = 1, groups = {"smoke"}, description = "Test Successful Login")
    public void testSuccessfulLogin() {
        loginPage.login(config.getProperty("standardUser"), config.getProperty("password"));
        Assert.assertTrue(loginPage.isUserLoggedInSuccessfully(), "Gagal login atau Header 'Products' tidak ditemukan.");
    }

    @Test(priority = 2, groups = {"smoke"}, description = "Test Add Product to Cart", dependsOnMethods = "testSuccessfulLogin")
    public void testAddProductToCart() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        homePage.addBackpackToCart();
        try { Thread.sleep(1000); } catch (Exception e) {}
    }

    @Test(priority = 3, groups = {"smoke"}, description = "Test Navigate to Cart Page", dependsOnMethods = "testAddProductToCart")
    public void testNavigateToCart() {
        homePage.navigateToCart();

        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("cart"));

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("cart"), "Gagal berpindah ke keranjang!");

        try { Thread.sleep(1500); } catch (Exception e) {}
    }

    @Test(priority = 4, groups = {"smoke"}, description = "Test Checkout Process", dependsOnMethods = "testNavigateToCart")
    public void testCheckoutProcess() {
        cartPage.clickCheckout();

        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));

        try { Thread.sleep(2000); } catch (Exception e) {}

        checkoutPage.fillCheckoutForm(config.getProperty("firstName"), config.getProperty("lastName"), config.getProperty("zip"));

        checkoutPage.clickContinue();

        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }

    @Test(priority = 5, groups = {"smoke"}, description = "Test Finish Checkout", dependsOnMethods = "testCheckoutProcess")
    public void testFinishCheckout() {
        overviewPage.clickFinish();

        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("checkout-complete"));

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("checkout-complete"), "Gagal menyelesaikan pesanan! URL saat ini: " + currentUrl);

        try { Thread.sleep(4000); } catch (Exception e) {}
    }
}