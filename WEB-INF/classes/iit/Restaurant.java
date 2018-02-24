package iit;

import java.io.Serializable;

public class Restaurant implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String rid;
	private String name;
	private Float price;
	private String city;
	private String cid;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	public Restaurant(Integer id, String rid, String name, Float price,
			String city, String cid) {
		super();
		this.id = id;
		this.rid = rid;
		this.name = name;
		this.price = price;
		this.city = city;
		this.cid = cid;
	}
	
	
	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", rid=" + rid + ", name=" + name
				+ ", price=" + price + ", cid=" + cid + "]";
	}
}
