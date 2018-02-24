package iit;
import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.*;


public class AdminServlet extends HttpServlet {
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
	
	/**
	 * admin adds a new Hotel
	 */
	protected void adminAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// get parameters
		String hid = request.getParameter("hid");
		String name = request.getParameter("name");
		String descp = request.getParameter("descp");
		String address = request.getParameter("address");
		String cityZip = request.getParameter("city");
		String ss = request.getParameter("ss");
		String sd = request.getParameter("sd");
		String ls = request.getParameter("ls");
		String ld = request.getParameter("ld");
		String f = request.getParameter("f");
		PrintWriter out = response.getWriter();
		
		// validations
		if (hid == null || hid.trim().equals("") || name == null || name.trim().equals("") || descp == null || descp.trim().equals("") 
				|| address == null || address.trim().equals("") || cityZip == null || cityZip.trim().equals("")) {
			out.println("<script language=\"javascript\">alert('All fields must be filled.')");
			out.println("window.location.href = \"./addHotel.jsp\"");
			out.println("</script>");
			out.close();
			return;
		}
		
		if (ss.trim().equals("") && sd.trim().equals("") && ls.trim().equals("") && ld.trim().equals("") && f.trim().equals("")) {
			out.println("<script language=\"javascript\">alert('At least one room type must be priced.')");
			out.println("window.location.href = \"./addHotel.jsp\"");
			out.println("</script>");
			out.close();
			return;
		}
		
