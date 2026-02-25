package saucedemo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginPage extends BasePage {

    private static final Logger log = LogManager.getLogger(LoginPage.class);

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorAlert;

    @FindBy(className = "title")
    private WebElement pageTitle;

    public LoginPage(WebDriver driver) {
        super(driver);
        log.info("LoginPage initialized");
    }

    public void login(String username, String password) {
        log.info("Attempting to login with username: {}", username);
        waitForElementToBeVisible(usernameInput);
        usernameInput.sendKeys(username);
        log.info("Username entered");
        passwordInput.sendKeys(password);
        log.info("Password entered");
        loginButton.click();
        log.info("Login button clicked");
    }

    public boolean isUserLoggedInSuccessfully() {
        try {
            log.info("Verifying successful login...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
            return title.isDisplayed() && title.getText().equalsIgnoreCase("Products");
        } catch (Exception e) {
            log.warn("Login verification failed: 'Products' title not found.");
            return false;
        }
    }

    public boolean isErrorMessageDisplayed() {
        log.info("Checking if error message is displayed");
        try {
            waitForElementToBeVisible(errorAlert);
            boolean isDisplayed = errorAlert.isDisplayed();
            if (isDisplayed) {
                log.info("Error message is displayed");
            } else {
                log.info("Error message is not displayed");
            }
            return isDisplayed;
        } catch (Exception e) {
            log.info("No error message found");
            return false;
        }
    }

    public String getErrorMessage() {
        log.info("Getting error message text");
        try {
            waitForElementToBeVisible(errorAlert);
            String message = errorAlert.getText();
            log.info("Error message: {}", message);
            return message;
        } catch (Exception e) {
            log.error("Failed to get error message: {}", e.getMessage());
            return "";
        }
    }

    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        log.info("Current URL fetched: {}", url);
        return url;
    }
}