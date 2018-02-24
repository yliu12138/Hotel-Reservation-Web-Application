package iit;
import java.sql.*;
import java.sql.Date;
import java.util.*;


public class OrderRoomDAO {

	private static ConnectionUtil connUtil = ConnectionUtil.getInstance();
	
	/**
	 * Get all orderRooms' information of an order
	 */
	public static ArrayList<OrderRoom> getOrderRoomsByOrder(Order order) {
		if (order.getOrderRooms() != null && order.getOrderRooms().size() > 0) {
			System.out.println("Get getOrderRoomsByOrder from order's OrderHotelList");
			return order.getOrderRooms();
		}
		
		// no orders in user's orderHashMap, then get orders from db
		ArrayList<OrderRoom> orderRooms = new ArrayList<OrderRoom>();
		String sql = "SELECT * FROM orderRooms WHERE oid = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getOrderHotels conn: " + conn);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, order.getId());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer id = rs.getInt("id");
	            String roomId = rs.getString("roomId");
	            String hid = rs.getString("hid");
	            Integer oid = rs.getInt("oid");
	            Date checkinDate = rs.getDate("checkinDate");
	            Date checkoutDate = rs.getDate("checkoutDate");
	            Integer quantity = rs.getInt("quantity");
	            String zipCode = rs.getString("zipCode");
	            String state = rs.getString("state");
	            String city = rs.getString("city");
	            Double price = rs.getDouble("price");
	            
	            OrderRoom orderRoom = new OrderRoom(id, roomId, hid, oid, checkinDate, checkoutDate, quantity, zipCode, state, city, price);
	            orderRooms.add(orderRoom);
	        }
			System.out.println("Get OrderHotels from db: " + orderRooms);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return orderRooms;
	}
	/**
	 * Get all orderRooms' information 
	 */
	public static ArrayList<OrderRoom> getOrderRoomsList() {

		

		ArrayList<OrderRoom> orderRooms = new ArrayList<OrderRoom>();
		String sql = "SELECT * FROM orderRooms;";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getOrderHotels conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer id = rs.getInt("id");
	            String roomId = rs.getString("roomId");
	            String hid = rs.getString("hid");
	            Integer oid = rs.getInt("oid");
	            Date checkinDate = rs.getDate("checkinDate");
	            Date checkoutDate = rs.getDate("checkoutDate");
	            Integer quantity = rs.getInt("quantity");
	            String zipCode = rs.getString("zipCode");
	            String state = rs.getString("state");
	            String city = rs.getString("city");
	            Double price = rs.getDouble("price");
	            
	            OrderRoom orderRoom = new OrderRoom(id, roomId, hid, oid, checkinDate, checkoutDate, quantity, zipCode, state, city, price);
	            orderRooms.add(orderRoom);
	        }
			System.out.println("Get OrderHotels from db: " + orderRooms);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return orderRooms;
	}
	/**
	 * Get hotel count orderRooms' information 
	 */
	public static ArrayList<OrderRoom> getOrderHotelList() {

		

		ArrayList<OrderRoom> orderRooms = new ArrayList<OrderRoom>();
		String sql = "select count(hid) quantity, hid from orderRooms group by hid;";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getOrderHotels conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
	            String hid = rs.getString("hid");
	            int quantity = rs.getInt("quantity");
	            
	            OrderRoom orderRoom = new OrderRoom(quantity, hid);
	            orderRooms.add(orderRoom);
	        }
			System.out.println("Get OrderHotels from db: " + orderRooms);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return orderRooms;
	}

	/**
	 * Insert orderRooms into db.orderRooms table
	 */
	public static void insertOrderRooms(ArrayList<OrderRoom> orderRooms) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "INSERT INTO orderRooms(roomId, hid, oid, checkinDate, checkoutDate, "
				+ "quantity, zipCode, state, city, price) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn = connUtil.getConnection();
			System.out.println("insertOrderRooms conn: " + conn);
			
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			int i = 0;
			for (OrderRoom orderRoom : orderRooms) {
			    ps.setString(1, orderRoom.getRoomId());
			    ps.setString(2, orderRoom.getHid());
			    ps.setInt(3, orderRoom.getOid());
			    ps.setDate(4, orderRoom.getCheckinDate());
			    ps.setDate(5, orderRoom.getCheckoutDate());
			    ps.setInt(6, orderRoom.getQuantity());
			    ps.setString(7, orderRoom.getZipCode());
			    ps.setString(8, orderRoom.getState());
			    ps.setString(9, orderRoom.getCity());
			    ps.setDouble(10, orderRoom.getPrice());
			    ps.addBatch();
			    
			    // Execute every 100 OrderHotel.
			    i++;
			    if (i % 100 == 0 || i == orderRooms.size()) {
			    	ps.executeBatch(); 
	                System.out.println(orderRooms + " inserted into db.");
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
	}
	
	
	/**
	 * Remove an orderRoom from db.orderRooms table
	 */
	public static void removeOrderRooms(Integer oid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "DELETE FROM orderRooms WHERE oid = ?";
		
		try {
			conn = connUtil.getConnection();
			System.out.println("removeOrderRooms conn: " + conn);
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, oid);
			ps.executeUpdate();		// delete the order from db
			System.out.println(oid + "'s orderRooms removed from db.");
			
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
		String sql = "select sum(quantity), zipCode from orderRooms GROUP BY zipCode ORDER BY sum(quantity) DESC LIMIT 5";
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
	 * Statistics on Top 5 Rooms reserved regardless of rating
	 */
	public static HashMap<String, Integer> getTop5HotelsReserved() {
		HashMap<String, Integer> topRoomsReserved = new HashMap<String, Integer>();
		String sql = "select sum(quantity), roomId from orderRooms GROUP BY hid ORDER BY sum(quantity) DESC LIMIT 5";
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
	            String roomId = rs.getString("roomId");
	            topRoomsReserved.put(roomId, total);
	        }
			if (!topRoomsReserved.isEmpty()) {
				System.out.println("get top 5 rooms reserved from db: " + topRoomsReserved);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return topRoomsReserved;
	}
	
	
	/**
	 * Find highest room reserved in every city or zipCode
	 */
	public static HashMap<String, String> getHighestPriceCity(String group) {
		HashMap<String, String> highestPriceCity = new HashMap<String, String>();
		String sql = "select a.roomId, a.price, a." + group + " FROM orderRooms a " +
	        "INNER JOIN " +
	        "(" +
	            "SELECT MAX(price) maxPrice, " + group +
	            " FROM orderRooms GROUP BY " + group +
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
				String roomId = rs.getString("roomId");
				highestPriceCity.put(value, roomId);
			}
			if (!highestPriceCity.isEmpty()) {
				System.out.println("get highest price product sold in every city from db: " + highestPriceCity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return highestPriceCity;
	}
}