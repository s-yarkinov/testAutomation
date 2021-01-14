package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;

public abstract class SearchPageObject extends MainPageObject{
    private AppiumDriver driver;
    protected static String
        SEARCH_INIT_ELEMENT,
        SEARCH_INPUT,
        SEARCH_CANCEL_BUTTON,
        SEARCH_RESULT_BY_SUBSTRING_TPL,
        SEARCH_RESULTS,
        EMPTY_RESULT_LABEL,
        TITLE_AND_SUBTITLE_TPL;


//    TPL
    private String getSearchResultElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private String getSearchResultByTitleAndDescription(String title, String subtitle) {
        String xpath = TITLE_AND_SUBTITLE_TPL.replace("{TITLE}", title).replace("{SUBTITLE}", subtitle);
        return xpath;
    }
//    TPL
    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void initSearchInput() {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element",
                5
        );
        if(Platform.getInstance().isAndroid()) {
            this.waitForElementPresent(
                    SEARCH_INIT_ELEMENT,
                    "Cannot find search inpur after clicking init search element"
            );
        }
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(SEARCH_INPUT,
                search_line,
                "Cannot find 'Search input' element"
        );
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getSearchResultElement(substring);
        this.waitForElementPresent(search_result_xpath,
                "Cannot find search result with substring: '" + substring + "'"
        );
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getSearchResultElement(substring);
        this.waitForElementAndClick(
                search_result_xpath,
                "Cannot find and click search result with substring: '" + substring + "'",
                10
        );
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find Cancel button"
        );
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search Cancel button is still present",
                5
        );
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                5
        );
    }

    public int getAmountOfFoundArticles() {
        return this.getAmountElements(SEARCH_RESULTS);
    }

    public void waitForEmptyResultsLabel() {
        waitForElementPresent(
                EMPTY_RESULT_LABEL,
                "Cannot find EMPTY_RESULT_LABEL"
        );
    }

    public void assertThereIsNoResultOfSearch() {
        this.waitForElementNotPresent(
                SEARCH_RESULTS,
                "Search items were found that shouldn't have been",
                5
        );
    }

    public void waitForElementByTitleAndDescription(String title, String description) {
        String xpath = getSearchResultByTitleAndDescription(title, description);
        waitForElementPresent(
                xpath,
                "Cannot find element: " + xpath);
        Assert.assertEquals("Cannot find more 3 elements", this.getAmountElements(xpath), 3);
    }

}
