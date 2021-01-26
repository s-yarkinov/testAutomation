package lib.ui.mobileWeb;

import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type = 'search']";
        SEARCH_CANCEL_BUTTON = "css:div>button.cancel";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class, 'wikidata-description')][contains(text(), '{SUBSTRING}')]";
        SEARCH_RESULTS = "xpath://li[contains(@class, 'page-summary')]";
        EMPTY_RESULT_LABEL = "css:p.with-results";
    }
}
