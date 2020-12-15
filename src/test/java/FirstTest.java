import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

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
        cap.setCapability("app", "/Users/macmini2/IdeaProjects/testAutomation/apps/org.wikipedia_50336.apk");

        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), cap);

        waiteForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Skip button not found",
                5
        );
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
                By.xpath("//android.view.View/android.view.View[1]/android.view.View[1]"),
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
                "Search Wikipedia",
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
                By.xpath("//android.view.View/android.view.View[1]/android.view.View[1]"),
                "Java (programming language)",
                "We see unexpected title"
                );

    }



    private WebElement waitForElement(By by, String error_msg, long timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_msg + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
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

    private WebElement waitForElementAndClear(By by, String err_msg) throws InterruptedException {
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

}
