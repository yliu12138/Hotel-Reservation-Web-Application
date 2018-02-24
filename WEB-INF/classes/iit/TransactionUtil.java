package iit;
import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;


public class TransactionUtil implements Filter {
	
	private static boolean dbCreated = false;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Connection connection = null;
		try {
			// for debugging
//			MySQLDataStoreUtilities.loadSql("smartstore_orderItems.sql");
			
			if (!dbCreated) {
				// create mysql db and tables
				MySQLDataStoreUtilities.loadProperties(request.getServletContext().getRealPath("/") + "db.properties");
				MySQLDataStoreUtilities.createDB();
				MySQLDataStoreUtilities.createTables();
				
				// create mongoDB collection
    			// MongoDBDataStoreUtilities.createReviewCollection(request.getServletContext().getRealPath("/") + "review.xml");
				dbCreated = true;
			}
			connection = MySQLDataStoreUtilities.getConnection(request.getServletContext().getRealPath("/") + "db.properties");
			System.out.println("In filter, setAutoCommit false: " + connection);
			connection.setAutoCommit(false);
			ConnectionUtil.getInstance().bind(connection);
			
			chain.doFilter(request, response);
			
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				System.out.println("Transaction rolling back...");
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}	
			
			HttpServletResponse rep = (HttpServletResponse) response;
			HttpServletRequest req = (HttpServletRequest) request;
			rep.sendRedirect(req.getContextPath() + "/error.jsp");
		} finally {
			ConnectionUtil.getInstance().removeConnection();
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Program init...");
	}

	public void destroy() {
		MySQLDataStoreUtilities.releaseConnection();
		
		try {
		    AbandonedConnectionCleanupThread.checkedShutdown();
		} catch (Exception e) {
			System.err.println("SEVERE problem cleaning up: " + e.getMessage());
		}
		System.out.println("Program Destroyed. Connection released, drivers deregistered and thread cleaned.");
	}

}
