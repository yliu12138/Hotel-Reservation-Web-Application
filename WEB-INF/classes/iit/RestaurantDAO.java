package iit;

import java.io.*;
import java.sql.*;
import java.util.*;

public class RestaurantDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static HashMap<String, Restaurant> restaurantsMap = new HashMap<String, Restaurant>();
	private static ConnectionUtil connUtil = ConnectionUtil.getInstance();
	
	/**
	 * Get all restaurants from db or memory
	 */
	public static HashMap<String, Restaurant> getAllRestaurants() {
		if (restaurantsMap != null && !restaurantsMap.isEmpty()) {
			return restaurantsMap;
		}
		// get restaurants from db.restaurants table
		// no restaurants in hashmap, then get restaurants from db
		String sql = "SELECT * FROM restaurants";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			conn = connUtil.getConnection();
			System.out.println("getAllRestaurants conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer id = rs.getInt("id");
	            String rid  = rs.getString("rid");
	            String name = rs.getString("name");
	            Float price = rs.getFloat("price");
	            String city = rs.getString("city");
	            String cid = rs.getString("cid");
	            Restaurant rest = new Restaurant(id, rid, name, price, city, cid);
	            restaurantsMap.put(rid, rest);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		System.out.println("Get all restaurants from db");
		return restaurantsMap;
	}
	
	
	/**
	 * Get restaurants in a city
	 */
	public static ArrayList<Restaurant> getRestaurantsOfCity(String cid) {
		ArrayList<Restaurant> restList = new ArrayList<Restaurant>();
		if (restaurantsMap != null && !restaurantsMap.isEmpty()) {
			for (Restaurant rest : restaurantsMap.values()) {
				if (rest.getCid().equals(cid)) {
					restList.add(rest);
				}
			}
			return restList;
		}
		
		// get restaurants from db.restaurants table
		// no restaurant in hashmap, then get restaurants from db
		String sql = "SELECT * FROM restaurants WHERE cid = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getRestaurantsOfCity conn: " + conn);
			ps = conn.prepareStatement(sql);
			ps.setString(1, cid);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer id = rs.getInt("id");
	            String rid  = rs.getString("rid");
	            String name = rs.getString("name");
	            Float price = rs.getFloat("price");
	            String city = rs.getString("city");
	            Restaurant rest = new Restaurant(id, rid, name, price, city, cid);
	            
				restaurantsMap.put(rid, rest);
				restList.add(rest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		System.out.println("Get all restaurants of " + cid + " from db");
		return restList;
	}
	
	
	/**
	 * Get a restaurant object by using the rid
	 */
	public static Restaurant getRestaurantById(String rid) {
		if (restaurantsMap != null && !restaurantsMap.isEmpty()) {
			return restaurantsMap.get(rid);
		}
		Restaurant rest = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM restaurants WHERE rid = ?";
		
		try {
			conn = connUtil.getConnection();
			System.out.println("getRestaurantById conn: " + conn);
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, rid);
			rs = ps.executeQuery();		
			if (rs.next()) {
				Integer id = rs.getInt("id");
	            String name = rs.getString("name");
	            Float price = rs.getFloat("price");
	            String city = rs.getString("city");
	            String cid = rs.getString("cid");
	            rest = new Restaurant(id, rid, name, price, city, cid);
	            restaurantsMap.put(rid, rest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return rest;
	}
	
	
	/**
	 * Get a restaurant's name by using rid
	 */
	public static String getRestNameById(String rid) {
		if (restaurantsMap != null && !restaurantsMap.isEmpty()) {
			return restaurantsMap.get(rid).getName();
		}
		Restaurant rest = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM restaurants WHERE rid = ?";
		String name = "";
		
		try {
			conn = connUtil.getConnection();
			System.out.println("getRestNameById conn: " + conn);
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, rid);
			rs = ps.executeQuery();		
			if (rs.next()) {
				Integer id = rs.getInt("id");
				name = rs.getString("name");
				Float price = rs.getFloat("price");
				String city = rs.getString("city");
				String cid = rs.getString("cid");
				rest = new Restaurant(id, rid, name, price, city, cid);
				restaurantsMap.put(rid, rest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return name;
	}

	
}
