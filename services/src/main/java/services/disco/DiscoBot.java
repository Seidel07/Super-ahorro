package services.disco;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import pages.model.Product;
import pages.superMarkets.disco.DiscoHomePage;
import pages.superMarkets.disco.DiscoProductsPage;
import configuration.ConfigurationLoaderGui;

public class DiscoBot extends ConfigurationLoaderGui{
	
	@Test
	public void run() {
		DiscoHomePage discoHomePage = new DiscoHomePage(this.driver);
		DiscoProductsPage discoProductsPage = discoHomePage.goToGuestPage();
		List<Product> productList = new ArrayList<Product>();
		productList.addAll(discoProductsPage.setAllProducts());
	}

}
