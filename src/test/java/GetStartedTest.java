import lib.CoreTestCase;
import lib.ui.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase {
    @Test
    public void testPassThroughWelcome() {
        if(Platform.getInstance().isAndroid()){
            return;
        }
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
