package lib;

import io.appium.java_client.AppiumDriver;
import lib.ui.WelcomePageObject;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import static java.time.Duration.ofSeconds;

public class CoreTestCase {

    protected RemoteWebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.out.println("From core test");
        driver = Platform.getInstance().getDriver();
        this.rotateScreenPortrait();
        this.skipWelcomePageForIos();
        this.openWikiPageForMobileWeb();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }


    protected void rotateScreenLandscape(){
        if(driver instanceof AppiumDriver)
        {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE);
        }
        else
            System.out.println("Method rotateScreenLandscape() does nothing for platform " + Platform.getInstance().getPlatformVar());

    }

    protected void rotateScreenPortrait(){
        if(driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver)this.driver;
            driver.rotate(ScreenOrientation.PORTRAIT);
        }
        else
        {
            System.out.println("Method rotateScreenPortrait() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }

    }

    protected void runAppInBackground(int seconds) {
        if(driver instanceof AppiumDriver){
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(ofSeconds(seconds));
        }
        else
        {
            System.out.println("Method runAppInBackground() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }

    }

    private void skipWelcomePageForIos() {
        if (Platform.getInstance().isIOS()) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
            welcomePageObject.clickSkip();
        }
    }

    protected void openWikiPageForMobileWeb(){
        if(Platform.getInstance().isMw()){
            driver.get("https://en.m.wikipedia.org");
        }
        else
        {
            System.out.println("Method openWikiPageForMobileWeb() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }
}
