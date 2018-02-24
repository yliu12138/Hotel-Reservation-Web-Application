<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="iit.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Add Hotel</title>
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
						<li class="scoll"><a href="trending.jsp">TRENDING</a></li>						
						<li class="scroll"><a href="register.jsp">REGISTER</a></li>
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
		HashMap<String, Hotel> hotelsMap = HotelDAO.getAllHotels();
	%>
	<div id="body" class="rooms text-center">
		<section id="content">
			<article >
				<h3>To Add a Hotel</h3>
				<form action="AdminServlet" method="post">
					<input type="hidden" name="method" value="adminAdd">
					<table style="border-collapse: separate;margin-left:25%; border-spacing: 30px 20px;">
						<tr>
							<td>Hotel Id</td>
							<td><input type="text" name="hid"></td>
						</tr>
						<tr>
							<td>Hotel Name</td>
							<td><input type="text" size="25" name="name"></td>
						</tr>
						<tr>
							<td>Detail</td>
							<td><input type="text" size="25" name="descp"></td>
						</tr>
						<tr>
							<td>Address</td>
							<td><input type="text" size="30" name="address"></td>
						</tr>
						<tr>
							<td>City</td>
							<td>
								<select name="city" style="width: 120px">
									<option value="Boston_0">Boston</option>
									<option value="Chicago_6">Chicago</option>
									<option value="Detroit_5">Detroit</option>
									<option value="New York_1">New York</option>
									<option value="Houston_7">Houston</option>
									<option value="Los Angeles_9">Los Angeles</option>
									<option value="Miami_3">Miami</option>
									<option value="Philadelphia_2">Philadelphia</option>
									<option value="San Francisco_8">San Francisco</option>
									<option value="Seattle_4">Seattle</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Standard Single Room Price</td>
							<td>$<input type="text" name="ss"></td>
						</tr>
						<tr>
							<td>Standard Double Room Price</td>
							<td>$<input type="text" name="sd"></td>
						</tr>
						<tr>
							<td>Deluxe Single Room Price</td>
							<td>$<input type="text" name="ls"></td>
						</tr>
						<tr>
							<td>Deluxe Double Room Price</td>
							<td>$<input type="text" name="ld"></td>
						</tr>
						<tr>
							<td>Family Room Price</td>
							<td>$<input type="text" name="f"></td>
						</tr>
						<tr>
							<td colspan="2"><input class="submit-button" type="submit" value="Add" style="background-color:orange; width: 200px"/></td>
						</tr>
					</table>
				</form>
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