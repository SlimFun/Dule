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
    
    <title>My JSP 'listBooksOfOneInfo.jsp' starting page</title>
    
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
    
    <s:if test="%{#session.admin.account}">
  		Balance of Account: <s:property value="#session.admin.account.money"/>
  	</s:if>
    
    <div class="row">
      <div class="collection with-header col s8 offset-s2">
      	<div href="" class="collection-header">
			<div class="row">
	      		<div class="col s4" style="padding: 0px">
			        	<img style="height: 190px;" class="activator" src="upload/<s:property value="bookInfo.img"></s:property>">
	      		</div>
	      		<div class="col s6">
			        	<p>
			        	Book Name：<s:property value="bookInfo.name"></s:property><br>
		        		Author：<s:property value="bookInfo.author"></s:property><br>
		        		Publisher：<s:property value="bookInfo.publisher"></s:property><br>
		        		DateOfPub：<s:date name="bookInfo.dateOfPub"   format="yyyy-MM-dd" nice="false"/><br>
		        		Type：<s:property value="bookInfo.type.name"></s:property><br> 
		        		</p>
			    </div>
			</div>
		</div>
		<s:iterator value="books.datas" var="book">
	        <div href="#!" class="collection-item">
	        	<div class="row">
	        		<div class="col s10">
			        	<p>
			        		id : <s:property value="#book.id"/><br>
			        		location : <s:property value="#book.position"/><br>
			        		status : <s:property value="#book.status"/> 
			        	</p>
	        		</div>
	        	</div>
	        </div>
	        
        </s:iterator>
      </div>
    </div>
    
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script type="text/javascript">
    	$(document).ready(function(){
		    $('.modal-trigger').leanModal();
		  });
    </script>
  </body>
</html>
