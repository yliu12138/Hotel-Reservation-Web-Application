<%@page import="iit.*"%>
<%
	User user = (User)session.getAttribute("user");
	ShoppingCart sc = user.getCart();
	System.out.println("User " + user.getUsername() + " logged out.");
	
	// if the user's shopping cart is not empty, insert the cart into db
	if (sc != null && !sc.isEmpty()) {
		sc.clear();
	}
	
	session.removeAttribute("user");
	user.getOrdersMap().clear();
	user.setOrdersMap(null);
	/* user.getCart().clear();
	user.setCart(null); */
	
	System.out.println("user : " + session.getAttribute("user"));
	response.sendRedirect(request.getContextPath() + "/index.jsp");
%>