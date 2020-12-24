package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{
    private static final String
        ARTICLE_TITLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "org.wikipedia:id/page_external_link",
        OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
        ADD_TO_MY_LIST_BUTTON = "//*[@text = 'Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "android:id/button1",
        CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]";


    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waiteForTitleElement() {
        return this.waitForElementPresent(By.id(ARTICLE_TITLE),"Cannot find article title");
    }

    public String getArticleTitle() {
        WebElement title_element = waiteForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                By.id(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20
        );
    }

    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Element 'More option' not found",
                5
        );

        this.waitForElementAndClick(
                By.xpath(ADD_TO_MY_LIST_BUTTON),
                "'Add to read list' element not found",
                5
        );

        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Button 'GOT IT' not found",
                5
        );

        this.waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "'Text input' element not found"
        );

        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                name_of_folder,
                "'Text input' element not found"
        );

        this.waitForElementAndClick(
                By.id(MY_LIST_OK_BUTTON),
                "Not found 'OK' button",
                5
        );
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Element 'X' not found",
                5
        );
    }
}
