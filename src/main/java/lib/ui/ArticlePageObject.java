package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.factories.SearchPageObjectFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject{
    protected static String
        ARTICLE_TITLE,
        FOOTER_ELEMENT,
        OPTIONS_BUTTON,
        ADD_TO_MY_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        EXISTING_MY_LIST_TPL;

//    TPL
    public String getXpathExistingMyList(String myListName) {
        return EXISTING_MY_LIST_TPL.replace("{MY_LIST_NAME}", myListName);
    }
//    TPL
    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(ARTICLE_TITLE,"Cannot find article title");
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        }
        {
            return title_element.getAttribute("name");
        }

    }

    public void swipeToFooter() {
        if(Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        }
        else {
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        }
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

    public void addArticlesToSaved(){
        this.waitForElementAndClick(ADD_TO_MY_LIST_BUTTON, "Add to saved list button is not found", 1);
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
        if(Platform.getInstance().isIOS()) {
            SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
            searchPageObject.clickCancelSearch();
        }
    }

    public void assertTitleIsPresent() {
        this.assertElementPresent(By.id(ARTICLE_TITLE), "Title is not present");
    }


}
