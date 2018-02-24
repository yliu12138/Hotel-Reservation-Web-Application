package iit;

import java.io.*;
import java.sql.*;
import java.util.*;

public class RoomDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	private static HashMap<String, Room> roomsMap = new HashMap<String, Room>();
	private static ConnectionUtil connUtil = ConnectionUtil.getInstance();
	
	public static HashMap<String, Room> getAllRooms() {
		if (roomsMap != null && !roomsMap.isEmpty()) {
			return roomsMap;
		}
		
		// get items from db.products table
		// no users in hashmap, then get user from db
		String sql = "SELECT * FROM rooms";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			conn = connUtil.getConnection();
			System.out.println("getAllItems conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer id = rs.getInt("id");
	            String roomId  = rs.getString("roomId");
	            String roomType = rs.getString("roomType");
	            String descp = rs.getString("descp");
	            String hid = rs.getString("hid");
	            Double price = rs.getDouble("price");
	            Double discount = rs.getDouble("discount");
	            
	            // set accessories, sold and avgRating
	            Room room = new Room(id, roomId, roomType, descp, hid, price, discount);
	            roomsMap.put(roomId, room);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		System.out.println("Get all rooms from db");
		return roomsMap;
	}
	
	
	/**
	 * Get room list sorted by city
	 */
	public static ArrayList<Room> getRoomList() {
		ArrayList<Room> roomList = new ArrayList<Room>();
		if (roomsMap != null && !roomsMap.isEmpty()) {
			roomList.addAll(roomsMap.values());
			Collections.sort(roomList);
			return roomList;
		}
		String sql = "SELECT * FROM rooms";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getRoomList conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer id = rs.getInt("id");
	            String roomId  = rs.getString("roomId");
	            String roomType = rs.getString("roomType");
	            String descp = rs.getString("descp");
	            String hid = rs.getString("hid");
	            Double price = rs.getDouble("price");
	            Double discount = rs.getDouble("discount");
	            
	            Room room = new Room(id, roomId, roomType, descp, hid, price, discount);
	            roomList.add(room);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		System.out.println("get roomList sorted from db");
		Collections.sort(roomList);
		return roomList;
	}

	/**
	 * Get room list sorted by city
	 */
	public static ArrayList<Room> getRoomListGroupByHotel() {
		ArrayList<Room> roomList = new ArrayList<Room>();

		String sql = "select count(roomType) numberOfRoomTypes, hid from rooms group by hid;";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("getRoomList conn: " + conn);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
	            String hid = rs.getString("hid");
	            int nRoomType = rs.getInt("numberOfRoomTypes");
	            // System.out.println("the room number is: ");
	            Room room = new Room(nRoomType, hid);
	            roomList.add(room);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		System.out.println("get roomList sorted from db");
		Collections.sort(roomList);
		return roomList;
	}	
	
	/**
	 * Get rooms of a hotel
	 */
	public static ArrayList<Room> getRoomsOfHotel(String hid) {
		ArrayList<Room> roomList = new ArrayList<Room>();
		if (roomsMap != null && !roomsMap.isEmpty()) {
			for (Room room : roomsMap.values()) {
				if (room.getHid().equals(hid)) {
					roomList.add(room);
				}
			}
			return roomList;
		}
		
		// get rooms from db.rooms table
		// no rooms in hashmap, then get room from db
		String sql = "SELECT * FROM rooms WHERE hid = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			conn = connUtil.getConnection();
			System.out.println("getRoomsOfHotel conn: " + conn);
			ps = conn.prepareStatement(sql);
			ps.setString(1, hid);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String roomId  = rs.getString("roomId");
				String roomType = rs.getString("roomType");
				String descp = rs.getString("descp");
				Double price = rs.getDouble("price");
				Double discount = rs.getDouble("discount");
				
				// set accessories, sold and avgRating
				Room room = new Room(id, roomId, roomType, descp, hid, price, discount);
				roomsMap.put(roomId, room);
				roomList.add(room);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		System.out.println("Get all rooms from db");
		return roomList;
	}
	
	
	/**
	 * Get a room object by using the roomId
	 */
	public static Room getRoomById(String roomId) {
		if (roomsMap != null && !roomsMap.isEmpty()) {
			return roomsMap.get(roomId);
		}
		Room room = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM rooms WHERE roomId = ?";
		
		try {
			conn = connUtil.getConnection();
			System.out.println("getRoomById conn: " + conn);
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, roomId);
			
			// get the title from db
			rs = ps.executeQuery();		
			if (rs.next()) {
				Integer id = rs.getInt("id");
	            String roomType = rs.getString("roomType");
	            String descp = rs.getString("descp");
	            String hid = rs.getString("hid");
	            Double price = rs.getDouble("price");
	            Double discount = rs.getDouble("discount");
	            
	            room = new Room(id, roomId, roomType, descp, hid, price, discount);
	            roomsMap.put(roomId, room);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return room;
	}
	
	
	
	
	/**
	 * Get a room's type by using the roomId
	 */
	public static String getTypeById(String roomId) {
		if (roomsMap != null && !roomsMap.isEmpty()) {
			return roomsMap.get(roomId).getRoomType();
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String type = "";
		String sql = "SELECT roomType FROM rooms WHERE roomId = ?";
		
		try {
			conn = connUtil.getConnection();
			System.out.println("getTypeById conn: " + conn);
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, roomId);
			
			// get the title from db
			rs = ps.executeQuery();		
			if (rs.next()) {
	            type = rs.getString("roomType");
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return type;
	}
	
	
	/**
	 * insert new rooms into db
	 */
	public static boolean insertRooms(ArrayList<Room> roomList) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "INSERT INTO rooms(roomId, roomType, descp, hid, price, discount) "
				+ "VALUES(?, ?, ?, ?, ?, ?)";
		
		try {
			conn = connUtil.getConnection();
			System.out.println("insertRooms conn: " + conn);
			
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			for (Room room : roomList) {
				ps.setString(1, room.getRoomId());
				ps.setString(2, room.getRoomType());
				ps.setString(3, room.getDescp());
				ps.setString(4, room.getHid());
				ps.setDouble(5, room.getPrice());
				ps.setDouble(6, room.getDiscount());
				
				// insert the hotel into db
				ps.executeUpdate();	
				rs = ps.getGeneratedKeys();
				if(rs.next()){
					room.setId(rs.getInt(1));
				}
				roomsMap.put(room.getRoomId(), room);
				System.out.println(room + ", roomId = " + room.getRoomId() + " inserted into db.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return true;
	}
	
	
	/**
	 * insert one room into db
	 */
	public static Integer insertRoom(Room room) {
		if (roomsMap.containsKey(room.getRoomId())) {
			return 0;
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = 0;
		String sql = "INSERT INTO rooms(roomId, roomType, descp, hid, price, discount) "
				+ "VALUES(?, ?, ?, ?, ?, ?)";
		
		try {
			conn = connUtil.getConnection();
			System.out.println("insertRooms conn: " + conn);
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, room.getRoomId());
			ps.setString(2, room.getRoomType());
			ps.setString(3, room.getDescp());
			ps.setString(4, room.getHid());
			ps.setDouble(5, room.getPrice());
			ps.setDouble(6, room.getDiscount());
			
			// insert the hotel into db
			ps.executeUpdate();	
			rs = ps.getGeneratedKeys();
			if(rs.next()){
				id = rs.getInt(1); 
				room.setId(id);
			}
			roomsMap.put(room.getRoomId(), room);
			System.out.println(room + ", roomId = " + room.getRoomId() + ", id = " + id + " inserted into db.");
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return id;
	}
	
	
	/**
	 * delete rooms of a hotel from db
	 */
	public static boolean deleteRooms(String hid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "DELETE FROM rooms WHERE hid = ? and id != 0";
		
		try {
			conn = connUtil.getConnection();
			System.out.println("deleteRooms conn: " + conn);
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, hid);;
			
			// delete the hotel from db and memory
			ps.executeUpdate();		
			System.out.println(hid + "'s removed.");
			roomsMap.remove(hid + "_ss");
			roomsMap.remove(hid + "_sd");
			roomsMap.remove(hid + "_ls");
			roomsMap.remove(hid + "_ld");
			roomsMap.remove(hid + "_f");
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
	}
	
	
	/**
	 * delete one room of a hotel from db
	 */
	public static boolean deleteOneRoom(String roomId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "DELETE FROM rooms WHERE roomId = ? and id != 0";
		
		try {
			conn = connUtil.getConnection();
			System.out.println("deleteRooms conn: " + conn);
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, roomId);;
			
			// delete the hotel from db and memory
			ps.executeUpdate();		
			System.out.println(roomId + "'s removed.");
			roomsMap.remove(roomId);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return true;
	}
	
	
	/**
	 * admin updates an hotel
	 */
	public static boolean adminUpdate(String roomId, String property, Object value) {
		if (roomId == null || roomId.length() == 0) {
			return false;
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "UPDATE rooms SET " + property.toLowerCase() + " = ? WHERE roomId = ? and id != 0";
		if (property.equals("RoomType")) {
			sql = "UPDATE rooms SET roomType = ?, roomId = ? WHERE roomId = ? and id != 0";
		}
		
		try {
			conn = connUtil.getConnection();
			System.out.println("RoomDAO adminUpdate conn: " + conn);
			ps = conn.prepareStatement(sql);
			
			if (property.equals("RoomType")) {
				ps.setString(1, (String)value);
				ps.setString(2, roomId.substring(0, 3) + (String)value);
				ps.setString(3, roomId);
			} else {
				ps.setObject(1, value);
				ps.setString(2, roomId);
			}
			
			Room room = roomsMap.get(roomId);
			String newRoomId = room.getHid() + "_" + value;
			if (roomsMap.containsKey(newRoomId)) {
				return false;
			}
			// insert the hotel into db
			ps.executeUpdate();		
			System.out.println(roomId + "'s " + property + " = " + value + " updated in db.");
			
			if (property.equals("RoomType")) {
				room.updateRoom(property, value);
				roomsMap.remove(roomId);
				room.setRoomId(newRoomId);
				roomsMap.put(newRoomId, room);
			}
			room.updateRoom(property, value);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return true;
	}

	
	
	/**
	 * Get a hotel's lowest price of rooms
	 */
	public static Double getLowestPriceOfHotel(String hid) {
		Double lowest = Double.MAX_VALUE;
		if (roomsMap != null && !roomsMap.isEmpty()) {
			for (Room room : roomsMap.values()) {
				if (room.getHid().equals(hid) && room.getDiscountedPrice() < lowest) {
					lowest = room.getPrice();
				}
			}
			return lowest;
		}
		
		// query from db if no result from memory
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT min(price * discount) FROM rooms WHERE hid = ?";
		
		try {
			conn = connUtil.getConnection();
			System.out.println("getLowestPriceOfHotel conn: " + conn);
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, hid);
			
			// get the title from db
			rs = ps.executeQuery();		
			if (rs.next()) {
				lowest = rs.getDouble("min(price * discount)");
			}
//			System.out.println("lowerst price: " + lowest);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			MySQLDataStoreUtilities.release(rs, ps);
		}
		return lowest;
	}
	
}
