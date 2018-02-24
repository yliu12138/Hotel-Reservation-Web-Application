package iit;
import java.sql.*;
import java.util.*;


public class OrderRestDAO {

	private static ConnectionUtil connUtil = ConnectionUtil.getInstance();
	
	/**
	 * Get all orderRests' information of an order
	 */
	public static ArrayList<OrderRest> getOrderRestsByOrder(Order order) {
		if (order.getOrderRests() != null && order.getOrderRests().size() > 0) {
			System.out.println("Get getOrderRestsByOrder from order's OrderHotelList");
			return order.getOrderRests();
		}
		
		// no orders in user's orderHashMap, then get orders from db
		ArrayList<OrderRest> orderRests = new ArrayList<OrderRest>();
		String sql = "SELECT * FROM orderRestaurants WHERE oid = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getOrderRestsByOrder conn: " + conn);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, order.getId());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer id = rs.getInt("id");
	            String restId = rs.getString("restId");
	            Integer oid = rs.getInt("oid");
	            Integer quantity = rs.getInt("quantity");
	            String zipCode = rs.getString("zipCode");
	            String state = rs.getString("state");
	            String city = rs.getString("city");
	            Double price = rs.getDouble("price");
	            
	            OrderRest orderRest = new OrderRest(id, restId, oid, quantity, zipCode, state, city, price);
	            orderRests.add(orderRest);
	        }
			System.out.println("Get orderRests from db: " + orderRests);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return orderRests;
	}
	/**
	 * Get all orderRests' information 
	 */
	public static ArrayList<OrderRest> getOrderRestsList() {

		
		// no orders in user's orderHashMap, then get orders from db
		ArrayList<OrderRest> orderRests = new ArrayList<OrderRest>();
		String sql = "SELECT * FROM orderRestaurants;";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getOrderRestsByOrder conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer id = rs.getInt("id");
	            String restId = rs.getString("restId");
	            Integer oid = rs.getInt("oid");
	            Integer quantity = rs.getInt("quantity");
	            String zipCode = rs.getString("zipCode");
	            String state = rs.getString("state");
	            String city = rs.getString("city");
	            Double price = rs.getDouble("price");
	            
	            OrderRest orderRest = new OrderRest(id, restId, oid, quantity, zipCode, state, city, price);
	            orderRests.add(orderRest);
	        }
			System.out.println("Get orderRests from db: " + orderRests);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return orderRests;
	}



	/**
	 * Insert orderRests into db.orderRests table
	 */
	public static void insertOrderRests(ArrayList<OrderRest> orderRests) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "INSERT INTO orderRestaurants(restId, oid, quantity, zipCode, state, city, price) VALUES(?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn = connUtil.getConnection();
			System.out.println("insertOrderRests conn: " + conn);
			
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			int i = 0;
			for (OrderRest orderRest : orderRests) {
			    ps.setString(1, orderRest.getRestId());
			    ps.setInt(2, orderRest.getOid());
			    ps.setInt(3, orderRest.getQuantity());
			    ps.setString(4, orderRest.getZipCode());
			    ps.setString(5, orderRest.getState());
			    ps.setString(6, orderRest.getCity());
			    ps.setDouble(7, orderRest.getPrice());
			    ps.addBatch();
			    
			    // Execute every 100 OrderHotel.
			    i++;
			    if (i % 100 == 0 || i == orderRests.size()) {
			    	ps.executeBatch(); 
	                System.out.println(orderRests + " inserted into db.");
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
	}
	
	
	/**
	 * Remove an orderRest from db.orderRests table
	 */
	public static void removeOrderRests(Integer oid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "DELETE FROM orderRestaurants WHERE oid = ?";
		
		try {
			conn = connUtil.getConnection();
			System.out.println("removeOrderHotels conn: " + conn);
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, oid);
			ps.executeUpdate();		// delete the order from db
			System.out.println(oid + "'s orderRests removed from db.");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
	}
	
	
	/**
	 * Statistics on Top 5 zip codes where maximum number of products sold
	 */
	public static HashMap<String, Integer> getTop5ZipCode() {
		HashMap<String, Integer> topZipCodes = new HashMap<String, Integer>();
		String sql = "select sum(quantity), zipCode from orderRests GROUP BY zipCode ORDER BY sum(quantity) DESC LIMIT 5";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getTop5ZipCode conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer total = rs.getInt("sum(quantity)");
	            String zipCode = rs.getString("zipCode");
	            topZipCodes.put(zipCode, total);
	        }
			if (!topZipCodes.isEmpty()) {
				System.out.println("get top 5 zipCodes from db: " + topZipCodes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return topZipCodes;
	}

	
	
	/**
	 * Statistics on Top 5 Restaurants reserved regardless of rating
	 */
	public static HashMap<String, Integer> getTop5RestaurantsReserved() {
		HashMap<String, Integer> topRestsReserved = new HashMap<String, Integer>();
		String sql = "select sum(quantity), restId from orderRests GROUP BY hid ORDER BY sum(quantity) DESC LIMIT 5";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getTop5ProductsSold conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer total = rs.getInt("sum(quantity)");
	            String restId = rs.getString("restId");
	            topRestsReserved.put(restId, total);
	        }
			if (!topRestsReserved.isEmpty()) {
				System.out.println("get top 5 restaurants reserved from db: " + topRestsReserved);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return topRestsReserved;
	}
	
	
	/**
	 * Find highest Rest reserved in every city or zipCode
	 */
	public static HashMap<String, String> getHighestPriceCity(String group) {
		HashMap<String, String> highestPriceCity = new HashMap<String, String>();
		String sql = "select a.restId, a.price, a." + group + " FROM orderRestaurants a " +
	        "INNER JOIN " +
	        "(" +
	            "SELECT MAX(price) maxPrice, " + group +
	            " FROM orderRestaurants GROUP BY " + group +
	        ") b ON a." + group + " = b." + group + " AND a.price = b.maxPrice";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getHighestPriceCity conn: " + conn);
			ps = conn.prepareStatement(sql);
			System.out.println("preparedStatement = " + ps);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String value = rs.getString(group);
				String restId = rs.getString("restId");
				highestPriceCity.put(value, restId);
			}
			if (!highestPriceCity.isEmpty()) {
				System.out.println("get highest price restaurants reserved in every city from db: " + highestPriceCity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return highestPriceCity;
	}
}