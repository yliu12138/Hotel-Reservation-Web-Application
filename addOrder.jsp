<%@page import="java.util.*"%>
<%@page import="iit.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Update Order</title>
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
						<li class="scroll"><a href="signout.jsp">HI, <%=user.getUsername()%>! SIGNOUT?</a></li>
						<li class="scroll"><a href="cart.jsp">CART <%=(cart != null && !cart.isEmpty() ? "(" + cart.getObjNumber() + ")" : "") %></a></li>
						<li class="active"><a href="reservations.jsp">RESERVATIONS</a></li>
						<li class="scoll"><a href="trending.jsp">TRENDING</a></li>
						<li class="scoll"><a href="travel.jsp">TRAVEL SUGGESTION</a></li>						
						
						<li class="scroll"><a href="register.jsp">REGISTER</a></li>
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
	
	
	<!-- checkout form -->    
	<div id="body" class="rooms text-center">
		<section id="content">
			<article><h2>Update Order</h2></article><br>
            
			<article class="expanded" style="margin-left: 400px;">
				<form action="./addOrder" method="post" >
					<table style="border-collapse: separate; border-spacing: 10px;">
						<tr>
							<td>Order ID: </td>
							<td><input type="text" name="oid"/></td>
                        </tr>
						<tr>
                            <td>Confirmation ID: </td>
                            <td><input type="text" name="confirmationId"/></td>
                        </tr>
                            
						<tr>
							<td>username: </td>
							<td><input type="text" name="username"/></td>
                        </tr>
						<tr>
                            <td>Order Time: </td>
                            <td><input type="text" name="orderTime"/></td>
                        </tr>	
						<tr>
                                <td>Checkin Date: </td>
                                <td><input type="text" name="checkinDate"/></td>
                        </tr>	                        
                        
						<tr>
                                <td>Cancel Deadline: </td>
                                <td><input type="text" name="cancelDeadline"/></td>
                        </tr>	

						<tr>
                                <td>Checkout Date: </td>
                                <td><input type="text" name="checkoutDate"/></td>
                        </tr>	

                        <tr>
                            <td>City: </td>
                            <td><input type="text" name="city"/></td>
                        </tr>
						<tr>
                            <td>ZipCode: </td>
                            <td><input type="text" name="zipcode"/></td>
                        </tr>	                        
                        <tr>
                            <td>Total Price : </td>
                            <td><input type="text" name="cost"/></td>
                        </tr>
						<tr>
							<td colspan="4" align="center"><input type="submit" value="Place your order" style="background-color:orange"/></td>
                        </tr>
                        <tr>
                            <td colspan="4" align="center"><a href="#" onclick="javascript:history.back(-1);">Return</a></td>                                
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