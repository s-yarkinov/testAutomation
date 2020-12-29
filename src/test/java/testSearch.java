import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class testSearch extends CoreTestCase {

    private MainPageObject mainPageObject;

    protected void setUp() throws Exception {
        super.setUp();
        mainPageObject = new MainPageObject(driver);
    }


    @Test
    public void testSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() throws InterruptedException {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testClearTextField() {
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found"
        );

        mainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Element 'search field' not found"
        );
    }

    @Test
    public void testCancel() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("java");
        int amount_results = searchPageObject.getAmountOfFoundArticles();
        System.out.println(amount_results);
        assertTrue("Search result is empty", amount_results>0);
        searchPageObject.clickCancelSearch();
        amount_results = searchPageObject.getAmountOfFoundArticles();
        assertTrue("Search results remained after canceling", amount_results==0);
    }

    @Test
    public void testCheckingSearchResults()
    {
        String searchText = "Java";
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        mainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                searchText,
                "Text field not found"
        );

        List<WebElement> searchResult = mainPageObject.waitForElements(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Results not found"
        );

        for (WebElement webElement : searchResult) {
            assertTrue("The results does not contain '" + searchText + "'",
                    webElement.getAttribute("text").contains(searchText));
        }
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        String searchLine = "Linkin park";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);

        int amount_find_articles = searchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "No articles found",
                amount_find_articles > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        String searchLine = "as2d1as2d1a2s1d2";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.assertThereIsNoResultOfSearch();
        searchPageObject.waitForEmptyResultsLabel();
    }


    @Test
    public void testTitleIsPresent(){
        By titleOfArticle = By.id("org.wikipedia:id/view_page_title_text");
        String firstSubtitle = "//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]";


        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        mainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found"
        );

        mainPageObject.waitForElementAndClick(
                By.xpath(firstSubtitle),
                "Element not found: " + firstSubtitle,
                3
        );

        mainPageObject.assertElementPresent(titleOfArticle, "Article title not found");
    }

    @Test
    public void testtest() {

        String article_title = "JavaScript", subtitle = "Programming language";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(article_title);
        driver.hideKeyboard();
        searchPageObject.waitForElementByTitleAndDescription(article_title, subtitle);
    }
}
