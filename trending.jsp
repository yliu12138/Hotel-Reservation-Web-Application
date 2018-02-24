<%@page import="iit.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Trending</title>
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
                                <li class="scoll"><a href="trending.jsp">TRENDING</a></li>
                                
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
	<br><br><br>

	<div id="body" >
		<section id="content">
			<article style="margin-left:35%">
				
				<h2> Trending</h2><hr/>
                <h3> Top 5 highest rating Hotel</h3><hr>
                <% 
                HashMap<String, Double> top5MostLikedProducts = new HashMap<String, Double>();
                top5MostLikedProducts = ReviewDAO.getTop5MostLikedProducts();
                HashMap<String, Hotel> hotels = HotelDAO.getAllHotels();
                Object [] a = top5MostLikedProducts.entrySet().toArray();
                Arrays.sort(a, new Comparator() {
                    public int compare(Object o1, Object o2){
                        return ((Map.Entry<String, Double>) o2).getValue().compareTo(((Map.Entry<String, Double>) o1).getValue());
                    }
                });
                        

                %>
                <table>
				<%
				int c1 = 0;
                for (Object e : a){
					c1 ++;
                    String hid = ((Map.Entry<String, Double>) e).getKey();
                    Hotel hotel = hotels.get(hid);
                %>
				 <tr><td>--------------</td></tr> 
				 <tr><td>Top #<%=c1%></td></tr>      
                <tr><td>Hotel name : </td><td><%= hotel.getName() %></td></tr>
                <tr><td>Rating: </td><td><%= ((Map.Entry<String, Double>) e).getValue()%></td></tr>
                
                <%
                }    
                %>
			</table><br><br><hr>
			
			<h3> Top 5 most sold Hotel</h3><hr>
			<% 
			HashMap<String, Integer> top5MostSoldProducts = new HashMap<String, Integer>();
			top5MostSoldProducts = ReviewDAO.getTop5MostSoldProducts();
			Object [] b = top5MostSoldProducts.entrySet().toArray();
			Arrays.sort(b, new Comparator() {
				public int compare(Object o1, Object o2){
					return ((Map.Entry<String, Integer>) o2).getValue().compareTo(((Map.Entry<String, Integer>) o1).getValue());
				}
			});
					

			%>
			<table>
			<%
			int c2 = 0;
			for (Object e : b){
				c2 ++;
				String hid = ((Map.Entry<String, Integer>) e).getKey();
				Hotel hotel = hotels.get(hid);
			%>
			 <tr><td>--------------</td></tr>  
			 <tr><td>Top #<%=c2%></td></tr>       
			<tr><td>Hotel name : </td><td><%= hotel.getName() %></td></tr>
			<tr><td>Sold Number: </td><td><%= ((Map.Entry<String, Integer>) e).getValue()%></td></tr>
			
			<%
			}    
			%>
		</table>

            </article>
            <br><br><br><br><br><br>
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