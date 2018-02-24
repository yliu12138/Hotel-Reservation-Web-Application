<%@page import="java.util.*"%>
<%@page import="iit.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Cart</title>
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
		User user = (User)session.getAttribute("user");
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/signin.jsp");
			return;
		}
		ShoppingCart cart = user.getCart();
		
		// avoid erroneously re-submission
		double flag = Math.random();  
		session.setAttribute("flag", flag + ""); 
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
							if (user.getLevel() == 1) {
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
						<li class="active"><a href="cart.jsp" id="cartRoom">CART <%=(cart != null && !cart.isEmpty() ? "(" + cart.getObjNumber() + ")" : "") %></a></li>
						<li class="scroll"><a href="reservations.jsp">RESERVATIONS</a></li>
						
						<li class="scroll"><a href="register.jsp">REGISTER</a></li>
						<li class="scoll"><a href="trending.jsp">TRENDING</a></li>
						<li class="scoll"><a href="travel.jsp">TRAVEL SUGGESTION</a></li>						
						
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
	
	<script src="js/jquery-ui.js"></script>
	<script>
	
		$(function() {

			//ajax change cartRoom quantity
			$(":text").change(function(){
				var quantityVal = $.trim(this.value);
				
				var flag = false;
				var reg = /^\d+$/g;
				var quantity = -1;
				if(reg.test(quantityVal)){
					quantity = parseInt(quantityVal);
					if(quantity >= 0){
						flag = true;
					}
				}
				
				if(!flag){
					alert("Invalid input");
					$(this).val($(this).attr("class"));
					return;
				}
				
				var $tr = $(this).parent().parent();
				var title = $.trim($tr.find("td:eq(1)").text());
				if(quantity == 0){
					var flag2 = confirm("Are you sure to remove " + title +"?");
					
					if(flag2){
						$tr.find("td:last").children()[0].click();
						return;
					}
					
					$(this).val($(this).attr("class"));
					return;
				}
				
				var flag = confirm("Are you sure to update the quantity of " + title +"?");
				
				if(!flag){
					$(this).val($(this).attr("class"));
					return;
				}
				
				var url = "RoomServlet";
			/*	var roomIdStr = $.trim(this.name); */
				var idStr = $.trim(this.name);
				var args = {"method":"updateCart","id":idStr, "quantity":quantityVal, "time":new Date()};
				
				$.post(url, args, function(data){
					var roomNumber = data.roomNumber;
					var objNumber = data.objNumber;
					var totalCost = data.totalCost;
					
					$("#roomNumber").text(roomNumber);
					$("#objNumber").text(objNumber);
					$("#cartRoom").text("CART (" + objNumber + ")");
					$("#totalCost").text(totalCost);
				}, "JSON");
			});
			
		});
	</script>

	<div id="body" class="rooms text-center">
		<section id="content">
			<article style="margin-left:8%">
				<!-- <h2>Your Shopping Cart</h2><br> -->	    
			
			<%
			if(cart == null || cart.isEmpty()) {
			%>
				<h4>Your shopping cart is empty</h4><br>
				<a href="./HomeServlet">Back to Home</a>
			<%
			} 
			else {
			%>
				<a href="#" onclick="javascript:history.back(-1);">Return</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="./index.jsp">Change search</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="./RoomServlet?method=clearCart" onclick="return confirm('Clear everything?');">Clear cart</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="./checkout.jsp">Check out</a>
				<br><br><br>
				
				<div><h3>Your cart has <font color="red" id="roomNumber"><%=user.getCart().getRoomNumber() %></font> room(s)</h3></div><br>
				<div>Total Cost: <font color="green" id="totalCost">$<%=String.format("%.2f", cart.getTotalCost()) %></font></div>
				
				<table style="border-collapse: separate; border-spacing: 30px 50px;">
					<tr>
						<td align="center">Picture</td>
						<td align="center">Name</td>
						<td align="center">Check in</td>
						<td align="center">Check out</td>
						<td align="center">Quantity</td>
						<td align="center">Unit Cost</td>
						<td align="center">Option</td>
					</tr>
				
				<%	
				for(ShoppingCartRoom cartRoom: cart.getCartRooms()) {
					Room room = cartRoom.getRoom();
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
					<tr>
						<td align="center"><img src="images/<%=roomType %>.jpg" style="width: 300px; height: 200px"></td>
						<td align="center"><%=type %></td>
						<td align="center"><%=cartRoom.getFormattedCheckinDate() %></td>
						<td align="center"><%=cartRoom.getFormattedCheckoutDate() %></td>
						<td>
							<input class="<%=cartRoom.getQuantity() %>" type="text" size="1" name="<%=cartRoom.getRoomId() %>" value="<%=cartRoom.getQuantity() %>"/>
						</td>
						<td align="center"><del><font color="red">$<%=room.getPrice() %></font></del>&nbsp;<font color="green"><%=String.format("%.2f", room.getDiscountedPrice()) %></font></td>
						<td><a href="./RoomServlet?method=removeCartRoom&roomId=<%=room.getRoomId() %>" class="delete">remove</a></td>
					</tr>
			<%
				}	// end for
				
				for (ShoppingCartRest cartRest: cart.getCartRestaurants()) {
					Restaurant rest = cartRest.getRest();
			%>
					<tr>
						<td align="center"><img src="images/<%=cartRest.getRestId() %>.jpg" style="width: 300px; height: 200px"></td>
						<td align="center"><%=rest.getName() %></td>
						<td align="center"></td>
						<td align="center"></td>
						<td>
							<input class="<%=cartRest.getQuantity() %>" type="text" size="1" name="<%=rest.getRid() %>" value="<%=cartRest.getQuantity() %>"/>
						</td>
						<td align="center"><del><font color="red">$<%=rest.getPrice() + 8 %></font></del>&nbsp;<font color="green">$<%=rest.getPrice() %></font></td>
						<td><a href="./RoomServlet?method=removeCartRest&rid=<%=rest.getRid() %>" class="delete">remove</a></td>
					</tr>
			<%
				}
			%>
					
				</table>
				
			<%
			}	// end if
			%>
			
			</article>
		</section>
	</div>
	<br><br>
			
			
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