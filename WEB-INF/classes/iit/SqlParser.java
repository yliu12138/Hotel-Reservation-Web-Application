package iit;
import java.io.*;
import java.sql.*;
import java.util.*;


public class SqlParser {
	
	public static void createTables(String ppsFile) {
		/*
		 * DROP TABLE IF EXISTS `products`;
			DROP TABLE IF EXISTS `orders`;
			DROP TABLE IF EXISTS `orderItems`;
			DROP TABLE IF EXISTS `shoppingCart`;
			DROP TABLE IF EXISTS `users`;
		 */
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
//			Properties pps = new Properties();
//			pps.load(new FileInputStream(ppsFile));
			Connection conn = MySQLDataStoreUtilities.getInitConnection();
			String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = ?";
			ps = conn.prepareStatement(sql);
//			ps.setString(1, pps.getProperty("DB_NAME"));
			System.out.println(ps);
			rs = ps.executeQuery();
			rs.last();
			if (rs.getRow() == 5) {
				// tables already exist
				System.out.println("Tables already exist");
				conn.close();
				return;
			}
			
			String[] sqls = {"smartstore_orderItems.sql", "smartstore_orders.sql", "smartstore_products.sql", "smartstore_shoppingCart.sql", "smartstore_users.sql"};
        	for (int i = 0; i < sqls.length; i++) {
        		conn = MySQLDataStoreUtilities.getInitConnection();
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
    private static List<String> loadSql(String sqlFile) throws Exception {
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
}
