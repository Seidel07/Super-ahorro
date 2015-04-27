package pages.model;

public class Product {
	
	private String description;
	private Double price;
	private Long categoryId;
	private Long productId;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@Override
	public String toString() {
		return "Product [description=" + description + ", price=" + price
				+ ", categoryId=" + categoryId + ", productId=" + productId
				+ "]";
	}
	
	

}
