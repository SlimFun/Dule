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
				  <h1 style="color: #009FC6">Welcome Dule!</h1><br>
				  <form action="<%=basePath%>user/admin/searchBook">
				  		<div class="row">
					  		<div class="input-field col s12" style="text-align: left;">
							          	<input id="search_book" type="text" class="validate" name="searchInfo">
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
	
	<div id="addAccount" class="modal">
				<form action="<%=basePath%>user/admin/addAccount" method="post">
				    <div class="modal-content">
						<div class="input-field col s8 offset-s2">
				  			<input id="balance" type="number" class="validate" name="balance" required>
					        <label for="balance">Input the balance of account: </label>
				  		</div>
				    </div>
				    <input type="hidden" name="userId" value="<s:property value="userSearchResult.id"/>" />
				    <div class="modal-footer">
				    	<input type="submit" onclick="submitForm()" class=" modal-action waves-effect waves-green btn-flat" value="Add">
				    </div>
				</form>
	</div>
	
	<span id="code" style="visibility: hidden;"><s:property value="errorCode"/></span>
	
	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script type="text/javascript">
    	$(document).ready(function() {
		    $('.modal-trigger').leanModal();
		    
		    var code = $('#code').text();
		    var content = "";
		    if(code != 0){
		    	if(code == 5){
			    	content = "<span>Borrow book successed.</span>";
			    }else if(code == 6){
			    	content = "<span>Return book successed.</span>";
			    }
			    var $toastContent = $(content);
	  			Materialize.toast($toastContent, 1000);
		    }
		    
		});
		
    </script>
  </body>
</html>
