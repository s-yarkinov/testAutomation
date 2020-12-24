package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject{
    private AppiumDriver driver;
    private static final String
        SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
        SEARCH_INPUT = "//android.widget.LinearLayout/android.widget.EditText",
        SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, '{SUBSTRING}')]",
        SEARCH_RESULTS = "//*[@resource-id = 'org.wikipedia:id/fragment_search_results']//*[@resource-id = 'org.wikipedia:id/page_list_item_container']",
        EMPTY_RESULT_LABEL = "//*[@text = 'No results found']";


//    TPL
    private String getSearchResultElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
//    TPL
    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void initSearchInput() {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find search inpur after clicking init search element");
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Cannot find 'Search input' element");
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getSearchResultElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with substring: '" + substring + "'");
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getSearchResultElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath), "Cannot find and click search result with substring: '" + substring + "'", 10);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find Cancel button");
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search Cancel button is still present", 5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button", 5);
    }

    public int getAmountOfFoundArticles() {
        return this.getAmountElements(By.xpath(SEARCH_RESULTS));
    }

    public void waitForEmptyResultsLabel() {
        waitForElementPresent(By.xpath(EMPTY_RESULT_LABEL), "Cannot find EMPTY_RESULT_LABEL");
    }

    public void assertThereIsNoResultOfSearch() {
        this.waitForElementNotPresent(By.xpath(SEARCH_RESULTS), "Search items were found that shouldn't have been", 5);
    }
}
