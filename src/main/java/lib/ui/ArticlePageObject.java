package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{
    private static final String
        ARTICLE_TITLE = "id:org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "id:org.wikipedia:id/page_external_link",
        OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
        ADD_TO_MY_LIST_BUTTON = "xpath://*[@text = 'Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "id:android:id/button1",
        CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc=\"Navigate up\"]",
        EXISTING_MY_LIST_TPL = "xpath://*[@text = '{MY_LIST_NAME}']";

//    TPL
    public String getXpathExistingMyList(String myListName) {
        return EXISTING_MY_LIST_TPL.replace("{MY_LIST_NAME}", myListName);
    }
//    TPL
    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(ARTICLE_TITLE,"Cannot find article title");
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                FOOTER_ELEMENT,
                "Cannot find the end of article",
                20
        );
    }

    public void createMyListAndAddArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Element 'More option' not found",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_BUTTON,
                "'Add to read list' element not found",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Button 'GOT IT' not found",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "'Text input' element not found"
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "'Text input' element not found"
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Not found 'OK' button",
                5
        );
    }

    public void addArticleToExistingMyList(String existingListName) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Element 'More option' not found",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_BUTTON,
                "'Add to read list' element not found",
                5
        );

        String existingMyListXpath = this.getXpathExistingMyList(existingListName);
        this.waitForElementAndClick(
                existingMyListXpath,
                "Reading list not found",
                5
        );
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Element 'X' not found",
                5
        );
    }

    public void assertTitleIsPresent() {
        this.assertElementPresent(By.id(ARTICLE_TITLE), "Title is not present");
    }
}
