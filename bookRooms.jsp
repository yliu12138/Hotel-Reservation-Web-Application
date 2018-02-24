<%@page import="iit.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Book Rooms</title>
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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="js/jquery.min.js"></script>
</head>
<body>
	<%
		String hid = request.getParameter("hid");
		String checkinDate = request.getParameter("checkinDate");
		String checkoutDate = request.getParameter("checkoutDate");
		String roomCount = request.getParameter("roomCount");
		Integer count = Integer.parseInt(roomCount);
		int index = 0;
	
		System.out.println("bookRooms.jsp count: " + count);
		System.out.println("bookRooms.jsp checkin Date: " + checkinDate);
		System.out.println("bookRooms.jsp checkout Date: " + checkoutDate);
		
		ArrayList<Room> roomList = new ArrayList<Room>();
		synchronized(session) {
			roomList = RoomDAO.getRoomsOfHotel(hid);
		}
		
		if(roomList.isEmpty()) {
			out.println("<script>alert(\"Sorry, no room is available for now.\")");
			out.println("window.location.href = \"./index.jsp\"");
			out.println("</script>");
			return;
		}
		
		User user = (User) session.getAttribute("user");
		
		// avoid erroneously re-submission
		double flag = Math.random();  
		session.setAttribute("flag", flag + ""); 
	%>

	<!--header starts-->
	<div class="header">
		<div class="top-header">
			<div class="container">
				<div class="logo">
					<a href="./HomeServlet"><font size="6" color="yellow">BEST ANDY HOTELS</font></a>
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
						if(user.getLevel() == 1) {
					%>
						<li class="scoll"><a href="analytics.jsp">ANALYTICS</a></li>
					<%
						}
						
						ShoppingCart cart = user.getCart();
						if (cart == null || (cart != null && cart.isEmpty())) {
							index = 1;
						}
						else {
							index = cart.getRoomNumber() + 1;
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
						<li class="scroll"><a href="cart.jsp">CART <%=(cart != null && !cart.isEmpty() ? "(" + cart.getObjNumber() + ")" : "") %></a></li>
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
				<h1><%=HotelDAO.getNameById(hid) %></h1>
				<h2><%=checkinDate %> - <%=checkoutDate %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=roomCount %> Room<%=(count > 1 ? "s" : "") %></h2>
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
			
			$(".rButton").change(function(){
				switch($(this).val()) {
					case 'single':
						$(".s").show();
						$(".d").hide();
						$(".f").hide();
						break;
					case 'double':
						$(".s").hide();
						$(".d").show();
						$(".f").hide();
						break;
					case 'all':
						$(".s").show();
						$(".d").show();
						$(".f").show();
						break;
				}	
			});
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
									<!-- <select id="country" onchange="change_country(this.value)"
										name="roomType" class="frm-field required">
										<option value="ss">Standard Single Room</option>
										<option value="sd">Standard Double Room</option>
										<option value="ls">Deluxe Single Room</option>
										<option value="ld">Deluxe Double Room</option>
										<option value="f">Family Room</option>
									</select> -->
									
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
							<!-- <div class="clearfix"></div> -->

						</ul>
					</form>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<br><br>

<div id="container" class="rooms text-center">
	<aside class="sidebar" style="float: left; width: 12%; margin-top: 40px;">
		Filter:
		<input type="radio" class="rButton" name="roomType" value="single"> Single
		<input type="radio" class="rButton" name="roomType"  value="double"> Double
		<input type="radio" class="rButton" name="roomType"  value="all" checked="checked"> All
	</aside>
	<div class="clear"></div>
	<br><br>
	<%-- <div><h2>Room <%=index %></h2></div> --%>
		
		
	<div id="body">
		<section id="content">
			<article>

				<%
					if(roomList.isEmpty()) {
				%>
				<h3>No hotel available by your search</h3>
				<%
					} 
					else {
				%>
	
				<h3><font color="orange"><%=(index != 0 ? "Room " + index : "") %></font></h3>
				<table style="border-collapse: separate; border-spacing: 30px 50px;">
					<tr>
						<td align="center">Picture</td>
						<td align="center">Detail</td>
						<td align="center">Type</td>
						<td align="center">Price</td>
					</tr>

						<%
							for(Room room: roomList) {	
								String roomId = room.getRoomId();
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
						%>
							<tr class="<%=(roomType.equals("f") ? "f" : roomType.substring(1)) %>">
								<td align="center"><img src="images/<%=roomType %>.jpg" style="width: 300px; height: 200px"></td>
								<td align="center"><%=room.getDescp() %></td>
								<td align="center"><%=type %><br><br>
								
									<form action="./RoomServlet" method="get">
										<!-- out.println("<input type=\"hidden\" name=\"method\" value=\"addToCart\">"); -->
										<input type="hidden" name="flag" value="<%=flag %>">
										<input type="hidden" name="method" value="addToCart">
										<input type="hidden" name="hid" value="<%=hid %>">
										<input type="hidden" name="checkinDate" value="<%=checkinDate %>">
										<input type="hidden" name="checkoutDate" value="<%=checkoutDate %>">
										<input type="hidden" name="roomId" value="<%=roomId %>">
										<input type="hidden" name="roomCount" value="<%=roomCount %>">
										<input type="submit" value="Book" style="background-color: orange">
									</form>
								</td>
								
								<%
									if(room.getDiscount() < 1) {
								%>
									<td align="center"><del><font color="red">$<%=room.getPrice() %></font></del><br><font color="green">$<%=String.format("%.2f", room.getDiscountedPrice()) %></font></td>		
								<%
									} else {
								%>
									<td align="center"><font color="green">$<%=room.getPrice() %></font></td>		
								<%
									}
								%>
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
	
	<%-- 
	<!-- Review Carousel -->
	<%
		ArrayList<Review> reviewList = ReviewDAO.getReviewsByHotelId(hid);
	%>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<div id="myCarousel" class="carousel slide" data-ride="carousel">
	<ol class="carousel-indicators">
		<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
		<%
		for (int i = 0; i < reviewList.size(); i++) {
			out.println("<li data-target=\"#myCarousel\" data-slide-to=\"" + (i + 1) + "\"></li>");
		}
		out.println("</ol>");
		
		// slides wrapper
		out.println("<div class=\"carousel-inner\">");
		for(int i = 0; i < reviewList.size(); i++) {
			out.println("<div class=\"item" + (i == 0 ? " active" : "") + "\">");
			out.println("<h3>" + reviewList.get(i).getReviewText() + "</h3>");
			out.println("</div>");
		}
		out.println("</div>");

		// left and right arrow controls
		out.println("<a class=\"left carousel-control\" href=\"#myCarousel\" data-slide=\"prev\">");
		out.println("<span class=\"glyphicon glyphicon-chevron-left\"></span>");
		out.println("<span class=\"sr-only\">Previous</span>");
		out.println("</a>");
		
		out.println("<a class=\"right carousel-control\" href=\"#myCarousel\" data-slide=\"next\">");
		out.println("<span class=\"glyphicon glyphicon-chevron-right\"></span>");
		out.println("<span class=\"sr-only\">Next</span>");
		out.println("</a>");
		
		out.println("</div></article>");
//		out.println("</article></section>");
		%>
	 --%>
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