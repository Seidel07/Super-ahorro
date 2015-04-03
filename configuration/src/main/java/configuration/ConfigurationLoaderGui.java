package configuration;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
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
	
	@AfterMethod
	public void addScreenshot(ITestResult result) {
		if (!result.isSuccess()) {
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String path = "C:\\Users\\Irene\\Documents\\Dani\\Programas\\Workspace\\SuperAhorro\\GIT\\SuperAhorro\\services\\output";
			String imageExtension = ".png";
			String completePath = path+ result.getTestName() + DateTime.now().getMillis() + imageExtension;
			try {
				FileUtils.copyFile(scrFile, new File(completePath));
				System.out.println("Screenshot added: " + completePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@AfterTest(alwaysRun = true)
	public void closeDriver() {
		if (this.driver != null) {
			this.driver.quit();
		}
	}

	

}
