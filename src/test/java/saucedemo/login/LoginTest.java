package saucedemo.login;

import org.testng.annotations.Test;
import saucedemo.core.BaseTest;
import saucedemo.core.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import saucedemo.LoginPage;
import org.testng.Assert;

public class LoginTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(LoginTest.class);

    @Test(priority = 1, groups = {"smoke"}, description = "Test successful login")
    public void testLogin() {
        String user = config.getProperty("standardUser");
        log.info("Login with standard_user '{}'", user);

        DriverManager.getDriver().manage().deleteAllCookies();
        DriverManager.getDriver().get("https://www.saucedemo.com/");

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(user, config.getProperty("password"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Verifying successful login UI and URL redirect...");
        Assert.assertTrue(loginPage.isUserLoggedInSuccessfully(), "Header 'Products' tidak ditemukan di layar.");

        String currentUrl = loginPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory"), "Gagal! URL tidak mengandung inventory. URL saat ini: " + currentUrl);
    }
}