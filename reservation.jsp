<%@page import="java.util.*"%>
<%@page import="iit.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Reservation</title>
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Pinyon+Script' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Quicksand:400,700' rel='stylesheet' type='text/css'>
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/styles_login.css" rel="stylesheet" type="text/css" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="js/jquery.min.js"></script>
</head>
<body>

	<%
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/signin.jsp");
			return;
		}
		ShoppingCart cart = user.getCart();
		
		// avoid erroneously re-submission
		double flag = Math.random();  
		session.setAttribute("flag", flag + ""); 
	%>

	<!--header starts-->
	<div class="header">
		<div class="top-header">
			<div class="container">
				<div class="logo">
					<a href="./HomeServlet"><font size="6" color="yellow"><%=Constant.NAME %> HOTELS</font></a>
					<div class="clearfix"></div>
				</div>
				<span class="menu"> </span>
				<div class="m-clear"></div>
				<div class="top-menu">
					<ul>
						<li class="scroll"><a href="./HomeServlet">START</a></li>
					<%
						if(user.getLevel() == 1) {
					%>
							<li class="scoll"><a href="analytics.jsp">ANALYTICS</a></li>
						<%
						}
						%>
						<li class="scroll"><a href="signout.jsp">HI, <%=user.getUsername()%>SIGNOUT?</a></li>
						<li class="active"><a href="cart.jsp" id="cartRoom">CART <%=(cart != null && !cart.isEmpty() ? "(" + cart.getObjNumber() + ")" : "") %></a></li>
						<li class="scroll"><a href="reservations.jsp">RESERVATIONS</a></li>
						<li class="scoll"><a href="trending.jsp">TRENDING</a></li>
						<li class="scoll"><a href="travel.jsp">TRAVEL SUGGESTION</a></li>						
						
						<li class="scroll"><a href="register.jsp">REGISTER</a></li>
						<!-- <li class="scroll"><a href="restaurant.jsp">RESTAURANT</a></li> -->
					</ul>
					<script>
						$("span.menu").click(function() {
							$(".top-menu ul").slideToggle(200);
						});
					</script>
				</div>
			</div>
		</div>
	</div>

	<%
		Integer oid = Integer.parseInt(request.getParameter("oid"));
		Order order = user.getOrdersMap().get(oid);
		System.out.println("order: " + order);
	%>

	<div id="body" class="rooms text-center">
		<section id="content">
			<article style="margin-left:5%"><h2>Your Reservation &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Time: <%=order.getFormattedOrderTime() %></h2></article>
				
			<article style="margin-left:5%">
				
			<%
				if(user.getOrdersMap() != null && !user.isOrderEmpty()) {
					out.println("<table frame='void' style=\"border-collapse:separate; border-spacing:25px 45px;\">");
					out.println("<tr>");
					out.println("<td align=\"center\">Hotel</td>");
					out.println("<td align=\"center\">Picture</td>");
					out.println("<td align=\"center\">Name</td>");
					out.println("<td align=\"center\">Check in</td>");
					out.println("<td align=\"center\">Check out</td>");
					out.println("<td align=\"center\">Unit Price</td>");
					out.println("<td align=\"center\">Quantity</td>");
					out.println("</tr>");
					
					if (order.getOrderRooms() == null || (order.getOrderRooms() != null && order.getOrderRooms().isEmpty())) {
						order.setOrderRooms(OrderRoomDAO.getOrderRoomsByOrder(order));
						order.setOrderRests(OrderRestDAO.getOrderRestsByOrder(order));
					}
					
					for (OrderRoom orderRoom : order.getOrderRooms()) {
						String roomId = orderRoom.getRoomId();
						Room room = RoomDAO.getRoomById(roomId);
						String roomType = room.getRoomType();
						String type = "";
						if (roomType.equals("ss")) {
							type = "Standard Single: 1 King Bed";
						} else if (roomType.equals("sd")) {
							type = "Standard Double: 2 Queen Beds";
						} else if (roomType.equals("ls")) {
							type = "Deluxe Single: 1 King Bed";
						} else if (roomType.equals("ld")) {
							type = "Deluxe Double: 2 Queen Beds";
						} else if (roomType.equals("f")) {
							type = "Family Room";
						}
						String hid = orderRoom.getHid();
						
						out.println("<tr>");
						out.println("<td><a href=\"./hotelInfo.jsp?hid=" + hid + "\">" + HotelDAO.getNameById(hid) + "</a></td>");
						out.println("<td><img src=\"images/" + roomType + ".jpg\" style=\"width:128px;height:128px;\"></a></td>");
						out.println("<td>" + type + "</td>");
						out.println("<td>" + orderRoom.getFormattedCheckinDate() + "</td>");
						out.println("<td>" + orderRoom.getFormattedCheckoutDate() + "</td>");
						out.println("<td align=\"center\"><font color=\"green\">$" + String.format("%.2f", orderRoom.getPrice()) + "</font></td>");
						out.println("<td align=\"center\">" + orderRoom.getQuantity() + "</td>");
						out.println("</tr>");
					}
					
					for (OrderRest orderRest: order.getOrderRests()) {
						String restId = orderRest.getRestId();
						Restaurant rest = RestaurantDAO.getRestaurantById(restId);
						
						out.println("<tr>");
						out.println("<td></td>");
						out.println("<td><img src=\"images/" + restId + ".jpg\" style=\"width:128px;height:128px;\"></a></td>");
						out.println("<td>" + rest.getName() + "</td>");
						out.println("<td></td>");
						out.println("<td></td>");
						out.println("<td align=\"center\"><font color=\"green\">$" + String.format("%.2f", rest.getPrice()) + "</font></td>");
						out.println("<td align=\"center\">" + orderRest.getQuantity() + "</td>");
						out.println("</tr>");
					}
					out.println("<tr></tr>");
					out.println("<tr><td></td>");
					out.println("<td>Order total cost: $" + String.format("%.2f", order.getCost()) + "</td>");
					out.println("<td><a href=\"./reservations.jsp\">Back to Reservations</a></td>");
					out.println("<td><a href=\"./HomeServlet\">Continue Booking</a></td></tr>");
					out.println("</table>");
				}
			%>
			</article>
			
		</section>
	</div>
	
					
	<div class="fotter-info">
		<div class="container">
			<div class="hotel-info">
				<h4>Get to Know Us</h4>
				<p>
					<a href="#" style="color: rgba(237, 237, 237, 0.87)">Career</a>
				</p>
				<p>
					<a href="#" style="color: rgba(237, 237, 237, 0.87)">About</a>
				</p>
			</div>
			<div class=hotel-info>
				<h4>Apps</h4>
				<p>
					<a href="#" style="color: rgba(237, 237, 237, 0.87)">iOS</a>
				</p>
				<p>
					<a href="#" style="color: rgba(237, 237, 237, 0.87)">Android</a>
				</p>
			</div>
			<div class=hotel-info>
				<h4>Let Us Help You</h4>
				<p>
					<a href="#" style="color: rgba(237, 237, 237, 0.87)">Help</a>
				</p>
				<p>
					<a href="#" style="color: rgba(237, 237, 237, 0.87)">Contact Us</a>
				</p>
			</div>
			<div class="clearfix"></div>
		</div>
		<br>
		<h6>
			<p>CSP 584 - Project</p>
		</h6>
	</div>

</body>
</html>