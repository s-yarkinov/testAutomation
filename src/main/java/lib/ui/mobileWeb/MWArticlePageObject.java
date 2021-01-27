package lib.ui.mobileWeb;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        ARTICLE_TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        ADD_TO_MY_LIST_BUTTON = "css:ul#page-actions>li#page-actions-watch>a#ca-watch.mw-ui-icon-wikimedia-star-base20";
        OPTIONS_REMOVE_TO_MY_LIST_BUTTON = "css:ul#page-actions>li#page-actions-watch>a#ca-watch.mw-ui-icon-wikimedia-unStar-progressive";
    }
}
