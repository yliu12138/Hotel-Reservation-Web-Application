package iit;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import javax.servlet.*;
import javax.servlet.http.*;


public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// simple validation: only checks whether all fields are filled, no complicated transaction.
		String idStr = request.getParameter("id");
		String hid = request.getParameter("hid");
		String username = request.getParameter("username");
		String ageStr = request.getParameter("age");
		String gender = request.getParameter("gender");
		String occupation = request.getParameter("occupation");
		String ratingStr = request.getParameter("rating");
		String reviewCity = request.getParameter("reviewCity");
		String reviewZipCode = request.getParameter("reviewZipCode");
		String reviewText = request.getParameter("reviewText");
		
		String errors = validateFields(hid, username, ageStr, gender, occupation, ratingStr, reviewCity, reviewZipCode, reviewText).toString();
		PrintWriter out = response.getWriter();
		
		// fail to pass empty check or integer check
		if (!errors.equals("")) {
			System.out.println(errors);
			out.println("<script language=\"javascript\">alert('" + errors + "')");
			out.println("window.location.href = \"./review.jsp?hid=" + hid + "\"");
			out.println("</script>");
			out.close();
			return;
		} 
		
		// review successfully submitted
		// convert age, rating to integers
		Integer age = Integer.parseInt(ageStr);
		Integer rating = Integer.parseInt(ratingStr);
		
		// set review time
		Date now = new Date();
		java.sql.Date reviewTime = new java.sql.Date(now.getTime());
		
		Integer id = null;
		System.out.println("idStr = " + idStr);
		if (idStr != null && !idStr.equals("")) {
			id = Integer.parseInt(idStr);
		}
		
		Review review = new Review(id, hid, username, age, gender, occupation, rating, reviewTime, reviewCity, reviewZipCode, reviewText);
		
		// insert/update the review into db
		synchronized (session) {
			ReviewDAO.insertOrUpdateReview(review);
			ReviewDAO.insertReview(review);
		}
		
		// update the avg rating 
		synchronized (session) {
			ReviewDAO.updateAvgRating(hid);
		}
		
		// jump back to hotelInfo.jsp
		response.sendRedirect(request.getContextPath() + "/hotelInfo.jsp?hid=" + hid);
	}
	
	
	private String validateFields(String hid, String username, String ageStr, String gender, String occupation, String ratingStr, String reviewCity, String reviewZipCode, String reviewText) {
		String errorInfo = "";
		if (hid == null || hid.trim().equals("")) {
			errorInfo += "ItemId cannot be empty.\\n";
		}
		if (username == null || username.trim().equals("")) {
			errorInfo += "UserId cannot be empty.\\n";
		}
		if (ageStr == null || ageStr.trim().equals("")) {
			errorInfo += "Age cannot be empty.\\n";
		}
		if (gender == null || gender.trim().equals("")) {
			errorInfo += "Gender cannot be empty.\\n";
		}
		if (occupation == null || occupation.trim().equals("")) {
			errorInfo += "Occupation cannot be empty.\\n";
		}
		if (reviewCity == null || reviewCity.trim().equals("")) {
			errorInfo += "Review city cannot be empty.\\n";
		}
		if (reviewZipCode == null || reviewZipCode.trim().equals("")) {
			errorInfo += "Review zip-code cannot be empty.\\n";
		}
		if (ratingStr == null || ratingStr.trim().equals("")) {
			errorInfo += "Rating cannot be empty.\\n";
		}
		if (reviewText == null || reviewText.trim().equals("")) {
			errorInfo += "Review text cannot be empty.\\n";
		}
		
		if (!isAllInteger(ageStr)) {
			errorInfo += "Age must be all numeric\\n";
		}
		if (!isAllInteger(reviewZipCode)) {
			errorInfo += "Review zip-code must be all numeric\\n";
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
	

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String methodName = request.getParameter("method");
//		System.out.println("calling method: " + methodName);
//		
//		try {
//			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
//			method.invoke(this, request, response);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
}
