package iit;
import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.Gson;


public class RoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse rep) throws ServletException, IOException {
		doPost(req, rep);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws ServletException, IOException {
		String methodName = req.getParameter("method");
		System.out.println(methodName + " is calling");
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, req, rep);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds a room to the cart
	 */
	protected void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String hid = request.getParameter("hid");
		String checkinDateStr = request.getParameter("checkinDate");
		String checkoutDateStr = request.getParameter("checkoutDate");
		String roomCount = request.getParameter("roomCount");
		Integer count = Integer.parseInt(roomCount);
	
		System.out.println("count: " + count);
		System.out.println("checkin Date: " + checkinDateStr);
		System.out.println("checkout Date: " + checkoutDateStr);
		
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/signin.jsp");
			return;
		}
		
		String sessionFlag = (String)session.getAttribute("flag");  
        String requestFlag = request.getParameter("flag"); 
        
//        if (!requestFlag.equals(sessionFlag)) {
//			response.sendRedirect(request.getContextPath() + "/bookRooms.jsp?hid=" + hid + "&checkinDate=" + checkinDateStr + "&checkoutDate=" + checkoutDateStr + "&roomCount=" + roomCount);
//			System.out.println("wrongly re-submission.");
//			return;
//		}
//        session.removeAttribute("flag");
		
        
		String roomId = request.getParameter("roomId");	// room id
		String rid = request.getParameter("rid");		// restaurant id
		
		ShoppingCart sc = user.getCart();
		if (sc == null) {
			sc = new ShoppingCart();
			user.setCart(sc);		// bind shopping cart to user
		}
		
		// add a room to  cart
		if (roomId != null) {
			if (!requestFlag.equals(sessionFlag)) {
				response.sendRedirect(request.getContextPath() + "/bookRooms.jsp?hid=" + hid + "&checkinDate=" + checkinDateStr + "&checkoutDate=" + checkoutDateStr + "&roomCount=" + roomCount);
				System.out.println("wrongly re-submission.");
				return;
			}
	        session.removeAttribute("flag");
	        
			System.out.println("roomId = " + roomId + " about to be added to cart.");
			// successfully adds a room to cart
			if (sc.addRoom(roomId, checkinDateStr, checkoutDateStr)) {
				System.out.println(roomId + " added to cart.");
				
				if (sc.getRoomNumber() < count) {
					request.getRequestDispatcher("/bookRooms.jsp?hid=" + hid + "&checkinDate=" + checkinDateStr + "&checkoutDate=" + checkoutDateStr + "&roomCount=" + roomCount).forward(request, response);
				} else {
					request.getRequestDispatcher("/restaurant.jsp?hid=" + hid + "&checkinDate=" + checkinDateStr + "&checkoutDate=" + checkoutDateStr + "&roomCount=" + roomCount).forward(request, response);
				}
				return;
			}
		} 
		else if (rid != null) {		// adds a restaurant to cart
			if (!requestFlag.equals(sessionFlag)) {
				response.sendRedirect(request.getContextPath() + "/restaurant.jsp?hid=" + hid + "&checkinDate=" + checkinDateStr + "&checkoutDate=" + checkoutDateStr + "&roomCount=" + roomCount);
				System.out.println("wrongly re-submission.");
				return;
			}
	        session.removeAttribute("flag");
	        
			System.out.println("rid = " + rid + " about to be added to cart.");
			if (sc.addRestaurant(rid)) {
				System.out.println(rid + " added to cart");
//				request.getRequestDispatcher("/cart.jsp?hid=" + hid + "&checkinDate=" + checkinDateStr + "&checkoutDate=" + checkoutDateStr + "&roomCount=" + roomCount).forward(request, response);
				
				request.setAttribute("rid", rid);
				request.getRequestDispatcher("/restaurant.jsp?hid=" + hid + "&checkinDate=" + checkinDateStr + "&checkoutDate=" + checkoutDateStr + "&roomCount=" + roomCount).forward(request, response);
				return;
			}
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h4>Error. The requested information does not exist.</h4><br>");
		out.println("<a href=\"./HomeServlet\">Back to home</a>");
		out.close();
	}
	
	
	/**
	 * Customer removes a room from the cart by clicking the "remove" link
	 */
	protected void removeCartRoom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roomId = request.getParameter("roomId");
		Room room = RoomDAO.getRoomById(roomId);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		if(room != null){
			ShoppingCart sc = user.getCart();
			
			// remove a room from the shopping cart
			synchronized (session) {
				sc.removeRoom(roomId);
			}
			
			System.out.println(roomId + " removed. Shopping cart has " + sc.getRoomNumber() + " rooms.");
			if (sc != null && !sc.isEmpty()) {
				request.getRequestDispatcher("/cart.jsp").forward(request, response);
				return;
			}
			System.out.println(user.getUsername() + "'s shopping cart is empty");
			response.sendRedirect(request.getContextPath() + "/cart.jsp");
		} 
		else{
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<h4>Error. The requested web page does not exist.</h4>");
			out.println("<a href=\"./index.jsp\">Back to home</a>");
			out.close();
		}
	}
	
	
	/**
	 * Customer removes a restaurant from the cart by clicking the "remove" link
	 */
	protected void removeCartRest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rid = request.getParameter("rid");
		Restaurant rest = RestaurantDAO.getRestaurantById(rid);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		if(rest != null){
			ShoppingCart sc = user.getCart();
			
			// remove a room from the shopping cart
			synchronized (session) {
				sc.removeRest(rid);
			}
			
			System.out.println(rid + " removed. Shopping cart has " + sc.getObjNumber() + " objects.");
			if (sc != null && !sc.isEmpty()) {
				request.getRequestDispatcher("/cart.jsp").forward(request, response);
				return;
			}
			System.out.println(user.getUsername() + "'s shopping cart is empty");
			response.sendRedirect(request.getContextPath() + "/cart.jsp");
		} 
		else{
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<h4>Error. The requested web page does not exist.</h4>");
			out.println("<a href=\"./index.jsp\">Back to home</a>");
			out.close();
		}
	}
	
	
	/**
	 * Clears the cart
	 */
	protected void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		System.out.println("User :" + user + " wants to clear shopping cart.");
		ShoppingCart sc = user.getCart();
		sc.clear();
		System.out.println("Shopping cart cleared.");
		
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}
	
	
	/**
	 * Updates the room numbers in the cart
	 */
	protected void updateCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String roomId = request.getParameter("roomId");
