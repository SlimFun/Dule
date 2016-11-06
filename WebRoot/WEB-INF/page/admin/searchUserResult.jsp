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
  			<form action="<%=basePath%>user/admin/searchUser" method="post">
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
					  	<img class="circle" style="height: 90px;" src="upload/<s:property value="userSearchResult.profile"/>">
					  	<s:property value="userSearchResult.username"/>
					  </div>
				      <div class="collapsible-body">
			      			<div class="row">
				      			<div class="col s8 offset-s1" style="margin-top: 5px;">
				      				Id: <s:property value="userSearchResult.id"/><br>
						      		Email: <s:property value="userSearchResult.email"/><br>
						      		Phone: <s:property value="userSearchResult.phone"/><br>
			      				</div>
				      		
				      			<div class="col s8 offset-s1" style="margin-top: 5px;">
					      			Account: <br>
					      			<s:if test="%{userSearchResult.account==null}">
					      				This user don`t have account yet.  <a href="#addAccount" class="modal-trigger btn-floating btn-small waves-effect waves-light red tooltipped" data-position="right" data-delay="50" data-tooltip="Add a account"><i class="material-icons">add</i></a>
					      			</s:if>
					      			<s:else>
					      				balance: <s:property value="userSearchResult.account.money"/> <a class="btn-floating btn-small waves-effect waves-light red tooltipped modal-trigger" href="#recharge" data-position="right" data-delay="50" data-tooltip="recharge"><i class="material-icons">add</i></a> <br><br>
					      				status: 
					      				<s:if test="%{userSearchResult.account.frozen==false}">
					      					Active &nbsp;&nbsp;&nbsp;
					      					<a href="<%=basePath%>user/admin/frozenAccount?id=<s:property value="userSearchResult.account.id"/>&userId=<s:property value="userSearchResult.id"/>" class="btn waves-effect waves-light tooltipped" data-position="right" data-delay="50" data-tooltip="Frozen this account" style="background-color: #ef5350">Frozen</a>
					      				</s:if>
					      				<s:else>
					      					Frozen &nbsp;&nbsp;&nbsp;
					      					<a href="<%=basePath%>user/admin/activeAccount?id=<s:property value="userSearchResult.account.id"/>&userId=<s:property value="userSearchResult.id"/>" class="btn waves-effect waves-light tooltipped" data-position="right" data-delay="50" data-tooltip="Active this account">Active</a>
										</s:else>
					      			</s:else>
				      			</div>
					  </div>
				    </li>
			  </ul>
			  
  		</div>
  	</div>
  	
  	<div id="addAccount" class="modal">
				<form action="<%=basePath%>user/admin/addAccount" method="post">
				    <div class="modal-content">
						<div class="input-field col s8 offset-s2">
				  			<input id="balance" type="number" class="validate" min="0" name="balance" required>
					        <label for="balance">Input the balance of account: </label>
				  		</div>
				    </div>
				    <input type="hidden" name="userId" value="<s:property value="userSearchResult.id"/>" />
				    <div class="modal-footer">
				    	<input type="submit" onclick="submitForm()" class=" modal-action waves-effect waves-green btn-flat" value="Add">
				    </div>
				</form>
			</div>
			
			<div id="recharge" class="modal">
				<form action="<%=basePath%>user/admin/recharge" method="post">
				    <div class="modal-content">
						<div class="input-field col s8 offset-s2">
				  			<input id="balance" type="number" class="validate" name="balance" min="0" required>
					        <label for="balance">recharge: </label>
				  		</div>
				    </div>
				    <input type="hidden" name="userId" value="<s:property value="userSearchResult.id"/>" />
				    <div class="modal-footer">
				    	<input type="submit" onclick="submitForm()" class=" modal-action waves-effect waves-green btn-flat" value="Add">
				    </div>
				</form>
			</div>
  	
  	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script type="text/javascript">
    	$(document).ready(function(){
		    $('.modal-trigger').leanModal();
		  });
		  
		function submitForm(){
			var balance = $('#balance').attr("value"); 
			if(balance != null && !isNaN(val)){
				$('#userNotExists').closeModal();
			}
		};
    </script>
  </body>
</html>
