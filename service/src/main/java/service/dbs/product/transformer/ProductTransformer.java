package service.dbs.product.transformer;

import java.math.BigDecimal;

import pages.model.Product;
import service.dbs.marketProduct.request.MarketProduct;
import service.dbs.price.request.Price;
import service.dbs.product.request.ProductRequest;

public class ProductTransformer {
	
	public static ProductRequest toProductRequest(Product input) {
		ProductRequest output = new ProductRequest();
		output.setCategoryId(input.getCategoryId());
		output.setDescription(input.getDescription());
		return output;
	}
	
	public static MarketProduct toMarketProduct(Product input) {
		MarketProduct output = new MarketProduct();
		output.setDescription(input.getDescription());
		return output;
	}
	
	public static Price toPrice(Product input) {
		Price output = new Price();
		output.setAmount(BigDecimal.valueOf(input.getPrice()));
		return output;
	}
	

}
