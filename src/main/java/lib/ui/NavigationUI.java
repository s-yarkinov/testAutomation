package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject{

    private static final String
        MY_LIST_LINK = "//android.widget.FrameLayout[@content-desc=\"My lists\"]";

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyList(){
        this.waiteForElementAndClick(
                By.xpath(MY_LIST_LINK),
                "'My list' element not found",
                5
        );
    }
}
