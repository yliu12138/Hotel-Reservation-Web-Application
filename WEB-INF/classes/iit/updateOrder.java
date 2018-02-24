package iit;
import java.io.*;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class updateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateOrder(request, response);
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateOrder(request, response);
	}

	protected void updateOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        
		PrintWriter out = response.getWriter();
		
		// past empty check
        String oid = request.getParameter("oid");
        String username = request.getParameter("username");
        String city = request.getParameter("city");
        String zipcode = request.getParameter("zipcode");
        String cost = request.getParameter("cost");

        int id = Integer.parseInt(oid);
        float price = Float.parseFloat(cost);
        User user = (User)request.getSession().getAttribute("user");
		

        OrderDAO.updateOrder(id, username, city, zipcode, price);


        response.sendRedirect(request.getContextPath()+"/reservations.jsp");
		out.close();
	}
	
}
