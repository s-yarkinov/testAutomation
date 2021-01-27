package lib.ui;

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
        REMOVE_FROM_SAVE_BUTTON;

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



    public void openFolderByName(String name_of_folder) {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name",
                5
        );
    }

    public void swipeByArticleToDelete(String article_title) {

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
            this.waitForElementAndClick(remove_locator, "Cannot click button to remove article from saved", 10);
        }

        if(Platform.getInstance().isIOS()){
            this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find saved article");
        }

        if(Platform.getInstance().isMw()){
            driver.navigate().refresh();
        }

        waitForArticleToDisappearByTitle(article_title);
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByArticle(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present by title '" + article_title + "'",
                5
        );
    }

    public void waitForArticleToDisappearBySubtitle(String article_subtitle) {
        String article_xpath = getSavedArticleXpathByArticle(article_subtitle);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present by title '" + article_subtitle + "'",
                5
        );
    }

    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByArticle(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Saved article not present by title '" + article_title + "'"
        );
    }

    public void waitForArticleToAppearBySubtitle(String article_subtitle) {
        String article_bySubtitle_xpath = getSavedArticleXpathBySubtitle(article_subtitle);
        this.waitForElementPresent(
                article_bySubtitle_xpath,
                "Saved article not present by title '" + article_subtitle + "'"
        );
    }

    public void openArticleByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByArticle(article_title);
        this.waitForElementAndClick(
                article_xpath,
                "Article with title:'" + article_title + "' not found", 10);
    }

    public void closeSyncPopUp(){
        this.waitForElementPresent(CLOSE_SYNC_TEXT, "Text 'Sync your saved articles?' not found");
        this.waitForElementAndClick(CLOSE_SYNC_BUTTON, "Button 'Close sync pop-up' not found", 10);
    }
}
