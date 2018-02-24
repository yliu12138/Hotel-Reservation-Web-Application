<%@page import="iit.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Search Hotels</title>
<link href='http://fonts.googleapis.com/css?family=Open+Sans:600italic,700italic,800italic,400,300,600,700,800'
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
					User user = (User)session.getAttribute("user");
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
	</div>
	<br><br>
	
	<%
		String hid = request.getParameter("hid");
		Hotel hotel = HotelDAO.getHotelById(hid);
	%>

	<div id="body" style="margin:50px">
		<section id="content">
			<article>
				<img src="images/<%=hotel.getImage() %>.jpg" style="width: 600px; height: 400px"><br><br>
				<h2><%=hotel.getName() %></h2><br><br>
				<h3>Hotel Overview:</h3> <br><%=hotel.getDescp() %><br><br>
				Address: <%=hotel.getAddress() %><br><br>
				Rating: <%=hotel.getAvgRating() %><br><br>
				
				<a href="#" onclick="javascript:history.back(-1);">Return</a>&nbsp;&nbsp;&nbsp;<br><br>
				
				<form action="./review.jsp" method="get">
					<input type="hidden" name= "hid" value="<%=hotel.getHid() %>">
					<input class= "submit-button" align="right" type= "submit" value= "Leave a review" style="background-color:orange">
				</form>
			</article>
			
			<br><br>			
			<article>
				<h3>User Reviews</h3><br>
				
				<% 
					ArrayList<Review> reviewList = ReviewDAO.getReviewsByHotelId(hid);
			 		for (Review review : reviewList) {
			 			String username = review.getUsername();
			 			out.println("<div>");
						out.println("Rate:  " + review.getRating() +"&nbsp;&nbsp;&nbsp;by&nbsp;&nbsp;" + username 
								+ "&nbsp;&nbsp on &nbsp;&nbsp;" + review.getFormattedDate());
						
						// if the current user gave the review, add an "edit review" button
						if (user != null && username.equals(user.getUsername())) {
							out.println("<form action=\"./review.jsp\" method=\"get\" style=\"display: inline;\">");
							out.println("<input type=\"hidden\" name=\"id\" value=\"" + review.getId() + "\">");
							out.println("<input type=\"hidden\" name=\"hid\" value=\"" + hid + "\">");
							out.println("<input type=\"hidden\" name=\"username\" value=\"" + username + "\">");
							out.println("<input type=\"hidden\" name=\"age\" value=\"" + review.getAge() + "\">");
							out.println("<input type=\"hidden\" name=\"gender\" value=\"" + review.getGender() + "\">");
							out.println("<input type=\"hidden\" name=\"occupation\" value=\"" + review.getOccupation() + "\">");
							out.println("<input type=\"hidden\" name=\"rating\" value=\"" + review.getRating()	 + "\">");
							out.println("<input type=\"hidden\" name=\"reviewCity\" value=\"" + review.getReviewCity()	 + "\">");
							out.println("<input type=\"hidden\" name=\"reviewZipCode\" value=\"" + review.getReviewZipCode()	 + "\">");
							out.println("<input type=\"hidden\" name=\"reviewText\" value=\"" + review.getReviewText()	 + "\">");
							out.println("<input class=\"submit-button\" align=\"right\" type=\"submit\" value=\"Edit review\" style=\"background-color:orange\">");
							out.println("</form>");
						}
						out.println("<ul>" + review.getReviewText() + "</ul>");
						out.println("</div><br><br>");
					}
			 		
			 		out.println("</article></section>");
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