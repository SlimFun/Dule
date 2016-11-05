<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page language="java" import="cn.edu.dule.beans.Admin" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Admin admin = (Admin)session.getAttribute("admin");
%>
<%@ taglib uri="/struts-tags" prefix="s" %>



<nav class="navbar-fixed" style="background-color: #009FC6;">
	  <a href="#" id="button-collapse" data-activates="slide-out" class="brand-logo" style="color: white-space;margin-left: 5px">
	  	<i class="material-icons">reorder</i>
	  </a>
	  <ul id="nav-mobile" class="right hide-on-med-and-down">
	        <form action="<%=basePath%>user/admin/searchBook" method="post">
		        <div class="input-field">
		          <input id="search" type="search" name="searchInfo" required>
		          <label for="search"><i class="material-icons">search</i></label>
		          <i class="material-icons">close</i>
		        </div>
	      </form>
      </ul>
	</nav>
  
  <ul id="slide-out" class="side-nav">
    <li><div class="userView">
      <img class="background" src="images/menu_background.jpg">
      <a href="#!user"><img class="circle" src="upload/<s:property value="#session.admin.profile"/>"></a>
      <a href="#!name">
	      <span class="white-text name">
		  	<!-- <s:property value="#session.user.username"/> -->
		  	<s:property value="#session.admin.username"/>
		  </span>
	  </a>
      <a href="#!email">
      	<span class="white-text email">
			<s:property value="#session.admin.email"/>
		</span>
	  </a>
    </div></li>
    <li><a href="<%=basePath%>/user/admin/editPersonInfo"><i class="material-icons">person_pin</i>Person Info</a></li>
    <li><div class="divider"></div></li>
    <c:if test="<%=admin.containPriority("MANAGE_BOOK")||admin.containPriority("ADD_BOOK") %>">
    	<li><a class="subheader">Books</a></li>
    </c:if>
    
    <c:if test="<%=admin.containPriority("MANAGE_BOOK") %>">
	    <li><a href="<%=basePath%>/user/admin/manageBook?currentPage=1"><i class="material-icons">assignment</i>Manage Books</a></li>
    </c:if>
    <c:if test="<%=admin.containPriority("ADD_BOOK") %>">
    	<li><a href="<%=basePath%>user/admin/toAddBook"><i class="material-icons">loyalty</i>Add Book</a></li>
   	</c:if>
    
    <c:if test="<%=admin.getPriority()>=64 %>">
    	<li><a class="subheader">Administrator Manage</a></li>
    	<li><a href="<%=basePath%>/user/admin/addAdmin"><i class="material-icons">assignment</i>Add Admin</a></li>
    	<li><a href="<%=basePath%>/user/admin/manageAdmins"><i class="material-icons">assignment</i>ManageAdmins</a></li>
    </c:if>
    
    <c:if test="<%=admin.containPriority("ACCOUNT_MANAGE") %>">
	    <li><a class="subheader">Account</a></li>
	    <li><a href="<%=basePath%>/user/admin/manageAccount"><i class="material-icons">message</i>ManageAccount</a></li>
    </c:if>
    
    <li><a class="subheader">Message</a></li>
    <li><a href="<%=basePath%>user/admin/messages"><i class="material-icons">message</i>Message</a></li>
  </ul>
  
  <c:if test="${admin==null }">
	<c:redirect url="/success.jsp"></c:redirect>
</c:if>
  	
  	<br>
  	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script type="text/javascript">
    	$("#button-collapse").sideNav();
    </script>