package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import static java.time.Duration.ofSeconds;

public class CoreTestCase extends TestCase {
    protected AppiumDriver driver;
    private static String appiumURl = "http://0.0.0.0:4723/wd/hub";

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        DesiredCapabilities cap = this.getCapabilitiesFromEnv();
//        driver = new AndroidDriver(new URL(appiumURl), cap);
        driver = initDriver(appiumURl, cap);
        this.rotateScreenPortrait();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    protected void rotateScreenLandscape(){
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void rotateScreenPortrait(){
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void runAppInBackground(int seconds) {
        driver.runAppInBackground(ofSeconds(seconds));
    }

    private DesiredCapabilities getCapabilitiesFromEnv() throws Exception {
        String platform = System.getenv("PLATFORM");
        DesiredCapabilities cap = new DesiredCapabilities();
        if (platform.equals(PLATFORM_ANDROID)) {
            cap.setCapability("platformName", "Android");
            cap.setCapability("deviceName", "emulator-5554");
            cap.setCapability("platformVersion", "7.1.1");
            cap.setCapability("automationName", "Appium");
            cap.setCapability("appPackage", "org.wikipedia");
            cap.setCapability("appActivity", "main.MainActivity");
            cap.setCapability("fullReset", "false");
            cap.setCapability("app", "/Users/macmini2/IdeaProjects/testAutomation/apps/org.wikipedia.apk");
        }
        else if(platform.equals(PLATFORM_IOS)) {
            cap.setCapability("platformName", "iOS");
            cap.setCapability("deviceName", "iPhone10_13.3");
            cap.setCapability("platformVersion", "13.3");
            cap.setCapability("app", "/Users/macmini2/IdeaProjects/testAutomation/apps/Wikipedia.app");
        }
        else
            throw new Exception("Cannot get run platform from env variable. Platform = " + platform);

        return cap;
    }

    private AppiumDriver initDriver(String URL, DesiredCapabilities capabilities) throws Exception{
        String platform = System.getenv("PLATFORM");

        if(platform.equals(PLATFORM_IOS)) {
            driver = new IOSDriver(new URL(URL), capabilities);
        }
        else if(platform.equals(PLATFORM_ANDROID)){
            driver = new AndroidDriver(new URL(URL), capabilities);
        }
        else
            throw new Exception("Cannot init driver: PLATFORM = " + platform);

        return driver;
    }
}
