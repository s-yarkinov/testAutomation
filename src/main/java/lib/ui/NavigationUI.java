package lib.ui;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject{

    protected static String
        MY_LIST_LINK,
        OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    public void openNavigation() {
        if(Platform.getInstance().isMw()){
            this.waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigation button", 5);
        }
        else
            System.out.println("Method openNavigation() do nothing for platform " + Platform.getInstance().getPlatformVar());
    }

    public void clickMyList(){
        if(Platform.getInstance().isMw()){
            this.tryClickElementWithFewAttempts(MY_LIST_LINK, "'My list' element not found", 5);
        }
        else {
            this.waitForElementAndClick(
                    MY_LIST_LINK,
                    "'My list' element not found",
                    5
            );
        }
    }
}
