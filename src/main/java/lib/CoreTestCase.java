package lib;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lib.ui.WelcomePageObject;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileOutputStream;
import java.util.Properties;

import static java.time.Duration.ofSeconds;

public class CoreTestCase {

    protected RemoteWebDriver driver;

    @Before
    @Step("Run driver and session")
    public void setUp() throws Exception {
        System.out.println("From core test");
        driver = Platform.getInstance().getDriver();
        this.createAllurePropertyFile();
        this.rotateScreenPortrait();
        this.skipWelcomePageForIos();
        this.openWikiPageForMobileWeb();
    }

    @After
    @Step("Remove driver and session")
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Step("Rotate screen to Landscape mode")
    protected void rotateScreenLandscape(){
        if(driver instanceof AppiumDriver)
        {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE);
        }
        else
            System.out.println("Method rotateScreenLandscape() does nothing for platform " + Platform.getInstance().getPlatformVar());

    }
    @Step("Rotate screen to Portrait mode")
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
    @Step("Send mobile app to background (this method does nothing for mobile web)")
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

    @Step("Skip welcome message for iOS")
    private void skipWelcomePageForIos() {
        if (Platform.getInstance().isIOS()) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
            welcomePageObject.clickSkip();
        }
    }
    @Step("Open Wikipedia URL for mobile web (this method does nothing for iOS and Android)")
    protected void openWikiPageForMobileWeb(){
        if(Platform.getInstance().isMw()){
            driver.get("https://en.m.wikipedia.org");
        }
        else
        {
            System.out.println("Method openWikiPageForMobileWeb() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    private void createAllurePropertyFile() {
        String path = System.getProperty("allure.results.directory");
        try {
            Properties props = new Properties();
            FileOutputStream fos = new FileOutputStream(path + "environment.properties");
            props.setProperty("Environment", Platform.getInstance().getPlatformVar());
            props.store(fos, "See https://github.com/AutomatedOwl/allure-environment-writer");
            fos.close();
        } catch (Exception e) {
            System.err.println("IO problem when writing allure properties file");
            e.printStackTrace();
        }
    }

}
