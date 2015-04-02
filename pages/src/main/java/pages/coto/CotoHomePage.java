package pages.coto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pages.selenium.SeleniumPage;

public class CotoHomePage extends SeleniumPage{
	
	public String baseUrl = "http://www.cotodigital.com.ar/Home.asp";
//	private static Logger LOGGER = LoggerFactory.getLogger(CotoHomePage.class.getName());
	
	@FindBy(id = "IMG1")
	private WebElement getAsGuest;

	public CotoHomePage(WebDriver driver) {
		super(driver);
	}
	
	public CotoGuestHomePage goToGuestPage() {
		this.driver.get(this.baseUrl);
		this.getAsGuest.click();
		return new CotoGuestHomePage(this.driver);
		
	}

}
