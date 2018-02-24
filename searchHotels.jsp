<%@page import="iit.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Search Hotels</title>
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Pinyon+Script' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Quicksand:400,700' rel='stylesheet' type='text/css'>
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" href="css/jquery-ui.css" />
<link type="text/css" rel="stylesheet" href="css/JFGrid.css" />
<link type="text/css" rel="stylesheet" href="css/JFFormStyle-1.css" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="js/jquery.min.js"></script>
</head>
<body>
	<%!
		private boolean isCity(String location) {
			location = location.toLowerCase();
			for (int i = 0; i < location.length(); i++) {
				if (location.charAt(i) < 'a' && location.charAt(i) > 'z') {
					return false;
				}
			}
			return true;
		}
		
		private boolean isZipCode(String location) {
			for (int i = 0; i < location.length(); i++) {
				if (location.charAt(i) < '0' || location.charAt(i) > '9') {
					return false;
				}
			}
			return true;
		}
	%>

	<%
		String checkinDate = request.getParameter("checkinDate");
		String checkoutDate = request.getParameter("checkoutDate");
		String roomCount = request.getParameter("roomCount");
		String location = request.getParameter("location");
		System.out.println(checkinDate);
		System.out.println(checkoutDate);
		System.out.println(roomCount);
		System.out.println(location);
		
		if(location.equals("")) {
			out.println("<script>alert(\"Please input your destination or choose from major cities below.\")");
			out.println("window.location.href = \"./index.jsp\"");
			out.println("</script>");
			return;
		}
	
	
		// if input is zip-code
		ArrayList<Hotel> hotelList = new ArrayList<Hotel>();
		if (isZipCode(location)) {
			Integer zipCodeRange = Integer.parseInt(location) / 10000;
			hotelList = HotelDAO.getHotelsByZipCode(zipCodeRange);
		}
		else if (isCity(location)) {	// if input is city
			hotelList = HotelDAO.getHotelsByCity(location);
		}
		
		if(hotelList.isEmpty()) {
			out.println("<script>alert(\"Sorry, the location that you are looking for is unavailable.\")");
			out.println("window.location.href = \"./index.jsp\"");
			out.println("</script>");
			return;
		}
		
		User user = (User) session.getAttribute("user");
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
					if (user == null) {
					%>
						<li class="active"><a href="signin.jsp">SIGN IN</a></li>
					<%
					} else {
						ShoppingCart cart = user.getCart();
						if(user.getLevel() == 1) {
					%>
						<li class="scoll"><a href="analytics.jsp">ANALYTICS</a></li>
					<%
						}
					%>
						<li class="scroll"><a href="signout.jsp">HI, <%=user.getUsername()%>! SIGNOUT?</a></li>
						
						<%
						if(user.getLevel() == 2) {
						%>
						<li class="scroll"><a href="manage.jsp">MANAGEMENT</a></li>
						<%
						}
						%>
						<li class="scroll"><a href="cart.jsp">CART <%=(cart != null && !cart.isEmpty() ? "(" + cart.getRoomNumber() + ")" : "") %></a></li>
						<li class="scroll"><a href="reservations.jsp">RESERVATIONS</a></li>
					<%
					}
					%>
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
				<div class="clearfix"></div>
			</div>
		</div>
		<div class="banner">
			<div class="banner-info text-center">
				<h1><%=Constant.NAME %> near <%=location %></h1>
				<h2><%=checkinDate %> - <%=checkoutDate %></h2>
				<br>
				<span></span>
				<br>
			</div>
		</div>
	</div>

	<!---start-date-picker---->
	<script src="js/jquery-ui.js"></script>
	<script>
		$(function() {
			$("#datepicker,#datepicker1").datepicker();
		});
	</script>
	<!---/End-date-piker---->
	
	<script type="text/javascript" src="js/JFCore.js"></script>
	<script type="text/javascript" src="js/JFForms.js"></script>
	<!-- Set here the key for your domain in order to hide the watermark on the web server -->
	<script type="text/javascript">
		(function() {
			JC.init({
				domainKey : ''
			});
		})();
	</script>
	<div class="online_reservation">
		<div class="b_room">
			<div class="booking_room">
				<div class="reservation">
					<form action="./searchHotels.jsp" method="get">
						<ul>
							<li class="span1_of_1 left">
								<h5>Check-in</h5>
								<div class="book_date">
									<input class="date" id="datepicker" type="text"
										name="checkinDate" value="<%=checkinDate %>"
										onfocus="this.value = '';"
										onblur="if (this.value == '') {this.value = '11/08/2017';}">
								</div>
							</li>
							<li class="span1_of_1 left">
								<h5>Check-out</h5>
								<div class="book_date">
									<input class="date" id="datepicker1" type="text"
										name="checkoutDate" value="<%=checkoutDate %>"
										onfocus="this.value = '';"
										onblur="if (this.value == '') {this.value = '11/10/2017';}">
								</div>
							</li>
							<li class="span1_of_1">
								<h5>Room type</h5> <!----------start section_room----------->
								<div class="section_room">
									<select id="country" onchange="change_country(this.value)"
										name="roomCount" class="frm-field required">
										<option value="1">1 Room</option>
										<option value="2">2 Rooms</option>
										<option value="3">3 Rooms</option>
										<option value="4">4 Rooms</option>
									</select>
								</div>
							</li>
							<li class="span1_of_1">
								<h5>Destination</h5>
								<div class="zipCode">
									<input type="text" name="location" placeholder="City, Zip code">
								</div>
							</li>
							<li class="span1_of_3">
								<div class="date_btn">
									<input type="submit" value="Update Search" style="background-color: orange" />
								</div>
							</li>
							<div class="clearfix"></div>

						</ul>
					</form>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<br>
	<br>

	<div id="body" class="rooms text-center">
		<section id="content">
			<article>

				<%
					if(hotelList.isEmpty()) {
				%>
				<h3>No hotel available by your search</h3>
				<%
					} 
					else {
				%>

				<table style="border-collapse: separate; border-spacing: 30px 50px;">
					<tr>
						<td align="center">Picture</td>
						<td align="center">Hotel</td>
						<td align="center">Hotel Rating</td>
						<td align="center">Price from</td>
						<td align="center">Options</td>
					</tr>

						<%
							for(Hotel hotel: hotelList) {	
								String hid = hotel.getHid();
						%>
							<tr>
								<td align="center"><a href="./hotelInfo.jsp?hid=<%=hid %>"><img src="images/<%=hotel.getImage() %>.jpg" style="width: 300px; height: 200px"></a></td>
								<td align="center"><h3><a href="./hotelInfo.jsp?hid=<%=hid %>"><%=hotel.getName() %></a></h3><br><br><%=hotel.getAddress() %></td>
								<td align="center"><%=hotel.getAvgRating() %></td>
								<td align="center"><%=(RoomDAO.getLowestPriceOfHotel(hid) == Double.MAX_VALUE ? "" : "$" + RoomDAO.getLowestPriceOfHotel(hid)) %></td>		
								<td>
									<form action="./bookRooms.jsp">
										<!-- out.println("<input type=\"hidden\" name=\"method\" value=\"addToCart\">"); -->
										<!-- <input type="hidden" name="index" value="1"> -->
										<input type="hidden" name="hid" value="<%=hid %>">
										<input type="hidden" name="checkinDate" value="<%=checkinDate %>"> 
										<input type="hidden" name="checkoutDate" value="<%=checkoutDate %>"> 
										<input type="hidden" name="roomCount" value="<%=roomCount %>"> 
										<input type="submit" value="View Rooms" style="background-color: orange">
										<!-- <input type="submit" value="View prices" style="background-color: orange" /> -->
									</form>
								</td>
							</tr>
						<%
							}
						%>

				</table>
				<%
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