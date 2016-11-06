<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'main.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="css/materialize.min.css">

  </head>
  
  <body>
  	<jsp:include page="/WEB-INF/page/share/user/main_model.jsp"></jsp:include>
  	
  	<div class="carousel slide" style="background-image: url(images/bg.jpg); background-repeat: no-repeat; text-align: center;" >
	  
		  <div class="carousel-inner">
	        <div class="item active">
		  
			  <div style="width: 500px; margin:0px auto; ">
				  <h1 style="color: #009FC6">Welcome Dule!</h1><br>
				  <form action="<%=basePath%>/user/searchBook" method="post">
				  		<div class="row">
					  		<div class="input-field col s12" style="text-align: left;">
							          	<input id="search_book" type="text" class="validate" name="searchInfo" required>
							          	<label for="search_book">Search Book</label>
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
  </body>
</html>
