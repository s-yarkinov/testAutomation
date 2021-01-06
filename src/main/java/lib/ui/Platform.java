package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static final String APPIUM_URL = "http://0.0.0.0:4723/wd/hub";


    public AppiumDriver getDriver() throws Exception {
        URL url = new URL(APPIUM_URL);
        if(this.isAndroid()){
            return new AndroidDriver(url, this.getAndroidDesiredCapabilities());
        }
        else if(this.isIOS()) {
            return new IOSDriver(url, this.getIosDesiredCapabilities());
        }
        else
            throw new Exception("Invalid platform:" + this.getPlatformVar());
    }

    public boolean isAndroid() {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS() {
        return isPlatform(PLATFORM_IOS);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability("platformVersion", "7.1.1");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", "main.MainActivity");
        capabilities.setCapability("fullReset", "false");
        capabilities.setCapability("app", "/Users/macmini2/IdeaProjects/testAutomation/apps/org.wikipedia.apk");
        return capabilities;
    }

    private DesiredCapabilities getIosDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone10_13.3");
        capabilities.setCapability("platformVersion", "13.3");
        capabilities.setCapability("app", "/Users/macmini2/IdeaProjects/testAutomation/apps/Wikipedia.app");
        return capabilities;
    }

    private String getPlatformVar() {
        return System.getenv("PLATFORM");
    }

    private boolean isPlatform(String my_platform) {
        String platform = this.getPlatformVar();

        return my_platform.equals(platform);
    }
}
