package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListPageObject;
import lib.Platform;
import lib.ui.android.AndroidMyListPageObject;
import lib.ui.ios.IOSMyListPageObject;
import lib.ui.mobileWeb.MWMyListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MyListPageObjectFactory {
    public static MyListPageObject get(RemoteWebDriver driver) {
        if(Platform.getInstance().isAndroid()){
            return new AndroidMyListPageObject(driver);
        }
        else if (Platform.getInstance().isIOS())
            return new IOSMyListPageObject(driver);
        else
            return new MWMyListPageObject(driver);
    }
}
