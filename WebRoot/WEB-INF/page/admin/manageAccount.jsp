<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
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
  	<jsp:include page="/WEB-INF/page/share/admin/main_model.jsp"></jsp:include>
  	
  	
  	<div class="carousel slide" style="background-image: url(images/bg.jpg); background-repeat: no-repeat; text-align: center;" >
	  
		  <div class="carousel-inner">
	        <div class="item active">
		  
			  <div style="width: 500px; margin:0px auto; ">
				  <h1 style="color: #009FC6">Search User</h1><br>
				  <form action="<%=basePath%>user/admin/searchUser">
				  		<div class="row">
					  		<div class="input-field col s12" style="text-align: left;">
							          	<input id="search_book" type="text" class="validate" name="searchInfo">
							          	<label for="search_book">User Id/Username/Email:</label>
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
	
	<span style="visibility: hidden;" id="code"><s:property value="errorCode"/></span>
				
	<div id="userNotExists" class="modal">
			    <div class="modal-content">
			      <p style="color: #ef5350">User not exits.</p>
			    </div>
			    <div class="modal-footer">
			      <a class=" modal-action modal-close waves-effect waves-green btn-flat">OK</a>
			    </div>
	</div>
		
	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script type="text/javascript">
    	$(document).ready(function() {
		    $('select').material_select();
		     $('.modal-trigger').leanModal();
		    
		    var code = $('#code').text();
		    if(code == 2){
		    	$('#userNotExists').openModal();
		    }
		});
		
		$('.datepicker').pickadate({
		    selectMonths: false, // Creates a dropdown to control month
		    selectYears: 15, // Creates a dropdown of 15 years to control year
		    format:"yyyy-mm-dd"
		  });
    </script>
  </body>
</html>
