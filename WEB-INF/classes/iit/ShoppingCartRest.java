package iit;
import java.io.Serializable;


public class ShoppingCartRest implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	private Restaurant rest;
	private Integer quantity;
	private Integer userId;
	
	// getter and setter
	public Restaurant getRest() {
		return rest;
	}
	public void setRest(Restaurant rest) {
		this.rest = rest;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getRestId() {
		return rest.getRid();
	}
	
	public void increment() {
		quantity++;
	}
	
	public double getRestCost() {
		return quantity * (rest.getPrice());
	}
	
	public ShoppingCartRest(Restaurant rest) {
		super();
		this.rest = rest;
		this.quantity = 1;
	}
	
	public ShoppingCartRest(Restaurant rest, Integer quantity) {
		super();
		this.rest = rest;
		this.quantity = quantity;
	}
	
}
