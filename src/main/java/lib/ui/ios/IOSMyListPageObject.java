package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListPageObject;

public class IOSMyListPageObject extends MyListPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{ARTICLE_TITLE}')]";
        CLOSE_SYNC_BUTTON = "id:Close";
        CLOSE_SYNC_TEXT = "id:Sync your saved articles?";
    }

    public IOSMyListPageObject(AppiumDriver driver) {
        super(driver);
    }
}
