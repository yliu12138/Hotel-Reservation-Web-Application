<%@page import="iit.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Register</title>
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
	<div class="header" >
		<div class="top-header">
			<div class="container">
				<div class="logo">
					<a href="./HomeServlet"><font size="6" color="yellow"><%=Constant.NAME %> HOTELS</font></a>
				</div>
				<span class="menu"> </span>
				<div class="m-clear"></div>
				<div class="top-menu">
					<ul>
						<li class="scroll"><a href="./HomeServlet">START</a></li>
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
							<li class="scroll"><a href="signout.jsp">HI, <%=user.getUsername()%>! SIGNOUT?</a></li>
						<%
							if(user.getLevel() == 2) {
						%>
								<li class="scroll"><a href="manage.jsp">MANAGEMENT</a></li>
							<%
							}
							%>
							
							<li class="scroll"><a href="cart.jsp">CART <%=(cart != null && !cart.isEmpty() ? "(" + cart.getObjNumber() + ")" : "") %></a></li>
							<li class="scroll"><a href="reservations.jsp">RESERVATIONS</a></li>
							
					<%
						}
					%>
						<li class="active"><a href="register.jsp">REGISTER</a></li>
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
	</div><br>
	
	<script src="js/jquery-ui.js"></script>
	<script>
	
		$(function() {
			// checks whether password and passwordConfirm matches
			$("#passwordConfirm").change(function(){
				var pwd = $.trim($("#password").val());
				var pwdConfirm = $.trim(this.value);
				
				$("#unconfirm").text("");
				if(pwd == pwdConfirm) {
					$('#submit').prop('disabled',false).css('opacity', 1);
					return;
				}
				$("#unconfirm").text("Password not confirmed");
				$('#submit').prop('disabled',true).css('opacity',0.5);
			});
			
			
			// use ajax to check whether a user-input username already exists or is ok for registration
			$("#username").change(function(){
				var username = $.trim(this.value);
				if(username == "" || username == null) {
					return;
				}
				
				// ajax request, args (method: isExists，username：username，time：new Date (free cache))
				var url = "RegisterServlet";
				var args = {"method":"isExists", "username":username, "time":new Date()};
				
				// in isExists method，check whether username already exists in db, returns JSON: {"exists": exists}
				// update #ok and #exists
				$.post(url, args, function(data){
					var exists = data.exists;
					var info = data.info;
					
					$("#ok").text("");
					$("#exists").text("");
					
					if(!exists) {
						$("#ok").text(info);
						$('#submit').prop('disabled',false).css('opacity',1);
					} else {
						$("#exists").text(info);
						$('#submit').prop('disabled',true).css('opacity',0.5);
					}
				}, "JSON");
			});
			
		});
	</script>
	
	<!-- display error message with ajax -->
	<div style="text-align: center" >
		<font id="exists" style="color: red; text-align: center"></font>
		<font id="ok" style="color: green; text-align: center"></font><br>
		<font id="unconfirm" style="color: red; text-align: center"></font>
	</div><br>

	<div id="body">
		<section id="content">
			<article class="expanded" style="margin-left: 35%">

				<div class="login-page">
					<div class="form">
						<form class="register-form" method="post" action="./RegisterServlet">
							<input type="hidden" name="method" value="register">
							<input id="username" type="text" name="username" placeholder="username" /> 
							<input id="password" type="password" name="password" placeholder="password" /> 
							<input id="passwordConfirm" type="password" name="passowordConfirm" placeholder="confirm password" />
							<button id="submit" type="submit">create</button>

							<div id="message"></div>

							<p class="message">
								Already registered? <a href="./signin.jsp">Sign In</a>
							</p>
						</form>

					</div>
				</div>

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