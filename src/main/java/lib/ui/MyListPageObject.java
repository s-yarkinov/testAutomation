package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListPageObject extends MainPageObject{

    private static final String
        FOLDER_NAME_TPL = "//*[@resource-id = 'org.wikipedia:id/item_title'][@text = '{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TPL = "//*[@text = '{ARTICLE_TITLE}']";

    private static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByArticle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{ARTICLE_TITLE}", article_title);
    }

    public MyListPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName(String name_of_folder) {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waiteForElementAndClick(
                By.xpath(folder_name_xpath),
                "Cannot find folder by name",
                5
        );
    }

    public void swipeByArticleToDelete(String article_title) {

        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByArticle(article_title);
        this.swipeToLeftElement(
                By.xpath(article_xpath),
                "Saved article not found",
                5
        );

        waitForArticleToDisappearByTitle(article_title);
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByArticle(article_title);
        this.waiteForElementNotPresent(
                By.xpath(article_xpath),
                "Saved article still present by title '" + article_title + "'",
                5
        );
    }

    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByArticle(article_title);
        this.waitForElementPresent(
                By.xpath(article_xpath),
                "Saved article not present by title '" + article_title + "'"
        );
    }

}
