package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ChangeAppConditionTests extends CoreTestCase {
    @Test
    public void testChangeScreenOrientationOnSearchResults(){
        if(Platform.getInstance().isMw())
            return;
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String title_before_rotation = articlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String title_after_rotation = articlePageObject.getArticleTitle();

        assertEquals("Article have been changed after rotation", title_before_rotation, title_after_rotation);

        rotateScreenPortrait();
        String title_after_second_rotate = articlePageObject.getArticleTitle();

        assertEquals("Article have been changed after rotation", title_before_rotation, title_after_second_rotate);
    }

    @Test
    public void testCheckSearchInBackground() {
        if(Platform.getInstance().isMw())
            return;
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");

        runAppInBackground(5);

        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
