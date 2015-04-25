package pages.superMarkets.jumbo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pages.model.Product;

public class JumboProductsPage extends JumboHomePage{

	@FindBy(className = "itemBody")
	private List<WebElement> productElementList;
	@FindBy(partialLinkText = "siguiente »")
	private WebElement nextPageButton;

	public JumboProductsPage(WebDriver driver) {
		super(driver);
		this.waitComponentsPage();
	}

	public void waitComponentsPage() {
		this.waitUntilElementIsDisplayedInPage(By.className("itemBody"), TimeUnit.SECONDS.toMillis(10));
	}

	private Product setProduct(WebElement productElement) {
		Product product = new Product();
		product.setDescription(productElement.findElement(By.className("data")).getText());
		WebElement priceElement = productElement.findElement(By.className("price")).findElement(By.tagName("b"));
		Double price = null;
		if (priceElement.getText().trim().matches("\\$\\d+(\\.\\d+)")) {
			price = Double.valueOf(priceElement.getText().replace(",", ".").replace("$", "").trim());
		}
		product.setPrice(price);
		System.out.println(product.toString());
		return product;
	}

	private List<Product> setAllProductsFromPage() {
		List<Product> productList = new ArrayList<Product>();
		for (WebElement productElement : this.productElementList) {
			if (productElement.isDisplayed()) {
				productList.add(this.setProduct(productElement));
			}
		}
		return productList;
	}

	private List<Product> setAllProductsFromSubCategory() {
		List<Product> productList = new ArrayList<Product>();
		while(this.webElementIsDisplayedInPage(By.partialLinkText("siguiente »"))) {
			productList.addAll(this.setAllProductsFromPage());
			this.nextPageButton.click();
		}
		productList.addAll(this.setAllProductsFromPage());
		return productList;
	}

	private List<Product> setAllProductsFromCategory(WebElement categoryElement) {
		List<Product> productList = new ArrayList<Product>();
		for (int i = 0; i < categoryElement.findElements(By.tagName("li")).size(); i++) {
			if (categoryElement.findElements(By.tagName("li")).get(i).isDisplayed()) {
				WebElement subCategoryElement = categoryElement.findElements(By.tagName("li")).get(i);
				System.out.println(subCategoryElement.findElement(By.tagName("a")).getText());
				try {
					subCategoryElement.findElement(By.tagName("a")).click();
				} catch (ElementNotVisibleException e) {
					System.out.println("Fucking ENVE");
				}
				
				this.waitUntilCategotyOpens(subCategoryElement);
				if (this.webElementIsDisplayedInElement(subCategoryElement, By.className("mainCat")) || this.webElementIsDisplayedInElement(subCategoryElement, By.className("lastCat"))) {
					productList.addAll(this.setAllProductsFromCategory(subCategoryElement));
				} else {
					productList.addAll(this.setAllProductsFromSubCategory());
				}
				try {
					subCategoryElement.findElement(By.tagName("a")).click();
				} catch (ElementNotVisibleException e) {
					System.out.println("2 Fucking ENVE");
				}
				
				this.waitUntilCategoryCloses(subCategoryElement);
			}
		}
		return productList;
	}

	private void waitUntilCategoryCloses(WebElement categoryElement) {
		if(!this.webElementAttributeContains(categoryElement, "class", "lastCat")) {
//			this.waitUntilElementContainsAttribute(categoryElement, "class", "closed", TimeUnit.SECONDS.toMillis(5));
			this.waitUntilElementDissapearsFromPage(categoryElement.findElement(By.tagName("li")), TimeUnit.SECONDS.toMillis(5));
		}
	}

	private void waitUntilCategotyOpens(WebElement categoryElement) {
		if(this.webElementAttributeContains(categoryElement, "class", "lastCat")) {
			this.waitUntilElementContainsAttribute(categoryElement, "class", "here", TimeUnit.SECONDS.toMillis(5));
		} else {
			this.waitUntilElementContainsAttribute(categoryElement, "class", "opened", TimeUnit.SECONDS.toMillis(5));
		}
	}

	public List<Product> setAllProducts() {
		List<Product> productList = new ArrayList<Product>();
		List<Integer> displayedLiId = new ArrayList<Integer>();
		Integer idAux = 0;
		for (WebElement categoryElement : this.categoriesElementList) {
			if(categoryElement.isDisplayed()) {
				displayedLiId.add(idAux);
			}
			idAux++;
		}
		for (Integer id : displayedLiId) {
			if (!this.categoriesElementList.get(id).findElement(By.tagName("a")).getText().equals("Ofertas")) {
				System.out.println(this.categoriesElementList.get(id).findElement(By.tagName("a")).getText());
				this.categoriesElementList.get(id).findElement(By.tagName("a")).click();
				productList.addAll(this.setAllProductsFromCategory(this.categoriesElementList.get(id)));
				System.out.println(new Date());
				System.out.println(productList.size());
			}
		}
		return productList;
	}

}
