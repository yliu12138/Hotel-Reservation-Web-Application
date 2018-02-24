package iit;

import java.io.Serializable;
import java.lang.reflect.Method;

public class Room implements Serializable, Comparable<Room> {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String roomId;
	private String roomType;
	private String descp;
	private String hid;
	private Double price;
	private Double discount;
	private int numberOfRoomTypes;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getDescp() {
		return descp;
	}
	public void setDescp(String descp) {
		this.descp = descp;
	}
	public String getHid() {
		return hid;
	}
	public void setHid(String hid) {
		this.hid = hid;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getDiscountedPrice() {
		return this.price * this.getDiscount();
	}
	public void setNumberOfRoomType(int numberOfRoomTypes){
		this.numberOfRoomTypes = numberOfRoomTypes;
	}
	public int getNumberOfRoomType(){
		return numberOfRoomTypes;
	}
	
	public Room(Integer id, String roomId, String roomType, String descp,
			String hid, Double price, Double discount) {
		super();
		this.id = id;
		this.roomId = roomId;
		this.roomType = roomType;
		this.descp = descp;
		this.hid = hid;
		this.price = price;
		this.discount = discount;
	}

	public Room(int numberOfRoomTypes, String hid){
		this.hid = hid;
		setNumberOfRoomType(numberOfRoomTypes);
	}
	
	
	public String toString() {
		return "Room [id=" + id + ", roomId=" + roomId + ", roomType="
				+ roomType + ", hid=" + hid + "]";
	}
	
	public int compareTo(Room r) {
		return this.hid.compareTo(r.hid);
	}
	
	// update a value and call its setter by reflect
	public void updateRoom(String propertyName, Object value) {
		System.out.println("calling method: set" + propertyName);
		Method method = null;
		try {
//			if (propertyName.equals("RoomType")) {
//				setRoomType((String)value);
////				setRoomId(hid + "_" + (String)value);
//			} 
//			else if (propertyName.equals("Descp")) {
//				setDescp((String)value);
//			}
			
			if (propertyName.trim().equals("Descp") || propertyName.trim().equals("RoomType")) {
				method = getClass().getDeclaredMethod("set" + propertyName, String.class);
			}
			else {				
				method = getClass().getDeclaredMethod("set" + propertyName, Double.class);
			}
			method.invoke(this, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
