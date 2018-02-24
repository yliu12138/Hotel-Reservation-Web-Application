<%@page import="java.util.*"%>
<%@page import="iit.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Review</title>
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
						<li class="scroll"><a href="cart.jsp">CART <%=(cart != null && !cart.isEmpty() ? "(" + cart.getRoomNumber() + ")" : "") %></a></li>
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
	<br>
	
	<%
		String id = request.getParameter("id");
	
		// Add a New hotel Review form
		String hid = request.getParameter("hid");
		Hotel hotel = HotelDAO.getHotelById(hid);
	%>
	<div id="body">
		<section id="content">
			<article class="expanded" style="margin-left: 10%"><h2><%=(id != null && !id.equals("") ? "Edit my Review" : "Submit a Product Review") %></h2></article>
			<br>
			<article class="expanded" style="margin-left: 10%">
				<form action="./ReviewServlet" method="post">
				<% 
				if (id != null) {
					System.out.println("Editting a review, id = " + id);
				%>
					<input type="hidden" name="id" value="<%=id %>">
				<%
				}
				%>
					<input type="hidden" name="hid" value="<%=hid  %>">
					Hotel Name: <input type="text" size="40" readonly="readonly" name="name" value="<%=hotel.getName() %>"/><br><br>
					Hotel Address: <input type="text" size="50" readonly="readonly" name="address" value="<%=hotel.getAddress() %>"/><br><br>
					City Area: <input type="text" size="10" readonly="readonly" name="city" value="<%=hotel.getCity() %>"/><br><br>
					UserID: <input type="text" readonly="readonly" name="username" value="<%=user.getUsername() %>"/><br><br>
		
				<% 
					// inputs
					String age = request.getParameter("age");
					String gender = request.getParameter("gender");
					String occupation = request.getParameter("occupation");
					String rating = request.getParameter("rating");
					String reviewCity = request.getParameter("reviewCity");
					String reviewZipCode = request.getParameter("reviewZipCode");
					String reviewText = request.getParameter("reviewText");
					
					if (age != null && !age.equals("")) {
						out.println("UserAge: <input type=\"text\" name=\"age\" value=\"" + age + "\"/><br><br>");
					} else {
						out.println("UserAge: <input type=\"text\" name=\"age\" placeholder=\"age\"/><br><br>");
					}
					
					out.println("UserGender: ");
					out.println("<input type=\"radio\" name=\"gender\" value=\"m\" " + (gender != null && gender.equals("m") ? "checked" : "") + "> Male");
					out.println("<input type=\"radio\" name=\"gender\" value=\"f\" " + (gender != null && gender.equals("f") ? "checked" : "") + "> Female<br><br>");
					
					if (occupation != null && !occupation.equals("")) {
						out.println("UserOccupation: <input type=\"text\" name=\"occupation\" value=\"" + occupation + "\"/><br><br>");
					} else {
						out.println("UserOccupation: <input type=\"text\" name=\"occupation\" placeholder=\"occupation\"/><br><br>");
					}
					
					// rating
					out.println("ReviewRating: ");
					out.println("<input id=\"rating5\" type=\"radio\" name=\"rating\" value=\"5\" " + (rating != null && rating.equals("5") ? "checked" : "") + "> 5");
					out.println("<input id=\"rating4\" type=\"radio\" name=\"rating\" value=\"4\" " + (rating != null && rating.equals("4") ? "checked" : "") + "> 4");
					out.println("<input id=\"rating3\" type=\"radio\" name=\"rating\" value=\"3\" " + (rating != null && rating.equals("3") ? "checked" : "") + "> 3");
					out.println("<input id=\"rating2\" type=\"radio\" name=\"rating\" value=\"2\" " + (rating != null && rating.equals("2") ? "checked" : "") + "> 2");
					out.println("<input id=\"rating1\" type=\"radio\" name=\"rating\" value=\"1\" " + (rating != null && rating.equals("1") ? "checked" : "") + "> 1");
					out.println("&nbsp;&nbsp;");
					
					// date
					Calendar now = Calendar.getInstance();
					String date = (now.get(Calendar.MONTH) + 1) + "/"  + now.get(Calendar.DATE) + "/" + now.get(Calendar.YEAR);
			//		out.println("<input type=\"hidden\" name=\"date\" value=\"" + now + "\">");
					out.println("ReviewDate: <input type=\"text\" name=\"date\" value=\"" + date + "\" disabled/><br><br>");
					
					// reviewCity
					if (reviewCity != null && !reviewCity.equals("")) {
						out.println("Review city: <input type=\"text\" name=\"reviewCity\" value=\"" + reviewCity + "\"/><br><br>");
					} else {
						out.println("Review city: <input type=\"text\" name=\"reviewCity\" placeholder=\"Your city\"/><br><br>");
					}
					
					// reviewZipCode
					if (reviewZipCode != null && !reviewZipCode.equals("")) {
						out.println("Review zip-code: <input type=\"text\" name=\"reviewZipCode\" value=\"" + reviewZipCode + "\"/><br><br>");
					} else {
						out.println("Review zip-code: <input type=\"text\" name=\"reviewZipCode\" placeholder=\"Your zip code\"/><br><br>");
					}
					
					// review text
					if (reviewText != null && !reviewText.equals("")) {
						out.println("ReviewText: <input type=\"text\" size=\"100\" name=\"reviewText\" value=\"" + reviewText + "\"/><br><br>");
					} else {
						out.println("ReviewText: <input type=\"text\" size=\"100\" name=\"reviewText\" placeholder=\"review text\"/><br><br>");
					}
					out.println("<input class=\"submit-button\" type=\"submit\" value=\"Submit\" style=\"background-color: orange\">");
		
				%>
					
				</form><br>
				<a href="#" onclick="javascript:history.back(-1);">Return</a>&nbsp;&nbsp;&nbsp;
				<a href="./index.jsp">Back to Home Page</a><br><br>
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