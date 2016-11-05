<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="css/materialize.min.css">
	<link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	
  </head>
  
  <body>
  
  	<div class="input-field col s12">
    <select>
      <option value="" disabled selected>Choose your option</option>
      <option value="1">Option 1</option>
      <option value="2">Option 2</option>
      <option value="3">Option 3</option>
    </select>
    <label>Materialize Select</label>
  </div>
  
  <form action="#">
    <p>
      <input type="checkbox"/>
      <label for="test5">asdfasdf</label>
    </p>
    <p>
      <input type="checkbox" id="test6" checked="checked" />
      <label for="test6">Yellow</label>
    </p>
    <p>
      <input type="checkbox" class="filled-in" id="filled-in-box" checked="checked" />
      <label for="filled-in-box">Filled in</label>
    </p>
    <p>
      <input type="checkbox" id="indeterminate-checkbox" />
      <label for="indeterminate-checkbox">Indeterminate Style</label>
    </p>
    <p>
      <input type="checkbox" id="test7" checked="checked" disabled="disabled" />
      <label for="test7">Green</label>
    </p>
      <p>
        <input type="checkbox" id="test8" disabled="disabled" />
        <label for="test8">Brown</label>
    </p>
  </form>
  
  <ul class="collapsible popout" data-collapsible="accordion">
    <li>
      <div class="collapsible-header"><i class="material-icons">filter_drama</i>First</div>
      <div class="collapsible-body"><p>Lorem ipsum dolor sit amet.</p></div>
    </li>
    <li>
      <div class="collapsible-header"><i class="material-icons">place</i>Second</div>
      <div class="collapsible-body"><p>Lorem ipsum dolor sit amet.</p></div>
    </li>
    <li>
      <div class="collapsible-header"><i class="material-icons">whatshot</i>Third</div>
      <div class="collapsible-body"><p>Lorem ipsum dolor sit amet.</p></div>
    </li>
  </ul>

 <div class="row">
 	<div class="col s8 offset-s2">
 		 <ul>
		 	<li><a class="subheader">Message</a></li>
		    <li><div href="#!">Message<span class="new badge red">4</span></div> </li>
		    <li><div class="divider"></div></li>
		    <li><a href="<%=basePath%>user/logout"><i class="material-icons">message</i>Logout</a></li>
	 	</ul>
 	</div>
 </div>
	

<div style="width: 35px;height:20px;font-weight: 300;font-size: 0.8rem;color: #fff;background-color:#F44336;border-radius:4px;text-align: center;">new</div>

    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script type="text/javascript">
    	$(".button-collapse").sideNav();
    	$(document).ready(function() {
    $('select').material_select();
  });
    </script>
  </body>
</html>
