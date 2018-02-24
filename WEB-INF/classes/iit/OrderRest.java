package iit;
import java.io.Serializable;

public class OrderRest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String restId;
	private Integer oid;
	private Integer quantity;
	private String zipCode;
	private String state;
	private String city;
	private Double price;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRestId() {
		return restId;
	}
	public void setRestId(String restId) {
		this.restId = restId;
	}
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public OrderRest(Integer id, String restId, Integer oid, Integer quantity,
			String zipCode, String state, String city, Double price) {
		super();
		this.id = id;
		this.restId = restId;
		this.oid = oid;
		this.quantity = quantity;
		this.zipCode = zipCode;
		this.state = state;
		this.city = city;
		this.price = price;
	}
	public OrderRest() {
		super();
	}
	public String toString() {
		return "OrderRest [id=" + id + ", restId=" + restId + ", oid=" + oid
				+ ", quantity=" + quantity + ", zipCode=" + zipCode + ", city="
				+ city + ", price=" + price + "]";
	}
}
