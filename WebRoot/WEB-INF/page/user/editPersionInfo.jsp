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
    
    <title>My JSP 'addBook.jsp' starting page</title>
    
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
  	
  	<div class="row">
	  	<form action="<%=basePath%>user/doEditPersonInfo" class="col s12" method="post"  enctype="multipart/form-data">
	  		<div class="row">
		  		<div class="input-field col s8 offset-s2">
		  			<input id="Id" type="text" class="validate" name="user.id" disabled value="${user.id }">
			        <label for="Id">Id：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			<input id="username" type="text" class="validate" name="user.username" value="${user.username }">
			        <label for="username">Username：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			<input id="email" type="email" class="validate" name="user.email" value="${user.email }">
			        <label for="email">Email：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			<input id="phone" type="tel" class="validate" name="user.phone" value="${user.phone }">
			        <label for="phone">Phone：</label>
		  		</div>
				<div class="file-field input-field col s8 offset-s2">
			      <div class="btn">
			        <span>File</span>
			        <input type="file" name="file">
			      </div>
			      <div class="file-path-wrapper">
			        <input class="file-path validate" type="text" value="${user.profile }">
			      </div>
			    </div>
		  		<div class="input-field col s6 offset-s4">
		        	<button class="btn waves-effect waves-light" type="submit">
					    <i class="material-icons right">send</i>Submit
					</button>
					<button class="btn waves-effect waves-light modal-trigger" href="#confirmPassword">ChangePassword</button>
				
					<p style="color: red;">
						<s:if test="%{errorCode==3}">
							Username has bean used.
						</s:if>
						<s:if test="%{errorCode==4}">
							Email has bean used.
						</s:if>
					</p>
				</div>
	  		</div>
	  	</form>
	</div>
	
	 <div id="confirmPassword" class="modal">
				<form action="<%=basePath%>user/confirmPassword" method="post">
				    <div class="modal-content">
						<div class="input-field col s8 offset-s2">
				  			<input id="position" type="password" class="validate" name="password" required>
					        <label for="position">Your current password is:  </label>
				  		</div>
				    </div>
				    <div class="modal-footer">
				    	<input class="modal-action modal-close waves-effect waves-green btn-flat" type="submit" value="Confirm">
				    </div>
				</form>
			</div>
	
		<div id="passwordError" class="modal">
			    <div class="modal-content">
			      <p style="color: #ef5350">The password is wrong!</p>
			    </div>
			    <div class="modal-footer">
			      <a class=" modal-action modal-close waves-effect waves-green btn-flat">OK</a>
			    </div>
		</div>
	
<!-- 	<br>

	<div class="input-field">
					<input name="bookTypes" value="<s:property value="bookTypes"/>" type="hidden">
				</div>

		 <s:iterator value="bookTypes" var="type">
	      	<s:property value="#type.id"/><br>
	      </s:iterator>
	      
	      <s:iterator value="bookTypes" var="type">
				      	 <option value="<s:property value="#type.id"></s:property>"><s:property value="#type.name"></s:property></option>
				      </s:iterator>
	<br> -->
	
	<span id="code" style="visibility: hidden;"><s:property value="errorCode"/></span>
	
	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script type="text/javascript">
    	$(document).ready(function() {
		    $('select').material_select();
		    $('.modal-trigger').leanModal();
		    
		    var code = $('#code').text();
		    if(code == 6){
		    	$('#passwordError').openModal();
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
