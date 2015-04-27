package pages.superMarkets.disco;

import java.util.ArrayList;
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
	
//	private final List<Category> categoriesList = this.getCategoriesList();

	public DiscoProductsPage(WebDriver driver) {
		super(driver);
		this.waitUntilElementExistsInPage(By.className("tM"), TimeUnit.SECONDS.toMillis(10));
	}

	private Product setProduct(Long categoryId, WebElement productElement) {
		Product product = new Product();

		List<WebElement> tdList = null;
		String productName = productElement.findElement(By.className("link-lista2")).getText();
		product.setDescription(productName);
//		ProductRequest newProduct = this.getActualProduct(category, productName);
		product.setCategoryId(categoryId);
//		product.setProductId(newProduct.getId());
		
		
		tdList = productElement.findElements(By.tagName("td"));

		Double price = null;
		for (WebElement td : tdList) {
			if (td.getText().trim().matches("\\$\\d+(\\.\\d+)")) {
				price = Double.valueOf(td.getText().replace(",", ".").replace("$", "").trim());
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
	
	private List<Product> setAllProductsFromPage(Long categoryId) {
		List<Product> productList = new ArrayList<Product>();
		//		for (WebElement productElement : this.productElementList) {
		for (int i = 0; i < this.productElementList.size(); i++) {
			Product newProduct = null;
			while (newProduct == null) {
				try {
					newProduct = this.setProduct(categoryId, this.productElementList.get(i));
					productList.add(newProduct);
				} catch (StaleElementReferenceException e) {
					System.out.println("hola4");
				}
			}

		}

		//		}
		return productList;
	}

	private List<Product> setAllProductsFromSubCategory(Long categoryId) {
		List<Product> productList = new ArrayList<Product>();
		while(this.webElementIsDisplayedInPage(By.partialLinkText(">>"))) {
			productList.addAll(this.setAllProductsFromPage(categoryId));
			this.nextPageButton.click();
			boolean pageHasLoaded = this.waitUntilPageStopsLoading();
		}
		productList.addAll(this.setAllProductsFromPage(categoryId));
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
				System.out.println(pageHasLoaded);
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

//	private Integer getSubCategoriesQuantity(List<WebElement> subCategoriesElementList) {
//		Integer output = 0;
//		for (WebElement subCategoryElement : subCategoriesElementList) {
//			if (subCategoryElement.isDisplayed()) {
//				output++;
//			}
//		}
//		return output;
//	}

//	private Category assureCategoryExists(List<Category> categoryList, String categoryName) {
//		Category newCategory = new Category();
//		CategoryServices categoryServices = new CategoryServices();
////		List<Category> categoryList = categoryServices.getAllCategories();
//		newCategory.setDescription(categoryName);
//
//		for (Category category : categoryList) {
//			if (category.getDescription().equalsIgnoreCase(newCategory.getDescription())) {
//				return category;
//			}
//		}
//
//		return categoryServices.createCategory(newCategory);
//
//
//	}
//
//	public List<Product> setAllProducts(List<Category> categoryList) {
//		List<Product> productList = new ArrayList<Product>();
//		//		for (WebElement categoryElement : this.categoriesElementList) {
//		for (int i = 0; i < this.categoriesElementList.size(); i++) {
//			Boolean categoryLoaded = false;
//			while(!categoryLoaded) {
//				try {
//					WebElement categoryElement = this.categoriesElementList.get(i);
//					String categoryName = categoryElement.findElement(By.tagName("a")).getText();
//					
//					Category category = this.assureCategoryExists(categoryList, categoryName);
//
//					System.out.println(categoryName);
//					categoryElement.findElement(By.tagName("a")).click();
//					productList.addAll(this.setAllProductsFromCategory(category, categoryElement));
//					System.out.println(new Date());
//					System.out.println(productList.size());
//					
//					i = this.categoriesElementList.size();
//					
//				} catch (StaleElementReferenceException e) {
//					e.printStackTrace();
//					this.driver.navigate().refresh();
//					System.out.println("There was a problem with the category. Retrying");
//					this.waitUntilPageStopsLoading();
//				}
//			}
//
//		}
//		return productList;
//	}
	
//	private List<Category> getCategoriesList() {
//		CategoryServices cs = new CategoryServices();
//		return cs.getAllCategories();
//	}

}