//		String rid = request.getParameter("rid");
		String id = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		User user = (User)request.getSession().getAttribute("user");
		ShoppingCart sc = user.getCart();
		PrintWriter out = response.getWriter();
//		String id = (roomId != null && !roomId.equals("") ? roomId : rid);
		
		int quantity = -1;
		try {
			quantity = Integer.parseInt(quantityStr);
			if (quantity < 0) {
				throw new Exception();
			} else if (quantity == 0) {
				removeCartRoom(request, response);
				return;
			}
		} catch (Exception e) {
			out.println("<script>alert(\"Your input is invalid.\")");
			out.println("window.location.href = \"./roomCart.jsp\"");
			out.println("</script>");
			return;
		}
		
		System.out.println("id: " + id + "'s quantity should be set to " + quantity);
		if (id != null && !id.equals("") && quantity > -1) {
			synchronized (request) {
//				sc.updateRoomQuantity(roomId, quantity);
				sc.updateObjectQuantity(id, quantity);
			}
			System.out.println("id: " + id + "'s quantity updated to " + quantity);
		}
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("roomNumber", sc.getRoomNumber());
		result.put("objNumber", sc.getObjNumber());
		result.put("totalCost", String.format("%.2f", sc.getTotalCost()));
		System.out.println(result);
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);
		System.out.println(jsonStr);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
	}
	
	protected void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String accountId = request.getParameter("cardNumber");
		
		StringBuffer errors = validateFormField(username, accountId);

		if(errors.toString().equals("")){
//			errors = validateItemStock(request);
		}
		
		if(!errors.toString().equals("")){
			request.setAttribute("errors", errors.toString());
			request.getRequestDispatcher("/CheckoutServlet").forward(request, response);
			return;
		}
	}
	
	protected void forwardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("/" + page ).forward(request, response);
	}
	
	protected void redirectPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		response.sendRedirect(request.getContextPath() + "/" + page);
	}
	
	/**
	 *  validate name and card number
	 */
	protected StringBuffer validateFormField(String username, String cardNumber) {
		StringBuffer errors = new StringBuffer("");
		
		if(username == null || username.trim().equals("")){
			errors.append("Your name cannot be empty<br>");
		}
		
		if(cardNumber == null || cardNumber.trim().equals("")){
			errors.append("Card number cannot be empty<br>");
		}
		return errors;
	}
}
