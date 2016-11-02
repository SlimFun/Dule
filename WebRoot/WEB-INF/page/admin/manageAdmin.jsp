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
	      
	      <s:if test="%{errorCode==2}">
				<div>
	                 <span style="color: red;">User not exists.</span>
	            </div>
           </s:if>
  		</div>
  		<div class="col s8 offset-s2">
  			<ul class="collapsible popout" data-collapsible="accordion">
  				<s:iterator value="admins.resultList" var="ad">
  					<li>
				      <div class="collapsible-header" style="vertical-align: middle;">
					  	<img class="circle" style="height: 90px;" src="upload/<s:property value="#ad.profile"/>">
					  	<s:property value="#ad.username"/>
					  </div>
				      <div class="collapsible-body">
				      	<div class="row">
				      		<div class="col s8 offset-s1" style="margin-top: 5px;">
					      		Id: <s:property value="#ad.id"/><br>
					      		Email: <s:property value="#ad.email"/><br>
					      		Phone: <s:property value="#ad.phone"/><br>
							  	<form action="<%=basePath%>user/admin/updateAdminPrio" method="post">
							  		Priorities:<br>
						  			<s:iterator value="allPriorities" var="pri">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							  			<input type="checkbox" name="priorities" value="<s:property value="#pri"/>" id="<s:property value="#pri"/>_<s:property value="#ad.id"/>" <s:if test="%{#ad.containPriority(#pri)}">checked="checked"</s:if> <s:if test="%{#pri.ordinal()==0||#pri.ordinal()==1}">checked="checked" disabled="disabled"</s:if>/>
					      				<label for="<s:property value="#pri"/>_<s:property value="#ad.id"/>"><s:property value="#pri"/></label><br>
				      				</s:iterator>
				      				<input type="hidden" value="<s:property value="#ad.id"/>" name="id" />
				      				<button class="btn waves-effect waves-light" type="submit" style="margin-top: 8px;">Change</button>
							  	</form>
						  	</div>
					  	</div>
					  </div>
				    </li>
  				</s:iterator>
			  </ul>
  		</div>
  	</div>
  </body>
</html>
