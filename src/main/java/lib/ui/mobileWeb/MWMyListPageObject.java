package lib.ui.mobileWeb;

import lib.ui.MyListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListPageObject extends MyListPageObject {
    public MWMyListPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://h3[contains(text(), '{ARTICLE_TITLE}')]";
        REMOVE_FROM_SAVE_BUTTON = "xpath://h3[contains(text(), '{ARTICLE_TITLE}')]/../../a[contains(@class, 'watched')]";
        BEGIN_ARTICLE_TPL = "xpath://p[contains(class, mw-empty-elt)]//b[contains(text(), '{BEGIN_TEXT}')]";
    }
}
