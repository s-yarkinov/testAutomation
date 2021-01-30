package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListPageObject extends MainPageObject{

    protected static String
        FOLDER_NAME_TPL,
        ARTICLE_BY_TITLE_TPL,
        ARTICLE_BY_SUBTITLE_TPL,
        CLOSE_SYNC_BUTTON,
        CLOSE_SYNC_TEXT,
        LAYOUT_TITLE,
        REMOVE_FROM_SAVE_BUTTON,
        BEGIN_ARTICLE_TPL;

    public MyListPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void isOpen(){
        this.waitForElementPresent(LAYOUT_TITLE, "My List screen is not open");
    }

    private static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByArticle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{ARTICLE_TITLE}", article_title);
    }
    private static String getRemoveButtonByType(String article_title) {
        return REMOVE_FROM_SAVE_BUTTON.replace("{ARTICLE_TITLE}", article_title);
    }
    private static String getSavedArticleXpathBySubtitle(String article_title) {
        return ARTICLE_BY_SUBTITLE_TPL.replace("{ARTICLE_SUBTITLE}", article_title);
    }

    private static String getSavedArticleXpathByStartText(String start_text) {
        return BEGIN_ARTICLE_TPL.replace("{BEGIN_TEXT}", start_text);
    }


    @Step("Open folder by name")
    public void openFolderByName(String name_of_folder) {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name",
                5
        );
    }
    @Step("Swipe article to delete")
    public void swipeByArticleToDelete(String article_title) throws InterruptedException {

        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByArticle(article_title);
        if(Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            this.swipeToLeftElement(
                    article_xpath,
                    "Saved article not found",
                    5
            );
        }
        else {
            String remove_locator = getRemoveButtonByType(article_title);
            Thread.sleep(1000);
            this.waitForElementAndClick(remove_locator, "Cannot click button to remove article from saved", 10);
        }

        if(Platform.getInstance().isIOS()){
            this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find saved article");
        }

        if(Platform.getInstance().isMw()){
            driver.navigate().refresh();
            driver.navigate().refresh();
        }

        waitForArticleToDisappearByTitle(article_title);
    }
    @Step("Wait for article to disappear by title")
    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByArticle(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present by title '" + article_title + "'",
                5
        );
    }
    @Step("Wait for article to disappear by subtitle")
    public void waitForArticleToDisappearBySubtitle(String article_subtitle) {
        String article_xpath = getSavedArticleXpathByArticle(article_subtitle);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present by title '" + article_subtitle + "'",
                5
        );
    }
    @Step("Wait for article to appear by title")
    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByArticle(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Saved article not present by title '" + article_title + "'"
        );
    }
    @Step("Wait for article to appear by subtitle")
    public void waitForArticleToAppearBySubtitle(String article_subtitle) {
        String article_bySubtitle_xpath = getSavedArticleXpathBySubtitle(article_subtitle);
        this.waitForElementPresent(
                article_bySubtitle_xpath,
                "Saved article not present by title '" + article_subtitle + "'"
        );
    }
    @Step("Check rest article to appear by start text in article page")
    public void checkRestArticleByStartText(String article_title, String start_text) {
        this.openArticleByTitle(article_title);
        String start_textXpath = getSavedArticleXpathByStartText(start_text);
        this.waitForElementPresent(start_textXpath, "Cannot find article start text");
    }
    @Step("Open article by title")
    public void openArticleByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByArticle(article_title);
        this.waitForElementAndClick(
                article_xpath,
                "Article with title:'" + article_title + "' not found", 10);
    }
    @Step("Close article PopUp for ios")
    public void closeSyncPopUp(){
        this.waitForElementPresent(CLOSE_SYNC_TEXT, "Text 'Sync your saved articles?' not found");
        this.waitForElementAndClick(CLOSE_SYNC_BUTTON, "Button 'Close sync pop-up' not found", 10);
    }
}
