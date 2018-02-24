package iit;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class OrderDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static HashMap<Integer, Order> orders = new HashMap<Integer, Order>();
	private static ConnectionUtil connUtil = ConnectionUtil.getInstance();
	
	
	public static HashMap<Integer, Order> getAllOrders() {
		if (orders != null && !orders.isEmpty()) {
			return orders;
		}
		// no orders in user's orderHashMap, then get orders from db
		String sql = "SELECT * FROM orders";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getAllOrders conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
	            Integer id  = rs.getInt("id");
	            String confirmationId = rs.getString("confirmationId");
	            String username = rs.getString("username");
	            Date orderTime = rs.getDate("orderTime");
	            Date checkinDate = rs.getDate("checkinDate");
	            Date cancelDeadline = rs.getDate("cancelDeadline");
	            Date checkoutDate = rs.getDate("checkoutDate");
	            Float cost = rs.getFloat("cost");
	            String city = rs.getString("city");
	            String zipCode = rs.getString("zipCode");
	            
	            Order order = new Order(id, confirmationId, username, orderTime, checkinDate, checkoutDate, cancelDeadline, cost, city, zipCode, null, null);
	            
	            // get the orderRooms, orderRestaurants of this order
	            ArrayList<OrderRoom> orderRooms = OrderRoomDAO.getOrderRoomsByOrder(order);
	            ArrayList<OrderRest> orderRests = OrderRestDAO.getOrderRestsByOrder(order);
	            order.setOrderRooms(orderRooms);
	            order.setOrderRests(orderRests);
	            
	            orders.put(id, order);
	        }
			System.out.println("Get all orders from db: " + orders);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return orders;
	}
	

	public static ArrayList<Order> getTotalCostList() {
		ArrayList<Order> orderList = new ArrayList<Order>();
		// no orders in user's orderHashMap, then get orders from db
		String sql = "select sum(cost) TotalCost, orderTime from orders group by orderTime;";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getAllOrders conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {

	            Date orderTime = rs.getDate("orderTime");

	            Float cost = rs.getFloat("TotalCost");
	            
	            Order order = new Order(orderTime, cost);
	            
	            orderList.add(order);
	        }
			System.out.println("Get all orders from db: " + orders);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return orderList;
	}	

	/**
	 * Get all orders' information of a user
	 */
	public static HashMap<Integer, Order> getOrdersByUser(User user) {
		
		if (user.getOrdersMap() != null && user.getOrdersMap().size() > 0) {
			System.out.println("Get orders from user's orderHashMap");
			return user.getOrdersMap();
		}
		
		// no orders in user's orderHashMap, then get orders from db
		HashMap<Integer, Order> ordersMap = new HashMap<Integer, Order>();
		String sql = "SELECT * FROM orders WHERE username = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getOrdersByUser conn: " + conn);
			ps = conn.prepareStatement(sql);
