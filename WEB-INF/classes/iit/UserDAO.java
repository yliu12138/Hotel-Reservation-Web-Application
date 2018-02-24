package iit;
import java.io.*;
import java.sql.*;
import java.util.*;


public class UserDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static HashMap<String, User> users = new HashMap<String, User>();
	private static ConnectionUtil connUtil = ConnectionUtil.getInstance();

	/**
	 * Get all users' information
	 */
	public static HashMap<String, User> getAllUsers() {
		if (users != null && !users.isEmpty()) {
			System.out.println("Get users from hashmap: " + users);
			return users;
		}
		
		// no users in hashmap, then get user from db
		String sql = "SELECT * FROM users";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
//			conn = MySQLDataStoreUtilities.getConnection();
			
			conn = connUtil.getConnection();
			System.out.println("getAllUsers conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
	            Integer id  = rs.getInt("id");
	            String username = rs.getString("username");
	            String password = rs.getString("password");
	            Integer level = rs.getInt("level");
	            
	            User user = new User(id, username, password, level, null, null);
	            users.put(username, user);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		System.out.println("Get users from db: " + users);
		return users;
	}
	

	/**
	 * Get a user object by using username represented by string
	 */
	public static User getUserByName(String username) {
		User user = users.get(username);
		if (user != null) {
			return user;
		}
		
		// no user in hashmap, then get user from db
		String sql = "SELECT id, username, password, level FROM users WHERE username = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
//			conn = MySQLDataStoreUtilities.getConnection();
			
			conn = connUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			if (rs.next()) {
	            Integer id  = rs.getInt("id");
	            String password = rs.getString("password");
	            Integer level = rs.getInt("level");
	            user = new User(id, username, password, level, null, null);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		System.out.println(user);
		return user;
	}
	
	
	/**
	 * Insert a user into db
	 */
	public static Integer insertUser(User user) {
		Integer id = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "INSERT INTO users(username, password, level) VALUES(?, ?, ?)";
		
		try {
			conn = connUtil.getConnection();
			System.out.println(conn);
			
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getLevel());
			
			ps.executeUpdate();		// insert the user object into db
			rs = ps.getGeneratedKeys();
			if(rs.next()){
				id = rs.getInt(1);
			}
//			user.setId(id);
//			users.put(user.getUsername(), user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		System.out.println(user + " added. id = " + id);
		return id;
	}

	
	public static Boolean add(User user) {
		if (isExists(user)) {
			return false;
		}
		users.put(user.getUsername(), user);
		return true;
	}

	
	/**
	 * validate if the user to be added already exists
	 */
	private static Boolean isExists(User user) {
		if (users.containsKey(user.getUsername())) {
			return true;
		}
		return false;
	}
	
}
