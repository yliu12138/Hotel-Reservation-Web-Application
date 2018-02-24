package iit;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Hotel implements Serializable, Comparable<Hotel> {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String hid;
	private String name;
	private String descp;
	private String address;
	private String city;
	private Integer zipCodeRange;	
	private Float avgRating;
	
	private String image;
	
	private ArrayList<Review> reviews;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHid() {
		return hid;
	}
	public void setHid(String hid) {
		this.hid = hid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescp() {
		return descp;
	}
	public void setDescp(String descp) {
		this.descp = descp;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getZipCodeRange() {
		return zipCodeRange;
	}
	public void setZipCodeRange(Integer zipCodeRange) {
		this.zipCodeRange = zipCodeRange;
	}
	public Float getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(Float avgRating) {
		this.avgRating = avgRating;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public ArrayList<Review> getReviews() {
		return reviews;
	}
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}
	
	public Hotel(Integer id, String hid, String name, String descp,
			String address, String city, Integer zipCodeRange, Float avgRating, String image,
			ArrayList<Review> reviews) {
		super();
		this.id = id;
		this.hid = hid;
		this.name = name;
		this.descp = descp;
		this.address = address;
		this.city = city;
		this.zipCodeRange = zipCodeRange;
		this.avgRating = avgRating;
		this.image = image;
		this.reviews = reviews;
	}
	
	public String toString() {
		return "Hotel [id=" + id + ", hid=" + hid + ", name=" + name
				+ ", descp=" + descp + ", address=" + address + ", city="
				+ city + ", zipCodeRange=" + zipCodeRange + ", avgRating="
				+ avgRating + ", image=" + image + ", reviews=" + reviews + "]";
	}
	
	// update a value and call its setter by reflect
	public void updateHotel(String propertyName, String value) {
		System.out.println("calling method: set" + propertyName);
		Method method = null;
		try {
			if (propertyName.equals("City")) {
				setCity(value.split("_")[0]);
				setZipCodeRange(Integer.parseInt(value.split("_")[1]));
			} else {
				method = getClass().getDeclaredMethod("set" + propertyName, String.class);
				method.invoke(this, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public int compareTo(Hotel h) {
		return this.getCity().compareTo(h.getCity());
	}
}
