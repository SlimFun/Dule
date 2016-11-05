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
  	
  	<div class="row">
  		<div class="col s8 offset-s2">
  			<ul class="collapsible popout" data-collapsible="accordion">
  				<s:iterator value="#session.user.messages" var="msg">
  					<li>
				      <div class="collapsible-header" style="vertical-align: middle;">
					  	<s:property value="#msg.header"/>
					  </div>
				      <div class="collapsible-body">
				      	<div class="row">
				      		<p>
				      			<s:property value="#msg.content"/>
						  	</p>
					  	</div>
					  </div>
				    </li>
  				</s:iterator>
			  </ul>
  		</div>
  	</div>
  </body>
</html>
