<%@page import="java.util.*"%>
<%@page import="iit.*"%>
<!DOCTYPE html>
<html>
<head>
<title>All Rooms Information</title>
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
							if(user.getLevel() == 2) {
							%>
								<li class="scroll"><a href="manage.jsp">MANAGEMENT</a></li>
							<%
							}
							%>
							<li class="scroll"><a href="signout.jsp">HI, <%=user.getUsername()%>! SIGNOUT?</a></li>
							<li class="scroll"><a href="cart.jsp">CART <%=(cart != null && !cart.isEmpty() ? "(" + cart.getObjNumber() + ")" : "")%></a></li>
							<li class="scroll"><a href="reservations.jsp">RESERVATIONS</a></li>
							
					<%
						}
					%>
						<li class="scroll"><a href="register.jsp">REGISTER</a></li>
						<li class="scoll"><a href="trending.jsp">TRENDING</a></li>
						<li class="scoll"><a href="travel.jsp">TRAVEL SUGGESTION</a></li>						
						
						<!-- <li class="scroll"><a href="restaurant.jsp">RESTAURANT</a></li> -->
					</ul>					<script>
						$("span.menu").click(function() {
							$(".top-menu ul").slideToggle(200);
						});
					</script>
				</div>
			</div>
		</div>
	</div>

	<%
		ArrayList<Room> roomList = RoomDAO.getRoomList();
	%>
	<script src="js/jquery-ui.js"></script>
	<script>
	
		$(function() {

			$("#price").change(function(){
				var priceVal = $.trim(this.value);
				var flag = false;
				var reg = /^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$/;
				var price = -1;
				if(reg.test(priceVal)){
					price = parseDouble(priceVal);
					if(price > 0){
						flag = true;
					}
				}
				
				if(!flag){
					alert("Invalid input");
					$(this).val($(this).attr("class"));
				}
			});
			
			//ajax change cartRoom quantity
			$("#discount").change(function(){
				var discountVal = $.trim(this.value);
				var flag = false;
				var reg = /^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$/;
				var discount = -1;
				if(reg.test(discountVal)){
					discount = parseDouble(discountVal);
					if(discount > 0){
						flag = true;
					}
				}
				
				if(!flag){
					alert("Invalid input");
					$(this).val($(this).attr("class"));
				}
			});
			
		});
	</script>
	<div id="body" class="rooms text-center">
		<section id="content">
			<article>
				<h3>All Rooms</h3>
				<a href="#" onclick="javascript:history.back(-1);">Return</a>
				<div id="error"></div>
			</article>
			
			<article>
				<center><table style="border-collapse: separate; border-spacing: 30px 50px;">
					<tr>
						<td>Hotel Name</td>
						<td>City</td>
						<!-- <td>room Id</td> -->
						<td>room Type</td>
						<!-- <td>Description</td> -->
						<td>Price</td>
						<td>Discount</td>

					</tr>				
					
					<%
						for(Room room: roomList) {
							String hid = room.getHid();
							String hname = HotelDAO.getHotelById(hid).getName();
							String roomId = room.getRoomId();
							String city = HotelDAO.getHotelById(hid).getCity();
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
					%>
							<tr>
								<td><%=hname %></td>
								<td><%=city %></td>
								<!-- <td><%=roomId %></td> -->
								<td>
									<%=roomType %>
								</td>
								
								<!-- <td>
									<%=room.getDescp() %>
								</td> -->
								
								<td>
									<%=room.getPrice() %>
								</td>
								
								<td>
									<%=room.getDiscount() %>
								</td>
								

							</tr>
					<%
						}
					%>
				</table></center>
			
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