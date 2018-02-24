package iit;
import java.io.*;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class CancelOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		System.out.println("calling method: " + methodName);
		
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	protected void userCancelOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		String sessionFlag = (String)session.getAttribute("flag");  
        String requestFlag = request.getParameter("flag"); 
        
        if (!requestFlag.equals(sessionFlag)) {
			response.sendRedirect(request.getContextPath() + "/reservations.jsp");
			System.out.println("wrongly re-submission.");
			return;
		}
        session.removeAttribute("flag");
        
		PrintWriter out = response.getWriter();
		String inputId = request.getParameter("inputId");
		if (inputId == null) {
			response.setContentType("text/html");
			out.println("<h4>Error. The requested information does not exist.</h4><br>");
			out.println("<a href=\"./HomeServlet\">Back to home</a>");
			out.close();
			return;
		}
		
		if (inputId.trim().equals("")) {
			System.out.println("Error. Confirmation ID cannot be empty.");
			out.println("<script>alert(\"Incorrect confirmation id.\")");
			out.println("window.location.href = \"./reservations.jsp\"");
			out.println("</script>");
			out.close();
			return;
		}
		
		// past empty check
		Integer oid = Integer.parseInt(request.getParameter("oid"));		// order's Id
		User user = (User)request.getSession().getAttribute("user");
		String confirmationId = user.getOrdersMap().get(oid).getConfirmationId();	// order's confirmationId
		if (!confirmationId.equals(inputId)) {
			out.println("<script>alert(\"Incorrect confirmation id.\")");
			out.println("window.location.href = \"./reservations.jsp\"");
			out.println("</script>");
			out.close();
			return;
		}
		
		/** If code comes here, order successfully cancelled */
		
		// remove an order and all its orderItems from db
		synchronized (session) {
			OrderDAO.removeOrderById(oid);
			OrderRoomDAO.removeOrderRooms(oid);
			OrderRestDAO.removeOrderRests(oid);
		}
		
		user.removeAnOrder(oid);
		System.out.println("Order cancelled");
		
		String message = "Order Cancelled";
		request.setAttribute("cancelMessage", message);
		request.getRequestDispatcher("/reservations.jsp").forward(request, response);
		out.close();
	}
	
	
	protected void managerCancelOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		String sessionFlag = (String)session.getAttribute("flag");  
        String requestFlag = request.getParameter("flag"); 
        
        if (!requestFlag.equals(sessionFlag)) {
			response.sendRedirect(request.getContextPath() + "/reservations.jsp");
			System.out.println("wrongly re-submission.");
			return;
		}
        session.removeAttribute("flag");
        
		PrintWriter out = response.getWriter();
		
		// past empty check
		Integer oid = Integer.parseInt(request.getParameter("oid"));		// order's Id
		
		// remove an order and all its orderItems from db
		synchronized (session) {
			OrderDAO.removeOrderById(oid);
			OrderRoomDAO.removeOrderRooms(oid);
			OrderRestDAO.removeOrderRests(oid);
		}
		System.out.println("Order cancelled");
		
		request.getRequestDispatcher("/reservations.jsp").forward(request, response);
		out.close();
	}
}
