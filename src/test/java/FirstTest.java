import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;
import java.util.Locale;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;


public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Android");
        cap.setCapability("deviceName", "emulator-5554");
        cap.setCapability("platformVersion", "7.1.1");
        cap.setCapability("automationName", "Appium");
        cap.setCapability("appPackage", "org.wikipedia");
        cap.setCapability("appActivity", "main.MainActivity");
        cap.setCapability("fullReset", "false");
        cap.setCapability("app", "/Users/macmini2/IdeaProjects/testAutomation/apps/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), cap);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void firstTestRun() throws InterruptedException {

        waiteForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element",
                1
        );
        waiteForElementAndSendKeys(
                By.xpath("//android.widget.LinearLayout/android.widget.EditText"),
                "Java",
                "Cannot SendKeys to element 'SearchField'",
                1
        );
        waitForElementPresent(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]"),
                "Result not found"
        );
    }

    @Test
    public void testCancelSearch() {
        waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );
        waiteForElementAndClick(
                By.xpath("//android.view.ViewGroup/android.widget.ImageButton"),
                "Back button not found",
                3
        );

        waiteForElementNotPresent(
                By.xpath("//android.view.ViewGroup/android.widget.ImageButton"),
                "X is still present on the screen",
                5);
    }

    @Test
    public void clearTextField() throws InterruptedException {
        waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found",
                3
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Element 'search field' not found"
        );
    }

    @Test
    public void testCompareArticleTitle() {
        waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found",
                3
        );

        waiteForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]"),
                "Result not found",
                3
        );
        WebElement titleElement = waitForElement(
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

        waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found",
                3
        );

        waiteForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]"),
                "Result not found",
                3
        );

        assertElementHasText(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Java (programming language)",
                "We see unexpected title"
                );
    }

    @Test
    public void cancelSearch(){
        waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found",
                15
        );

        List<WebElement> listResultElements = waitForElements(
                By.className("android.view.ViewGroup"),
                "Results is empty or elements not found"
        );

        Assert.assertTrue("No search results found",listResultElements.size()>1);

        waiteForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Element X not found",
                3
        );

        waiteForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Element X not found",
                3
        );

        waiteForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Element X still on page",
                3
        );
    }

    @Test
    public void checkingSearchResults()
    {
        String searchText = "Java";
        waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                searchText,
                "Text field not found",
                15
        );

        List<WebElement> searchResult = waitForElements(
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
        waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Appium",
                "Text field not found",
                15
        );

        waiteForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = 'Appium']"),
                "Result not found",
                3
        );
        WebElement titleElement = waitForElement(
                By.xpath("//android.view.View/android.view.View[1]/android.view.View[1]"),
                "Title desc not found",
                15
        );

        swipeUpToFindElement(
                By.id("org.wikipedia:id/page_external_link"),
                "View article in browser not found",
                15
        );

    }

    @Test
    public void testSaveArticle() {

        String listName = "test";
        waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Field not equals 'Search Wikipedia'"
        );

        waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Text field not found",
                15
        );

        waiteForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[contains(@text, 'Object-oriented programming language')]"),
                "Result not found",
                3
        );

        waitForElement(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Title desc not found",
                15
        );


        waiteForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Element 'More option' not found",
                5
        );

        waiteForElementAndClick(
                By.xpath("//*[@text = 'Add to reading list']"),
                "'Add to read list' element not found",
                5
        );

        waiteForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Button 'GOT IT' not found",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "'Text input' element not found"
        );

        waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                listName,
                "'Text input' element not found",
                5
        );

        waiteForElementAndClick(
                By.id("android:id/button1"),
                "Not found 'OK' button",
                5
        );

        waiteForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"),
                "Element 'X' not found",
                5
        );

        waiteForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc=\"My lists\"]"),
                "'My list' element not found",
                5
        );

        waiteForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/item_title'][@text = '" + listName +"']"),
                "List not found",
                5
        );

        swipeToLeftElement(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Saved article not found",
                5
        );

        waiteForElementNotPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Article still in saved articles",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        String searchLine = "Linkin park";
//        String searchLine = "as2d1a2sd1a2s1d2as1d2asd";
        waiteForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element",
                1
        );

        waiteForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                searchLine,
                "Text field not found",
                15
        );

        driver.hideKeyboard();

        List<WebElement> search_result = waitForElements(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/fragment_search_results']//*[@resource-id = 'org.wikipedia:id/page_list_item_container']"),
                "Search result is empty: " + searchLine
        );

        Assert.assertTrue(
                "No articles found",
                search_result.size() > 0
        );

    }



    private WebElement waitForElement(By by, String error_msg, long timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_msg + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private List<WebElement> waitForElements(By by, String err_msg) {
        List<WebElement> elements = driver.findElements(by);
        return elements;
    }


    private WebElement waitForElementPresent(By by, String error_msg) {
        return  waitForElement(by, error_msg, 5);
    }

    private WebElement waiteForElementAndClick(By by, String err_msg, long timeoutSeconds){
        WebElement element = waitForElementPresent(by, err_msg);
        element.click();
        return element;
    }

    private WebElement waiteForElementAndSendKeys(By by, String value, String err_msg, long timeoutSeconds){
        WebElement element = waitForElementPresent(by, err_msg);
        element.sendKeys(value);
        return element;
    }

    private boolean waiteForElementNotPresent(By by, String err_msg, long timeoutSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(err_msg + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String err_msg) {
        WebElement element = waitForElementPresent(by, err_msg);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String expectedValue, String err_msg) {
        WebElement element = waitForElement(
                by,
                "Element " + by + " not found",
                5
        );

        String obtainText = element.getAttribute("text");
        Assert.assertEquals(
                "Element does not contain " + expectedValue,
                expectedValue,
                obtainText
        );
    }

    private void swipeUp(int timeOfSwipe) {

        TouchAction action = new TouchAction(driver);

        Dimension size = driver.manage().window().getSize();

        int x = size.width / 2;
        int start_y = (int)(size.height * 0.8);
        int end_y = (int)(size.height * 0.2);

        action.press(point(x, start_y)).waitAction(waitOptions(ofMillis(timeOfSwipe))).
                moveTo(point(x, end_y)).release().perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String err_msg, int maxSwipes) {
        int already_swipe = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swipe > maxSwipes)
            {
                waitForElement(
                        by,
                        "Cannot find element by swiping up.\n" + err_msg,
                        0
                );
                return;
            }
            swipeUpQuick();
            ++already_swipe;
        }
    }

    protected void swipeToLeftElement(By by, String err_msg, long timeoutSeconds) {
        WebElement element =  waitForElement(by, err_msg, timeoutSeconds);
        int x_left = element.getLocation().getX() + 20;
        int x_right = x_left + (element.getSize().width - 40);
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().height;
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);

        action.
                press(point(x_right, middle_y)).
                waitAction(waitOptions(ofMillis(300))).
                moveTo(point(x_left, middle_y)).
                release().
                perform();
    }
}
