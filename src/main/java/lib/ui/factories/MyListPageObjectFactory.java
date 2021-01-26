package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListPageObject;
import lib.Platform;
import lib.ui.android.AndroidMyListPageObject;
import lib.ui.ios.IOSMyListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MyListPageObjectFactory {
    public static MyListPageObject get(RemoteWebDriver driver) {
        if(Platform.getInstance().isAndroid()){
            return new AndroidMyListPageObject(driver);
        }
        else
            return new IOSMyListPageObject(driver);
    }
}
