package saucedemo.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(String browser) {
        WebDriver webDriver = null;
        switch (browser.toLowerCase()) {
            case "chrome" -> {
                // Setup WebDriverManager
                String githubActions = System.getenv("GITHUB_ACTIONS");
                boolean isCI = githubActions != null && githubActions.equals("true");

                if (!isCI) {
                    WebDriverManager.chromedriver().setup();
                } else {
                    String chromeDriverPath = System.getenv("CHROMEDRIVER_PATH");
                    if (chromeDriverPath != null && !chromeDriverPath.isEmpty()) {
                        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
                    }
                }

                ChromeOptions options = new ChromeOptions();

                // 1. Matikan Pop-up Password & Save Card
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("autofill.profile_enabled", false);
                prefs.put("autofill.credit_card_enabled", false);
                options.setExperimentalOption("prefs", prefs);

                // 2. Mode Incognito & Hilangkan deteksi bot
                options.addArguments("--incognito");
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                options.setExperimentalOption("useAutomationExtension", false);

                // 3. Argumen Stabilitas
                options.addArguments("--disable-notifications");
                options.addArguments("--remote-allow-origins=*");

                if (isCI) {
                    String chromeBin = System.getenv("CHROME_BIN");
                    if (chromeBin != null && !chromeBin.isEmpty()) {
                        options.setBinary(chromeBin);
                    }
                    options.addArguments("--headless=new");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--window-size=1920,1080");
                } else {
                    options.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                }

                webDriver = new ChromeDriver(options);
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOptions = new FirefoxOptions();
                // Matikan pop-up password di Firefox juga
                ffOptions.addPreference("signon.rememberSignons", false);
                ffOptions.addPreference("browser.privatebrowsing.autostart", true); // Incognito di Firefox

                webDriver = new FirefoxDriver(ffOptions);
            }
        }
        driver.set(webDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}