<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="iit.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Manage Hotels</title>
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
		ShoppingCart cart = user.getCart();
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
						
						<li class="scroll"><a href="signout.jsp">HI, <%=user.getUsername()%>! SIGNOUT?</a></li>
						<li class="scroll"><a href="manage.jsp">MANAGEMENT</a></li>
						<li class="scroll"><a href="cart.jsp">CART <%=(cart != null && !cart.isEmpty() ? "(" + cart.getObjNumber() + ")" : "")%></a></li>
						<li class="scroll"><a href="reservations.jsp">RESERVATIONS</a></li>
						
						<li class="scroll"><a href="register.jsp">REGISTER</a></li>
						<li class="scoll"><a href="trending.jsp">TRENDING</a></li>
						<li class="scoll"><a href="travel.jsp">TRAVEL SUGGESTION</a></li>						
						
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
		ArrayList<Hotel> hotelList = HotelDAO.getHotelList();
	%>
	<div id="body" class="rooms text-center">
		<section id="content">
			<article>
				<h3>All hotels</h3>
				<a href="./addHotel.jsp"><font color="red">Add a Hotel</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" onclick="javascript:history.back(-1);">Return</a>
			</article>
			
			<article>
				<table style="border-collapse: separate; border-spacing: 30px 50px;">
					<tr>
						<td>Hotel Name</td>
						<td>Detail</td>
						<td>Address</td>
						<td>City</td>
						<td>Option</td>
					</tr>				
					
					<%
						for(Hotel hotel: hotelList) {
							String city = hotel.getCity();
					%>
							<tr>
								<td>
									<form action="AdminServlet" method="post">
										<input type="hidden" name="method" value="adminUpdate">
										<input type="hidden" name="hid" value="<%=hotel.getHid() %>">
										<input type="hidden" name="property" value="Name">
										<input type="text" size="25" name="value" value="<%=hotel.getName() %>">
										<input class="submit-button" type="submit" value="update" style="background-color: orange"/>
									</form>
								</td>
								
								<td>
									<form action="AdminServlet" method="post">
										<input type="hidden" name="method" value="adminUpdate">
										<input type="hidden" name="hid" value="<%=hotel.getHid() %>">
										<input type="hidden" name="property" value="Descp">
										<input type="text" size="25" name="value" value="<%=hotel.getDescp() %>">
										<input class="submit-button" type="submit" value="update" style="background-color: orange"/>
									</form>
								</td>
								
								<td>
									<form action="AdminServlet" method="post">
										<input type="hidden" name="method" value="adminUpdate">
										<input type="hidden" name="hid" value="<%=hotel.getHid() %>">
										<input type="hidden" name="property" value="Address">
										<input type="text" size="30" name="value" value="<%=hotel.getAddress() %>"><br>
										<input class="submit-button" type="submit" value="update" style="background-color: orange"/>
									</form>
								</td>
								
								<td>
									<form action="AdminServlet" method="post">
										<input type="hidden" name="method" value="adminUpdate">
										<input type="hidden" name="hid" value="<%=hotel.getHid() %>">
										<input type="hidden" name="property" value="City">
										<select name="value" style="width: 120px">
											<%
											out.println("<option value=\"Boston_0\" " + (city.equals("Boston") ? "selected" : "") + ">Boston</option>");
											out.println("<option value=\"Chicago_6\" " + (city.equals("Chicago") ? "selected" : "") + ">Chicago</option>");
											out.println("<option value=\"Detroit_5\" " + (city.equals("Detroit") ? "selected" : "") + ">Detroit</option>");
											out.println("<option value=\"New York_1\" " + (city.equals("New York") ? "selected" : "") + ">New York</option>");
											out.println("<option value=\"Houston_7\" " + (city.equals("Houston") ? "selected" : "") + ">Houston</option>");
											out.println("<option value=\"Los Angeles_9\" " + (city.equals("Los Angeles") ? "selected" : "") + ">Los Angeles</option>");
											out.println("<option value=\"Miami_3\" " + (city.equals("Miami") ? "selected" : "") + ">Miami</option>");
											out.println("<option value=\"Philadelphia_2\" " + (city.equals("Philadelphia") ? "selected" : "") + ">Philadelphia</option>");
											out.println("<option value=\"San Francisco_8\" " + (city.equals("San Francisco") ? "selected" : "") + ">San Francisco</option>");
											out.println("<option value=\"Seattle_4\" " + (city.equals("Seattle") ? "selected" : "") + ">Seattle</option>");
											%>
										</select><br>
										<input class="submit-button" type="submit" value="update" style="background-color: orange"/>
									</form>
								</td>
								
								<td align="center">
									<a href="./AdminServlet?method=adminDelete&hid=<%=hotel.getHid() %>" onclick='return confirm("Are you sure to remove this hotel?")'><font color="red">Delete</font></a>
								</td>
							</tr>
					<%
						}
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