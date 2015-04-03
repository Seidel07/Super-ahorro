package pages.superMarkets.jumbo;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import pages.selenium.SeleniumPage;

public class JumboHomePage extends SeleniumPage{
	
	@FindBys({ @FindBy(id = "menuHolder"), @FindBy(tagName = "li") })
	protected List<WebElement> categoriesElementList;
	
	String baseUrl = "https://www.jumboacasa.com.ar/Comprar/Home.aspx#";

	public JumboHomePage(WebDriver driver) {
		super(driver);
	}
	
	public JumboHomePage go() {
		this.driver.get(this.baseUrl);
		return new JumboHomePage(this.driver);
	}

}
