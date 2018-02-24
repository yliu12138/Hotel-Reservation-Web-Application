package iit;
import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import com.mongodb.*;


public class ReviewDAO {
	
	private static HashMap<String, Hotel> hotelsMap = HotelDAO.getAllHotels();
	private static HashMap<Integer, Review> reviewsMap = new HashMap<Integer, Review>();
	private static ConnectionUtil connUtil = ConnectionUtil.getInstance();
	
	
	/**
	 * Get reviews of a hotel using its hotel id
	 */
	public static ArrayList<Review> getReviewsByHotelId(String hid) {
		// get the the reviews from memory
		ArrayList<Review> reviewList = new ArrayList<Review>();
		System.out.println("ReviewsMap: " + reviewsMap);
		if (reviewsMap != null && !reviewsMap.isEmpty()) {
			for (Review review : reviewsMap.values()) {
				if (review.getHid().equals(hid)) {
					reviewList.add(review);
				}
			}
		}
		if (reviewList != null && !reviewList.isEmpty()) {
			Collections.sort(reviewList, new DateComparator());
			System.out.println(hid + " has " + reviewList.size() + " reviews in memory: " + reviewList);
			return reviewList;
		}
		
		
		// get hid's reviews from db
		String sql = "SELECT * FROM reviews WHERE hid = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getReviewsByHotelId conn: " + conn);
			ps = conn.prepareStatement(sql);
			ps.setString(1, hid);
			rs = ps.executeQuery();
			
