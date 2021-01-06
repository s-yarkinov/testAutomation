package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.ui.Platform;
import org.openqa.selenium.ScreenOrientation;
import static java.time.Duration.ofSeconds;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;
    protected Platform platform;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.platform = new Platform();
        driver = platform.getDriver();
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
