package saucedemo;

import core.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OverviewPage extends BasePage {
    private static final Logger log = LogManager.getLogger(OverviewPage.class);

    public OverviewPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "finish")
    private WebElement finishBtn;

    public void clickFinish() {
        log.info("Clicking the 'Finish' button on Overview Page");
        waitForElementToBeVisible(finishBtn);
        finishBtn.click();
    }
}