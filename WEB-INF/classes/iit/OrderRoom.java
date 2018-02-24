package iit;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderRoom implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String roomId;
	private String hid;
	private Integer oid;
	private Date checkinDate;
	private Date checkoutDate;
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
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getHid() {
		return hid;
	}
	public void setHid(String hid) {
		this.hid = hid;
	}
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public Date getCheckinDate() {
		return checkinDate;
	}
	public String getFormattedCheckinDate() {
		Calendar orderT = Calendar.getInstance();
		orderT.setTime(checkinDate);
		return (orderT.get(Calendar.MONTH) + 1) + "/" + (orderT.get(Calendar.DATE)) + "/" + (orderT.get(Calendar.YEAR));
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
	public Date getCheckoutDate() {
		return checkoutDate;
	}
	public String getFormattedCheckoutDate() {
		Calendar orderT = Calendar.getInstance();
		orderT.setTime(checkoutDate);
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
	public OrderRoom(Integer id, String roomId, String hid, Integer oid,
			Date checkinDate, Date checkoutDate, Integer quantity,
			String zipCode, String state, String city, Double price) {
		super();
		this.id = id;
		this.roomId = roomId;
		this.hid = hid;
		this.oid = oid;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.quantity = quantity;
		this.zipCode = zipCode;
		this.state = state;
		this.city = city;
		this.price = price;
	}
	public OrderRoom(Integer quantity, String hid){
		this.hid = hid;
		this.quantity = quantity;
	}
	
	
	public OrderRoom() {
		super();
	}
	public String toString() {
		return "OrderRoom [id=" + id + ", roomId=" + roomId + ", hid=" + hid
				+ ", oid=" + oid + ", checkinDate=" + checkinDate
				+ ", checkoutDate=" + checkoutDate + ", quantity=" + quantity
				+ ", zipCode=" + zipCode + ", city=" + city + ", price="
				+ price + "]";
	}
	
}
