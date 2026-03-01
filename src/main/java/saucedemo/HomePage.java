package saucedemo;

import core.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    private static final Logger log = LogManager.getLogger(HomePage.class);

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addBackpackToCartBtn;

    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    private WebElement clickCart;

    @FindBy(xpath = "//select[@class='product_sort_container']")
    private WebElement sortProduct;

    @FindBy(xpath = "//*[@id='inventory_filter_container']/select/option[4]")
    private WebElement sortFromHighToLow;

    @FindBy(xpath = "//*[@id='inventory_filter_container']/select/option[3]")
    private WebElement sortFromLowToHigh;

    public void addBackpackToCart() {
        log.info("Clicking 'Add to cart' for product: Sauce Labs Backpack");
        waitForElementToBeVisible(addBackpackToCartBtn);
        addBackpackToCartBtn.click();
    }

    public void navigateToCart() {
        log.info("Clicking the shopping cart icon using JavascriptExecutor");
        waitForElementToBeVisible(clickCart);

        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", clickCart);
    }
}