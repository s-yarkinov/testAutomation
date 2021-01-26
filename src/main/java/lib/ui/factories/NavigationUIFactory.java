package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;
import lib.Platform;
import lib.ui.android.AndroidNavigationUI;
import lib.ui.ios.IOSNavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NavigationUIFactory {
    public static NavigationUI get(RemoteWebDriver driver) {
        if(Platform.getInstance().isIOS())
            return new IOSNavigationUI(driver);
        else
            return new AndroidNavigationUI(driver);
    }
}
