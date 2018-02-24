package iit;
import java.io.*;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.Date;
import java.text.ParseException;

public class addOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addOrder(request, response);
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addOrder(request, response);
	}

	protected void addOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        
		PrintWriter out = response.getWriter();
		
		// past empty check
        String oid = request.getParameter("oid");
        String confirmationId = request.getParameter("confirmationId");
        String username = request.getParameter("username");
        String orderTime = request.getParameter("orderTime");
        System.out.println("ordertime is " + orderTime);
        String checkinDate = request.getParameter("checkinDate");
        String cancelDeadline = request.getParameter("cancelDeadline");
        String checkoutDate = request.getParameter("checkoutDate");
        String city = request.getParameter("city");
        String zipcode = request.getParameter("zipcode");
        String cost = request.getParameter("cost");

        int id = Integer.parseInt(oid);
        float price = Float.parseFloat(cost);
        try {
        Date ud1 = new SimpleDateFormat("yyyy-MM-dd").parse(orderTime);
        Date ud2 = new SimpleDateFormat("yyyy-MM-dd").parse(checkinDate);
        Date ud3 = new SimpleDateFormat("yyyy-MM-dd").parse(cancelDeadline);
        Date ud4 = new SimpleDateFormat("yyyy-MM-dd").parse(checkoutDate);

        java.sql.Date d1 = new java.sql.Date(ud1.getTime());
        java.sql.Date d2 = new java.sql.Date(ud2.getTime());
        java.sql.Date d3 = new java.sql.Date(ud3.getTime());
        java.sql.Date d4 = new java.sql.Date(ud4.getTime());
        User user = (User)request.getSession().getAttribute("user");
		
        Order order = new Order(id,confirmationId,username, d1, d2, d3, d4, price, zipcode, city);
        // OrderDAO.updateOrder(id, username, city, zipcode, price);
        OrderDAO.insertOrder(order);
        }  catch (ParseException e) {
			e.printStackTrace();
		}
        response.sendRedirect(request.getContextPath()+"/reservations.jsp");
		out.close();
	}
	
}
