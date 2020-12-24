package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject{
    private AppiumDriver driver;
    private static final String
        SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
        SEARCH_INPUT = "//android.widget.LinearLayout/android.widget.EditText",
        SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, '{SUBSTRING}')]";
//    TPL
    private String getSearchResultElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
//    TPL
    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void initSearchInput() {
        this.waiteForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find search inpur after clicking init search element");
    }

    public void typeSearchLine(String search_line) {
        this.waiteForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Cannot find 'Search input' element");
    }

    public void waiteForSearchResult(String substring) {
        String search_result_xpath = getSearchResultElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with substring: '" + substring + "'");
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getSearchResultElement(substring);
        this.waiteForElementAndClick(By.xpath(search_result_xpath), "Cannot find and click search result with substring: '" + substring + "'", 10);
    }

    public void waiteForCancelButtonToAppear() {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find Cancel button");
    }

    public void waiteForCancelButtonToDisappear() {
        this.waiteForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search Cancel button is still present", 5);
    }

    public void clickCancelSearch() {
        this.waiteForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button", 5);
    }


}
