package service.dbs.market.request;

import java.math.BigDecimal;

public class Market {
	
	private Long id;
	private BigDecimal version;
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getVersion() {
		return version;
	}
	public void setVersion(BigDecimal version) {
		this.version = version;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Market [id=" + id + ", version=" + version + ", description="
				+ description + "]";
	}
	
}
