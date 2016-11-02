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
  	<jsp:include page="/WEB-INF/page/share/admin/main_model.jsp"></jsp:include>
  	
  	<div class="row">
	  	<form action="<%=basePath%>user/admin/doUpdateAdminInfo" class="col s12" method="post">
	  		<div class="row">
	  			<div class="input-field col s8 offset-s2">
		  			<input id="Id" type="text" class="validate" name="admin.id" disabled value="<s:property value="admin.id"/>">
			        <label for="Id">Id：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			<input id="username" type="text" class="validate" name="admin.username" disabled value="<s:property value="admin.username"/>" required>
			        <label for="username">Username：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			<input id="email" type="email" class="validate" name="admin.email" disabled value="<s:property value="admin.email"/>" required>
			        <label for="email">Email：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			<input id="phone" type="tel" class="validate" name="admin.phone" disabled value="<s:property value="admin.phone"/>" required>
			        <label for="phone">Phone：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			Priorities:<br>
		  			<s:iterator value="allPriorities" var="pri">
			  			<input type="checkbox" name="priorities" value="<s:property value="#pri"/>" id="<s:property value="#pri"/>" <s:if test="%{#pri.ordinal()==0||#pri.ordinal()==1}">checked="checked" disabled="disabled"</s:if> <s:if test="%{admin.containPriority(#pri) }">checked="checked"</s:if> />
	      				<label for="<s:property value="#pri"/>"><s:property value="#pri"/></label><br>
      				</s:iterator>
				</div>
		  		<div class="input-field col s6 offset-s4">
		  		<br>
		        	<button class="btn waves-effect waves-light" type="submit">
					    <i class="material-icons right">send</i>Submit
					</button>
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
