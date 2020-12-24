import lib.CoreTestCase;
import lib.ui.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import java.util.List;
import static java.time.Duration.*;


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
        searchPageObject.waiteForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() throws InterruptedException {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waiteForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waiteForCancelButtonToDisappear();
    }

    @Test
    public void testClearTextField() {
        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.waiteForElementAndSendKeys(
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
    public void testCompareArticleTitle() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waiteForTitleElement();



        String articleTitle = articlePageObject.getArticleTitle();

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                articleTitle
        );

    }

    @Test
    public void testHasTextInSearchField() {

        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        mainPageObject.waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found"
        );

        mainPageObject.waiteForElementAndClick(
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
    public void cancelSearch(){
        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        mainPageObject.waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found"
        );

        List<WebElement> listResultElements = mainPageObject.waitForElements(
                By.className("android.view.ViewGroup"),
                "Results is empty or elements not found"
        );

        Assert.assertTrue("No search results found",listResultElements.size()>1);

        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Element X not found",
                3
        );

        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Element X not found",
                3
        );

        mainPageObject.waiteForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Element X still on page",
                3
        );
    }

    @Test
    public void testCheckingSearchResults()
    {
        String searchText = "Java";
        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        mainPageObject.waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                searchText,
                "Text field not found"
        );

        List<WebElement> searchResult = mainPageObject.waitForElements(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Results not found"
        );

        for (WebElement webElement : searchResult) {
            Assert.assertTrue("The results does not contain '" + searchText + "'",
                    webElement.getAttribute("text").contains(searchText));
        }
    }

    @Test
    public void testSwipeArticle(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waiteForTitleElement();
        articlePageObject.swipeToFooter();
    }

    @Test
    public void testSaveArticle() {
        String name_of_folder = "test";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waiteForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        articlePageObject.addArticleToMyList(name_of_folder);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyList();

        MyListPageObject myListPageObject = new MyListPageObject(driver);
        myListPageObject.openFolderByName(name_of_folder);
        myListPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        String searchLine = "Linkin park";
//        String searchLine = "as2d1a2sd1a2s1d2as1d2asd";
        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                searchLine,
                "Text field not found"
        );

        driver.hideKeyboard();

        List<WebElement> search_result = mainPageObject.waitForElements(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/fragment_search_results']//*[@resource-id = 'org.wikipedia:id/page_list_item_container']"),
                "Search result is empty: " + searchLine
        );

        Assert.assertTrue(
                "No articles found",
                search_result.size() > 0
        );

    }

    @Test
    public void testAmountOfEmptySearch() {
        String searchLine = "as2d1as2d1a2s1d2";
        String emptyResultLabel  = "//*[@text = 'No results found']";
        String searchResults = "//*[@resource-id = 'org.wikipedia:id/fragment_search_results']//*[@resource-id = 'org.wikipedia:id/page_list_item_container']";
        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                searchLine,
                "Text field not found"
        );

        mainPageObject.waitForElementPresent(
                By.xpath(emptyResultLabel),
                "Not found 'Empty results label'"
        );

        mainPageObject.assertElementNotPresent(
                By.xpath(searchResults),
                "We found some results by request: " + searchLine
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults(){
        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        mainPageObject.waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found"
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]"),
                "Result not found",
                3
        );

        String title_before_ratation = mainPageObject.waiteElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text"
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_ratation = mainPageObject.waiteElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text"
        );

        Assert.assertEquals("Article have been changed after rotation", title_before_ratation, title_after_ratation);

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotate = mainPageObject.waiteElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text"
        );
    }

    @Test
    public void testCheckSearchInBackground() {
        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        mainPageObject.waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found"
        );

        driver.hideKeyboard();

        mainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]"),
                "Result not found"
        );

        driver.runAppInBackground(ofSeconds(5));

        mainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot find article after returning in background"
        );
    }

    @Test
    public void testSaveTwoArticles(){
        String readingListName = "Test1";
        String firstSubtitle = "//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]";
        String secondSubtitle = "//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Programming language')]";

        By titleOfArticle = By.id("org.wikipedia:id/view_page_title_text");

        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        mainPageObject.waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found"
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath(firstSubtitle),
                "Element not found: " + firstSubtitle,
                3
        );

        String firstArticleTitle = mainPageObject.waiteElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text"
        );


        mainPageObject.waiteForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Element 'More option' not found",
                5
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath("//*[@text = 'Add to reading list']"),
                "'Add to read list' element not found",
                5
        );

        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Button 'GOT IT' not found",
                5
        );

        mainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "'Text input' element not found"
        );

        mainPageObject.waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                readingListName,
                "'Text input' element not found"
        );

        mainPageObject.waiteForElementAndClick(
                By.id("android:id/button1"),
                "Not found 'OK' button",
                5
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"),
                "Element 'X' not found",
                5
        );

        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        mainPageObject.waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found"
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath(secondSubtitle),
                "Element not found: " + secondSubtitle,
                3
        );

        String secondArticleTitle = mainPageObject.waiteElementAndGetAttribute(
                titleOfArticle,
                "text"
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Element 'More option' not found",
                5
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath("//*[@text = 'Add to reading list']"),
                "'Add to read list' element not found",
                5
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath("//*[@text = '" + readingListName + "']"),
                "Reading list not found",
                5
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"),
                "Element 'X' not found",
                5
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc=\"My lists\"]"),
                "'My list' element not found",
                5
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/item_title'][@text = '" + readingListName +"']"),
                "List not found",
                5
        );

        mainPageObject.swipeToLeftElement(
                By.xpath("//*[@text = '" + firstArticleTitle +"']/../../.."),
                "Article '" + firstArticleTitle + "' not found",
                5
        );

        mainPageObject.waiteForElementAndClick(
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


        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        mainPageObject.waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found"
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath(firstSubtitle),
                "Element not found: " + firstSubtitle,
                3
        );

        mainPageObject.assertElementPresent(titleOfArticle, "Article title not found");
    }

}
