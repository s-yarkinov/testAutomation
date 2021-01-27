package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject{
    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    private static final String
        LOGIN_BUTTON = "xpath://a[text() = 'Log in']",
        LOGIN_INPUT = "css:#wpName1",
        PASS_INPUT = "css:#wpPassword1",
        SUBMIT_BUTTON = "css:#wpLoginAttempt",
        OPTION_TO_SAVE_WITHOUT_AUTH = "ul#page-actions>li#page-actions-watch>a#ca-watch.mw-ui-icon-wikimedia-star-base20";


    public void clickAuthButton() throws InterruptedException {
            Thread.sleep(1000);
//            this.waitForElementAndClick(OPTION_TO_SAVE_WITHOUT_AUTH, "Cannot call auth menu", 10);

            this.waitForElementPresent(
                LOGIN_BUTTON,
                "Cannot find 'AUTh BUTTON"
        );

        this.waitForElementAndClick(
                LOGIN_BUTTON,
                "Cannot find and click 'AUTh BUTTON",
                5
        );
    }

    public void enterLoginData(String login, String pass) throws InterruptedException {
        Thread.sleep(1000);
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Login filed not found");
        this.waitForElementAndSendKeys(PASS_INPUT, pass, "Password filed not found");
    }

    public void submitForm() {
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find 'SUBMIT' button", 10);
    }

}
