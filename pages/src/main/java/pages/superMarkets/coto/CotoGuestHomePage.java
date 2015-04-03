package pages.superMarkets.coto;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import pages.model.Categories;
import pages.selenium.SeleniumPage;

public class CotoGuestHomePage extends SeleniumPage {

	String baseUrl = "http://www.cotodigital.com.ar/novedades.asp";

	@FindBy(id = "arbolrubros")
	private WebElement categoryBlockElement;
	@FindBys({ @FindBy(id = "arbolrubros"), @FindBy(tagName = "a") })
	private List<WebElement> categoriesList;
	@FindBy(id = "arbolrubros")
	protected WebElement subCategoriesBlock;
	@FindBy(tagName = "a")
	private List<WebElement> linkElementList;

	public CotoGuestHomePage(WebDriver driver) {
		super(driver);
	}

	public CotoGuestHomePage go() {
		this.driver.get(this.baseUrl);
		return new CotoGuestHomePage(driver);
	}

	public void openCategory(Categories selectedCategory) {
		String selectedCategoryString = selectedCategory.toString().replace("_", "").toLowerCase();
		for (WebElement category : this.categoriesList) {
			if (this.webElementIsDisplayedInElement(category, By.tagName("img"))) {
				if (category.findElement(By.tagName("img")).getAttribute("src").contains(selectedCategoryString)) {
					category.click();
					break;
				}
			}
		}
	}
	
	public void openSubCategories(List<String> subCategoryList) {
		for (String subCategory : subCategoryList) {
			WebElement categoryBlock = this.getSubCategoryBlockAvailable(this.subCategoriesBlock);
			categoryBlock.findElement(By.linkText(subCategory)).click();
		}
	}

	public WebElement getSubCategoryBlockAvailable() {
		List<WebElement> subCategoriesList = this.subCategoriesBlock.findElements(By.tagName("div"));
		for (WebElement subCategoriesElement : subCategoriesList) {
			if (subCategoriesElement.getAttribute("style").equals("")) {
				return subCategoriesElement;
			}
		}
		return null;
	}
	public WebElement getSubCategoryBlockAvailable(WebElement subCategoryBlock) {
		List<WebElement> subCategoryList = subCategoryBlock.findElements(By.tagName("div"));
		for (WebElement subCategoriesElement : subCategoryList) {
			if (subCategoriesElement.getAttribute("style").equals("")) {
				return this.getSubCategoryBlockAvailable(subCategoriesElement);
			} 
		}
		return subCategoryBlock;
	}

	public void goToGuestHomePage() {
		for (WebElement linkElement : this.linkElementList) {
			try {
				if(linkElement.getAttribute("href").contains("novedades.asp")) {
					linkElement.click();
					break;
				}
			} catch (Exception e) {

			}
		}
	}

}
