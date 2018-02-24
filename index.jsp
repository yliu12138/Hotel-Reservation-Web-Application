<%@page import="iit.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=Constant.NAME %> HOTELS Home</title>
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Pinyon+Script' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Quicksand:400,700' rel='stylesheet' type='text/css'>
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="js/jquery.min.js"></script>
</head>
<body>

	<%
		User user = (User) session.getAttribute("user");
		int roomNumber = 0;
	%>

	<!--header starts-->
	<div class="header">
		<div class="top-header">
			<div class="container">
				<div class="logo">
					<a href="./HomeServlet"><font size="6" color="yellow"><%=Constant.NAME %> HOTELS</font></a>
				</div>
				
				<span class="menu"> </span>
				<div class="m-clear"></div>
				<div class="top-menu">
					<ul>
						<li class="active"><a href="./HomeServlet">START</a></li>

						<%
							if (user == null) {
						%>
							<li class="scroll"><a href="signin.jsp">SIGN IN</a></li>
						<%
							} else {
								ShoppingCart cart = user.getCart();
								if(user.getLevel() == 1) {
						%>
									<li class="scoll"><a href="analytics.jsp">ANALYTICS</a></li>
									
								<%
								}
								%>
							<li class="scroll"><a href="signout.jsp">Hi, <%=user.getUsername()%>! SIGN OUT?</a></li>
								<%
								if(user.getLevel() == 2) {
								%>
									<li class="scroll"><a href="manage.jsp">MANAGEMENT</a></li>
								<%
								}
							}
						%>
						<li class="scroll"><a href="cart.jsp">CART</a></li>
						<li class="scroll"><a href="reservations.jsp">RESERVATIONS</a></li>
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
		<div class="banner">
			<div class="banner-info text-center">
				<h3>
					<label>Hello,</label> You've Reached
				</h3>
				<h1><%=Constant.NAME %></h1>
				<!-- auto-complete search bar -->
				<label for="searchHotels"><font color="orange">Search Hotel:</font></label>&nbsp;
               	<input id="searchHotels" placeholder="hotel name"/>
                <br><br>
				<span></span>
			</div>
		</div>
	</div>
	
	<!---start-date-picker---->
	<link rel="stylesheet" href="css/jquery-ui.css" />
	<script src="js/jquery-ui.js"></script>
	<script>
		$(function() {
			$("#datepicker,#datepicker1").datepicker();
			
			// major cities carousel callback
			$(".city").click(function() {
				var location = $(this).attr("name");
				var checkinDate = $("#datepicker").val();
				var checkoutDate = $("#datepicker1").val();
				var roomCount = $("#country").val();
				window.location.href = "./searchHotels.jsp?checkinDate=" + checkinDate + "&checkoutDate=" 
						+ checkoutDate + "&roomCount=" + roomCount + "&location=" + location;
			});
			
			
			// auto complete by Ajax
			$("#searchHotels").autocomplete({
				 source: function( request, response ) {
				 	var url = "AutocompleteServlet";
				 	var args = {"time":new Date(), "term": request.term};
			        
			        $.post(url, args, function(data){
			        	response(data);
			        }, "JSON");
			    },
			    select: function (event, ui) {
		            window.location = ui.item.url;
		        }
		    });
			
		});
		
	</script>
	<!---/End-date-piker---->
	<link type="text/css" rel="stylesheet" href="css/JFGrid.css" />
	<link type="text/css" rel="stylesheet" href="css/JFFormStyle-1.css" />
	<script type="text/javascript" src="js/JFCore.js"></script>
	<script type="text/javascript" src="js/JFForms.js"></script>
	
	<!-- Set here the key for your domain in order to hide the watermark on the web server -->
	<script type="text/javascript">
		(function() {
			JC.init({
				domainKey : ''
			});
		})();
	</script>

	<div class="online_reservation">
		<div class="b_room">
			<div class="booking_room">
				<div class="reservation">
					<!-- <form action="./SearchHotelServlet" method="post"> -->
					<form action="./searchHotels.jsp" method="get">
						<ul>
							<li class="span1_of_1 left">
								<h5>Check-in</h5>
								<div class="book_date">
									<input class="date" id="datepicker" type="text"
										name="checkinDate" value="12/08/2017"
										onfocus="this.value = '';"
										onblur="if (this.value == '') {this.value = '11/08/2017';}">
								</div>
							</li>
							<li class="span1_of_1 left">
								<h5>Check-out</h5>
								<div class="book_date">
									<input class="date" id="datepicker1" type="text"
										name="checkoutDate" value="12/10/2017"
										onfocus="this.value = '';"
										onblur="if (this.value == '') {this.value = '11/10/2017';}">
								</div>
							</li>
							<li class="span1_of_1">
								<h5>Number of Rooms</h5> <!----------start section_room----------->
								<div class="section_room">
									<!-- <select id="country" onchange="change_country(this.value)"
										name="roomType" class="frm-field required">
										<option value="ss">Standard Single Room</option>
										<option value="sd">Standard Double Room</option>
										<option value="ls">Deluxe Single Room</option>
										<option value="ld">Deluxe Double Room</option>
										<option value="f">Family Room</option>
									</select> -->
									<select id="country" onchange="change_country(this.value)"
										name="roomCount" class="frm-field required">
										<option value="1">1 Room</option>
										<option value="2">2 Rooms</option>
										<option value="3">3 Rooms</option>
										<option value="4">4 Rooms</option>
									</select>
								</div>
							</li>
							<li class="span1_of_1">
								<h5>Destination</h5>
								<div class="zipCode">
									<input type="text" name="location" placeholder="City, Zip code">
								</div>
							</li>
							<li class="span1_of_3">
								<div class="date_btn">
									<input id="view" onclick="alert11()" type="submit" value="View Prices" style="background-color: orange" />
								</div>
							</li>
							<div class="clearfix"></div>

						</ul>
					</form>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<br>
	
	<!---->
	<div class="package text-center">
		<div class="container">
			<h3>Major Cities</h3>
			<link href="css/owl.carousel.css" rel="stylesheet">
			<script src="js/owl.carousel.js"></script>
			<script>
				$(document).ready(function() {
					$("#owl-demo").owlCarousel({
						items : 1,
						lazyLoad : true,
						autoPlay : true,
						navigation : true,
						navigationText : false,
						pagination : false,
					});
				});
				
			</script>
			
			<!-- //requried-jsfiles-for owl -->
			<div id="owl-demo" class="owl-carousel">
				<div class="item text-center image-grid">
					<ul>
						<li><img class="city" name="New York" src="images/1_NewYork.jpg"><br>New York</li>
						<li><img class="city" name="Chicago" src="images/2_Chicago.jpg"><br>Chicago</li>
						<li><img class="city" name="Los Angeles" src="images/3_LA.jpg"><br>Los Angeles</li>
					</ul>
				</div>
				<div class="item text-center image-grid">
					<ul>
						<li><img class="city" name="San Francisco" src="images/4_SanFrancisco.jpg"><br>San Francisco</li>
						<li><img class="city" name="Houston" src="images/5_Houston.jpg"><br>Houston</li>
						<li><img class="city" name="Miami" src="images/6_Miami.jpg"><br>Miami</li>
					</ul>
				</div>
				
				<!-- <div class="item text-center image-grid">
					<ul>
						<li><a class="city" ><img class="city" name="" src="images/1_NewYork.jpg" alt=""></a><br>New York</li>
						<li><a class="city"><img class="city" src="images/2_Chicago.jpg" alt=""></a><br>Chicago</li>
						<li><a class="city"><img class="city" src="images/3_LA.jpg" alt=""></a><br>Los Angeles</li>
					</ul>
				</div>
				<div class="item text-center image-grid">
					<ul>
						<li><a class="city"><img class="city" src="images/4_SanFrancisco.jpg" alt=""></a><br>San Francisco</li>
						<li><a class="city"><img class="city" src="images/5_Houston.jpg" alt=""></a><br>Houston</li>
						<li><a class="city"><img class="city" src="images/6_Miami.jpg" alt=""></a><br>Miami</li>
					</ul>
				</div> -->
				

			</div>
		</div>
	</div>


	<div class="rooms text-center">
		<div class="container">
			<h3>Our Room Types</h3>
			<div class="room-grids">
				<div class="col-md-4 room-sec">
					<img src="images/ss.jpg" alt=""
						style="width: 300px; height: 183px"/>
					<h4>Standard Single Room</h4>
					<p>You can relax and feel at home as all single rooms are
						fitted with a spacious semi double bed. Some are also fitted with
						a desk.</p>
				</div>
				<div class="col-md-4 room-sec">
					<img src="images/sd.jpg" alt=""
						style="width: 300px; height: 183px" />
					<h4>Standard Double Room</h4>
					<p>Most suitable for couples and the size enables you to relax
						and feel at home. All rooms are also fitted with a desk, a closet
						and a washlet.</p>
				</div>
				<div class="col-md-4 room-sec">
					<img src="images/f.jpg" alt=""
						style="width: 300px; height: 183px" />
					<h4>Family Room</h4>
					<p>You can enjoy the family room as if at your own home. All
						family rooms are also fitted with kids' bed and toys</p>
				</div>
				<div class="clearfix"></div>
				<div class="col-md-4 room-sec">
					<img src="images/ls.jpg" alt=""
						style="width: 300px; height: 183px" />
					<h4>Deluxe Single Room</h4>
					<p>Deluxe double rooms are ideal for one who wishes to
						experience a romantic holiday escape in Rome or is visiting for a
						business matter. Deluxe rooms are designed with ultimate guest
						comfort in mind at one of the top hotels near Trevi Fountain.</p>
				</div>
				<div class="col-md-4 room-sec">
					<img src="images/ld.jpg" alt=""
						style="width: 300px; height: 183px" />
					<h4>Deluxe Double Room</h4>
					<p>Elegantly furnished, the Deluxe double is perfect for a
						comfortable single occupancy sojourn or a couple’s weekend getaway
						at one of the best Rome City centre hotels. All Deluxe doubles
						feature king-size beds, refined marble bathrooms and work desks.</p>
				</div>

				<div class="clearfix"></div>
			</div>
		</div>
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