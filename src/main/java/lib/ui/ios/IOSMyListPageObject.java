package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListPageObject;

public class IOSMyListPageObject extends MyListPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{ARTICLE_TITLE}')]";
        ARTICLE_BY_SUBTITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{ARTICLE_SUBTITLE}')]";
        CLOSE_SYNC_BUTTON = "id:Close";
        CLOSE_SYNC_TEXT = "id:Sync your saved articles?";
        LAYOUT_TITLE = "xpath://XCUIElementTypeStaticText[@name=\"Saved\"]";
    }

    public IOSMyListPageObject(AppiumDriver driver) {
        super(driver);
    }
}
