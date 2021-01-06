package IOS;

import lib.IosTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends IosTestCase {
    @Test
    public void testPassThroughWelcome() {
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
