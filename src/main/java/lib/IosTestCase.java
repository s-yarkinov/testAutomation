package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import static java.time.Duration.ofSeconds;

public class IosTestCase extends TestCase {
    protected AppiumDriver driver;
    private static String appiumURl = "http://0.0.0.0:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "iOS");
        cap.setCapability("deviceName", "iPhone10_13.3");
        cap.setCapability("platformVersion", "13.3");
        cap.setCapability("app", "/Users/macmini2/IdeaProjects/testAutomation/apps/Wikipedia.app");

        driver = new IOSDriver(new URL(appiumURl), cap);
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
