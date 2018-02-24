package iit;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;

public class Order implements Serializable, Comparable<Order> {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String confirmationId;
	private String username;
	private Date orderTime ;
	private Date checkinDate;
	private Date checkoutDate;
	private Date cancelDeadline;

	private Float cost;
	private String city;
	private String zipCode;
	
	//List of OrderRooms, OrderRestaurants
	private ArrayList<OrderRoom> orderRooms = new ArrayList<OrderRoom>();
	private ArrayList<OrderRest> orderRests = new ArrayList<OrderRest>();
	
	// getter and setter below
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getConfirmationId() {
		return confirmationId;
	}
	public void setConfirmationId(String confirmationId) {
		this.confirmationId = confirmationId;
	}
	
	public Float getCost() {
		return cost;
	}
	public void setCost(Float cost) {
		this.cost = cost;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public String getFormattedOrderTime() {
		Calendar orderT = Calendar.getInstance();
		orderT.setTime(orderTime);
		return (orderT.get(Calendar.MONTH) + 1) + "/" + (orderT.get(Calendar.DATE)) + "/" + (orderT.get(Calendar.YEAR));
	}
	
	public void setCancelDeadline(Date cancelDeadline) {
		this.cancelDeadline = cancelDeadline;
	}
	public Date getCancelDeadline() {
		return cancelDeadline;
	}
	public String getFormattedCancelDeadline() {
		Calendar deadline = Calendar.getInstance();
		deadline.setTime(cancelDeadline);
		return (deadline.get(Calendar.MONTH) + 1) + "/" + (deadline.get(Calendar.DATE)) + "/" + (deadline.get(Calendar.YEAR));
	}
	
	public Date getCheckinDate() {
		return checkinDate;
	}
	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}
	public String getFormattedCheckinDate() {
		Calendar checkin = Calendar.getInstance();
		checkin.setTime(checkinDate);
		return (checkin.get(Calendar.MONTH) + 1) + "/" + (checkin.get(Calendar.DATE)) + "/" + (checkin.get(Calendar.YEAR));
	}
	
	public Date getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public String getFormattedCheckoutDate() {
		Calendar checkout = Calendar.getInstance();
		checkout.setTime(checkoutDate);
		return (checkout.get(Calendar.MONTH) + 1) + "/" + (checkout.get(Calendar.DATE)) + "/" + (checkout.get(Calendar.YEAR));
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	public String getCity() {
		return city;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getZipCode() {
		return zipCode;
	}
	

	public ArrayList<OrderRoom> getOrderRooms() {
		return orderRooms;
	}
	public void setOrderRooms(ArrayList<OrderRoom> orderRooms) {
		this.orderRooms = orderRooms;
	}
	public void setOrderRests(ArrayList<OrderRest> orderRests) {
		this.orderRests = orderRests;
	}
	public ArrayList<OrderRest> getOrderRests() {
		return orderRests;
	}
	
	// constructors
	public Order() {
		super();
	}
	
	public Order(Integer id, String confirmationId, String username,
			Date orderTime, Date checkinDate, Date checkoutDate,
			Date cancelDeadline, Float cost, String city, String zipCode,
			ArrayList<OrderRoom> orderRooms, ArrayList<OrderRest> orderRests) {
		super();
		this.id = id;
		this.confirmationId = confirmationId;
		this.username = username;
		this.orderTime = orderTime;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.cancelDeadline = cancelDeadline;
		this.cost = cost;
		this.city = city;
		this.zipCode = zipCode;
		this.orderRooms = orderRooms;
		this.orderRests = orderRests;
	}

	public Order(Date orderTime, Float cost){
		setOrderTime(orderTime);
		setCost(cost); 
	}

	public Order(Integer id, String confirmationId, String username,
			Date orderTime, Date checkinDate, Date checkoutDate,
			Date cancelDeadline, Float cost, String city, String zipCode){
		super();
		this.id = id;
		this.confirmationId = confirmationId;
		this.username = username;
		this.orderTime = orderTime;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.cancelDeadline = cancelDeadline;
		this.cost = cost;
		this.city = city;
		this.zipCode = zipCode;			
		}	
	

	public String toString() {
		return "Order [id=" + id + ", confirmationId=" + confirmationId
				+ ", username=" + username + ", orderTime=" + orderTime
				+ ", checkinDate=" + checkinDate + ", checkoutDate="
				+ checkoutDate + ", cancelDeadline=" + cancelDeadline
				+ ", cost=" + cost + ", city=" + city + ", zipCode=" + zipCode
				+ ", orderRooms=" + orderRooms + ", orderRests=" + orderRests
				+ "]";
	}
	// This order can be compared with other orders by orderTime
	public int compareTo(Order o) {
		return o.getOrderTime().compareTo(this.getOrderTime());
	}
}
