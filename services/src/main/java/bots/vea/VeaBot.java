package bots.vea;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.testng.annotations.Test;

import pages.model.Product;
import pages.superMarkets.disco.DiscoHomePage;
import pages.superMarkets.disco.DiscoProductsPage;
import pages.superMarkets.vea.VeaHomePage;
import pages.superMarkets.vea.VeaProductsPage;
import configuration.ConfigurationLoaderGui;

public class VeaBot extends ConfigurationLoaderGui{
	
	@Test
	public void run() {
		Long startTime = DateTime.now().getMillis();
		VeaHomePage veaHomePage = new VeaHomePage(this.driver);
		VeaProductsPage veaProductsPage = veaHomePage.goToGuestPage();
		List<Product> productList = new ArrayList<Product>();
		productList.addAll(veaProductsPage.setAllProducts());
		Long finishTime = DateTime.now().getMillis();
		System.out.println(TimeUnit.MILLISECONDS.toMinutes(finishTime - startTime));
	}

}
