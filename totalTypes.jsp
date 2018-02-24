<%@page import="iit.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<title>room types for hotel</title>
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
    <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

      // Load the Visualization API and the piechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table, 
      // instantiates the pie chart, passes in the data and
      // draws it.
      
      <% 
      	System.out.println("hello world");
      	ArrayList<Room> roomList = RoomDAO.getRoomListGroupByHotel();
      	String content = "[";
      	for (Room room : roomList){
      		content += "['";
      		String hid = room.getHid();
      		String hname = HotelDAO.getHotelById(hid).getName();
      		content += hname.replaceAll("[\\pP‘’“”]", "");
      		content += "', ";
      		content += room.getNumberOfRoomType();
      		content += "],";
      	}
      	content += "]";
      	System.out.println(content);
      %>
      function drawChart() {

      // Create the data table.
      var data = new google.visualization.DataTable();
      data.addColumn('string', 'Hotel');
      data.addColumn('number', 'Types');
      data.addRows(<%= content %>);

      // Set chart options
      var options = {'title':'How many rooms type for each Hotel',
                     'width':1000,
                     'height':1000};

      // Instantiate and draw our chart, passing in some options.
      var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
      chart.draw(data, options);
    }</script>
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
			
		});
	</script>

	<div id="body" class="rooms text-center">
		<section id="content">
			<article>
				<h3>Total room types for each hotel</h3>
				<!-- <a href="./addRestaurant.jsp"><font color="red">Add a Restaurant</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
				<a href="#" onclick="javascript:history.back(-1);">Return</a><br><br>
				<font color="green"><%=(request.getAttribute("msg") == null ? "" : request.getAttribute("msg")) %></font>
			</article>
			<article style="margin-left:15%">
				<div id="chart_div" style="width:400; height:300"></div>

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