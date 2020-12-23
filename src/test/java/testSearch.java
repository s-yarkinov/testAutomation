import lib.CoreTestCase;
import lib.ui.MainPageObject;
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
    public void testFirstTestRun() {

        mainPageObject.waiteForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element",
                1
        );
        mainPageObject.waiteForElementAndSendKeys(
                By.xpath("//android.widget.LinearLayout/android.widget.EditText"),
                "Java",
                "Cannot SendKeys to element 'SearchField'",
                1
        );
        mainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]"),
                "Result not found"
        );
    }

    @Test
    public void testCancelSearch() {
        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );
        mainPageObject.waiteForElementAndClick(
                By.xpath("//android.view.ViewGroup/android.widget.ImageButton"),
                "Back button not found",
                3
        );

        mainPageObject.waiteForElementNotPresent(
                By.xpath("//android.view.ViewGroup/android.widget.ImageButton"),
                "X is still present on the screen",
                5);
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
                "Text field not found",
                3
        );

        mainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Element 'search field' not found"
        );
    }

    @Test
    public void testCompareArticleTitle() {
        mainPageObject.waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        mainPageObject.waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found",
                3
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]"),
                "Result not found",
                3
        );
        WebElement titleElement = mainPageObject.waitForElement(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Title desc not found",
                15
        );

        String articleTitle = titleElement.getAttribute("text");

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
                "Text field not found",
                3
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
                "Text field not found",
                15
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
                "Text field not found",
                15
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
                "Appium",
                "Text field not found",
                15
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = 'Appium']"),
                "Result not found",
                3
        );
        WebElement titleElement = mainPageObject.waitForElement(
                By.xpath("//android.view.View/android.view.View[1]/android.view.View[1]"),
                "Title desc not found",
                15
        );

        mainPageObject.swipeUpToFindElement(
                By.id("org.wikipedia:id/page_external_link"),
                "View article in browser not found",
                15
        );

    }

    @Test
    public void testSaveArticle() {

        String readingListName = "test";
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
                "Text field not found",
                15
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]"),
                "Result not found",
                3
        );

        mainPageObject.waitForElement(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Title desc not found",
                15
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
                "'Text input' element not found",
                5
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
                By.id("org.wikipedia:id/page_list_item_container"),
                "Saved article not found",
                5
        );

        mainPageObject.waiteForElementNotPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Article still in saved articles",
                5
        );
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
                "Text field not found",
                15
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
                "Text field not found",
                15
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
                "Text field not found",
                15
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
                "Text field not found",
                15
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
                "Text field not found",
                15
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
                "'Text input' element not found",
                5
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
                "Text field not found",
                15
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
                "Text field not found",
                15
        );

        mainPageObject.waiteForElementAndClick(
                By.xpath(firstSubtitle),
                "Element not found: " + firstSubtitle,
                3
        );

        mainPageObject.assertElementPresent(titleOfArticle, "Article title not found");
    }

}
