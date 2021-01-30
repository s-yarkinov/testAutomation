package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

@Epic("Test for articles")
public class ArticleTests extends CoreTestCase {


//    @BeforeClass
//    public void logInApp(){
//        System.out.println("test1");
//    }

//    @Override
//    public void setUp(){
//        System.out.println("From test: ArticleTests");
//    }

    @Test
    @Severity(value = SeverityLevel.BLOCKER)
    @Features(value = {@Feature(value = "search"), @Feature(value = "Article")})
    @DisplayName("Compare article title with expected one")
    @Description("We look for an article with the title \"Java\", and then make sure that it appears in the search")
    @Step("Starting testCompareArticleTitle")
    public void testCompareArticleTitle() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

//        articlePageObject.takeScreenshot("article page");

        assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                articleTitle
        );
    }

    @Test
    @Severity(value = SeverityLevel.MINOR)
    @Features(value = {@Feature(value = "search"), @Feature(value = "Article")})
    @DisplayName("Swipe article to the the footer")
    @Description("Check if there is an element in the footer with a link to the site")
    @Step("Starting testSwipeArticle")
    public void testSwipeArticle(){
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();
    }

//    @Test
//    public void testHasTextInSearchField() {
//        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
//        searchPageObject.initSearchInput();
//        searchPageObject.typeSearchLine("Appium");
//        searchPageObject.clickByArticleWithSubstring("Appium");
//
//        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
//        articlePageObject.assertTitleIsPresent();
//    }
}