			while (rs.next()) {
	            Integer id  = rs.getInt("id");
	            String username = rs.getString("username");
	            Integer age = rs.getInt("age");
	            String gender = rs.getString("gender");
	            String occupation = rs.getString("occupation");
	            Integer rating = rs.getInt("rating");
	            Date date = rs.getDate("date");
	            String reviewCity = rs.getString("reviewCity");
	            String reviewZipCode = rs.getString("reviewZipCode");
	            String reviewText = rs.getString("reviewText");

	            Review review = new Review(id, hid, username, age, gender, occupation, rating, 
	            		date, reviewCity, reviewZipCode, reviewText);
	            reviewList.add(review);
	        }
			System.out.println("Get reviews from db: " + reviewList);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return reviewList;
	}
	
	
	/**
	 * Get all reviews
	 */
	public static HashMap<Integer, Review> getAllReviews() {
		if (reviewsMap != null && !reviewsMap.isEmpty()) {
			return reviewsMap;
		}
		
		// get hid's reviews from db
		String sql = "SELECT * FROM reviews";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getAllReviews conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
	            Integer id  = rs.getInt("id");
	            String hid = rs.getString("hid");
	            String username = rs.getString("username");
	            Integer age = rs.getInt("age");
	            String gender = rs.getString("gender");
	            String occupation = rs.getString("occupation");
	            Integer rating = rs.getInt("rating");
	            Date date = rs.getDate("date");
	            String reviewCity = rs.getString("reviewCity");
	            String reviewZipCode = rs.getString("reviewZipCode");
	            String reviewText = rs.getString("reviewText");

	            Review review = new Review(id, hid, username, age, gender, occupation, rating, 
	            		date, reviewCity, reviewZipCode, reviewText);
	            reviewsMap.put(id, review);
	        }
			System.out.println("Get all reviews from db: " + reviewsMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return reviewsMap;
	}
	// use mongodb
	public static void insertReview(Review review){
		DBCollection myReviews;
		MongoClient mongo;
		mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("hotel");
		myReviews = db.getCollection("myReviews");
		BasicDBObject doc = new BasicDBObject("title", "myReviews").
				append("hid", review.getHid()).
				append("username", review.getUsername()).
				append("age", review.getAge()).
				append("gender", review.getGender()).
				append("occupation", review.getOccupation()).
				append("rate", review.getRating()).
				append("Date", review.getDate()).
				append("City", review.getReviewCity()).
				append("ZipCode", review.getReviewZipCode()).
				append("ReviewText", review.getReviewText());
		myReviews.insert(doc);
		System.out.println("document inserted");
	}

	/**
	 * Insert or update a review into db.
	 * Successful insertion returns the review rid, otherwise null
	 */
	public static void insertOrUpdateReview(Review review) {
		// if review.id == null -> insert
		// else -> update
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		Integer id = review.getId();
		
		// update
		if (id != null) {
			sql = "UPDATE reviews SET age = ?, gender = ?, occupation = ?, rating = ?, date = ?, reviewCity = ?, " 
					+ "reviewZipCode = ?, reviewText = ? WHERE id = ?";
			
			try {
				conn = connUtil.getConnection();
				System.out.println("update review conn: " + conn);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, review.getAge());
				ps.setString(2, review.getGender());
				ps.setString(3, review.getOccupation());
				ps.setInt(4, review.getRating());
				ps.setDate(5, review.getDate());
				ps.setString(6, review.getReviewCity());
				ps.setString(7, review.getReviewZipCode());
				ps.setString(8, review.getReviewText());
				ps.setInt(9, id);
				
				// insert the item into db
				ps.executeUpdate();		
				reviewsMap.put(id, review);
				System.out.println(id + " updated in db.");
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				MySQLDataStoreUtilities.release(rs, ps);
			}
			return;
		}
		
		// Code comes here, id == null, insert
		sql = "INSERT INTO reviews(hid, username, age, gender, occupation, rating, date, reviewCity, reviewZipCode, reviewText) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			conn = connUtil.getConnection();
			System.out.println("insert a review conn: " + conn);
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, review.getHid());
			ps.setString(2, review.getUsername());
			ps.setInt(3, review.getAge());
			ps.setString(4, review.getGender());
			ps.setString(5, review.getOccupation());
			ps.setInt(6, review.getRating());
			ps.setDate(7, review.getDate());
			ps.setString(8, review.getReviewCity());
			ps.setString(9, review.getReviewZipCode());
			ps.setString(10, review.getReviewText());
			
			// insert the item into db
			ps.executeUpdate();	
			rs = ps.getGeneratedKeys();
			if(rs.next()){
				id = rs.getInt(1);
			}
			review.setId(id);
			reviewsMap.put(id, review);
			System.out.println(review + ", id = " + id + " inserted into db.");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
	}
	
	
	/**
	 * Update a hotel's avgRating using its hid
	 */
	public static void updateAvgRating(String hid) {
		// get hid's reviews from db
		String sql = "SELECT avg(rating), hid FROM reviews WHERE hid = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("updateAvgRating conn: " + conn);
			ps = conn.prepareStatement(sql);
			ps.setString(1, hid);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				Float avgRating = rs.getFloat("avg(rating)");
				HotelDAO.updateHotelAvgRating(hid, avgRating);		// update hotel's avgRating in db
	            hotelsMap.get(hid).setAvgRating(avgRating);			// update hotel's avgRating in hashmap
	            System.out.println(hid + "'s avgRating updated.");
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
	}


	
	
	/**
	 * Statistics on Top 5 Products Sold by rating
	 */
	public static HashMap<String, Double> getTop5MostLikedProducts() {
		HashMap<String, Double> top5MostLikedProducts = new HashMap<String, Double>();
		
		// get top reviews from db
		String sql = "SELECT avg(rating), hid FROM reviews GROUP BY hid order by avg(rating) desc limit 5";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getTop5MostLikedProducts conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Double avgRating = rs.getDouble("avg(rating)");
				String hid = rs.getString("hid");
				top5MostLikedProducts.put(hid, avgRating);
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return top5MostLikedProducts;
	}
		
	/**
	 * Statistics on Top 5 Products Sold regardless of rating
	 */
	public static HashMap<String, Integer> getTop5MostSoldProducts() {
		HashMap<String, Integer> top5MostSoldProducts = new HashMap<String, Integer>();
		
		// get top reviews from db
		String sql = "SELECT count(hid), hid FROM reviews GROUP BY hid order by count(hid) desc limit 5";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getTop5MostSoldProducts conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer cnthid = rs.getInt("count(hid)");
				String hid = rs.getString("hid");
				top5MostSoldProducts.put(hid, cnthid);
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return top5MostSoldProducts;
	}
	
	/**
	 * Returns a list of reviews where rating > num
	 */
	public static ArrayList<Review> getReviewsRatingGreaterThan(int num) {
		ArrayList<Review> reviewList = new ArrayList<Review>();
		if (reviewsMap != null && !reviewsMap.isEmpty()) {
			for (Review review : reviewsMap.values()) {
				if (review.getRating() > 3) {
					reviewList.add(review);
				}
			}
			return reviewList;
		}
		
		// get reviews whose rating > 3
		String sql = "SELECT * FROM reviews WHERE rating > ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getReviewsRatingGreaterThan conn: " + conn);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String hid = rs.getString("hid");
				String username = rs.getString("username");
				Integer age = rs.getInt("age");
				String gender = rs.getString("gender");
				String occupation = rs.getString("occupation");
				Integer rating = rs.getInt("rating");
				Date date = rs.getDate("date");
				String reviewCity = rs.getString("reviewCity");
				String reviewZipCode = rs.getString("reviewZipCode");
				String reviewText = rs.getString("reviewText");
				
				Review review = new Review(id, hid, username, age, gender, occupation, rating, date, reviewCity, reviewZipCode, reviewText);
				reviewList.add(review);
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return reviewList;
	}
	
	
	/**
	 * Returns reviews filtered by argument
	 */
