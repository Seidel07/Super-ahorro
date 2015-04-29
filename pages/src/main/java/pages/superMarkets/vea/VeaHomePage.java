package pages.superMarkets.vea;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import pages.selenium.SeleniumPage;

public class VeaHomePage extends SeleniumPage  {
	
	String baseUrl = "https://www.veadigital.com.ar/Login/PreHome.aspx";

	@FindBys({ @FindBy(id = "divGrupo199"), @FindBy(className = "tM") })
	protected List<WebElement> categoriesElementList;
	@FindBy(id = "invitado")
	private WebElement guestButton;
	@FindBys({@FindBy(id = "ctl00_hlLogoDiscoVirtual"), @FindBy(tagName = "img")})
	private WebElement loadImage;
	
	public List<WebElement> getCategoryElementList() {
		return this.categoriesElementList;
	}
	
	String notLoadingImageName = "https://www.veadigital.com.ar/_Imgs/logo_home.gif";

	public VeaHomePage(WebDriver driver) {
		super(driver);
	}
	
	public VeaProductsPage goToGuestPage() {
		driver.get(this.baseUrl);
		this.guestButton.click();
		return new VeaProductsPage(this.driver);
	}
	
	public boolean waitUntilPageStopsLoading() {
		Long startTime = DateTime.now().getMillis();
		while(!this.loadImage.getAttribute("src").equals(this.notLoadingImageName)) {
			this.sleep(500L);
			if (DateTime.now().getMillis() - startTime > TimeUnit.SECONDS.toMillis(20)) {
				return false;
			}
		}
		return true;
	}
	
	

}
