package iit;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ShoppingCartRoom implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	private Room room;
	private Integer quantity;
	private Integer userId;
	private Date checkinDate;
	private Date checkoutDate;
	
	// getter and setter
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
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
	
	public String getRoomId() {
		return room.getRoomId();
	}
	public String getHid() {
		return room.getHid();
	}
	
	public double getRoomCost() {
		return quantity * (room.getPrice() * room.getDiscount());
	}
	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}
	public void setCheckinDateStr(String checkinDateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date checkin;
		try {
			checkin = sdf.parse(checkinDateStr);
			Date checkinDate = new Date(checkin.getTime());
			this.checkinDate = checkinDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public Date getCheckinDate() {
		return checkinDate;
	}
	public String getFormattedCheckinDate() {
		Calendar orderT = Calendar.getInstance();
		orderT.setTime(checkinDate);
		return (orderT.get(Calendar.MONTH) + 1) + "/" + (orderT.get(Calendar.DATE)) + "/" + (orderT.get(Calendar.YEAR));
	}
	
	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public void setCheckoutDateStr(String checkoutDateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date checkout;
		try {
			checkout = sdf.parse(checkoutDateStr);
			Date checkoutDate = new Date(checkout.getTime());
			this.checkoutDate = checkoutDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public Date getCheckoutDate() {
		return checkoutDate;
	}
	public String getFormattedCheckoutDate() {
		Calendar orderT = Calendar.getInstance();
		orderT.setTime(checkoutDate);
		return (orderT.get(Calendar.MONTH) + 1) + "/" + (orderT.get(Calendar.DATE)) + "/" + (orderT.get(Calendar.YEAR));
	}
	
	public void increment() {
		quantity++;
	}
	
	public ShoppingCartRoom(Room room) {
		super();
		this.room = room;
		this.quantity = 1;
	}
	
	public ShoppingCartRoom(Room room, Integer quantity) {
		super();
		this.room = room;
		this.quantity = quantity;
	}
	
}