/*	public static ArrayList<Review> reviewSearch(Review review4Search) {
		ArrayList<Review> itemList = new ArrayList<Review>();
		
		String category = item.getCategory();
		String manufacturer = item.getManufacturer();
		Double price = item.getPrice();
		Integer stock = item.getStock();
		Integer sold = item.getSold();
		Double avgRating = item.getAvgRating();
		System.out.println("Product to search: " + item);
		
		// get reviews from db.reviews table
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM products";
		
		if (category.equals("") && manufacturer.equals("") && price == null && stock == null && sold == null && avgRating == null) {
			sql += "";
		} else {
			sql += " WHERE";
			sql += (!category.equals("") ? " category = '" + category + "' and" : "");
			sql += (!manufacturer.equals("") ? " manufacturer like '%" + manufacturer + "%' and" : "");
			sql += (price != null ? " price >= " + price + " and" : "");
			sql += (stock != null ? " stock >= " + stock + " and" : "");
			sql += (sold != null ? " sold >= " + sold + " and" : "");
			sql += (avgRating != null ? " avgRating >= " + avgRating : "");
			
			int len = sql.length();
			if (sql.substring(len - 3).equals("and")) {
				sql = sql.substring(0, len - 3);
			}
		}
		
		try {
			conn = connUtil.getConnection();
			System.out.println("reviewSearch conn: " + conn);
			ps = conn.prepareStatement(sql);
			System.out.println("Sql: " + ps);
			rs = ps.executeQuery();
			
			while (rs.next()) {
	            Integer pid = rs.getInt("pid");
	            String id = rs.getString("id");
	            String title = rs.getString("title");
	            String descp = rs.getString("descp");
	            String category2 = rs.getString("category");
	            Double price2 = rs.getDouble("price");
	            Double discount = rs.getDouble("discount");
	            Double rebate = rs.getDouble("rebate");
	            Integer stock2 = rs.getInt("stock");
	            String manufacturer2 = rs.getString("manufacturer");
	            Integer sold2 = rs.getInt("sold");
	            Double avgRating2 = rs.getDouble("avgRating");
	            
	            Item item2 = new Item(pid, id, title, descp, category2, price2, discount, rebate, stock2, id, manufacturer2);
	            item2.setSold(sold2);
	            item2.setAvgRating(avgRating2);
	            ArrayList<String> accessories = addAccessories(category, AC_COUNT);
	            item2.setAccessories(accessories);
	            itemList.add(item2);
	        }
			System.out.println("Get all searched items from db");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return itemList;
	}
	*/
}


class DateComparator implements Comparator<Review>, Serializable {
	private static final long serialVersionUID = 1L;

	public int compare(Review o1, Review o2) {
		return o2.getDate().compareTo(o1.getDate());
	}
}