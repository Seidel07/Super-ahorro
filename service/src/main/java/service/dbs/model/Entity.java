package service.dbs.model;

public enum Entity {
	
	CATEGORY(""),
	MARKETPRODUCT("market-product"),
	PRODUCT(""),
	MARKET(""),
	PRICE("");
	
	private String code;
	
	public String getCode() {
		return this.code;
	}
	
	Entity(String code) {
		this.code = code;
	}
	
	
}
