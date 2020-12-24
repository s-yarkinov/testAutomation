package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{
    private static final String
        ARTICLE_TITLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "org.wikipedia:id/page_external_link";


    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waiteForTitleElement() {
        return this.waitForElementPresent(By.id(ARTICLE_TITLE),"Cannot find article title");
    }

    public String getArticleTitle() {
        WebElement title_element = waiteForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                By.id(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20
        );
    }
}