		// String converted to number
		try {
			// Create an Hotel object using the above fields
			String city = cityZip.split("_")[0];
			Integer zip = Integer.parseInt(cityZip.split("_")[1]);
			Hotel hotel = new Hotel(null, hid, name, descp, address, city, zip, 0F, hid.charAt(0) + "10", null);
			
			System.out.println(hotel + " to be added.");
			
			// insert the new Hotel into db
			synchronized (session) {
				// if already exists
				if(HotelDAO.adminAdd(hotel) == 0) {
					out.println("<script language=\"javascript\">alert('This Hotel id has been used or your input is invalid.')");
					out.println("window.location.href = \"./addHotel.jsp\"");
					out.println("</script>");
					out.close();
					return;
				}
			}
			
			// insert the new rooms into db
			String roomDesc = "";
			ArrayList<Room> roomList = new ArrayList<Room>();
			if (!ss.equals("")) {
				Double ssPrice = Double.parseDouble(ss);
				roomDesc = RoomDAO.getAllRooms().get(hid.charAt(0) + "1_ss").getDescp();
				Room ssRoom = new Room(null, hid + "_ss", "ss", roomDesc, hid, ssPrice, 1.0D);
				roomList.add(ssRoom);
			}
			if (!sd.equals("")) {
				Double sdPrice = Double.parseDouble(sd);
				roomDesc = RoomDAO.getAllRooms().get(hid.charAt(0) + "1_sd").getDescp();
				Room sdRoom = new Room(null, hid + "_sd", "sd", roomDesc, hid, sdPrice, 1.0D);
				roomList.add(sdRoom);
			}
			if (!ls.equals("")) {
				Double lsPrice = Double.parseDouble(ls);
				roomDesc = RoomDAO.getAllRooms().get(hid.charAt(0) + "1_ls").getDescp();
				Room lsRoom = new Room(null, hid + "_ls", "ls", roomDesc, hid, lsPrice, 1.0D);
				roomList.add(lsRoom);
			}
			if (!ld.equals("")) {
				Double ldPrice = Double.parseDouble(ld);
				roomDesc = RoomDAO.getAllRooms().get(hid.charAt(0) + "1_ld").getDescp();
				Room ldRoom = new Room(null, hid + "_ld", "ld", roomDesc, hid, ldPrice, 1.0D);
				roomList.add(ldRoom);
			}
			if (!f.equals("")) {
				Double fPrice = Double.parseDouble(f);
				roomDesc = RoomDAO.getAllRooms().get(hid.charAt(0) + "1_f").getDescp();
				Room fRoom = new Room(null, hid + "_f", "f", roomDesc, hid, fPrice, 1.0D);
				roomList.add(fRoom);
			}
			synchronized (session) {
				if (!RoomDAO.insertRooms(roomList)) {
					out.println("<script language=\"javascript\">alert('Your input is invalid.')");
					out.println("window.location.href = \"./addHotel.jsp\"");
					out.println("</script>");
					out.close();
					return;
				}
			}
			// hotel successfully inserted 
			System.out.println("New Hotel " + hid + " added.");
			System.out.println("New rooms added: " + roomList);
			response.sendRedirect(request.getContextPath() + "/manageHotels.jsp");
			
		} catch (Exception e) {
			out.println("<script language=\"javascript\">alert('Your input is invalid.')");
			out.println("window.location.href = \"./addHotel.jsp\"");
			out.println("</script>");
			out.close();
			return;
		}
	}
	
	/**
	 * admin adds a new room
	 */
	protected void adminAddRoom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// get parameters
		String hid = request.getParameter("hid");
		String roomType = request.getParameter("roomType");
		String descp = request.getParameter("descp");
		String priceStr = request.getParameter("price");
		String discountStr = request.getParameter("discount");
		PrintWriter out = response.getWriter();
		
		// validations
		if (hid == null || hid.trim().equals("") || roomType == null || roomType.trim().equals("") || descp == null || descp.trim().equals("") 
				|| priceStr == null || priceStr.trim().equals("") || discountStr == null || discountStr.trim().equals("")) {
			out.println("<script language=\"javascript\">alert('All fields must be filled.')");
			out.println("window.location.href = \"./addRoom.jsp\"");
			out.println("</script>");
			out.close();
			return;
		}
		
		// String converted to number
		try {
			Double price = Double.parseDouble(priceStr);
			Double discount = Double.parseDouble(discountStr);
			if (discount < 0 || discount > 1) {
				out.println("<script language=\"javascript\">alert('Discount must be between 0 and 1.0 numerically.')");
				out.println("window.location.href = \"./addRoom.jsp\"");
				out.println("</script>");
				out.close();
				return;
			}
			
			Room room = new Room(null, hid + "_" + roomType, roomType, descp, hid, price, discount);
			System.out.println(room + " to be inserted into db.");
			
			// insert the new Hotel into db
			synchronized (session) {
				// if already exists
				if(RoomDAO.insertRoom(room) == 0) {
					out.println("<script language=\"javascript\">alert('Your input is invalid.')");
					out.println("window.location.href = \"./addRoom.jsp\"");
					out.println("</script>");
					out.close();
					return;
				}
			}
			
			// room successfully inserted 
			System.out.println("New room added: " + room);
			response.sendRedirect(request.getContextPath() + "/manageRooms.jsp");
		} catch (Exception e) {
			out.println("<script language=\"javascript\">alert('Your input is invalid.')");
			out.println("window.location.href = \"./addRoom.jsp\"");
			out.println("</script>");
			out.close();
			return;
		}
	}
	
	
	/**
	 * admin deletes an Hotel
	 */
	protected void adminDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String hid = request.getParameter("hid");
		if(!HotelDAO.adminDelete(hid)) {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<h4>Error. The requested web page does not exist.</h4>");
			out.println("<a href=\"./HomeServlet\">Back to home</a>");
			out.close();
			return;
		}
		
		if (!RoomDAO.deleteRooms(hid)) {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<h4>Error. The requested web page does not exist.</h4>");
			out.println("<a href=\"./HomeServlet\">Back to home</a>");
			out.close();
			return;
		}
		System.out.println("Hotel " + hid + " deleted.");
		System.out.println("Hotel " + hid + "'s rooms deleted");
		response.sendRedirect(request.getContextPath() + "/manageHotels.jsp");
	}
	
	
	/**
	 * admin deletes a room
	 */
	protected void adminDeleteRoom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roomId = request.getParameter("roomId");
		if(!RoomDAO.deleteOneRoom(roomId)) {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<h4>Error. The requested web page does not exist.</h4>");
			out.println("<a href=\"./HomeServlet\">Back to home</a>");
			out.close();
			return;
		}
		System.out.println("Room " + roomId + "'s rooms deleted");
		response.sendRedirect(request.getContextPath() + "/manageRooms.jsp");
	}
	
	/**
	 * admin updates an Hotel's info
	 */
	protected void adminUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String hid = request.getParameter("hid");
		String property = request.getParameter("property");
		String value = request.getParameter("value");
		PrintWriter out = response.getWriter();
		System.out.println("admin update.");
		
		if(!HotelDAO.adminUpdate(hid, property, value)) {
			response.setContentType("text/html");
			out.println("<h4>Error. The requested web page does not exist.</h4>");
			out.println("<a href=\"./HomeServlet\">Back to home</a>");
			out.close();
			return;
		}
		System.out.println("Hotel " + hid + " " + property + " " + value + " edited.");
		response.sendRedirect(request.getContextPath() + "/manageHotels.jsp");
	}

	
	/**
	 * admin updates a room
	 */
	protected void adminUpdateRoom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roomId = request.getParameter("roomId");
		String property = request.getParameter("property");
		Object value = null;
		PrintWriter out = response.getWriter();
		System.out.println("admin update room.");
		
		try {
			if (property.equals("Discount")) {		// validate discount
				value = Double.parseDouble(request.getParameter("value"));
				if ((Double)value > 1.0 || (Double)value <= 0) {
					out.println("<script language=\"javascript\">alert('Discount must be between 0 and 1.0.')");
					out.println("window.location.href = \"./manageRooms.jsp\"");
					out.println("</script>");
					out.close();
					return;
				}
			} 
			else if (property.equals("Price")) {		// validate price
				value = Double.parseDouble(request.getParameter("value"));
				if ((Double)value <= 0) {
					out.println("<script language=\"javascript\">alert('Price must be over 0.')");
					out.println("window.location.href = \"./manageRooms.jsp\"");
					out.println("</script>");
					out.close();
					return;
				}
			}
			else if (property.equals("RoomType")) {
				value = request.getParameter("value");
				if (!((String)value).trim().equals("ss") && !((String)value).trim().equals("sd") && 
						!((String)value).trim().equals("ls") && !((String)value).trim().equals("ld") && !((String)value).trim().equals("f")) {
					out.println("<script language=\"javascript\">alert('Room type is invalid.')");
					out.println("window.location.href = \"./manageRooms.jsp\"");
					out.println("</script>");
					out.close();
					return;
				}
			} 
			else {
				value = request.getParameter("value");
			}
			
		} catch (Exception e) {
			out.println("<script language=\"javascript\">alert('Your input is invalid.')");
			out.println("window.location.href = \"./manageRooms.jsp\"");
			out.println("</script>");
			out.close();
			return;
		}
		
		if(!RoomDAO.adminUpdate(roomId, property, value)) {
//			response.setContentType("text/html");
//			out.println("<h4>Error. Failed to update.</h4>");
//			out.println("<a href=\"./HomeServlet\">Back to home</a>");
			out.println("<script language=\"javascript\">alert('The room type already exits for this hotel.')");
			out.println("window.location.href = \"./manageRooms.jsp\"");
			out.println("</script>");
			out.close();
			return;
		}
		System.out.println("Room " + roomId + " " + property + " " + value + " edited.");
		response.sendRedirect(request.getContextPath() + "/manageRooms.jsp");
	}
}
