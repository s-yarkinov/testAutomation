package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }


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


    public WebElement waitForElement(By by, String error_msg, long timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_msg + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public List<WebElement> waitForElements(By by, String err_msg) {
        List<WebElement> elements = driver.findElements(by);
        return elements;
    }


    public WebElement waitForElementPresent(By by, String error_msg) {
        return  waitForElement(by, error_msg, 15);
    }

    public WebElement waiteForElementAndClick(By by, String err_msg, long timeoutSeconds){
        WebElement element = waitForElementPresent(by, err_msg);
        element.click();
        return element;
    }

//    private WebElement waiteForElementAndLongPress(By by, String err_msg, long timeoutSeconds) {
//        MobileElement element = (MobileElement) waitForElement(by, err_msg, timeoutSeconds);
//
//        TouchAction action = new TouchAction(driver);
//
//        action.longPress(LongPressOptions.longPressOptions());
//    }

    public WebElement waiteForElementAndSendKeys(By by, String value, String err_msg){
        WebElement element = waitForElementPresent(by, err_msg);
        element.sendKeys(value);
        return element;
    }

    public boolean waiteForElementNotPresent(By by, String err_msg, long timeoutSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(err_msg + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClear(By by, String err_msg) {
        WebElement element = waitForElementPresent(by, err_msg);
        element.clear();
        return element;
    }

    public void assertElementHasText(By by, String expectedValue, String err_msg) {
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

    public void swipeUp(int timeOfSwipe) {

        TouchAction action = new TouchAction(driver);

        Dimension size = driver.manage().window().getSize();

        int x = size.width / 2;
        int start_y = (int)(size.height * 0.8);
        int end_y = (int)(size.height * 0.2);

        action.press(point(x, start_y)).waitAction(waitOptions(ofMillis(timeOfSwipe))).
                moveTo(point(x, end_y)).release().perform();
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String err_msg, int maxSwipes) {
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

    public void swipeToLeftElement(By by, String err_msg, long timeoutSeconds) {
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

    public int getAmountElements(By by) {
        List<WebElement> listOfElement = driver.findElements(by);
        return listOfElement.size();
    }


    public void assertElementNotPresent(By by, String err_msg) {
        int amountElements = getAmountElements(by);
        if(amountElements > 0) {
            String default_msg = "An element '" + by.toString() + "' supposed to be not present\n";
            throw new AssertionError(default_msg + " " + err_msg);
        }
    }

    public String waiteElementAndGetAttribute(By by, String attribute) {
        return waitForElement(
                by,
                "Element not found: " + by.toString(),
                15
        ).getAttribute(attribute);
    }
}
