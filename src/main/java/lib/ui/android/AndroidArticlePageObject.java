package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidArticlePageObject extends ArticlePageObject {
     static {
                 ARTICLE_TITLE = "id:org.wikipedia:id/view_page_title_text";
                 FOOTER_ELEMENT = "id:org.wikipedia:id/page_external_link";
                 OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']";
                 ADD_TO_MY_LIST_BUTTON = "xpath://*[@text = 'Add to reading list']";
                 ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button";
                 MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
                 MY_LIST_OK_BUTTON = "id:android:id/button1";
                 CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc=\"Navigate up\"]";
                 EXISTING_MY_LIST_TPL = "xpath://*[@text = '{MY_LIST_NAME}']";
     }

    public AndroidArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
