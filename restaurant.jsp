<!DOCTYPE html>
<%@page import="iit.*"%>
<%@page import="java.util.*"%>
<html>
<head>
<title>Restaurant Coupon</title>
<link href='http://fonts.googleapis.com/css?family=Open+Sans:600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Pinyon+Script' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Quicksand:400,700' rel='stylesheet' type='text/css'>
<link href="css/bootstrap.css" rel='stylesheet' type='text/css'/>
<link href="css/style.css" rel="stylesheet" type="text/css" media="all"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="js/jquery.min.js"></script>
</head>
<body>

<%
	User user = (User)session.getAttribute("user");
	if (user == null) {
		response.sendRedirect(request.getContextPath() + "/signin.jsp");
		return;
	}
	ShoppingCart cart = user.getCart();
	String hid = request.getParameter("hid");
	
	// avoid erroneously re-submission
	double flag = Math.random();  
	session.setAttribute("flag", flag + ""); 
	
	String checkinDate = request.getParameter("checkinDate");
	String checkoutDate = request.getParameter("checkoutDate");
	String roomCount = request.getParameter("roomCount");
	Integer count = Integer.parseInt(roomCount);
	
%>
	
<!--header starts-->
<div class="header">
	 <div class="top-header">
		 <div class="container">
			 <div class="logo">
				 <a href="./HomeServlet"><img src="images/logo.png"/></a>
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
						
						<li class="scroll"><a href="signout.jsp">HI, <%=user.getUsername()%>! SIGNOUT?</a></li>
						<li class="scroll"><a href="cart.jsp">CART <%=(cart != null && !cart.isEmpty() ? "(" + cart.getObjNumber() + ")" : "")%></a></li>
						<li class="scroll"><a href="reservations.jsp">RESERVATIONS</a></li>
						<li class="scoll"><a href="trending.jsp">TRENDING</a></li>
						<li class="scoll"><a href="travel.jsp">TRAVEL SUGGESTION</a></li>						
						
						<li class="scroll"><a href="register.jsp">REGISTER</a></li>
					</ul>
					<script>
					$("span.menu").click(function(){
						$(".top-menu ul").slideToggle(200);
					});
				</script>
			 </div>
		  </div>
	  </div>
</div>


	<!-- Restaurant coupons -->
	<div class="rooms text-center">
		<div class="container">
			<h3>Discounted Buffet Restaurants</h3>
			
			<%
				String restId = (String)request.getAttribute("rid");
				if(restId != null && !restId.equals("")) {
			%>
					<h5><font color="red"><%=RestaurantDAO.getRestNameById(restId) %></font> 
					added to <a href="./cart.jsp?hid=<%=hid %>&checkinDate=<%=checkinDate %>&checkoutDate=<%=checkoutDate %>&roomCount=<%=roomCount %>">
					<font color="green">Cart</font></a>
					</h5>
			<%
				}
			%>
			
			<table style="border-collapse: separate; border-spacing: 30px 50px;">
				<tr>
					<td align="center">Picture</td>
					<td align="center">Name</td>
					<td align="center">Price per Person</td>
				</tr>

					<%
						for(Restaurant rest: RestaurantDAO.getRestaurantsOfCity(hid.substring(0, 1))) {	
							String rid = rest.getRid();
					%>
						<tr>
							<td align="center"><img src="images/<%=rid %>.jpg" style="width: 300px; height: 200px"></td>
							<td align="center"><%=rest.getName() %></td>
							<td align="center"><del><font color="red">$<%=rest.getPrice() + 8 %></font></del>&nbsp;<font color="green"><%=rest.getPrice() %></font><br><br>
							
								<form action="./RoomServlet" method="get">
									<!-- out.println("<input type=\"hidden\" name=\"method\" value=\"addToCart\">"); -->
									<input type="hidden" name="flag" value="<%=flag %>">
									<input type="hidden" name="method" value="addToCart">
									<input type="hidden" name="hid" value="<%=hid %>">
									<input type="hidden" name="checkinDate" value="<%=checkinDate %>">
									<input type="hidden" name="checkoutDate" value="<%=checkoutDate %>">
									<input type="hidden" name="rid" value="<%=rid %>">
									<input type="hidden" name="roomCount" value="<%=roomCount %>">
									<input type="submit" value="Book" style="background-color: orange">
								</form>
							</td>
						</tr>
					<%
						}
					%>
				
			</table>
			
		</div>
	</div>

	<!-- footer -->
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