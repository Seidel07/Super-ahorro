package service.dbs.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import service.dbs.model.CRUD;
import service.dbs.model.Entity;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Dbs {
	
	protected final String endpoint = "http://scdbs-svinci.rhcloud.com";
	protected final String PATH = "/dbs/crud/";
	
//	public int createProduct(Object product) {
//		CloseableHttpClient client = HttpClientBuilder.create().build();
//		String url = this.endpoint + this.PATH + Entity.PRODUCT.toString() + CRUD.CREATE.getCode(); 
//	    HttpPost request = new HttpPost(url);
//	    request.setHeader("Content-Type", "application/json");
//	    StringEntity entity;
//		try {
//			entity = new StringEntity(product.toString());
//			request.setEntity(entity);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//	    
//	    ObjectMapper mapper = new ObjectMapper();
//	    HttpResponse response;
//	    String result = null;
//	    int statusCode = -1;
//	    try {
//	        response = client.execute(request);  
//	        statusCode= response.getStatusLine().getStatusCode();
//	        HttpEntity entityResponse = response.getEntity();
//
//	        if (entityResponse != null) {
//
//	            // A Simple JSON Response Read
//	            InputStream instream = entityResponse.getContent();
//	            result = convertStreamToString(instream);
//	            // now you have the string representation of the HTML request
//	            System.out.println("RESPONSE: " + result);
//		        instream.close();
//	            
//
//	        }
//	        Category actualObj = mapper.readValue(result, Category.class);
//	        
//	        // Headers
//	    } catch (IOException e1) {
//	        // TODO Auto-generated catch block
//	        e1.printStackTrace();
//	    }
//		return statusCode;
//	}
//	
	protected static String convertStreamToString(InputStream is) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	
}
