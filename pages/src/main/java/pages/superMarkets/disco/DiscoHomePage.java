package pages.superMarkets.disco;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import pages.selenium.SeleniumPage;

public class DiscoHomePage extends SeleniumPage  {
	
	String baseUrl = "https://www3.discovirtual.com.ar/Comprar/Home.aspx";

	@FindBys({ @FindBy(id = "divGrupo1"), @FindBy(className = "tM") })
	protected List<WebElement> categoriesElementList;
	@FindBy(id = "invitado")
	private WebElement guestButton;
	@FindBys({@FindBy(id = "ctl00_hlLogoDiscoVirtual"), @FindBy(tagName = "img")})
	private WebElement loadImage;
	
	String notLoadingImageName = "https://www3.discovirtual.com.ar/_Imgs/logo_home.gif";

	public DiscoHomePage(WebDriver driver) {
		super(driver);
	}
	
	public DiscoProductsPage goToGuestPage() {
		driver.get(this.baseUrl);
		this.guestButton.click();
		return new DiscoProductsPage(this.driver);
	}
	
	protected boolean waitUntilPageStopsLoading() {
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
