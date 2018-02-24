package iit;

import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String username;
	private String password;
	private Integer level;
	private ShoppingCart cart;
	private HashMap<Integer, Order> ordersMap = new HashMap<Integer, Order>();
	
	
	public void addToOrders(Order order) {
		ordersMap.put(order.getId(), order);
	}
	public boolean isOrderEmpty() {
		return ordersMap.isEmpty();
	}
	public Collection<Order> getOrders() {
		return ordersMap.values();
	}
	public void removeAnOrder(Integer orderId) {
		ordersMap.remove(orderId);
	}
	
	
	// getters and setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public ShoppingCart getCart() {
		return cart;
	}
	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}
	public HashMap<Integer, Order> getOrdersMap() {
		return ordersMap;
	}
	public void setOrdersMap(HashMap<Integer, Order> ordersMap) {
		this.ordersMap = ordersMap;
	}
	
	public User() {
		super();
	}
	public User(Integer id, String username, String password, Integer level,
			ShoppingCart cart, HashMap<Integer, Order> ordersMap) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.level = level;
		this.cart = cart;
		this.ordersMap = ordersMap;
	}
	
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", level=" + level + ", cart=" + cart
				+ ", ordersMap=" + ordersMap + "]";
	}
}

