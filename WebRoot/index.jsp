<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="css/materialize.min.css">
	<style type="text/css">
		.input-field input[type=text]:focus + label {
		     color: #009FC6;
		   }
		.input-field input[type=text]:focus {
		     border-bottom: 1px solid #009FC6;
		     box-shadow: 0 1px 0 0 #009FC6;
		   }
		   .input-field input[type=text].valid {
		     border-bottom: 1px solid #009FC6;
		     box-shadow: 0 1px 0 0 #009FC6;
		   }
	</style>

  </head>
  
  <nav class="navbar-fixed" style="background-color: rgb(244,244,244);">
	  <!-- <div class="container-fluid" >
	    <div class="navbar-header">
	      <a class="navbar-brand" href="#">
	      	Dule
	        <img alt="Brand" src="images/logo.png" style="height: 40px;">
	      </a>
	    </div>
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	    	<div class="nav navbar-nav navbar-right">
			    <button type="button" class="btn btn-default"">
				  <span class="glyphicon glyphicon-user"></span> login
				</button>
			</div>
			
			<ul class="nav navbar-nav navbar-right">
		       <li><a href="#">Link</a></li>
		    </ul>
			
		</div>
	  </div> -->
	  <a href="#" class="brand-logo" style="color: #009FC6;margin-left: 5px">Logo</a>
	  <ul id="nav-mobile" class="right hide-on-med-and-down">
        <li><a href="<%=basePath%>WEB-INF/student/login.jsp" style="color: #009FC6">Login</a></li>
      </ul>
	</nav>
  
  <body>
  
   	<div class="carousel slide" style="background-image: url(images/bg.jpg); background-repeat: no-repeat; text-align: center;" >
	  
		  <div class="carousel-inner">
	        <div class="item active">
		  
			  <div style="width: 500px; margin:0px auto; ">
				  <h1 style="color: #009FC6">Welcome Dule!</h1><br>
				  <form action="/user/student/search">
				  		<div class="row">
					  		<div class="input-field col s12" style="text-align: left;">
							          	<input id="search_book" type="text" class="validate" name="bookName">
							          	<label for="search_book">找书</label>
							</div>
						</div>
						
						<div class="input-field col s6 offset-s3">
					        	<button class="btn waves-effect waves-light" type="submit">Search
								</button>
					    </div>
				  </form>
				  
			  </div>
			</div>
		 </div>	  
	</div>
	
	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
  </body>
</html>
