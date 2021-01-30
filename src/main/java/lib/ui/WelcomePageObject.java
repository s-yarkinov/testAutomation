package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject{
    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    private static final String
        NEXT_BUTTON = "xpath://XCUIElementTypeButton[@name='Next']",
        FIND_TEXT_BY_NAME_TPL = "xpath://XCUIElementTypeStaticText[@name='{TEXT}']",
        GET_STARTED_BUTTON = "xpath://XCUIElementTypeButton[@name='Get started']",
        SKIP_BUTTON = "xpath://XCUIElementTypeButton[@name='Skip']";



//    TPL
    private String getFindTextByNameTplXpath(String value){
        return FIND_TEXT_BY_NAME_TPL.replace("{TEXT}", value);
    }
//    TPL
    @Step("Wait for lear more link")
    public void waitForLearnMoreLink() {
        String elementXpath = getFindTextByNameTplXpath("Learn more about Wikipedia");
        this.waitForElementPresent(
                elementXpath,
                "'Learn more about Wikipedia' link not found"
        );
    }
    @Step("Click next button")
    public void clickNextButton() {
        this.waitForElementAndClick(NEXT_BUTTON,
                "'Next Button' not found",
                5
        );
    }
    @Step("Wait new ways to explore text")
    public void waiteNewWaysToExploreText() {
        String elementXpath = getFindTextByNameTplXpath("New ways to explore");
        this.waitForElementPresent(
                elementXpath,
                "'New ways to explore' not found"
        );
    }
    @Step("Wait search in over text")
    public void waiteSearchInOverText() {
        String elementXpath = getFindTextByNameTplXpath("Search in over 300 languages");
        this.waitForElementPresent(
                elementXpath,
                "'Search in over 300 languages' not found"
        );
    }
    @Step ("Wait learn more link")
    public void waitLearnMoreLink() {
        String elementXpath = getFindTextByNameTplXpath("Learn more about data collected");
        this.waitForElementPresent(
                elementXpath,
                "'Learn more about data collected' not found");
    }
    @Step("Click get started button")
    public void ClickGetStartedButton() {
        this.waitForElementAndClick(
                GET_STARTED_BUTTON,
                "'Get started' button not found",
                5
        );
    }
    @Step("Click to the skip button")
    public void clickSkip() {
        this.waitForElementAndClick(SKIP_BUTTON, "Cannot find and click Skip button", 10);
    }
}
