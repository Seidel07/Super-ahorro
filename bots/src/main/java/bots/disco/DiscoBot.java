package bots.disco;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import pages.model.Product;
import pages.superMarkets.disco.DiscoHomePage;
import pages.superMarkets.disco.DiscoProductsPage;
import service.dbs.category.CategoryServices;
import service.dbs.category.request.Category;
import service.dbs.market.MarketServices;
import service.dbs.market.request.Market;
import service.dbs.marketProduct.MarketProductServices;
import service.dbs.marketProduct.request.MarketProduct;
import service.dbs.price.PriceServices;
import service.dbs.price.request.Price;
import service.dbs.product.ProductServices;
import service.dbs.product.request.ProductRequest;
import service.dbs.product.transformer.ProductTransformer;
import configuration.ConfigurationLoaderGui;

public class DiscoBot extends ConfigurationLoaderGui{
	
	private final Market actualMarket = this.getActualMarket();
	private List<Category> categoriesList = this.getCategoryList();
	private final List<ProductRequest> ourProductList = this.getProductList();
	private final List<MarketProduct> marketProductList = this.getMarketProductList();
//	private final List<Price> pricesList = this.getPricesList();

	@Test
	public void run() {
		DiscoHomePage discoHomePage = new DiscoHomePage(this.driver);
		DiscoProductsPage discoProductsPage = discoHomePage.goToGuestPage();
		List<Product> productList = new ArrayList<Product>();
		productList.addAll(this.setAllProducts(this.categoriesList));
		
		for (Product product : productList) {
			boolean productLoaded= false;
			boolean marketProductLoaded = false;
			ProductRequest newProduct = ProductTransformer.toProductRequest(product);
			MarketProduct newMarketProduct = ProductTransformer.toMarketProduct(product);
			Price newPrice = ProductTransformer.toPrice(product);
			for (ProductRequest ourProduct : this.ourProductList) {
				if (product.getDescription().equalsIgnoreCase(ourProduct.getDescription())) {
					productLoaded = true;
					newProduct = ourProduct;
					break;
				}
			}
			for (MarketProduct marketProduct : this.marketProductList) {
				if (product.getDescription().equalsIgnoreCase(marketProduct.getDescription())) {
					marketProductLoaded = true;
					newMarketProduct= marketProduct;
					break;
				}
			}
			if (!productLoaded) {
				newProduct = this.createProduct(newProduct);
			}
			
			if (!marketProductLoaded) {
				newMarketProduct.setProductId(newProduct.getId());
				newMarketProduct.setMarketId(this.actualMarket.getId());
				newMarketProduct = this.createMarketProduct(newMarketProduct);
			}
			
			newPrice.setProductId(newMarketProduct.getId());
			newPrice.setDate(BigDecimal.valueOf(DateTime.now().getMillis()));
			this.createPrice(newPrice);
			
		}
	}

	private Market getActualMarket() {
		MarketServices ms = new MarketServices();
		List<Market> ml = ms.getAllMarkets();

		Market actualMarket = new Market();
		actualMarket.setDescription("Disco virtual");

		for (Market market : ml) {
			if (actualMarket.getDescription().equalsIgnoreCase(market.getDescription())) {
				return market;
			}
		}

		return ms.createMarket(actualMarket);
	}
	
	private ProductRequest createProduct(ProductRequest product) {
		ProductServices productServices = new ProductServices();
		return productServices.createProduct(product);
	}
	
	private MarketProduct createMarketProduct(MarketProduct marketProduct) {
		MarketProductServices marketProductServices = new MarketProductServices();
		return marketProductServices.createMarketProduct(marketProduct);
	}
	
	private Price createPrice(Price price) {
		PriceServices priceServices = new PriceServices();
		return priceServices.createPrice(price);
	}
	
	private List<MarketProduct> getMarketProductList() {
		MarketProductServices mps = new MarketProductServices();
		return mps.getAllMarketProducts();
	}
	
	private List<ProductRequest> getProductList() {
		ProductServices ps = new ProductServices();
		return ps.getAllProducts();
	}
	
	private List<Category> getCategoryList() {
		CategoryServices cs = new CategoryServices();
		return cs.getAllCategories();
	}
	
	private Category assureCategoryExists(List<Category> categoryList, String categoryName) {
		Category newCategory = new Category();
		CategoryServices categoryServices = new CategoryServices();
		newCategory.setDescription(categoryName);

		for (Category category : categoryList) {
			if (category.getDescription().equalsIgnoreCase(newCategory.getDescription())) {
				return category;
			}
		}

		return categoryServices.createCategory(newCategory);


	}

	public List<Product> setAllProducts(List<Category> categoryList) {
		List<Product> productList = new ArrayList<Product>();
		DiscoProductsPage dhp = new DiscoProductsPage(driver);
		
		
		//		for (WebElement categoryElement : this.categoriesElementList) {
		for (int i = 0; i < dhp.getCategoryElementList().size(); i++) {
			Boolean categoryLoaded = false;
			while(!categoryLoaded) {
				try {
					WebElement categoryElement = dhp.getCategoryElementList().get(i);
					String categoryName = categoryElement.findElement(By.tagName("a")).getText();
					
					Category category = this.assureCategoryExists(categoryList, categoryName);

					System.out.println(categoryName);
					categoryElement.findElement(By.tagName("a")).click();
					productList.addAll(dhp.setAllProductsFromCategory(category.getId(), categoryElement));
					System.out.println(new Date());
					System.out.println(productList.size());
					
					categoryLoaded = true;
					
				} catch (StaleElementReferenceException e) {
					e.printStackTrace();
					this.driver.navigate().refresh();
					System.out.println("There was a problem with the category. Retrying");
					dhp.waitUntilPageStopsLoading();
				}
			}
			i = dhp.getCategoryElementList().size();

		}
		return productList;
	}

}
