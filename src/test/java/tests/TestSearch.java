package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class TestSearch extends CoreTestCase {

    private MainPageObject mainPageObject;

//    protected void setUp() throws Exception {
//        super.setUp();
//        mainPageObject = new MainPageObject(driver);
//    }


    @Test
    @Severity(value = SeverityLevel.CRITICAL)
    @Features(value = {@Feature(value = "search"), @Feature(value = "Article")})
    @DisplayName("Search article")
    @Description("Checking the search on the page")
    @Step("Starting testSearch")
    public void testSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    @Severity(value = SeverityLevel.CRITICAL)
    @Features(value = {@Feature(value = "search"), @Feature(value = "Article"), @Feature("Canceling search field")})
    @DisplayName("Search cancel search question")
    @Description("Validate canceling search field")
    @Step("Starting testCancelSearch")
    public void testCancelSearch() throws InterruptedException {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

//    @Test
//    public void testClearTextField() {
//        mainPageObject.waitForElementAndClick(
//                By.id("org.wikipedia:id/search_container"),
//                "Cannot find element",
//                1
//        );
//
//        mainPageObject.waitForElementAndSendKeys(
//                By.id("org.wikipedia:id/search_src_text"),
//                "Java",
//                "Text field not found"
//        );
//
//        mainPageObject.waitForElementAndClear(
//                By.id("org.wikipedia:id/search_src_text"),
//                "Element 'search field' not found"
//        );
//    }

    @Test
    @Severity(value = SeverityLevel.MINOR)
    @Features(value = {@Feature(value = "search"), @Feature(value = "Article"), @Feature("Cancel button in search page")})
    @DisplayName("Test cancel")
    @Description("Validate cancel button")
    @Step("Starting testCancel")
    public void testCancel() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("java");
        int amount_results = searchPageObject.getAmountOfFoundArticles();
        System.out.println(amount_results);
        assertTrue("Search result is empty", amount_results>0);
        searchPageObject.clickCancelSearch();
        if(Platform.getInstance().isAndroid()) {
            amount_results = searchPageObject.getAmountOfFoundArticles();
            System.out.println(amount_results);
            assertTrue("Search results remained after canceling", amount_results==0);
        }
        else {
            searchPageObject.assertThereIsNoResultOfSearch();
        }


    }

    @Test
    @Severity(value = SeverityLevel.CRITICAL)
    @Features(value = {@Feature(value = "search"), @Feature(value = "Article"), @Feature("Cancel button in search page"), @Feature("Search result")})
    @DisplayName("Test checking search results")
    @Description("Validate search results")
    @Step("Starting testCheckingSearchResults")
    public void testCheckingSearchResults()
    {
        String searchResultsAtr = (Platform.getInstance().isAndroid()) ? "text" : "name";
        System.out.println(searchResultsAtr);
        String searchText = "Java";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("java");

        List<WebElement> searchResult = searchPageObject.getSearchResultElements();

        for (int i=0; i<=2; i++) {
            System.out.println(searchResult.get(i).getAttribute(searchResultsAtr));
            assertTrue("The results does not contain '" + searchText + "'",
                    searchResult.get(i).getAttribute(searchResultsAtr).contains(searchText));
        }
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @Features(value = {@Feature(value = "search"), @Feature(value = "Article"), @Feature("Cancel button in search page"), @Feature("Search result")})
    @DisplayName("Test checking empty search results")
    @Description("Validate empty search results")
    @Step("Starting testAmountOfNotEmptySearch")
    public void testAmountOfNotEmptySearch() {
        String searchLine = "Linkin park";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);

        int amount_find_articles = searchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "No articles found",
                amount_find_articles > 0
        );
    }


    @Test
    @Severity(value = SeverityLevel.MINOR)
    @Features(value = {@Feature(value = "search"), @Feature(value = "Article"), @Feature("Cancel button in search page"), @Feature("Search result")})
    @DisplayName("Test checking amount in empty search results")
    @Description("Validate amount in empty search results")
    @Step("Starting testAmountOfEmptySearch")
    public void testAmountOfEmptySearch() {
        String searchLine = "as2d1as2d1a2s1d2";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.assertThereIsNoResultOfSearch();
        searchPageObject.waitForEmptyResultsLabel();
    }


//    @Test
//    public void testTitleIsPresent(){
//        By titleOfArticle = By.id("org.wikipedia:id/view_page_title_text");
//        String firstSubtitle = "//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]";
//
//
//        mainPageObject.waitForElementAndClick(
//                By.id("org.wikipedia:id/search_container"),
//                "Cannot find element",
//                1
//        );
//
//        mainPageObject.assertElementHasText(
//                By.id("org.wikipedia:id/search_src_text"),
//                "Search…",
//                "Search Field not equals 'Search Wikipedia'"
//        );
//
//        mainPageObject.waitForElementAndSendKeys(
//                By.id("org.wikipedia:id/search_src_text"),
//                "Java",
//                "Text field not found"
//        );
//
//        mainPageObject.waitForElementAndClick(
//                By.xpath(firstSubtitle),
//                "Element not found: " + firstSubtitle,
//                3
//        );
//
//        mainPageObject.assertElementPresent(titleOfArticle, "Article title not found");
//    }


}
