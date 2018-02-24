package iit;
import java.io.Serializable;
import java.util.*;

public class ShoppingCart implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> cartMap = new LinkedHashMap<String, Object>();
	
	/**
	 * Add a room into cart
	 */
	public boolean addRoom(String roomId, String checkinDateStr, String checkoutDateStr) {
		Room room = RoomDAO.getRoomById(roomId);
		if (room == null) {
			return false;
		}
		ShoppingCartRoom scr = (ShoppingCartRoom) cartMap.get(roomId);
		if(scr == null){
			scr = new ShoppingCartRoom(room);
			scr.setCheckinDateStr(checkinDateStr);
			scr.setCheckoutDateStr(checkoutDateStr);
			cartMap.put(room.getRoomId(), scr);
		}else{
			scr.increment();
		}
		return true;
	}
	
	/**
	 * Get a restaurant into cart
	 */
	public boolean addRestaurant(String rid) {
		Restaurant rest = RestaurantDAO.getRestaurantById(rid);
		if (rest == null) {
			return false;
		}
		ShoppingCartRest scr = (ShoppingCartRest) cartMap.get(rid);
		if(scr == null){
			scr = new ShoppingCartRest(rest);
			cartMap.put(rest.getRid(), scr);
		}else{
			scr.increment();
		}
		return true;
	}
	
	
	public void setCartMap(Map<String, Object> cartMap) {
		this.cartMap = cartMap;
	}
	
	public Map<String, Object> getCartMap() {
		return cartMap;
	}
	
	/**
	 * @return the collection of Objects in cartroomsMapMap
	 */
	public Collection<Object> getCartObjects(){
		return cartMap.values();
	}
	
	/**
	 * Get all carted rooms
	 */
	public ArrayList<ShoppingCartRoom> getCartRooms() {
		ArrayList<ShoppingCartRoom> rooms = new ArrayList<ShoppingCartRoom>();
		for (Object o: cartMap.values()) {
			if (o instanceof ShoppingCartRoom) {
				rooms.add((ShoppingCartRoom)o);
			}
		}
		return rooms;
	}
	
	/**
	 * Get all carted restaurants
	 */
	public ArrayList<ShoppingCartRest> getCartRestaurants() {
		ArrayList<ShoppingCartRest> restaurants = new ArrayList<ShoppingCartRest>();
		for (Object o: cartMap.values()) {
			if (o instanceof ShoppingCartRest) {
				restaurants.add((ShoppingCartRest)o);
			}
		}
		return restaurants;
	}
	
	/**
	 * Checks if the cart has some room by roomId
	 */
	public boolean hasRoom(String roomId){
		return cartMap.containsKey(roomId);
	}
	
	/**
	 * Checks if the cart has some restaurant by rid
	 */
	public boolean hasRestaurant(String rid) {
		return cartMap.containsKey(rid);
	}
	
	/**
	 * Count rooms in the shopping cart
	 */
	public int getRoomNumber(){
		int total = 0;
		
		if (cartMap != null) {			
			for(Object o: cartMap.values()){
				if (o instanceof ShoppingCartRoom) {
					total += ((ShoppingCartRoom)o).getQuantity();
				}
			}
		}
		return total;
	}
	
	/**
	 * Count objects in the shopping cart
	 */
	public int getObjNumber(){
		int total = 0;
		if (cartMap != null) {			
			for(Object o: cartMap.values()){
				if (o instanceof ShoppingCartRoom) {
					total += ((ShoppingCartRoom)o).getQuantity();
				} else {
					total += ((ShoppingCartRest)o).getQuantity();
				}
			}
		}
		return total;
	}
	
	
	
	/**
	 * Calculate the total cost of carted rooms
	 */
	public float getTotalCost(){
		float total = 0;
		
		if (cartMap != null) {
			for(Object o: cartMap.values()){
				if (o instanceof ShoppingCartRoom) {
					total += ((ShoppingCartRoom)o).getRoomCost();
				} else {
					total += ((ShoppingCartRest)o).getRestCost();
				}
			}
		}
		return total;
	}
	
	/**
	 * Checks whether the shopping cart is empty
	 */
	public boolean isEmpty(){
		return cartMap.isEmpty();	
	}
	
	/**
	 * Clear all rooms in the shopping cart
	 */
	public void clear(){
		cartMap.clear();
		System.out.println(cartMap);
	}
	
	/**
	 * Remove an room from the cart with roomId
	 */
	public void removeRoom(String roomId){
		cartMap.remove(roomId);
	}
	
	/**
	 * Remove a restaurant from the cart with rid
	 */
	public void removeRest(String rid) {
		cartMap.remove(rid);
	}
	
	/**
	 * Update an room's quantity
	 */
//	public void updateRoomQuantity(String roomId, int quantity){
//		ShoppingCartRoom scr = cartMap.get(roomId);
//		if(scr != null){
//			scr.setQuantity(quantity);
//			System.out.println(scr.getRoomId() + "'s quantity set to " + quantity);
//		}
//	}
	
	/**
	 * Update a carted object's quantity
	 */
	public void updateObjectQuantity(String id, int quantity){
		Object o = cartMap.get(id);
		if(o != null){
			if (o instanceof ShoppingCartRoom) {
				((ShoppingCartRoom)o).setQuantity(quantity);
				System.out.println(((ShoppingCartRoom)o).getRoomId() + "'s quantity set to " + quantity);
			} else {
				((ShoppingCartRest)o).setQuantity(quantity);
				System.out.println(((ShoppingCartRest)o).getRestId() + "'s quantity set to " + quantity);
			}
		}
	}
}
