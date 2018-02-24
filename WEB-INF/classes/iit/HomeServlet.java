package iit;
import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static HashMap<String, Hotel> hotels;
	private static HashMap<String, Room> rooms;
	private static HashMap<String, User> users;
	private static HashMap<Integer, Review> reviewsMap;
	private static HashMap<String, Restaurant> restaurantsMap;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (hotels == null) {
			hotels = HotelDAO.getAllHotels();
//			System.out.println("db.hotels size: " + hotels.size());
		}

		if (users == null) {
			users = UserDAO.getAllUsers();
		}
		
		if (rooms == null) {
			rooms = RoomDAO.getAllRooms();
		}
		
		if (reviewsMap == null) {
			reviewsMap = ReviewDAO.getAllReviews();
		}
		
		if (restaurantsMap == null) {
			restaurantsMap = RestaurantDAO.getAllRestaurants();
		}
		
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
}
