package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lib.Platform;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.List;
import java.util.regex.Pattern;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    @Step("Assert element present")
    public WebElement assertElementPresent(By by, String err_msg) {

        try {
            driver.findElement(by);
            return driver.findElement(by);
        }
        catch (Exception e)
        {
            throw new AssertionError(err_msg);
        }
    }

    @Step("Wait for element")
    public WebElement waitForElement(String locator, String error_msg, long timeoutSeconds) {
        By by = this.getLocatorByString(locator);

        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_msg + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public List<WebElement> waitForElements(String locator, String err_msg) {
        By by = this.getLocatorByString(locator);

        List<WebElement> elements = driver.findElements(by);
        return elements;
    }

    @Step("Wait for element present")
    public WebElement waitForElementPresent(String locator, String error_msg) {
        return  waitForElement(locator, error_msg, 15);
    }

    @Step("Wait for element and click")
    public WebElement waitForElementAndClick(String locator, String err_msg, long timeoutSeconds){
        WebElement element = waitForElementPresent(locator, err_msg);
        element.click();
        return element;
    }

    @Step("Wait for element and send keys")
    public WebElement waitForElementAndSendKeys(String locator, String value, String err_msg){
        WebElement element = waitForElementPresent(locator, err_msg);
        element.sendKeys(value);
        return element;
    }

    @Step("Wait for element not present")
    public boolean waitForElementNotPresent(String locator, String err_msg, long timeoutSeconds){
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(err_msg + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    @Step("Check is element present")
    public boolean isElementPresent(String locator){
        return getAmountElements(locator)>0;
    }


    public void tryClickElementWithFewAttempts(String locator, String err_msg, int amount_attempts){
        int current_attempts = 0;
        boolean need_more_attempts = true;

        while (need_more_attempts) {
            try {
                this.waitForElementAndClick(locator, err_msg,1);
                need_more_attempts = false;
            } catch (Exception e) {
                if(current_attempts>amount_attempts) {
                    this.waitForElementAndClick(locator, err_msg, 1);
                }
            }
            ++ amount_attempts;
        }
    }

    @Step("Wait for element and clear")
    public WebElement waitForElementAndClear(String locator, String err_msg) {
        WebElement element = waitForElementPresent(locator, err_msg);
        element.clear();
        return element;
    }


    @Step("Assert element has text")
    public void assertElementHasText(String locator, String expectedValue, String err_msg) {

        WebElement element = waitForElement(
                locator,
                "Element " + locator + " not found",
                5
        );

        String obtainText = element.getAttribute("text");
        Assert.assertEquals(
                "Element does not contain " + expectedValue,
                expectedValue,
                obtainText
        );
    }

    @Step("Swipe up")
    public void swipeUp(int timeOfSwipe) {
        if(driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver)driver);

            Dimension size = driver.manage().window().getSize();

            int x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);

            action.press(point(x, start_y)).waitAction(waitOptions(ofMillis(timeOfSwipe))).
                    moveTo(point(x, end_y)).release().perform();
        } else {
            System.out.println("Method swipeUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }


    @Step("Swipe up to find element")
    public void swipeUpToFindElement(String locator, String err_msg, int maxSwipes) {
        By by = this.getLocatorByString(locator);

        int already_swipe = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swipe > maxSwipes)
            {
                waitForElement(
                        locator,
                        "Cannot find element by swiping up.\n" + err_msg,
                        0
                );
                return;
            }
            swipeUpQuick();
            ++already_swipe;
        }
    }

    @Step("Scroll WebPageUp")
    public void scrollWebPageUp(){
        if(Platform.getInstance().isMw()){
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("window.scrollBy(0, 250)");
        }
        else
            System.out.println("Method scrollWebPageUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
    }

    @Step("Scroll web page to till element not visible")
    public void scrollWebPageTillElementNotVisible(String locator, String error_msg, int max_swiped) {
        int alreadySwiped = 0;
        WebElement element = this.waitForElementPresent(locator, error_msg);
        while (this.isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp();
            ++alreadySwiped;
            if(alreadySwiped > max_swiped)
                Assert.assertTrue(error_msg, element.isDisplayed());
        }
    }

    @Step("Swipe up till Element appear")
    public void swipeUpTillElementAppear(String locator, String err_msg, int max_swipes) {
        int already_swiped = 0;
        while (!this.isElementLocatedOnTheScreen(locator)) {
            if(already_swiped>max_swipes) {
                Assert.assertTrue(err_msg, this.isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    @Step("Check is element locate on the screen")
    public boolean isElementLocatedOnTheScreen(String locator) {
        int element_location_by_y = this.waitForElementPresent(locator, "Element:" + locator + " not found").getLocation().getY();
        if(Platform.getInstance().isMw()){
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            Object js_result = javascriptExecutor.executeScript("return window.pageYOffset");
            element_location_by_y -= Integer.parseInt(js_result.toString());
        }
        int screen_size_by_y = driver.manage().window().getSize().height;
        return element_location_by_y<screen_size_by_y;
    }

    @Step("Swipe to left element")
    public void swipeToLeftElement(String locator, String err_msg, long timeoutSeconds) {
        if(driver instanceof AppiumDriver) {
            WebElement element = waitForElement(locator, err_msg, timeoutSeconds);
            int x_left = element.getLocation().getX() + 20;
            int x_right = x_left + (element.getSize().width - 40);
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().height;
            int middle_y = (upper_y + lower_y) / 2;

            TouchAction action = new TouchAction((AppiumDriver) driver);

            action.press(point(x_right, middle_y));
            action.waitAction(waitOptions(ofMillis(300)));

            if (Platform.getInstance().isAndroid()) {
                action.moveTo(point(x_left, middle_y));
            } else {
                int offset_x = (-1 * element.getSize().getWidth());
                action.moveTo(point(offset_x, 0));
            }

            action.release();
            action.perform();
        }
    }

    @Step("Click element to the right upper corner")
    public void clickElementToTheRightUpperCorner(String locator, String err_msg){
        if(driver instanceof AppiumDriver) {
            WebElement element = this.waitForElementPresent(locator + "/..", err_msg);
            int right_x = element.getLocation().getX();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().height;
            int middle_y = (lower_y + upper_y) / 2;
            int width = element.getSize().getWidth();

            int point_to_click_x = (right_x + width) - 20;
            int point_to_click_y = middle_y;

            TouchAction action = new TouchAction((AppiumDriver)driver);
            action.tap(point(point_to_click_x, point_to_click_y)).perform();
        }
    }

    @Step("Get amount of elements")
    public int getAmountElements(String locator) {
        By by = this.getLocatorByString(locator);
//        this.waitForElementPresent(locator, "Result elements not found");
        List<WebElement> listOfElement = driver.findElements(by);
        return listOfElement.size();
    }


    @Step("Assert element not present")
    public void assertElementNotPresent(String locator, String err_msg) {
        int amountElements = getAmountElements(locator);
        if(amountElements > 0) {
            String default_msg = "An element '" + locator + "' supposed to be not present\n";
            throw new AssertionError(default_msg + " " + err_msg);
        }
    }

    @Step("Wait element and get attribute")
    public String waitElementAndGetAttribute(String locator, String attribute) {
        return waitForElement(
                locator,
                "Element not found: " + locator,
                15
        ).getAttribute(attribute);
    }

    @Step("Get located by string")
    private By getLocatorByString(String locator_with_type) {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);

        String by_type = exploded_locator[0];
        String by_name = exploded_locator[1];


        if(by_type.equals("xpath")) {
            return By.xpath(by_name);
        }
        else if(by_type.equals("id")) {
            return By.id(by_name);
        }
        else if(by_type.equals("css")) {
            return By.cssSelector(by_name);
        }
        else
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator_with_type);
    }

    @Step("Take screen")
    public String takeScreenshot(String name) {
        TakesScreenshot ts = (TakesScreenshot) this.driver;
        File sourse = ts.getScreenshotAs(OutputType.FILE);
        String path = "./screenshots" + System.getProperty("user.dir") + "/" + name + "_screen.png";
        try {
            FileUtils.copyFile(sourse, new File(path));
            System.out.println("THe screen was taken :" + path);
        }
        catch (Exception e) {
            System.out.println("Cannot take screenshot error: " + e.getMessage());
        }

        return path;
    }

    @Attachment
    public static byte[] screenshot(String path) {
        byte[] bytes = new byte[0];
        try{
            bytes = Files.readAllBytes(Paths.get(path));
        } catch (Exception e) {
            System.out.println("Cannot get bytes from screenshot. Error: " + e.getMessage());
        }
        return bytes;
    }
}
