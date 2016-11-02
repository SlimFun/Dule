<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'searchAdminResult.jsp' starting page</title>
    
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
  		<div class="col s6 offset-s2">
  			<form action="<%=basePath%>user/admin/searchAdmin" method="post">
		        <div class="input-field">
		          <input id="search" type="search" name="searchInfo" required>
		          <label for="search"><i class="material-icons">search</i></label>
		          <i class="material-icons">close</i>
		        </div>
	      </form>
  		</div>
  		<div class="col s8 offset-s2">
  			<ul class="collapsible popout" data-collapsible="accordion">
  					<li>
				      <div class="collapsible-header active" style="vertical-align: middle;">
					  	<img class="circle" style="height: 90px;" src="upload/<s:property value="admin.profile"/>">
					  	<s:property value="admin.username"/>
					  </div>
				      <div class="collapsible-body">
				      	<div class="row">
				      		<div class="col s8 offset-s1" style="margin-top: 5px;">
					      		Id: <s:property value="admin.id"/><br>
					      		Email: <s:property value="admin.email"/><br>
					      		Phone: <s:property value="admin.phone"/><br>
							  	<form action="<%=basePath%>user/admin/updateAdminPrio" method="post">
							  		Priorities:<br>
						  			<s:iterator value="allPriorities" var="pri">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							  			<input type="checkbox" name="priorities" value="<s:property value="#pri"/>" id="<s:property value="#pri"/>" <s:if test="%{admin.containPriority(#pri)}">checked="checked"</s:if> <s:if test="%{#pri.ordinal()==0||#pri.ordinal()==1}">checked="checked" disabled="disabled"</s:if>/>
					      				<label for="<s:property value="#pri"/>"><s:property value="#pri"/></label><br>
				      				</s:iterator>
				      				<input type="hidden" value="<s:property value="admin.id"/>" name="id" />
				      				<button class="btn waves-effect waves-light" type="submit" style="margin-top: 8px;">Change</button>
							  	</form>
						  	</div>
					  	</div>
					  </div>
				    </li>
			  </ul>
  		</div>
  	</div>
  	
  	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
  </body>
</html>
