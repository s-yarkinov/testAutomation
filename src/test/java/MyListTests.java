import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;

public class MyListTests extends CoreTestCase {
    @Test
    public void testSaveArticle() {
        String name_of_folder = "test";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        articlePageObject.createMyListAndAddArticleToMyList(name_of_folder);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyList();

        MyListPageObject myListPageObject = new MyListPageObject(driver);
        myListPageObject.openFolderByName(name_of_folder);
        myListPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticles(){
        String name_of_folder = "test";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String first_article_title = articlePageObject.getArticleTitle();
        articlePageObject.createMyListAndAddArticleToMyList(name_of_folder);
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Programming language");
        articlePageObject.waitForTitleElement();
        String second_article_title = articlePageObject.getArticleTitle();
        articlePageObject.addArticleToExistingMyList(name_of_folder);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyList();

        MyListPageObject myListPageObject = new MyListPageObject(driver);
        myListPageObject.openFolderByName(name_of_folder);
        myListPageObject.swipeByArticleToDelete(first_article_title);
        myListPageObject.waitForArticleToDisappearByTitle(first_article_title);
        myListPageObject.waitForArticleToAppearByTitle(second_article_title);
        myListPageObject.openArticleByTitle(second_article_title);
        assertEquals("The title of the article does not match", articlePageObject.getArticleTitle(), second_article_title);
    }
}
