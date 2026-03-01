package saucedemo;

import core.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(id = "continue")
    private WebElement continueBtn;

    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        // Tunggu elemen muncul
        waitForElementToBeVisible(firstNameInput);

        // LOG DEBUG: Pastikan data tidak NULL
        System.out.println("DEBUG: Mengisi Form dengan -> FN: " + firstName + ", LN: " + lastName + ", Zip: " + postalCode);

        // Gunakan taktik Klik -> Clear -> SendKeys untuk stabilitas
        safeType(firstNameInput, firstName);
        safeType(lastNameInput, lastName);
        safeType(postalCodeInput, postalCode);
    }

    // Fungsi pembantu agar pengetikan tidak gagal
    private void safeType(WebElement element, String text) {
        element.click();
        element.clear();
        if (text != null && !text.isEmpty()) {
            element.sendKeys(text);
        } else {
            System.err.println("WARNING: Teks yang akan diketik adalah NULL atau Kosong!");
        }
    }

    public void clickContinue() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", continueBtn);
    }
}