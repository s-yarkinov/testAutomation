package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSArticlePageObject extends ArticlePageObject {

    public IOSArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        ARTICLE_TITLE = "id:Java (programming language)";
        FOOTER_ELEMENT = "id:View article in browser";
        ADD_TO_MY_LIST_BUTTON = "xpath://XCUIElementTypeButton[@name='Save for later']";
        CLOSE_ARTICLE_BUTTON = "xpath://XCUIElementTypeButton[@name='Search']";
        EXISTING_MY_LIST_TPL = "xpath://*[@text = '{MY_LIST_NAME}']";
    }
}
