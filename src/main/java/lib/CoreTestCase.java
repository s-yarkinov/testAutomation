package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import static java.time.Duration.ofSeconds;

public class CoreTestCase extends TestCase {
    protected AppiumDriver driver;
    private static String appiumURl = "http://0.0.0.0:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Android");
        cap.setCapability("deviceName", "emulator-5554");
        cap.setCapability("platformVersion", "7.1.1");
        cap.setCapability("automationName", "Appium");
        cap.setCapability("appPackage", "org.wikipedia");
        cap.setCapability("appActivity", "main.MainActivity");
        cap.setCapability("fullReset", "false");
        cap.setCapability("app", "/Users/macmini2/IdeaProjects/testAutomation/apps/org.wikipedia.apk");

        driver = new AndroidDriver(new URL(appiumURl), cap);
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
}
