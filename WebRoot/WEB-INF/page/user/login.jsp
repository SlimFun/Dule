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
    
    <title>My JSP 'index.jsp' starting page</title>
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
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<style type="text/css">
		.warp {   
		  position: absolute;   
		  width:500px;   
  		  height:200px; 
		  left:50%;   
		  top:50%;   
		  margin-left:-250px;   
		  margin-top:-100px;   
		}   
	</style>
  </head>
  
  <body>
  	<dir class="warp">
  		<div class="row">
  			
  			<form class="col s12" action="<%=basePath%>/user/login" method="post">
  				<div class="row">
  					<div class="col s8 offset-s2">
  						<img alt="logo" src="images/logo.png">
  					</div>
  					<div class="col s8 offset-s2">
  						<br>
  					</div>
  					<div class="input-field col s8 offset-s2">
			          	<input id="id" type="text" class="validate" name="loginName" value="<s:property value="loginName"/>" required>
			          	<label for="id">Id/Username/Email: </label>
			        </div>
			        <div class="input-field col s8 offset-s2">
			          	<input id="id" type="password" class="validate" name="password" required>
			          	<label for="id">Password: </label>
			        </div>
			        <div class="col s6 offset-s4">
			        	<div class="row">
			        		<div class="input-field col s6">
					        	<button class="btn waves-effect waves-light" type="submit">
								    <i class="material-icons right">send</i>login
								</button>
							</div>
							<div class="col s6">
								<s:if test="%{errorCode==5}">
									<div>
			                                <span style="color: red;">User not exists.</span>
			                        </div>
		                        </s:if>
		                        <s:if test="%{errorCode==6}">
									<div>
			                                <span style="color: red;">The password is wrong.</span>
			                        </div>
		                        </s:if>
	                        </div>
                        </div>
			        </div>
  				</div>
  			</form>
  		</div>
    </dir>
    
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
  </body>
</html>
