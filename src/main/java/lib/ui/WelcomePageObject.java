package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject{
    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    private static final String
        NEXT_BUTTON = "xpath://XCUIElementTypeButton[@name='Next']",
        SKIP_BUTTON = "xpath://XCUIElementTypeStaticText[@name=\"Skip\"]",
        FIND_TEXT_BY_NAME_TPL = "xpath://XCUIElementTypeStaticText[@name='{TEXT}']",
        GET_STARTED_BUTTON = "xpath://XCUIElementTypeButton[@name=\"Get started\"]";



//    TPL
    private String getFindTextByNameTplXpath(String value){
        return FIND_TEXT_BY_NAME_TPL.replace("{TEXT}", value);
    }
//    TPL
    public void waitForLearnMoreLink() {
        String elementXpath = getFindTextByNameTplXpath("Learn more about Wikipedia");
        this.waitForElementPresent(
                elementXpath,
                "'Learn more about Wikipedia' link not found"
        );
    }

    public void clickNextButton() {
        this.waitForElementAndClick(NEXT_BUTTON,
                "'Next Button' not found",
                5
        );
    }

    public void waiteNewWaysToExploreText() {
        String elementXpath = getFindTextByNameTplXpath("New ways to explore");
        this.waitForElementPresent(
                elementXpath,
                "'New ways to explore' not found"
        );
    }
    public void waiteSearchInOverText() {
        String elementXpath = getFindTextByNameTplXpath("Search in over 300 languages");
        this.waitForElementPresent(
                elementXpath,
                "'Search in over 300 languages' not found"
        );
    }
    public void waitLearnMoreLink() {
        String elementXpath = getFindTextByNameTplXpath("Learn more about data collected");
        this.waitForElementPresent(
                elementXpath,
                "'Learn more about data collected' not found");
    }

    public void ClickGetStartedButton() {
        this.waitForElementAndClick(
                GET_STARTED_BUTTON,
                "'Get started' button not found",
                5
        );
    }
}
