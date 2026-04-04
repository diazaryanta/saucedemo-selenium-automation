package saucedemo.login;

import org.testng.annotations.Test;
import saucedemo.core.BaseTest;
import saucedemo.core.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(LoginTest.class);

    @Test(priority = 1, groups = {"smoke"}, description = "Test successful login")
    public void testLogin() {
        String user = config.getProperty("standardUser");
        log.info("Login with standard_user '{}'", user);

        DriverManager.getDriver().manage().deleteAllCookies();
        DriverManager.getDriver().get("https://www.saucedemo.com/");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}