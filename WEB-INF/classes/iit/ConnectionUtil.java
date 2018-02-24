package iit;
import java.sql.*;

public class ConnectionUtil {
	
	// singleton
	private ConnectionUtil(){}
	private static ConnectionUtil instance = new ConnectionUtil();
	public static ConnectionUtil getInstance() {
		return instance;
	}
	
	
	private ThreadLocal<Connection> connThreadLocal = new ThreadLocal<Connection>();
	
	// bind connection to local thread
	public void bind(Connection connection){
		connThreadLocal.set(connection);
	}
	
	public Connection getConnection(){
		return connThreadLocal.get();
	}
	
	public void removeConnection(){
		connThreadLocal.remove();
	}
}
