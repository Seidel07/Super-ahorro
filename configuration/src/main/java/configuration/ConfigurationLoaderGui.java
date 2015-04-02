package configuration;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class ConfigurationLoaderGui {
	
	protected WebDriver driver;
	
	@BeforeTest
	public void openDriver() {
		File file = new File("");
		String path = file.getAbsolutePath().replace("services", "pages\\src\\main\\java\\pages\\chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", path);
		this.driver = new ChromeDriver();
	}

	@AfterTest
	public void closeDriver() {
		if (this.driver != null) {
			this.driver.quit();
		}
	}
	
}
