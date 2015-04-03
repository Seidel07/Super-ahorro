package pages.superMarkets.jumbo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import pages.selenium.SeleniumPage;

public class JumboPreHomePage extends SeleniumPage{

	@FindBy(className = "icono-categorias-electro")
	private WebElement electroCategoryElement;
	@FindBys({@FindBy(className = "sub-categorias-menu"),@FindBy(tagName = "a")})
	private List<WebElement> electroLinkElementList;

	String baseUrl = "https://www.jumboacasa.com.ar/Login/PreHome.aspx";

	public JumboPreHomePage(WebDriver driver) {
		super(driver);
	}

	public JumboProductsPage goToProductsPage() {
		this.driver.get(this.baseUrl);
		this.electroCategoryElement.click();
		this.waitUntilElementIsDisplayedInPage(By.className("sub-categorias-menu"), TimeUnit.SECONDS.toMillis(2));
		for (WebElement electroLinkElement : this.electroLinkElementList) {
			if(electroLinkElement.isDisplayed()) {
				electroLinkElement.click();
				break;
			}
		}
		new JumboProductsPage(this.driver);
		this.driver.navigate().refresh();
		return new JumboProductsPage(this.driver);
	}

}
