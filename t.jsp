<%@page import="java.util.*"%>
<%@page import="iit.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Reservations</title>
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
							<li class="scroll"><a href="signout.jsp">HI, <%=user.getUsername()%> SIGNOUT?</a></li>
						<%
						if(user.getLevel() == 2) {
						%>
							<li class="scroll"><a href="manage.jsp">MANAGEMENT</a></li>
						<%
						}
						%>
						<li class="scroll"><a href="cart.jsp" id="cartRoom">CART <%=(cart != null && !cart.isEmpty() ? "(" + cart.getObjNumber() + ")" : "") %></a></li>
						<li class="active"><a href="reservations.jsp">RESERVATIONS</a></li>
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

	<div id="body" class="rooms text-center">
		<section id="content">
			<article style="margin-left:5%"><h2><%=(user.getLevel() == 1 ? "Manage" : "All" )%> Reservations</h2><br>
				<%
				String message = (String)request.getAttribute("cancelMessage");
				if (message != null && !message.trim().equals("")) {
					out.println("<h4><font color=\"red\">" + message + "</font></h4>");
				}
				
				%>
				<br>
			</article>
				
			<article style="margin-left:25%">
				<table style="border-collapse: separate; border-spacing: 20px 20px;">
					<tr>
						<%
						if(user != null && user.getLevel() == 1) {
						%>
						<td align="center">User</td>
						<%
						}
						%>
                        <td align="center">Time</td>
                        <td align="center">Room/Restaurant_ID</td>
                        <td align="center">Hotel</td>
						<td align="center">Cost</td>
						<td>&nbsp;</td>
					</tr>
					
					<%
					if(user != null && user.getLevel() == 1) {
						ArrayList<Order> orderList = new ArrayList<Order>(OrderDAO.getAllOrders().values());
				        Collections.sort(orderList);
				        
				        for (Order order : orderList) {
                                ArrayList<OrderRoom> orderRooms = new ArrayList<OrderRoom>();
                                orderRooms = OrderRoomDAO.getOrderRoomsByOrder(order);
                                for ( OrderRoom orderRoom : orderRooms){
                                        out.println("<tr>");
                                        out.println("<td align=\"center\">" + order.getUsername() + "</a></td>");
                                        out.println("<td align=\"center\">" + order.getFormattedOrderTime() + "</a></td>");
                                        
                                        Room room = RoomDAO.getRoomById(orderRoom.getRoomId());
                                        String roomType ="";
                                        if (room.getRoomType().equals("f")){
                                                roomType += "Family Room";
                                        } else if (room.getRoomType().equals("ld")){
                                                roomType += "Deluxe Double";
                                        } else if (room.getRoomType().equals("ls")){
                                                roomType += "Deluxe Single";
                                        } else if (room.getRoomType().equals("sd")){
                                                roomType += "Standard Double";
                                        } else if (room.getRoomType().equals("ss")){
                                                roomType += "Standard Single";
                                        }
                                                        
                                        
                                        out.println("<td align=\"center\">" + roomType + "</a></td>");
                                        
                                        String hname = HotelDAO.getNameById(orderRoom.getHid());
                                        out.println("<td align=\"center\">" + hname + "</a></td>");
                                        out.println("<td align=\"center\">$" + String.format("%.2f", orderRoom.getPrice()) + "</td>");
                                        out.println("<td>");
                                        out.println("<form action=\"./CancelOrderServlet\" method=\"post\">");
                                        out.println("<input type=\"hidden\" name=\"flag\" value=\"" + flag + "\">");
                                        out.println("<input type=\"hidden\" name=\"method\" value=\"managerCancelOrder\">");
                                        out.println("<input type=\"hidden\" name=\"oid\" value=\"" + order.getId() + "\">");
                                        out.println("<input class=\"submit-button\" type=\"submit\" value=\"Cancel\" style=\"background-color:orange\" "
                                                + "onclick='return confirm(\"Are you sure to remove this item?\")'>");
                                        out.println("</form>");
                                        out.println("</td>");
                                        
                                        out.println("</tr>");
                                }
                                ArrayList<OrderRest> orderRests = new ArrayList<OrderRest>();
                                orderRests = OrderRestDAO.getOrderRestsByOrder(order);
                                for ( OrderRest orderRest : orderRests){
                                        out.println("<tr>");
                                        out.println("<td align=\"center\">" + order.getUsername() + "</a></td>");
                                        out.println("<td align=\"center\">" + order.getFormattedOrderTime() + "</a></td>");
                                        Restaurant rest = RestaurantDAO.getRestaurantById(orderRest.getRestId());

                                        out.println("<td align=\"center\">" + rest.getName() + "</a></td>");
                                        out.println("<td align=\"center\"> - </a></td>");
                                        out.println("<td align=\"center\">$" + String.format("%.2f", orderRest.getPrice()) + "</td>");
                                        out.println("<td>");
                                        out.println("<form action=\"./CancelOrderServlet\" method=\"post\">");
                                        out.println("<input type=\"hidden\" name=\"flag\" value=\"" + flag + "\">");
                                        out.println("<input type=\"hidden\" name=\"method\" value=\"managerCancelOrder\">");
                                        out.println("<input type=\"hidden\" name=\"oid\" value=\"" + order.getId() + "\">");
                                        out.println("<input class=\"submit-button\" type=\"submit\" value=\"Cancel\" style=\"background-color:orange\" "
                                                + "onclick='return confirm(\"Are you sure to remove this item?\")'>");
                                        out.println("</form>");
                                        out.println("</td>");
                                        
                                        out.println("</tr>");
                                    } 							

				        }
					}
					
					if(user != null && user.getLevel() != 1 && user.getOrdersMap() != null && !user.isOrderEmpty()) {
						// sort by order's time
						ArrayList<Order> orderList = new ArrayList<Order>(user.getOrdersMap().values());
				        Collections.sort(orderList);
				        
				        for (Order order : orderList) {
						
							out.println("<tr>");
							
							
							Date cancelDeadline = order.getCancelDeadline();
							Date now = new Date(Calendar.getInstance().getTime().getTime());
							if (now.before(cancelDeadline)) {
                                ArrayList<OrderRoom> orderRooms = new ArrayList<OrderRoom>();
                                        orderRooms = OrderRoomDAO.getOrderRoomsByOrder(order);
                                        for ( OrderRoom orderRoom : orderRooms){
                                                out.println("<tr>");
                                                out.println("<td align=\"center\">" + order.getUsername() + "</a></td>");
                                                out.println("<td align=\"center\">" + order.getFormattedOrderTime() + "</a></td>");
                                                
                                                Room room = RoomDAO.getRoomById(orderRoom.getRoomId());
                                                String roomType ="";
                                                if (room.getRoomType().equals("f")){
                                                        roomType += "Family Room";
                                                } else if (room.getRoomType().equals("ld")){
                                                        roomType += "Deluxe Double";
                                                } else if (room.getRoomType().equals("ls")){
                                                        roomType += "Deluxe Single";
                                                } else if (room.getRoomType().equals("sd")){
                                                        roomType += "Standard Double";
                                                } else if (room.getRoomType().equals("ss")){
                                                        roomType += "Standard Single";
                                                }
                                                                
                                                
                                                out.println("<td align=\"center\">" + roomType + "</a></td>");
                                                
                                                String hname = HotelDAO.getNameById(orderRoom.getHid());
                                                out.println("<td align=\"center\">" + hname + "</a></td>");
                                                out.println("<td align=\"center\">$" + String.format("%.2f", orderRoom.getPrice()) + "</td>");
                                                out.println("<td>");
                                                out.println("<form action=\"./CancelOrderServlet\" method=\"post\">");
                                                out.println("<input type=\"hidden\" name=\"flag\" value=\"" + flag + "\">");
                                                out.println("<input type=\"hidden\" name=\"method\" value=\"managerCancelOrder\">");
                                                out.println("<input type=\"hidden\" name=\"oid\" value=\"" + order.getId() + "\">");
                                                out.println("<input class=\"submit-button\" type=\"submit\" value=\"Cancel\" style=\"background-color:orange\" "
                                                        + "onclick='return confirm(\"Are you sure to remove this item?\")'>");
                                                out.println("</form>");
                                                out.println("</td>");
                                                
                                                out.println("</tr>");
                                        }
                                        ArrayList<OrderRest> orderRests = new ArrayList<OrderRest>();
                                        orderRests = OrderRestDAO.getOrderRestsByOrder(order);
                                        for ( OrderRest orderRest : orderRests){
                                                out.println("<tr>");
                                                out.println("<td align=\"center\">" + order.getUsername() + "</a></td>");
                                                out.println("<td align=\"center\">" + order.getFormattedOrderTime() + "</a></td>");
                                                Restaurant rest = RestaurantDAO.getRestaurantById(orderRest.getRestId());
        
                                                out.println("<td align=\"center\">" + rest.getName() + "</a></td>");
                                                out.println("<td align=\"center\"> - </a></td>");
                                                out.println("<td align=\"center\">$" + String.format("%.2f", orderRest.getPrice()) + "</td>");
                                                out.println("<td>");
                                                out.println("<form action=\"./CancelOrderServlet\" method=\"post\">");
                                                out.println("<input type=\"hidden\" name=\"flag\" value=\"" + flag + "\">");
                                                out.println("<input type=\"hidden\" name=\"method\" value=\"managerCancelOrder\">");
                                                out.println("<input type=\"hidden\" name=\"oid\" value=\"" + order.getId() + "\">");
                                                out.println("<input class=\"submit-button\" type=\"submit\" value=\"Cancel\" style=\"background-color:orange\" "
                                                        + "onclick='return confirm(\"Are you sure to remove this item?\")'>");
                                                out.println("</form>");
                                                out.println("</td>");
                                                
                                                out.println("</tr>");
                                            } 							
                                    } 
							else {
								out.println("<td>Cancel deadline past.</td>");
							}
							out.println("</tr>");
						}
					}
					else {
						out.println("<tr><td colspan=\"6\">No more reservation found</td></tr>");
					} // end if
					
					%>
				</table>
			
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