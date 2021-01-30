package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

public abstract class SearchPageObject extends MainPageObject{
    private AppiumDriver driver;
    protected static String
        SEARCH_INIT_ELEMENT,
        SEARCH_INPUT,
        SEARCH_CANCEL_BUTTON,
        SEARCH_RESULT_BY_SUBSTRING_TPL,
        SEARCH_RESULTS,
        EMPTY_RESULT_LABEL,
        TITLE_AND_SUBTITLE_TPL,
        CLEAR_SEARCH_FIELD;


    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

//    TPL
    private String getSearchResultElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private String getSearchResultByTitleAndDescription(String title, String subtitle) {
        String xpath = TITLE_AND_SUBTITLE_TPL.replace("{TITLE}", title).replace("{SUBTITLE}", subtitle);
        return xpath;
    }
//    TPL
    @Step("Initialization and click on the search field")
    public void initSearchInput() {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element",
                5
        );
        if(Platform.getInstance().isAndroid()) {
            this.waitForElementPresent(
                    SEARCH_INIT_ELEMENT,
                    "Cannot find search input after clicking init search element"
            );
        }
    }
    @Step("Entering data into the search bar")
    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(SEARCH_INPUT,
                search_line,
                "Cannot find 'Search input' element"
        );
    }
    @Step("Waiting for search results to appear")
    public void waitForSearchResult(String substring) {
        String search_result_xpath = getSearchResultElement(substring);
        this.waitForElementPresent(search_result_xpath,
                "Cannot find search result with substring: '" + substring + "'"
        );
    }
    @Step("Go to article by description")
    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getSearchResultElement(substring);
        this.waitForElementAndClick(
                search_result_xpath,
                "Cannot find and click search result with substring: '" + substring + "'",
                10
        );
    }
    @Step("Waiting for the Cancel button to appear")
    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find Cancel button"
        );
    }
    @Step("Waiting for the Cancel button to be disappear")
    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search Cancel button is still present",
                5
        );
    }
    @Step("Click on the \"Cancel\" button")
    public void clickCancelSearch() {
            this.waitForElementAndClick(
                    SEARCH_CANCEL_BUTTON,
                    "Cannot find and click search cancel button",
                    5
            );

//        else if(Platform.getInstance().isIOS()){
//            this.waitForElementAndClick(
//                    CLEAR_SEARCH_FIELD,
//                    "Cannot find clear 'Search field' field",
//                    5
//            );
//        }
    }

//    public void clickClearSearchField(){
//        this.waitForElementAndClick(
//                CLEAR_SEARCH_FIELD,
//                "Cannot find clear 'Search field' field",
//                5
//        );
//    }

    public int getAmountOfFoundArticles() {
        return this.getAmountElements(SEARCH_RESULTS);
    }
    @Step("Waiting for text with no results")
    public void waitForEmptyResultsLabel() {
        waitForElementPresent(
                EMPTY_RESULT_LABEL,
                "Cannot find EMPTY_RESULT_LABEL"
        );
    }
    @Step("Check assertThereIsNoResultOfSearch")
    public void assertThereIsNoResultOfSearch() {
        List<WebElement> elements = driver.findElements(By.xpath("//XCUIElementTypeLink"));
        System.out.println("Search result elements" + elements.size());
        this.waitForElementNotPresent(
                SEARCH_RESULTS,
                "Search items were found that shouldn't have been",
                5
        );
    }
    @Step("Waiting for a search result by title and subtitle")
    public void waitForElementByTitleAndDescription(String title, String description) {
        String xpath = getSearchResultByTitleAndDescription(title, description);
        waitForElementPresent(
                xpath,
                "Cannot find element: " + xpath);
        Assert.assertEquals("Cannot find more 3 elements", this.getAmountElements(xpath), 1);
    }

    public List<WebElement> getSearchResultElements() {
        this.waitForElement(SEARCH_RESULTS, "Search result is empty", 10);
        List<WebElement> list_of_results = this.waitForElements(SEARCH_RESULTS, "Search result is empty");
        return list_of_results;
    }

}
