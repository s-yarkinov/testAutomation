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
    public void testHasTextInSearchField() {

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
                By.xpath("//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]"),
                "Result not found",
                3
        );

        mainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Java (programming language)",
                "We see unexpected title"
        );
    }



    @Test
    public void cancelSearchTests(){

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");

//        mainPageObject.waitForElementAndClick(
//                By.id("org.wikipedia:id/search_container"),
//                "Cannot find element",
//                1
//        );
//
//        mainPageObject.assertElementHasText(
//                By.id("org.wikipedia:id/search_src_text"),
//                "Search Wikipedia",
//                "Search Field not equals 'Search Wikipedia'"
//        );
//
//        mainPageObject.waitForElementAndSendKeys(
//                By.id("org.wikipedia:id/search_src_text"),
//                "Java",
//                "Text field not found"
//        );
//
//        List<WebElement> listResultElements = mainPageObject.waitForElements(
//                By.className("android.view.ViewGroup"),
//                "Results is empty or elements not found"
//        );
//
//        assertTrue("No search results found",listResultElements.size()>1);
//
//        mainPageObject.waitForElementAndClick(
//                By.id("org.wikipedia:id/search_close_btn"),
//                "Element X not found",
//                3
//        );
//
//        listResultElements = mainPageObject.waitForElements(
//                By.className("android.view.ViewGroup"),
//                "Results is empty or elements not found"
//        );
//
//        assertTrue("Canceling the search didn't work", listResultElements.size() == 1);
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
    public void testSaveTwoArticles(){
        String readingListName = "Test1";
        String firstSubtitle = "//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]";
        String secondSubtitle = "//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Programming language')]";

        By titleOfArticle = By.id("org.wikipedia:id/view_page_title_text");

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

        String firstArticleTitle = mainPageObject.waitElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text"
        );


        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Element 'More option' not found",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text = 'Add to reading list']"),
                "'Add to read list' element not found",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Button 'GOT IT' not found",
                5
        );

        mainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "'Text input' element not found"
        );

        mainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                readingListName,
                "'Text input' element not found"
        );

        mainPageObject.waitForElementAndClick(
                By.id("android:id/button1"),
                "Not found 'OK' button",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"),
                "Element 'X' not found",
                5
        );

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
                By.xpath(secondSubtitle),
                "Element not found: " + secondSubtitle,
                3
        );

        String secondArticleTitle = mainPageObject.waitElementAndGetAttribute(
                titleOfArticle,
                "text"
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Element 'More option' not found",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text = 'Add to reading list']"),
                "'Add to read list' element not found",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text = '" + readingListName + "']"),
                "Reading list not found",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"),
                "Element 'X' not found",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc=\"My lists\"]"),
                "'My list' element not found",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/item_title'][@text = '" + readingListName +"']"),
                "List not found",
                5
        );

        mainPageObject.swipeToLeftElement(
                By.xpath("//*[@text = '" + firstArticleTitle +"']/../../.."),
                "Article '" + firstArticleTitle + "' not found",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text = '" + secondArticleTitle +"']"),
                "Article '" + secondArticleTitle + "' not found",
                15
        );

        mainPageObject.assertElementHasText(
                titleOfArticle,
                secondArticleTitle,
                "The title of the article is different"
        );
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

}