//			ps.setInt(1, user.getId());
			ps.setString(1, user.getUsername());
			rs = ps.executeQuery();
			
			while (rs.next()) {
	            Integer id  = rs.getInt("id");
	            String confirmationId = rs.getString("confirmationId");
	            String username = rs.getString("username");
	            Date orderTime = rs.getDate("orderTime");
	            Date checkinDate = rs.getDate("checkinDate");
	            Date cancelDeadline = rs.getDate("cancelDeadline");
	            Date checkoutDate = rs.getDate("checkoutDate");
	            Float cost = rs.getFloat("cost");
	            String city = rs.getString("city");
	            String zipCode = rs.getString("zipCode");
	            
	            Order order = new Order(id, confirmationId, username, orderTime, checkinDate, checkoutDate, cancelDeadline, cost, city, zipCode, null, null);
	            
	            // get the orderRooms, orderRestaurants of this order
	            ArrayList<OrderRoom> orderRooms = OrderRoomDAO.getOrderRoomsByOrder(order);
	            ArrayList<OrderRest> orderRests = OrderRestDAO.getOrderRestsByOrder(order);
	            order.setOrderRooms(orderRooms);
	            order.setOrderRests(orderRests);
	            
	            ordersMap.put(id, order);
	        }
			System.out.println("Get orders from db: " + ordersMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return ordersMap;
	}
	
	
	/**
	 * Insert an order into db
	 */
	public static Integer insertOrder(Order order) {
		Integer id = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "INSERT INTO orders(confirmationId, username, orderTime, checkinDate, cancelDeadline, "
				+ "checkoutDate, cost, city, zipCode) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn = connUtil.getConnection();
			System.out.println(conn);
			
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, order.getConfirmationId());
			ps.setString(2, order.getUsername());
			ps.setDate(3, order.getOrderTime());
			ps.setDate(4, order.getCheckinDate());
			ps.setDate(5, order.getCancelDeadline());
			ps.setDate(6, order.getCheckoutDate());
			ps.setFloat(7, order.getCost());
			ps.setString(8, order.getCity());
			ps.setString(9, order.getZipCode());
			
			ps.executeUpdate();		// insert the user object into db
			rs = ps.getGeneratedKeys();
			if(rs.next()){
				id = rs.getInt(1);
			}
			orders.put(id, order);
			System.out.println(order + " added. id = " + id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return id;
	}
	
	/**
	 * update an order into db
	 */
	public static Integer updateOrder(int oid, String username, String city, String zipcode, float cost) {
		Integer id = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "UPDATE orders"
				+  " SET city = \"" + city + "\", zipcode =\"" + zipcode + "\", cost = " + cost
				+ " where id = " + oid + ";";
		System.out.println(sql);
		
		try {
			conn = connUtil.getConnection();
			System.out.println(conn);
			
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return id;
	}
	
	/**
	 * Remove an order from db by using the orderId
	 */
	public static void removeOrderById(Integer id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "DELETE FROM orders WHERE id = ?";
		
		try {
			conn = connUtil.getConnection();
			System.out.println(conn);
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();		// delete the order from db
			System.out.println(id + " removed.");
			orders.remove(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
	}
	
	
	/**
	 * Search orders
	 */
	public static ArrayList<Order> orderSearch(Order order) {
		ArrayList<Order> orders = new ArrayList<Order>();
		
		String username = order.getUsername();
		Float cost = order.getCost();
		String city = order.getCity();
		String zipCode = order.getZipCode();

		// get reviews from db.reviews table
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM orders";
		
		if (username == null && cost == null && city == null && zipCode == null) {
			sql += "";
		} else if (username.equals("") && cost == null && city.equals("") && zipCode.equals("")) {
			sql += "";
		} else {
			sql += " WHERE";
			sql += (!username.equals("") ? " username = '" + username + "' and" : "");
			sql += (cost != null ? " cost >= " + cost + " and" : "");
			sql += (!city.equals("") ? " city like '" + city + "' and" : "");
			sql += (!zipCode.equals("") ? " zipCode = '" + zipCode + "' and" : "");

			int len = sql.length();
			if (sql.substring(len - 3).equals("and")) {
				sql = sql.substring(0, len - 3);
			}
		}

		try {
			conn = connUtil.getConnection();
			System.out.println("orderSearch conn: " + conn);
			ps = conn.prepareStatement(sql);
			System.out.println("Sql: " + ps.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				Integer id = rs.getInt("id");
				String confirmationId = rs.getString("confirmationId");
				String username2 = rs.getString("username");
				Date orderTime = rs.getDate("orderTime");
				Date checkinDate = rs.getDate("checkinDate");
				Date cancelDeadline = rs.getDate("cancelDeadline");
				Date checkoutDate = rs.getDate("checkoutDate");
				Float cost2 = rs.getFloat("cost");
				String city2 = rs.getString("city");
				String zipCode2 = rs.getString("zipCode");

				Order order2 = new Order(id, confirmationId, username2, orderTime, checkinDate, checkoutDate, cancelDeadline, cost2, city2, zipCode2, null, null);
				System.out.println("In db: " + order2);
				order2.setOrderRooms(OrderRoomDAO.getOrderRoomsByOrder(order2));
				order2.setOrderRests(OrderRestDAO.getOrderRestsByOrder(order2));
				orders.add(order2);
			}
			System.out.println("Get all searched orders from db");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return orders;
	}
}
