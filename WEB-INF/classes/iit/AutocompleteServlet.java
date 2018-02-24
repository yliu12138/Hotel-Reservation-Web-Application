package iit;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.Gson;

public class AutocompleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String input = request.getParameter("term");
		System.out.println("AutocompleteServlet user input: " + input);
		HashMap<String, Hotel> hotelsMap = HotelDAO.getAllHotels();
		ArrayList<AutocompleteData> results = new ArrayList<AutocompleteData>();
		for (Hotel hotel: hotelsMap.values()) {
			String[] names = hotel.getName().split(" ");
			if (names.length > 1) {
				if (names[0].toLowerCase().startsWith(input.toLowerCase()) || names[1].toLowerCase().startsWith(input.toLowerCase())) {
					results.add(new AutocompleteData(hotel.getName(), "./hotelInfo.jsp?hid=" + hotel.getHid()));
				}
			} else {
				if (names[0].toLowerCase().startsWith(input.toLowerCase())) {
					results.add(new AutocompleteData(hotel.getName(), "./hotelInfo.jsp?hid=" + hotel.getHid()));
				}
			}
		}
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(results);
		System.out.println("jsonStr: " + jsonStr);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
	}

}
