package iit;
import java.io.*;
import java.util.*;
import java.sql.Date;
import java.util.regex.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// simple validation: only checks whether all fields are filled, no complicated transaction.
		String username = request.getParameter("username");
		String cardNumber = request.getParameter("cardNumber");
		String cvv = request.getParameter("cvv");
		String billingAddress = request.getParameter("billingAddress");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zipCode = request.getParameter("zipCode");
		HttpSession session = request.getSession();
		
		String errors = validateFields(username, city, zipCode, cardNumber, cvv, billingAddress, state).toString();
		PrintWriter out = response.getWriter();
		
		// fail to pass empty check
		if (!errors.equals("")) {
			System.out.println(errors);
			out.println("<script language=\"javascript\">alert('" + errors + "')");
			out.println("window.location.href = \"./checkout.jsp\"");
			out.println("</script>");
			out.close();
			return;
		} 
		
		// successfully placed order
		System.out.println("Order placed successfully.");
		User user = (User)request.getSession().getAttribute("user");
		ShoppingCart sc = user.getCart();
		
		// update items' stocks and sold
//		synchronized (session) {
//			ItemDAO.updateItemStockSold(sc.getCartItems());
//			System.out.println("Order stock and sold updated.");
//		}
				
		
		// orderTime
		Calendar c = Calendar.getInstance();
		Date orderTime = new Date(c.getTime().getTime());
		
		// randomly generate an order confirmationId
		Random random = new Random(System.currentTimeMillis());
		String confirmationId = Math.abs(random.nextInt()) + "";	
		System.out.println("order confirmation: " + confirmationId);
		
		// create an order object
		Date checkinDate = sc.getCartRooms().get(0).getCheckinDate();
		Date checkoutDate = sc.getCartRooms().get(0).getCheckoutDate();
		Order order = new Order(null, confirmationId, username, orderTime, checkinDate, checkoutDate, null, 
				sc.getTotalCost(), city, zipCode, null, null);
		
		// output the "Order placed" page
//		out.println("<html><head><meta charset=\"UTF-8\"><title>Success</title></head>");
//		out.println("<body>");
//		out.println("<h2> Order successfully placed! Your confirmation id is: "+ confirmationId + "</h2><br>");
//		out.println("Please keep this number for your record.<br>");
//		request.setAttribute("confirmationId", confirmationId);
		
		// cancel deadline
		c.setTime(checkinDate);
		c.add(Calendar.DATE, -3);
		Date cancelDeadline = new Date(c.getTime().getTime());
		order.setCancelDeadline(cancelDeadline);
		
		// insert this order to db
		Integer id = 0;
		synchronized (session) {
			id = OrderDAO.insertOrder(order);
		}
		order.setId(id);
		System.out.println("New Order inserted into db: " + order);
		
		// get the orderRooms, orderRests from  cart and set the orderRooms, orderRests to this order
		ArrayList<OrderRoom> orderRooms = new ArrayList<OrderRoom>();
		ArrayList<OrderRest> orderRests = new ArrayList<OrderRest>();
		for(Object obj: sc.getCartObjects()){
			if (obj instanceof ShoppingCartRoom) {
				ShoppingCartRoom scr = (ShoppingCartRoom)obj;
				OrderRoom orderRoom = new OrderRoom();
				orderRoom.setRoomId(scr.getRoomId());
				orderRoom.setHid(scr.getHid());
				orderRoom.setOid(id);
				orderRoom.setCheckinDate(scr.getCheckinDate());
				orderRoom.setCheckoutDate(scr.getCheckoutDate());
				orderRoom.setQuantity(scr.getQuantity());
				orderRoom.setZipCode(zipCode);
				orderRoom.setState(state);
				orderRoom.setCity(city);
				orderRoom.setPrice(scr.getRoom().getPrice());
				orderRooms.add(orderRoom);
			} else {
				ShoppingCartRest scr = (ShoppingCartRest)obj;
				OrderRest orderRest = new OrderRest();
				orderRest.setRestId(scr.getRestId());
				orderRest.setOid(id);
				orderRest.setQuantity(scr.getQuantity());
				orderRest.setZipCode(zipCode);
				orderRest.setState(state);
				orderRest.setCity(city);
				orderRest.setPrice(scr.getRest().getPrice().doubleValue());
				orderRests.add(orderRest);
			}
		}
		
		// insert the orderItems into db
		synchronized (session) {
			OrderRoomDAO.insertOrderRooms(orderRooms);
			OrderRestDAO.insertOrderRests(orderRests);
		}
		order.setOrderRooms(orderRooms);
		order.setOrderRests(orderRests);
		
		// add this order to the current user
		user.addToOrders(order);
		
		// clear the shopping cart from memory and db
		sc.clear();
		
		request.setAttribute("order", order);
		request.getRequestDispatcher("/checkoutResult.jsp").forward(request, response);
	}
	
	private String validateFields(String username, String city, String zipCode, 
			String cardNumber, String cvv, String billingAddress, String state) {
		String errorInfo = "";
		if (username == null || username.trim().equals("")) {
			errorInfo += "Your name cannot be empty.\\n";
		}
		if (city == null || city.trim().equals("")) {
			errorInfo += "City cannot be empty.\\n";
		}
		if (zipCode == null || zipCode.trim().equals("")) {
			errorInfo += "Zip code cannot be empty.\\n";
		}
		if (cardNumber == null || cardNumber.trim().equals("")) {
			errorInfo += "Card number cannot be empty.\\n";
		}
		if (cvv == null || cvv.trim().equals("")) {
			errorInfo += "CVV cannot be empty.\\n";
		}
		if (state == null || state.trim().equals("")) {
			errorInfo += "State cannot be empty.\\n";
		}
		if (billingAddress == null || billingAddress.trim().equals("")) {
			errorInfo += "Billing address cannot be empty.\\n";
		}
		
		if (!isAllInteger(cvv) || !isAllInteger(cardNumber) || !isAllInteger(zipCode)) {
			errorInfo += "Zip code, CVV and Card number must be all numeric\\n";
		}
		return errorInfo;
	}
	
	
	/**
	 * Validate whether str is all numeric
	 */
	private boolean isAllInteger(String str) {    
	    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");    
	    return pattern.matcher(str).matches();    
	} 
}
