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
  
  <nav class="navbar-fixed" style="background-color: #009FC6;">
  	<s:if test="%{#session.user!=null }">
	  <a href="#" id="button-collapse" data-activates="slide-out" class="brand-logo" style="color: white-space;margin-left: 5px">
	  	<i class="material-icons">reorder</i>
	  </a>
	 </s:if>
	 <s:else>
	 	<a href="<%=basePath%>user/register" style="color: white-space;margin-left: 5px">Register  / </a>
	 	<a href="<%=basePath%>user/toLogin" style="color: white-space;margin-left: 5px">Login</a>
	 </s:else>
	  <ul id="nav-mobile" class="right hide-on-med-and-down">
	        <form action="<%=basePath%>user/searchBook" method="post">
	        <div class="input-field">
	          <input id="search" type="search" name="searchInfo" required>
	          <label for="search"><i class="material-icons">search</i></label>
	          <i class="material-icons">close</i>
	        </div>
	      </form>
      </ul>
	</nav>
  
  <body>
    <ul id="slide-out" class="side-nav">
    <li><div class="userView">
      <img class="background" src="images/menu_background.jpg">
      <a href="#!user"><img class="circle" src="upload/<s:property value="#session.user.profile"/>"></a>
      <a href="#!name">
	      <span class="white-text name">
		  	<s:property value="#session.user.username"/>
		  </span>
	  </a>
	  <a href="#!name">
	      <span class="white-text email">
	      	<s:if test="%{#session.user.account!=null}">
		      	<s:if test="%{#session.user.account.frozen==false}">
			  		balance: <s:property value="#session.user.account.money"/><br>
			  	</s:if>
			  	<s:else>
			  		Account has bean frozen.<br>
			  	</s:else>
		  	</s:if>
		  	<s:else>
		  		You don't have an account yet.
		  	</s:else>
		  	<s:property value="#session.user.email"/>
		  </span>
	  </a>
    </div></li>
    <li><a href="<%=basePath%>user/editPersonInfo"><i class="material-icons">person_pin</i>Person Info</a></li>
    <li><div class="divider"></div></li>
    <li><a class="subheader">Books</a></li>
    <li><a href="<%=basePath%>user/allBooks?currentPage=1"><i class="material-icons">assignment</i>All Books</a></li>
    <li><a href="<%=basePath%>user/borrowedBooks?currentPage=1"><i class="material-icons">assignment</i>Borrowed Books</a></li>
    <li><a href="<%=basePath%>user/focusedBooks?currentPage=1"><i class="material-icons">loyalty</i>Focused Books</a></li>
    <li><a class="subheader">Message</a></li>
    <li><a href="<%=basePath%>user/messages"><i class="material-icons">message</i>Message
    	<span id="newsBadge" style="font-weight: 300;font-size: 0.8rem;color: #fff;background-color:#F44336;border-radius:4px;text-align: center;padding-left: 6px;padding-right: 6px;padding-top: 4px;padding-bottom: 4px;margin-left: 17px;<s:if test='%{#session.msgToRead==0}'>visibility: hidden;</s:if>"><s:property value="#session.msgToRead"/> news</span>
    	</a> 
     </li>
    <li><div class="divider"></div></li>
    <li><a href="<%=basePath%>user/logout"><i class="material-icons">message</i>Logout</a></li>
  </ul>
    
    <br>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script type="text/javascript">
    
	    var curWwwPath=window.document.location.href;  
		var pathName=window.document.location.pathname;  
		var pos=curWwwPath.indexOf(pathName);  
		var localhostPaht=curWwwPath.substring(0,pos);  
		var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
		var baseRoot = localhostPaht+projectName;  
		
    	$("#button-collapse").sideNav();
    	
    	$(document).ready(function(){
    		var xmlhttp;
			if (window.XMLHttpRequest){
  				xmlhttp=new XMLHttpRequest();
  			}else{
  				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  			}
  			
  			xmlhttp.onreadystatechange=function(){
  				if (xmlhttp.readyState==4 && xmlhttp.status==200){
    				var result = xmlhttp.responseText;
    				var obj = eval('(' + result + ')');    
    				if(obj.status == 0){
    					if(obj.msgToRead != 0){
    						$('#newsBadge').show();
    						$('#newsBadge').html(obj.msgToRead+" news");
    					}else{
    						$('#newsBadge').hide();
    					}
    				}else{

    				}
    			}
  			};
  		
  			xmlhttp.open("POST",baseRoot+ "/user/resetUser",true);
  			xmlhttp.send();
    	});
    </script>
  </body>
</html>
