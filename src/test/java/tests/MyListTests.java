package tests;

import lib.CoreTestCase;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.By;

import static junit.framework.TestCase.assertEquals;

public class MyListTests extends CoreTestCase {
    @Test
    public void testSaveArticle() throws InterruptedException {
        String name_of_folder = "test";
        String subtitle = "Object-oriented programming language";
        String article_title = "Java (programming language)";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(article_title);
        searchPageObject.clickByArticleWithSubstring(subtitle);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        if(Platform.getInstance().isAndroid()) {
            articlePageObject.createMyListAndAddArticleToMyList(name_of_folder);
        }
        else
            articlePageObject.addArticlesToSaved();
        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyList();

        MyListPageObject myListPageObject = MyListPageObjectFactory.get(driver);

        if(Platform.getInstance().isIOS()){
            myListPageObject.closeSyncPopUp();
            myListPageObject.isOpen();
        }
        if(Platform.getInstance().isAndroid()) {
            myListPageObject.openFolderByName(name_of_folder);
        }

        myListPageObject.swipeByArticleToDelete(article_title);
        myListPageObject.waitForArticleToDisappearByTitle(article_title);
    }

    @Test
    public void testSaveTwoArticles() {
        String name_of_folder = "test";

        String firstArticleTitle = "Java (programming language)";
        String secondArticleTitle = "C++";
        String firstArticleSubtitle = "Object-oriented programming language";
        String secondArticleSubtitle = "General-purpose programming language";
        if(Platform.getInstance().isAndroid()){
            secondArticleSubtitle = "General purpose high-level programming language";
        }

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(firstArticleTitle);
        searchPageObject.clickByArticleWithSubstring(firstArticleSubtitle);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        if(Platform.getInstance().isAndroid()) {
            articlePageObject.createMyListAndAddArticleToMyList(name_of_folder);
        }
        else
            articlePageObject.addArticlesToSaved();
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(secondArticleTitle);
        searchPageObject.clickByArticleWithSubstring(secondArticleSubtitle);

        if(Platform.getInstance().isIOS()){
            articlePageObject.addArticlesToSaved();
        }
        else {
            articlePageObject.addArticleToExistingMyList(name_of_folder);
        }
        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyList();

        MyListPageObject myListPageObject = MyListPageObjectFactory.get(driver);

        if(Platform.getInstance().isIOS()){
            myListPageObject.closeSyncPopUp();
            myListPageObject.isOpen();
        }
        if(Platform.getInstance().isAndroid()) {
            myListPageObject.openFolderByName(name_of_folder);
        }

        myListPageObject.swipeByArticleToDelete(firstArticleTitle);
        if(Platform.getInstance().isIOS())
            myListPageObject.waitForArticleToAppearBySubtitle(secondArticleSubtitle);
        else
        {
            myListPageObject.waitForArticleToDisappearByTitle(firstArticleTitle);
            myListPageObject.waitForArticleToAppearByTitle(secondArticleTitle);
        }
    }


}
