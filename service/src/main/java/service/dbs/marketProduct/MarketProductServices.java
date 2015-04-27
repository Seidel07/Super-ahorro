package service.dbs.marketProduct;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import service.dbs.market.request.Market;
import service.dbs.marketProduct.request.MarketProduct;
import service.dbs.model.CRUD;
import service.dbs.model.Entity;
import service.dbs.product.request.ProductRequest;
import service.dbs.services.Dbs;

public class MarketProductServices extends Dbs{
	
	public MarketProduct createMarketProduct(MarketProduct marketProduct) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		ObjectMapper mapper = new ObjectMapper();
		
		String url = this.endpoint + this.PATH + Entity.MARKETPRODUCT.toString().toLowerCase() + "/" + CRUD.CREATE.getCode().toLowerCase();
	    HttpPost request = new HttpPost(url);
	    request.setHeader("Content-Type", "application/json");
	    StringEntity entity;
	    String dtoAsJson = "";
		try {
			dtoAsJson = mapper.writeValueAsString(marketProduct);
			entity = new StringEntity(dtoAsJson);
			request.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Url: " + url);
		System.out.println("Entity: " + dtoAsJson);
	    
	    
	    HttpResponse response;
	    String result = null;
	    int statusCode = -1;
	    MarketProduct actualObj = new MarketProduct();
	    try {
	        response = client.execute(request);  
	        statusCode = response.getStatusLine().getStatusCode();
	        System.out.println("Status code: " + statusCode);
	        HttpEntity entityResponse = response.getEntity();

	        if (entityResponse != null) {

	            InputStream instream = entityResponse.getContent();
	            result = convertStreamToString(instream);

	            System.out.println("RESPONSE: " + result);
		        instream.close();
	            

	        }
	        actualObj = mapper.readValue(result, MarketProduct.class);
	        
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    }
		return actualObj;
	}
	
	public List<MarketProduct> getAllMarketProducts() {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		String url = this.endpoint + this.PATH + Entity.MARKETPRODUCT.getCode().toLowerCase() + "/" + CRUD.RETRIEVE.getCode().toLowerCase(); 
	    HttpGet request = new HttpGet(url);
	    
	    System.out.println("Url: " + url);
	    
	    ObjectMapper mapper = new ObjectMapper();
	    HttpResponse response;
	    String result = null;
	    int statusCode = -1;
	    List<MarketProduct> actualObj = new ArrayList<MarketProduct>();
	    try {
	        response = client.execute(request);  
	        statusCode = response.getStatusLine().getStatusCode();
	        System.out.println("Status code: " + statusCode);
	        HttpEntity entityResponse = response.getEntity();

	        if (entityResponse != null) {

	            // A Simple JSON Response Read
	            InputStream instream = entityResponse.getContent();
	            result = convertStreamToString(instream);
	            // now you have the string representation of the HTML request
	            System.out.println("RESPONSE: " + result);
		        instream.close();
	            

	        }
	        actualObj = mapper.readValue(result, TypeFactory.defaultInstance().constructCollectionType(List.class, MarketProduct.class));
	        
	    } catch (IOException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    }
		return actualObj;
	}

}
