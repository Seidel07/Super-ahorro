package service.dbs.model;

public enum CRUD {
	
	RETRIEVE("all"),
	CREATE("create");
	
	private String code;
	
	public String getCode() {
		return this.code;
	}
	
	CRUD(String code) {
		this.code = code;
	}

}
