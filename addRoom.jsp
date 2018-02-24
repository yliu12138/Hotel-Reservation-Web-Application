<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="iit.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Add Room</title>
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
		HashMap<String, Hotel> hotelsMap = HotelDAO.getAllHotels();
	%>
	<div id="body" class="rooms text-center">
		<section id="content">
			<article >
				<h3>To Add a Room</h3>
				<form action="AdminServlet" method="post">
					<input type="hidden" name="method" value="adminAddRoom">
					<table style="border-collapse: separate;margin-left:25%; border-spacing: 30px 20px;">
						<tr>
							<td>Hotel Id</td>
							<td align="left"><input type="text" name="hid"></td>
						</tr>
						<tr>
							<td>Room type</td>
							<td align="left">
								<select name="roomType" style="width: 150px">
									<option value="ss">Standard Single</option>
									<option value="sd">Standard Double</option>
									<option value="ls">Deluxe Single</option>
									<option value="ld">Deluxe Double</option>
									<option value="f">Family</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Detail</td>
							<td align="left"><input type="text" size="75" name="descp"></td>
						</tr>
						<tr>
							<td>Price</td>
							<td align="left"><input type="text" name="price"></td>
						</tr>
						<tr>
							<td>Discount</td>
							<td align="left"><input type="text" name="discount" placeholder="between 0 and 1.0"></td>
						</tr>
						<tr>
							<td colspan="2" align="left"><input class="submit-button" type="submit" value="Add" style="background-color:orange; width: 200px"/></td>
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