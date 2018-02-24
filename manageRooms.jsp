<%@page import="java.util.*"%>
<%@page import="iit.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Manage Rooms</title>
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
				<a href="./addRoom.jsp"><font color="red">Add a Room</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" onclick="javascript:history.back(-1);">Return</a>
				<div id="error"></div>
			</article>
			
			<article>
				<table style="border-collapse: separate; border-spacing: 30px 50px;">
					<tr>
						<td>Hotel Id</td>
						<td>City</td>
						<td>room Id</td>
						<td>room Type</td>
						<td>Description</td>
						<td>Price</td>
						<td>Discount</td>
						<td>Option</td>
					</tr>				
					
					<%
						for(Room room: roomList) {
							String hid = room.getHid();
							String roomId = room.getRoomId();
							String city = HotelDAO.getHotelById(hid).getCity();
					%>
							<tr>
								<td><%=hid %></td>
								<td><%=city %></td>
								<td><%=roomId %></td>
								<td>
									<form action="AdminServlet" method="post">
										<input type="hidden" name="method" value="adminUpdateRoom">
										<input type="hidden" name="roomId" value="<%=roomId %>">
										<input type="hidden" name="property" value="RoomType">
										<input type="text" size="3" name="value" value="<%=room.getRoomType() %>">
										<input class="submit-button" type="submit" value="update" style="background-color: orange"/>
									</form>
								</td>
								
								<td>
									<form action="AdminServlet" method="post">
										<input type="hidden" name="method" value="adminUpdateRoom">
										<input type="hidden" name="roomId" value="<%=roomId %>">
										<input type="hidden" name="property" value="Descp">
										<input type="text" size="45" name="value" value="<%=room.getDescp() %>">
										<input class="submit-button" type="submit" value="update" style="background-color: orange"/>
									</form>
								</td>
								
								<td>
									<form action="AdminServlet" method="post">
										<input type="hidden" name="method" value="adminUpdateRoom">
										<input type="hidden" name="roomId" value="<%=roomId %>">
										<input type="hidden" name="property" value="Price">
										<input id="price" class="<%=room.getPrice() %>" type="text" size="5" name="value" value="<%=room.getPrice() %>">
										<input class="submit-button" type="submit" value="update" style="background-color: orange"/>
									</form>
								</td>
								
								<td>
									<form action="AdminServlet" method="post">
										<input type="hidden" name="method" value="adminUpdateRoom">
										<input type="hidden" name="roomId" value="<%=roomId %>">
										<input type="hidden" name="property" value="Discount">
										<input id="discount" class="<%=room.getDiscount() %>" type="text" size="3" name="value" value="<%=room.getDiscount() %>"><br>
										<input class="submit-button" type="submit" value="update" style="background-color: orange"/>
									</form>
								</td>
								
								<td align="center">
									<a href="./AdminServlet?method=adminDeleteRoom&roomId=<%=roomId %>" onclick='return confirm("Are you sure to remove this room?")'><font color="red">Delete</font></a>
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