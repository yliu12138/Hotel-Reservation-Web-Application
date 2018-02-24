package iit;
import java.io.Serializable;
import java.util.*;

public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Map<String, ShoppingCartRoom> cartRoomsMap = new LinkedHashMap<String, ShoppingCartRoom>();
	
//	public void addRoom(Room room){
//		ShoppingCartRoom scr = cartRoomsMap.get(room.getRoomId());
//		if(scr == null){
//			scr = new ShoppingCartRoom(room);
//			cartRoomsMap.put(room.getRoomId(), scr);
//		}else{
//			scr.increment();
//		}
//	}
	
	public boolean addRoom(String roomId, String checkinDateStr, String checkoutDateStr) {
		Room room = RoomDAO.getRoomById(roomId);
		if (room == null) {
			return false;
		}
		ShoppingCartRoom sci = cartRoomsMap.get(roomId);
		if(sci == null){
			sci = new ShoppingCartRoom(room);
			sci.setCheckinDateStr(checkinDateStr);
			sci.setCheckoutDateStr(checkoutDateStr);
			cartRoomsMap.put(room.getRoomId(), sci);
		}else{
			sci.increment();
		}
		return true;
	}
	
	public void setCartroomsMap(Map<String, ShoppingCartRoom> cartRoomsMap) {
		this.cartRoomsMap = cartRoomsMap;
	}
	
	/**
	 * @return the Map (id, ShoppingCartroom) representation of cartroomsMap
	 */
	public Map<String, ShoppingCartRoom> getCartRoomsMap() {
		return cartRoomsMap;
	}
	
	/**
	 * @return the collection of ShoppingCartroom in cartroomsMapMap
	 */
	public Collection<ShoppingCartRoom> getCartRooms(){
		return cartRoomsMap.values();
	}
	
	/**
	 * Checks if the cart has some room by id
	 */
	public boolean hasRoom(String roomId){
		return cartRoomsMap.containsKey(roomId);
	}
	
	/**
	 * Count rooms in the shopping cart
	 */
	public int getRoomNumber(){
		int total = 0;
		
		if (cartRoomsMap != null) {			
			for(ShoppingCartRoom scr: cartRoomsMap.values()){
				total += scr.getQuantity();
			}
		}
		return total;
	}
	
	
	
	/**
	 * Calculate the total cost of carted rooms
	 */
	public float getTotalCost(){
		float total = 0;
		
		if (cartRoomsMap != null) {
			for(ShoppingCartRoom scr: cartRoomsMap.values()){
				total += scr.getRoomCost();
			}
		}
		return total;
	}
	
	/**
	 * Checks whether the shopping cart is empty
	 */
	public boolean isEmpty(){
		return cartRoomsMap.isEmpty();	
	}
	
	/**
	 * Clear all rooms in the shopping cart
	 */
	public void clear(){
		cartRoomsMap.clear();
		System.out.println(cartRoomsMap);
	}
	
	/**
	 * Remove an room from the cart with roomId
	 */
	public void removeRoom(String roomId){
		cartRoomsMap.remove(roomId);
	}
	
	/**
	 * Update an room's quantity
	 */
	public void updateRoomQuantity(String roomId, int quantity){
		ShoppingCartRoom scr = cartRoomsMap.get(roomId);
		if(scr != null){
			scr.setQuantity(quantity);
			System.out.println(scr.getRoomId() + "'s quantity set to " + quantity);
		}
	}
}
