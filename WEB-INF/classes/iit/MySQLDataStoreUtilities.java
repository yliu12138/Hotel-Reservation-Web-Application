package iit;
import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * JDBC utilities class
 */
public class MySQLDataStoreUtilities {

	private static Connection conn = null;
	private static Properties pps = new Properties();
	private static FileInputStream fis = null;
	
	public static void loadProperties(String ppsFile) {
		try {
			fis = new FileInputStream(ppsFile);
			pps.load(fis);
			System.out.println("loaded db.properties");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Error on loading properties file.");
		}
	}
	
	
	public static void createDB() {
//		Properties pps = new Properties();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
//			pps.load(new FileInputStream(ppsFile));
		
			Class.forName(pps.getProperty("DB_DRIVER")).newInstance();
			conn = DriverManager.getConnection(pps.getProperty("DB_URL"), pps);
			System.out.println("Connecting to mysql...");
		
			// Execute a query of creating database
			stmt = conn.createStatement();
			String dbName = pps.getProperty("DB_NAME");
			String sql = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + dbName + "'";
			rs = stmt.executeQuery(sql);
			
			// if database already exists
			if (rs.next()) {
				System.out.println("DATABASE " + dbName + " already exits");
				return;
			}
			
			// database does not exist, then create it
			sql = "CREATE DATABASE IF NOT EXISTS " + dbName;
			System.out.println("Creating database... " + sql);
			stmt.executeUpdate(sql);
			System.out.println("Database " + dbName + " created successfully...");
		} catch(Exception e){
			e.printStackTrace();
			throw new DBException("Error on connection to database.");
		} finally{
			release(null, stmt, conn);
		}
	}
	
	//Access connection to database
	public static Connection getConnection(String ppsFile) {  
		if (conn != null) {
			System.out.println("Already connected to db.");
			return conn;
		}
		
//		Properties pps = new Properties();
		try {
			pps.load(new FileInputStream(ppsFile));
			pps.setProperty("useSSL", "false");
		
			Class.forName(pps.getProperty("DB_DRIVER")).newInstance();
			System.out.println("MySQLDataStoreUtils, connecting to db........");
			conn = DriverManager.getConnection(pps.getProperty("DB_URL") + pps.getProperty("DB_NAME"), pps);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Error on connection to database.");
		}
		return conn;
	}
	
	
	public static Connection getInitConnection() {  
		Connection conn = null;
//		Properties pps = new Properties();
		try {
//			pps.load(new FileInputStream(ppsFile));
			pps.setProperty("useSSL", "false");
			
			Class.forName(pps.getProperty("DB_DRIVER")).newInstance();
			System.out.println("Init connecting to db for creating tables........");
			conn = DriverManager.getConnection(pps.getProperty("DB_URL") + pps.getProperty("DB_NAME"), pps);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Error on connection to database.");
		} 
		return conn;
	}
	
 
	public static void createTables() {
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
//			pps.load(new FileInputStream(ppsFile));
			Connection conn = getInitConnection();
			
			// query whether the tables already exist
			String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, pps.getProperty("DB_NAME"));
			System.out.println(ps);
			rs = ps.executeQuery();
			if (rs.next()) {
				// tables already exist
				System.out.println("Tables already exist");
				conn.close();
				return;
			}
			
			String[] sqls = {"HotelFinder_hotels.sql", "HotelFinder_orderRestaurants.sql", "HotelFinder_orderRooms.sql", 
					"HotelFinder_orders.sql", "HotelFinder_restaurants.sql", "HotelFinder_reviews.sql", 
					"HotelFinder_rooms.sql", "HotelFinder_users.sql"};
        	for (int i = 0; i < sqls.length; i++) {
        		conn = getInitConnection();
        		execute(conn, sqls[i]);
        		conn.close();
			}
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

     
    /**
     * Read SQL
     */
    public static List<String> loadSql(String sqlFile) throws Exception {
        List<String> sqlList = new ArrayList<String>();
        try {
        	
            InputStream sqlFileIn = SqlParser.class.getResourceAsStream(sqlFile);
            StringBuffer sqlSb = new StringBuffer();
            byte[] buff = new byte[1024];
            int byteRead = 0;
            while ((byteRead = sqlFileIn.read(buff)) != -1) {
                sqlSb.append(new String(buff, 0, byteRead));
            }
 
            String[] sqlArr = sqlSb.toString()
                    .split("(\\s*\\r\\n)|(;\\s*\\n)");
            for (int i = 0; i < sqlArr.length; i++) {
                String sql = sqlArr[i].replaceAll("--.*", "").trim();
//                sql = sql.replaceAll("`", "'");
                if (!sql.equals("")) {
                    sqlList.add(sql);
                }
            }
//            sqlFileIn.close();
            
            System.out.println(sqlList);
            return sqlList;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
 
    /**
     * Execute the argument sqlFile
     */
    private static void execute(Connection conn, String sqlFile) throws Exception {
        Statement stmt = null;
        List<String> sqlList = loadSql(sqlFile);
        stmt = conn.createStatement();
        
        for (String sql : sqlList) {
            stmt.addBatch(sql);
        }
        
        int[] rows = stmt.executeBatch();
        String tableName = sqlFile.replace(".sql", "").replace("smartstore_", "");
        System.out.println("Row count:" + Arrays.toString(rows) + sqlFile + " parsed and " + tableName + " table created");
    }
    
    
	
	// release of connection to database
	public static void release(ResultSet rs, Statement statement)  {
		try {
			if(rs != null){
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Error on releasing the database.");
		}
		
		try {
			if(statement != null){
				statement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Error on releasing the database.");
		}
	}

	
	// release of connection to database
	private static void release(ResultSet rs, Statement statement, Connection conn)  {
		try {
			if(rs != null){
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Error on releasing the database.");
		}
		
		try {
			if(statement != null){
				statement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Error on releasing the database.");
		}
		
		try {
			if(conn != null){
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Error on releasing the database.");
		}
	}
	
	
	//release the connection to database
	public static void releaseConnection() {
		try {
			if(conn != null){
				System.out.println("Connection released.");
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Error on releasing the database.");
		}
		
		// deregister drivers
		Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("Deregistering jdbc driver: " + driver);
            } catch (SQLException e) {
            	e.printStackTrace();
            }

        }
	}
	
}
