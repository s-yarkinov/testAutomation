package lib.ui.mobileWeb;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {
    public MWNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        MY_LIST_LINK = "css:a[data-event-name='menu.unStar']";
        OPEN_NAVIGATION = "css:#mw-mf-main-menu-button";
    }
}
