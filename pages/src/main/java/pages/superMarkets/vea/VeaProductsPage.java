package pages.superMarkets.vea;

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

public class VeaProductsPage extends VeaHomePage {

	@FindBy(className = "filaListaDetalle")
	private List<WebElement> productElementList;
	@FindBy(partialLinkText = ">>")
	private WebElement nextPageButton;

	public VeaProductsPage(WebDriver driver) {
		super(driver);
		this.waitUntilElementExistsInPage(By.className("tM"), TimeUnit.SECONDS.toMillis(10));
	}

	private Product setProduct(Long categoryId, WebElement productElement) {
		Product product = new Product();

		List<WebElement> tdList = null;
		product.setDescription(productElement.findElement(By.className("link-lista2")).getText());
		product.setCategoryId(categoryId);
		tdList = productElement.findElements(By.tagName("td"));
		Double price = null;
		for (WebElement td : tdList) {
			if (td.getText().trim().matches("\\$\\d+(\\.\\d+)")) {
				price = Double.valueOf(td.getText().replace(",", ".").replace("$", "").trim());
				break;
			}
		}
		product.setPrice(price);
		System.out.println(product.toString());
		return product;
	}

	private List<Product> setAllProductsFromPage(Long categoryId) {
		List<Product> productList = new ArrayList<Product>();
		// for (WebElement productElement : this.productElementList) {
		for (int i = 0; i < this.productElementList.size(); i++) {
			Product newProduct = null;
			while (newProduct == null) {
				try {
					WebElement productElement = this.productElementList.get(i);
					productList.add(this.setProduct(categoryId, productElement));
				} catch (StaleElementReferenceException e) {
					System.out.println("This might be an infinite loop");
				}
			}
		}
		return productList;
	}

	private List<Product> setAllProductsFromSubCategory(Long categoryId) {
		List<Product> productList = new ArrayList<Product>();
		productList.addAll(this.setAllProductsFromPage(categoryId));
		while (this.webElementIsDisplayedInPage(By.partialLinkText(">>"))) {
			productList.addAll(this.setAllProductsFromPage(categoryId));
			this.nextPageButton.click();
			boolean pageHasLoaded = this.waitUntilPageStopsLoading();
		}
		return productList;
	}

	public List<Product> setAllProductsFromCategory(Long categoryId, WebElement categoryElement) {
		List<Product> productList = new ArrayList<Product>();
		for (int i = 0; i < categoryElement.findElements(By.tagName("table")).size(); i++) {
			if (categoryElement.findElements(By.tagName("table")).get(i).isDisplayed()) {
				WebElement subCategoryElement = categoryElement.findElements(By.tagName("table")).get(i);
				System.out.println(subCategoryElement.findElement(By.tagName("a")).getText());
				subCategoryElement.findElement(By.tagName("a")).click();
				boolean pageHasLoaded = this.waitUntilPageStopsLoading();
				if (this.webElementIsDisplayedInElement(subCategoryElement, By.className("visible"))) {
					productList.addAll(this.setAllProductsFromCategory(categoryId, subCategoryElement));
				} else {
					productList.addAll(this.setAllProductsFromSubCategory(categoryId));
				}
				subCategoryElement.findElement(By.tagName("a")).click();
			}
		}
		return productList;
	}

	// private Integer getSubCategoriesQuantity(List<WebElement>
	// subCategoriesElementList) {
	// Integer output = 0;
	// for (WebElement subCategoryElement : subCategoriesElementList) {
	// if (subCategoryElement.isDisplayed()) {
	// output++;
	// }
	// }
	// return output;
	// }

	// public List<Product> setAllProducts() {
	// List<Product> productList = new ArrayList<Product>();
	// for (WebElement categoryElement : this.categoriesElementList) {
	// System.out.println(categoryElement.findElement(By.tagName("a")).getText());
	// categoryElement.findElement(By.tagName("a")).click();
	// productList.addAll(this.setAllProductsFromCategory(cateogryId,
	// categoryElement));
	// System.out.println(new Date());
	// System.out.println(productList.size());
	// }
	// return productList;
	// }

}
