package pages.superMarkets.disco;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pages.model.Product;

public class DiscoProductsPage extends DiscoHomePage{

	@FindBy(className = "filaListaDetalle")
	private List<WebElement> productElementList;
	@FindBy(partialLinkText = ">>")
	private WebElement nextPageButton;

	public DiscoProductsPage(WebDriver driver) {
		super(driver);
		this.waitUntilElementExistsInPage(By.className("tM"), TimeUnit.SECONDS.toMillis(10));
	}

	private Product setProduct(WebElement productElement) {
		Product product = new Product();
		
		List<WebElement> tdList = null;
		try {
			product.setDescription(productElement.findElement(By.className("link-lista2")).getText());
			tdList = productElement.findElements(By.tagName("td"));
		} catch (StaleElementReferenceException e) {
			System.out.println("This staleElementReferenceException is a piece of shit");
		}
		Double price = null;
		for (WebElement td : tdList) {
			try {
				if (td.getText().trim().matches("\\$\\d+(\\.\\d+)")) {
					price = Double.valueOf(td.getText().replace(",", ".").replace("$", "").trim());
					break;
				}
			} catch (Exception e) {
				System.out.println("2 This staleElementReferenceException is a piece of shit");
			}
			
		}
		try {
			product.setPrice(price);
		} catch (NullPointerException e) {
			System.out.println("The price for " + product.getDescription() + " is null.");
		}
		System.out.println(product.toString());
		return product;
	}

	private List<Product> setAllProductsFromPage() {
		List<Product> productList = new ArrayList<Product>();
		for (WebElement productElement : this.productElementList) {
			productList.add(this.setProduct(productElement));
		}
		return productList;
	}

	private List<Product> setAllProductsFromSubCategory() {
		List<Product> productList = new ArrayList<Product>();
		while(this.webElementIsDisplayedInPage(By.partialLinkText(">>"))) {
			productList.addAll(this.setAllProductsFromPage());
			this.nextPageButton.click();
			boolean pageHasLoaded = this.waitUntilPageStopsLoading();
		}
		productList.addAll(this.setAllProductsFromPage());
		return productList;
	}

	private List<Product> setAllProductsFromCategory(WebElement categoryElement) {
		List<Product> productList = new ArrayList<Product>();
		//		Integer subCategoriesQuantity = this.getSubCategoriesQuantity(categoryElement.findElements(By.tagName("table")));
		//		for (int i = 0; i < subCategoriesQuantity; i++) {
		for (int i = 0; i < categoryElement.findElements(By.tagName("table")).size(); i++) {
			if (categoryElement.findElements(By.tagName("table")).get(i).isDisplayed()) {
				WebElement subCategoryElement = categoryElement.findElements(By.tagName("table")).get(i);
				System.out.println(subCategoryElement.findElement(By.tagName("a")).getText());
				subCategoryElement.findElement(By.tagName("a")).click();
				boolean pageHasLoaded = this.waitUntilPageStopsLoading();
				if (this.webElementIsDisplayedInElement(subCategoryElement, By.className("visible"))) {
					productList.addAll(this.setAllProductsFromCategory(subCategoryElement));
				} else {
					productList.addAll(this.setAllProductsFromSubCategory());
				}
				//			subCategoryElement = categoryElement.findElements(By.tagName("table")).get(i);
				subCategoryElement.findElement(By.tagName("a")).click();
			}
		}
		return productList;
	}

	private Integer getSubCategoriesQuantity(List<WebElement> subCategoriesElementList) {
		Integer output = 0;
		for (WebElement subCategoryElement : subCategoriesElementList) {
			if (subCategoryElement.isDisplayed()) {
				output++;
			}
		}
		return output;
	}

	public List<Product> setAllProducts() {
		List<Product> productList = new ArrayList<Product>();
		for (WebElement categoryElement : this.categoriesElementList) {
			System.out.println(categoryElement.findElement(By.tagName("a")).getText());
			categoryElement.findElement(By.tagName("a")).click();
			productList.addAll(this.setAllProductsFromCategory(categoryElement));
			System.out.println(new Date());
			System.out.println(productList.size());
		}
		return productList;
	}

}
