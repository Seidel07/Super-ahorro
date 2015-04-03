package services.coto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.model.Categories;
import pages.model.Product;
import pages.superMarkets.coto.CotoHomePage;
import pages.superMarkets.coto.CotoProductsPage;
import configuration.ConfigurationLoaderGui;

public class CotoBot extends ConfigurationLoaderGui{
	
	@Test
	public void run() {
		CotoHomePage cotoHomePage = new CotoHomePage(this.driver);
		CotoProductsPage cotoProductsPage = new CotoProductsPage(this.driver);
		cotoHomePage.goToGuestPage();
		List<Product> productList = new ArrayList<Product>();
		for (Categories category : Categories.values()) {
			productList.addAll(cotoProductsPage.setAllProductsFromCategory(category, true, new ArrayList<String>()));
			System.out.println(new Date());
			System.out.println(productList.size());
		}
	}
}
