<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="iit.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Booked Restaurant Information</title>
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
		ArrayList<OrderRest> restList = OrderRestDAO.getOrderRestsList();
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
				<h3>Booked Restaurants</h3>
				<a href="#" onclick="javascript:history.back(-1);">Return</a>
				<div id="error"></div>
			</article>
			
			<article>
				<center><table style="border-collapse: separate; border-spacing: 30px 50px;">
					<tr>
						<td>Restaurant Name</td>
						<td>City</td>
						<!-- <td>room Id</td> -->
						<td>quantity</td>
						<!-- <td>Description</td> -->
						<td>Price</td>
						<!-- <td>Discount</td> -->
						<!-- <td>Check in Date</td> -->
						<!-- <td>Check out Date</td> -->

					</tr>				
					
					<%
						for(OrderRest orest: restList) {
							String restid = orest.getRestId();
							Restaurant rest = RestaurantDAO.getRestaurantById(restid);
							String city = rest.getCity();
							String name = rest.getName();
        					double money = orest.getPrice();
        					NumberFormat formatter = NumberFormat.getCurrencyInstance();
        					String priceString = formatter.format(money);					

        			%>
							<tr>
								<td><%=name %></td>
								<td><%=city %></td>
								<td>
									<%=orest.getQuantity() %>
								</td>					
								<td>
									<%=priceString %>
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