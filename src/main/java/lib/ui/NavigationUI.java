package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

abstract public class NavigationUI extends MainPageObject{

    protected static String
        MY_LIST_LINK;

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyList(){
        this.waitForElementAndClick(
                MY_LIST_LINK,
                "'My list' element not found",
                5
        );
    }
}
