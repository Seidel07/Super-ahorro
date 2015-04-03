package pages.selenium;


import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SeleniumPage {
	protected WebDriver driver;

	protected static Logger LOGGER = LoggerFactory.getLogger(SeleniumPage.class.getName());
	private Long startTime;

	public SeleniumPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	public boolean webElementIsDisplayedInPage(By by) {
		try {
			return this.driver.findElement(by).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean webElementIsDisplayedInElement(WebElement webElement, By by) {
		try {
			return webElement.findElement(by).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean waitUntilElementIsDisplayedInPage(By by, long timeOutInMillis) {
		this.startTime = DateTime.now().getMillis();
		while (!this.webElementIsDisplayedInPage(by)) {
			if (DateTime.now().getMillis() - this.startTime > timeOutInMillis) {
				return false;
			}
		}
		return true;
	}

	public boolean waitUntilElementDissapearsFromPage(By by, long timeOutInMillis) {
		this.startTime = DateTime.now().getMillis();
		while (this.webElementIsDisplayedInPage(by)) {
			if (DateTime.now().getMillis() - this.startTime > timeOutInMillis) {
				return false;
			}
		}
		return true;
	}

	public boolean waitUntilElementExistsInElement(WebElement webElement, By by, long timeOutInMillis) {
		this.startTime = DateTime.now().getMillis();
		while (!this.webElementIsDisplayedInElement(webElement, by)) {
			if (DateTime.now().getMillis() - this.startTime > timeOutInMillis) {
				return false;
			}
		}
		return true;
	}
	
	public void sleep(Long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {

		}
	}
	
	public boolean webElementAttributeContains(WebElement webElement, String attribute, String value) {
		try {
			return webElement.getAttribute(attribute).contains(value);
		} catch (Exception e) {
			return false;
		}
		
	}
	
	public boolean waitUntilElementContainsAttribute(WebElement webElement, String attribute, String value, long timeOutInMillis) {
		this.startTime = DateTime.now().getMillis();
		while (!this.webElementAttributeContains(webElement, attribute, value)) {
			if (DateTime.now().getMillis() - this.startTime > timeOutInMillis) {
				return false;
			}
		}
		return true;
	}

}
