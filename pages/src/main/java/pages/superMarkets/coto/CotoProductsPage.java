package pages.superMarkets.coto;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pages.model.Categories;
import pages.model.Product;
import pages.transformer.TransformWebElement;

public class CotoProductsPage extends CotoGuestHomePage{

	@FindBy(className = "SecFListado")
	private List<WebElement> productElementList;
	@FindBy(partialLinkText = "adelante")
	private WebElement nextPageButton;

	public CotoProductsPage(WebDriver driver) {
		super(driver);
	}

	private Product setProduct(WebElement productElement) {
		Product product = new Product();
		product.setDescription(productElement.findElement(By.className("l1")).getText());
		Double price = Double.valueOf(productElement.findElement(By.className("txt11b")).getText().replace(",", ".").replace("$", "").trim());
		product.setPrice(price);
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

	public List<Product> setAllProductsFromSubCategory() {
		List<Product> productList = new ArrayList<Product>();
		while(this.webElementIsDisplayedInPage(By.partialLinkText("adelante"))) {
			productList.addAll(this.setAllProductsFromPage());
			this.nextPageButton.click();
		}
		productList.addAll(this.setAllProductsFromPage());
		return productList;
	}

	public List<Product> setAllProductsFromCategory(Categories category, boolean flag, List<String> subCategoryList) {
		if (flag) {
			this.openCategory(category);
		}
		List<Product> productList = new ArrayList<Product>();
		List<WebElement> categoryElementList = new ArrayList<WebElement>();
		List<String> categoryStringList = new ArrayList<String>();
		TransformWebElement transformWebElement = new TransformWebElement();
		
		WebElement categoryBlock = this.getSubCategoryBlockAvailable(this.subCategoriesBlock);
		
		
		categoryElementList = categoryBlock.findElements(By.className("l1"));
		categoryStringList = transformWebElement.toCategoryStringList(categoryElementList);
		
		for (String categoryString : categoryStringList) {
			if (!categoryString.equals("")) {
				System.out.println(categoryString);
				subCategoryList.add(categoryString);
				categoryBlock = this.getSubCategoryBlockAvailable(this.subCategoriesBlock);
				WebElement categoryElement = categoryBlock.findElement(By.linkText(categoryString));
				categoryElement.click();
				productList.addAll(this.setAllProductsFromCategory(category, false, subCategoryList));
				if (!subCategoryList.isEmpty()) {
					int lastIndexOfList = subCategoryList.size()-1;
					subCategoryList.remove(lastIndexOfList);
					this.goToGuestHomePage();
					this.openCategory(category);
					this.openSubCategories(subCategoryList);
				}
			}
		}
		
		if (categoryStringList.isEmpty()) {
			productList.addAll(this.setAllProductsFromSubCategory());
		}
		return productList;
	}

}
