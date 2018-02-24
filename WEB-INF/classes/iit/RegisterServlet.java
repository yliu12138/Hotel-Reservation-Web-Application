package iit;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;


public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String EMPTY = "Sorry, username or password cannot be empty.";
	private final String INVALID = "Sorry, the username is not valid for use";
	private final String UNCONFIRM = "Sorry, password is not confirmed";
	private final String EXISTS = "Sorry, username already exists.";
	private final String SUCCESS = "Registration succeeed!";
	
	private static HashMap<String, User> users = UserDAO.getAllUsers();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			System.out.println("calling method: " + methodName);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// get username and password
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		String passwordConfirm = request.getParameter("passowordConfirm").trim();
		
		// validate username and password
		String result = resultMessage(username, password, passwordConfirm);
		PrintWriter out = response.getWriter();
		out.println("<script>alert(\"" + result + "\")");
		if (!result.equals(SUCCESS)) {			
			out.println("window.location.href = \"./register.jsp\"");
			out.println("</script>");
			out.close();
			return;
		} 
		// successfully registered
		User user = new User(null, username, passwordConfirm, 0, null, null);
		
		// insert the new user into db
		Integer id = 0;
		synchronized (session) {
			id = UserDAO.insertUser(user);
		}
		user.setId(id);
		
		// put this user into users map
		users.put(username, user);
		
		System.out.println("New user (id: " + id + ") registered: " + user);
		
		System.out.println(users);
		out.println("window.location.href = \"./signin.jsp\"");
		out.println("</script>");
		
		out.close();
	}
	*/
	
	
	protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// get username and password
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		String passwordConfirm = request.getParameter("passowordConfirm").trim();
		
		// validate username and password
		String result = resultMessage(username, password, passwordConfirm);
		PrintWriter out = response.getWriter();
		out.println("<script>alert(\"" + result + "\")");
		if (!result.equals(SUCCESS)) {			
			out.println("window.location.href = \"./register.jsp\"");
			out.println("</script>");
			out.close();
			return;
		} 
		// successfully registered
		User user = new User(null, username, passwordConfirm, 0, null, null);
		
		// insert the new user into db
		Integer id = 0;
		synchronized (session) {
			id = UserDAO.insertUser(user);
		}
		user.setId(id);
		
		// put this user into users map
		users.put(username, user);
		
		System.out.println("New user (id: " + id + ") registered: " + user);
		
		System.out.println(users);
		out.println("window.location.href = \"./signin.jsp\"");
		out.println("</script>");
		
		out.close();
	}
	
	
	/**
	 * Ajax request for checking whether a user-input username already exists in db
	 */
	protected void isExists(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		User user = null;
		Boolean exists = true;
		String info = "";
		synchronized (session) {
			user = UserDAO.getUserByName(username);
		}
		
		if (user == null) {
			info = "Username ok to use";
			exists = false;
		} else {
			info = "Username already exists";
		}
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("exists", exists);
		result.put("info", info);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);
		System.out.println(jsonStr);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
	}
	
	
	private String resultMessage(String username, String password, String passwordConfirm){
		if (!isValid(username) || !isValid(password) || !isValid(passwordConfirm)) {
			return EMPTY;
		}
		if (username.equals("manager") || username.equals("retailer")) {
			return INVALID;
		}
		if (!password.equals(passwordConfirm)) {
			return UNCONFIRM;
		}
		if (users.containsKey(username)) {
			return EXISTS;
		}
		return SUCCESS;
	}
	
	
	/**
	 * validate string
	 */
	private Boolean isValid(String str) {
		if (str == null || str.trim().length() == 0) {
			return false;
		}
		return true;
	}
}
