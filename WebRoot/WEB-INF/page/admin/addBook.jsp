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
	  	<form action="<%=basePath%>user/admin/addBook" class="col s12" method="post"  enctype="multipart/form-data">
	  		<div class="row">
		  		<div class="input-field col s8 offset-s2">
		  			<input id="name" type="text" class="validate" name="book.bookInfo.name" required>
			        <label for="name">Book Name：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			<input id="author" type="text" class="validate" name="book.bookInfo.author" required>
			        <label for="author">Author：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			<input id="publisher" type="text" class="validate" name="book.bookInfo.publisher" required>
			        <label for="publisher">Publisher：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			<input type="date" id="dateOfPub" class="datepicker" name="dateOfPub" format="dd-mm-yyyy" required>
		  			<label for="dateOfPub">DateOfPub：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
		  			<input id="position" type="text" class="validate" name="book.position" required>
			        <label for="position">Location：</label>
		  		</div>
		  		<div class="input-field col s8 offset-s2">
				    <select name="typeId">
				      <option value="" disabled selected>Select the Type of Book</option>
				      <s:iterator value="bookTypes" var="type">
				      	 <option value="<s:property value="#type.id"></s:property>"><s:property value="#type.name"></s:property></option>
				      </s:iterator>
				    </select>
				    <label>Type of Book：</label>
				</div>
				<div class="file-field input-field col s8 offset-s2">
			      <div class="btn">
			        <span>File</span>
			        <input type="file" name="file">
			      </div>
			      <div class="file-path-wrapper">
			        <input class="file-path validate" type="text" required>
			      </div>
			    </div>
		  		<div class="input-field col s6 offset-s4">
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
