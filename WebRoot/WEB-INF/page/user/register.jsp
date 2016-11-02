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
  	
  	<div class="row">
	  	<form action="<%=basePath%>user/doRegister" class="col s12" method="post"  enctype="multipart/form-data">
	  		<div class="row">
		  		<div class="input-field col s8 offset-s2">
		  			<input id="username" type="text" class="validate" name="registerForm.username" value="<s:property value="registerForm.username"/>" required>
			        <label for="username">Username：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			<input id="email" type="email" class="validate" name="registerForm.email" value="<s:property value="registerForm.email"/>" required>
			        <label for="email">Email：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			<input id="password" type="password" class="validate" name="registerForm.password" required>
			        <label for="password">Password：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			<input id="resumePassword" type="password" class="validate" name="registerForm.resumePassword" required>
			        <label for="resumePassword">Confirm password：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			<input id="phone" type="tel" class="validate" name="registerForm.phone" value="<s:property value="registerForm.phone"/>">
			        <label for="phone">Phone：</label>
		  		</div>
				<div class="file-field input-field col s8 offset-s2">
			      <div class="btn">
			        <span>File</span>
			        <input type="file" name="file">
			      </div>
			      <div class="file-path-wrapper">
			        <input class="file-path validate" type="text" >
			      </div>
			    </div>
		  		<div class="input-field col s6 offset-s4">
		        	<button class="btn waves-effect waves-light" type="submit">
					    <i class="material-icons right">send</i>Submit
					</button>
					
					<p style="color: red;">
						<s:if test="%{errorCode==2}">
							The passwords you entered must be the same.
						</s:if>
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
	
	
	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script type="text/javascript">
    	$(document).ready(function() {
		    $('select').material_select();
		});
		
		$('.datepicker').pickadate({
		    selectMonths: false, // Creates a dropdown to control month
		    selectYears: 15, // Creates a dropdown of 15 years to control year
		    format:"yyyy-mm-dd"
		  });
    </script>
  </body>
</html>
