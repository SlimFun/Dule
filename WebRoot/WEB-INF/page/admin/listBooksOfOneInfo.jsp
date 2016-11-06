<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page language="java" import="cn.edu.dule.beans.Admin" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Admin admin = (Admin)session.getAttribute("admin");
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
    <jsp:include page="/WEB-INF/page/share/admin/main_model.jsp"></jsp:include>
    
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
			    <div class="col s2">
			        	<div class="row">
			        		<c:if test="<%=admin.containPriority("UPDATE_BOOK") %>">
			        			<a  class="col s12 waves-effect waves-light btn modal-trigger" href="#updateInfo" style="width: 100px">Update</a>
			        		</c:if>
			        		<c:if test="<%=admin.containPriority("ADD_BOOK") %>">
			        			<a class="col s12 waves-effect waves-light btn modal-trigger" style="width: 100px;margin-top: 5px;background-color: #009FC6;" href="#addOneBook">AddBook</a>
			        		</c:if>
			        		<c:if test="<%=admin.containPriority("DELETE_BOOK") %>">
			        			<a class="col s12 waves-effect waves-light btn modal-trigger" style="width: 100px;margin-top: 5px;background-color: #ef5350" href="#deleteInfo">Delete</a>
			        		</c:if>
			        	</div>
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
			        		status : <s:property value="#book.status"/> <br>
			        		<s:if test="%{#book.borrowedStu!=null }">
			        			borrower_id : <s:property value="#book.borrowedStu.id"/> 
			        		</s:if>
			        		
			        	</p>
	        		</div>
	        		<div class="col s2">
			        	<div class="row">
			        		<c:if test="<%=admin.containPriority("UPDATE_BOOK") %>">
			        			<a href="<%=basePath%>user/admin/toUpdateBook?id=<s:property value="#book.id"/>" class="col s12 waves-effect waves-light btn" style="width: 100px">Update</a>
			        		</c:if>
			        		<c:if test="<%=admin.containPriority("DELETE_BOOK") %>">
			        			<a id="deleteButoon" class="col s12 waves-effect waves-light btn modal-trigger" href="#modal1" style="width: 100px;margin-top: 5px;background-color: #ef5350">Delete</a>
			        		</c:if>
			        		<s:if test="%{#book.status.ordinal()==0 }">
			        			<a href="<%=basePath%>user/admin/ReturnBook?bookId=<s:property value="#book.id"/>" class="col s12 waves-effect waves-light btn" style="width: 100px;margin-top: 5px;">Return</a>
			        		</s:if>
			        		<s:else>
			        			<a href="#borrowBook" class="col s12 waves-effect waves-light btn modal-trigger" style="width: 100px;margin-top: 5px;">Borrow</a>
			        		</s:else>
			        	</div>
		        	</div>
	        	</div>
	        </div>
	        
	        <div id="borrowBook" class="modal">
				<form action="<%=basePath%>user/admin/BorrowBook" method="post">
				    <div class="modal-content">
						<div class="input-field col s8 offset-s2">
				  			<input id="position" type="text" class="validate" name="userId">
					        <label for="position">Input borrower`s id: </label>
				  		</div>
				    </div>
				    <input type="hidden" name="bookId" value="<s:property value="#book.id"/>" />
				    <div class="modal-footer">
				    	<input type="submit" class=" modal-action modal-close waves-effect waves-green btn-flat" value="Borrow">
				    </div>
				</form>
			</div>
	        
        </s:iterator>
      </div>
    </div>
    
    <div id="modal1" class="modal">
			    <div class="modal-content">
			      <h4 style="color: #ef5350">WARNING!!!</h4>
			      <p style="color: #ef5350">Are you sure delete the book?</p>
			    </div>
			    <div class="modal-footer">
			      <a href="<%=basePath%>user/admin/deleteBook?id=<s:property value="#book.id"/>&infoId=<s:property value="#book.bookInfo.id"/>" class=" modal-action modal-close waves-effect waves-green btn-flat">YES</a>
			    </div>
			</div>
			
			<div id="updateInfo" class="modal">
			    <div class="modal-content">
			      <h4 style="color: #ef5350">WARNING!!!</h4>
			      <p style="color: #ef5350">This will change all the books' info.Are you sure do this?</p>
			    </div>
			    <div class="modal-footer">
			      <a href="<%=basePath%>user/admin/toUpdateBookInfo?id=<s:property value="bookInfo.id"/>" class=" modal-action modal-close waves-effect waves-green btn-flat">YES</a>
			    </div>
			</div>
			
			<div id="addOneBook" class="modal">
				<form action="<%=basePath%>user/admin/addBookToInfo" method="post">
				    <div class="modal-content">
						<div class="input-field col s8 offset-s2">
				  			<input id="position" type="text" class="validate" name="book.position">
					        <label for="position">Input the location of the book：</label>
				  		</div>
				    </div>
				    <input type="hidden" name="infoId" value="<s:property value="bookInfo.id"/>" />
				    <div class="modal-footer">
				    	<input type="submit" class=" modal-action modal-close waves-effect waves-green btn-flat" value="Add">
				    </div>
				</form>
			</div>
			
			<div id="deleteInfo" class="modal">
			    <div class="modal-content">
			      <h4 style="color: #ef5350">WARNING!!!</h4>
			      <p style="color: #ef5350">This will delete all the books.Are you sure do this?</p>
			    </div>
			    <div class="modal-footer">
			      <a href="<%=basePath%>user/admin/deleteBookInfo?id=<s:property value="bookInfo.id"/>" class=" modal-action modal-close waves-effect waves-green btn-flat">YES</a>
			    </div>
			</div>
			
			<div id="bookHasBeanBorrowed" class="modal">
			    <div class="modal-content">
			      <p style="color: #ef5350">Book has bean borrowed, you can't delete it now.</p>
			    </div>
			    <div class="modal-footer">
			      <a class=" modal-action modal-close waves-effect waves-green btn-flat">OK</a>
			    </div>
		</div>
			
	<span id="code" style="visibility: hidden;"><s:property value="errorCode"/></span>
    
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script type="text/javascript">
    	$(document).ready(function(){
		    $('.modal-trigger').leanModal();
		    
		    var code = $('#code').text();
		    if(code == 13){
		    	$('#bookHasBeanBorrowed').openModal();
		    }
		  });
    </script>
  </body>
</html>
