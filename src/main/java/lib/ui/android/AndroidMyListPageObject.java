package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidMyListPageObject extends MyListPageObject {

    static {
        FOLDER_NAME_TPL = "xpath://*[@resource-id = 'org.wikipedia:id/item_title'][@text = '{FOLDER_NAME}']";
        ARTICLE_BY_TITLE_TPL = "xpath://*[@text = '{ARTICLE_TITLE}']";
    }

    public AndroidMyListPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
