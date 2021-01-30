package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;
@Epic("Welcome screen")
public class GetStartedTest extends CoreTestCase {
    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @Features(value = {@Feature(value = "welcome screen"), @Feature(value = "Welcome link"), @Feature("New ways check"), @Feature("Get started feature")})
    @DisplayName("Test checking amount in empty search results")
    @Description("Validate amount in empty search results")
    @Step("Starting testPassThroughWelcome")
    public void testPassThroughWelcome() {
        if(Platform.getInstance().isAndroid() || Platform.getInstance().isMw())
            return;
        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

        welcomePageObject.waitForLearnMoreLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waiteNewWaysToExploreText();
        welcomePageObject.clickNextButton();

        welcomePageObject.waiteSearchInOverText();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitLearnMoreLink();
        welcomePageObject.ClickGetStartedButton();
    }
}
