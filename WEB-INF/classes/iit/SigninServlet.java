package iit;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String EMPTY = "Sorry, username or password cannot be empty.";
	private final String SUCCESS = "Login succeeed!";
	private final String FAILURE = "Login failed. Please check your username and password.";
	
	private static HashMap<String, User> users = null;
	
	public void init() throws ServletException {
		super.init();
		if (users != null && !users.isEmpty()) {
			return;
		}
		users = UserDAO.getAllUsers();		// get users' info from db or hashmap
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// get username and password
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		PrintWriter out = response.getWriter();
		
		if (session.getAttribute("user") != null) {
			out.println("<script>alert(\"Already signed in. Please log out first.\")");
			out.println("window.location.href = \"./HomeServlet\"");
			out.println("</script>");
			return;
		}
		
		String result = validateSignin(username, password);
		System.out.println(result);
		out.println("<script>alert(\"" + result + "\")");
		// validate username and password
		if (!result.equals(SUCCESS)) {			
			out.println("window.location.href = \"./signin.jsp\"");
			out.println("</script>");
			System.out.println("Failed to sign in.");
			out.close();
			return;
		} 
		
		// If code comes here, sign-in is successful
		User user = users.get(username);
		System.out.println(user + " signed in");
		
		// get the user's orders from db and put into user's ordersHashMap
		HashMap<Integer, Order> ordersMap = new HashMap<Integer, Order>();
		synchronized (session) {
			ordersMap = OrderDAO.getOrdersByUser(user);
		}
		user.setOrdersMap(ordersMap);
		
		
		// get the user's shopping cart from db and set to user's cart
		ShoppingCart sc = new ShoppingCart();
		user.setCart(sc);
		
		// put SIGN-INed user into session
		session.setAttribute("user", user);
		System.out.println(username + " into session and logged in. ");
		
		out.println("window.location.href = \"./HomeServlet\"");
		out.println("</script>");
		out.close();
	}

	/**
	 * Validate the login
	 */
	private String validateSignin(String username, String password){
		if (!isValid(username) || !isValid(password)) {
			return EMPTY;
		}
		
		if (users.containsKey(username)) {
			if (users.get(username).getPassword().equals(password)) {
				return SUCCESS;
			}
		}
		return FAILURE;
	}
	
	private Boolean isValid(String str) {
		if (str == null || str.trim().length() == 0) {
			return false;
		}
		return true;
	}

}
