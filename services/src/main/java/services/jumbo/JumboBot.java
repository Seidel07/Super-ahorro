package services.jumbo;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import configuration.ConfigurationLoaderGui;
import pages.model.Product;
import pages.superMarkets.jumbo.JumboPreHomePage;
import pages.superMarkets.jumbo.JumboProductsPage;

public class JumboBot extends ConfigurationLoaderGui{
	
	@Test
	public void jumboBot() {
		JumboPreHomePage jumboPreHomePage = new JumboPreHomePage(this.driver);
		JumboProductsPage jumboProductsPage = jumboPreHomePage.goToProductsPage();
		List<Product> productList = new ArrayList<Product>();
		productList.addAll(jumboProductsPage.setAllProducts());
	}

}
