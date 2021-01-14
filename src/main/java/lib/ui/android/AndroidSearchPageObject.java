package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]";
                SEARCH_INPUT = "xpath://android.widget.LinearLayout/android.widget.EditText";
                SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
                SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, '{SUBSTRING}')]";
                SEARCH_RESULTS = "xpath://*[@resource-id = 'org.wikipedia:id/fragment_search_results']//*[@resource-id = 'org.wikipedia:id/page_list_item_container']";
                EMPTY_RESULT_LABEL = "xpath://*[@text = 'No results found']";
                TITLE_AND_SUBTITLE_TPL = "xpath://*[@text = '{TITLE}']/following-sibling::*[@text = '{SUBTITLE}']";
    }
    public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }



}
