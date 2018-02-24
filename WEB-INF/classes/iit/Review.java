package iit;

import java.sql.Date;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Review {

	private Integer id;
	private String hid;
	private String username;
	private Integer age;
	private String gender;
	private String occupation;
	private Integer rating;
	private Date date;
	private String reviewCity;
	private String reviewZipCode;
	private String reviewText;
	
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setDateFromStr(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		java.util.Date strToDate = formatter.parse(strDate, pos);
		setDate(new Date(strToDate.getTime()));
	}
	public String getFormattedDate() {
		Calendar reviewTime = Calendar.getInstance();
		reviewTime.setTime(date);
		return (reviewTime.get(Calendar.MONTH) + 1) + "/" + (reviewTime.get(Calendar.DATE)) + "/" + (reviewTime.get(Calendar.YEAR));
	}
	
	
	public String getReviewCity() {
		return reviewCity;
	}
	public void setReviewCity(String reviewCity) {
		this.reviewCity = reviewCity;
	}
	public String getReviewZipCode() {
		return reviewZipCode;
	}
	public void setReviewZipCode(String reviewZipCode) {
		this.reviewZipCode = reviewZipCode;
	}
	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	// constructor
	public Review(Integer id, String hid, String username, Integer age,
			String gender, String occupation, Integer rating, Date date,
			String reviewCity, String reviewZipCode, String reviewText) {
		super();
		this.id = id;
		this.hid = hid;
		this.username = username;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;
		this.rating = rating;
		this.date = date;
		this.reviewCity = reviewCity;
		this.reviewZipCode = reviewZipCode;
		this.reviewText = reviewText;
	}
	
	//default constructor
	public Review() {
		super();
	}
	
	public String toString() {
		return "Review [id=" + id + ", hid=" + hid + ", username=" + username
				+ ", age=" + age + ", gender=" + gender + ", occupation="
				+ occupation + ", rating=" + rating + ", date=" + date
				+ ", reviewCity=" + reviewCity + ", reviewZipCode="
				+ reviewZipCode + ", reviewText=" + reviewText + "]";
	}
}
