package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
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
            OPTIONS_REMOVE_TO_MY_LIST_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
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
        screenshot(this.takeScreenshot("article_title"));
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        }
        else if (Platform.getInstance().isIOS())
        {
            return title_element.getAttribute("name");
        }
        else
            return title_element.getText();

    }
    @Step("Swipe to the footer")
    public void swipeToFooter() {
        if(Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        }
        else if(Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        }
        else {
            this.scrollWebPageTillElementNotVisible(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        }
    }
    @Step("Created a reading list and added an article there")
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
    @Step("Add article to the saved list")
    public void addArticlesToSaved() throws InterruptedException {
        if(Platform.getInstance().isMw()) {
            this.removeArticleFromSavedIfItAdded();
        }
        Thread.sleep(2000);
        this.waitForElementAndClick(ADD_TO_MY_LIST_BUTTON, "Add to saved list button is not found", 1);
    }
    @Step("Removed article from Saved if it attached")
    public void removeArticleFromSavedIfItAdded(){
        if(this.isElementPresent(OPTIONS_REMOVE_TO_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_TO_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from saved",
                    5
            );
            this.waitForElementPresent(
                    ADD_TO_MY_LIST_BUTTON,
                    "Cannot find 'ADD TO MY LIST BUTTON' after removing it from this list before"
            );
        }
    }
    @Step("Add article to existing my list")
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
    @Step("Closed the article")
    public void closeArticle() {
        if(Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Element 'X' not found",
                    5
            );
        }
        else
            System.out.println("Method closeArticle() do nothing for platform " + Platform.getInstance().getPlatformVar());
        if(Platform.getInstance().isIOS()) {
            SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
            searchPageObject.clickCancelSearch();
        }
    }
    @Step("Check if the article is on the page")
    public void assertTitleIsPresent() {
        this.assertElementPresent(By.id(ARTICLE_TITLE), "Title is not present");
    }


}
