<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	<jsp:include page="/WEB-INF/page/share/include.jsp"></jsp:include>

  </head>
  
  <body>
  	<jsp:include page="/WEB-INF/page/share/admin/main_model.jsp"></jsp:include>
  	
  	<div class="row">
  		<s:if test="%{book!=null }">
  			<div class="col s3" style="margin: 0px;margin-top: 30px; height: 190px;width: 335px">
			    <div class="card">
			      <div class="row">
			      		<div class="col s4" style="padding: 0px">
			      			
					      	<div class="card-image">
					        	<img class="activator" src="upload/<s:property value="book.bookInfo.img"></s:property>">
				        	</div>
			      		</div>
			      		<div class="col s8">
					        	<p>
					        	Book Name：<s:property value="book.bookInfo.name"></s:property><br>
				        		Author：<s:property value="book.bookInfo.author"></s:property><br>
				        		Publisher：<s:property value="book.bookInfo.publisher"></s:property><br>
				        		DateOfPublish：<s:date name="book.bookInfo.dateOfPub"   format="yyyy-MM-dd" nice="false"/><br>
				        		Type：<s:property value="book.bookInfo.type.name"></s:property><br> 
				        		BookId：<s:property value="book.id"></s:property><br> 
				        		</p>
					    </div>
			      </div>
			      <div class="card-reveal">
			      	<div id="content">
				      <span class="card-title grey-text text-darken-4">详情<i class="material-icons right">close</i></span>
				     	<s:if test="%{book.status.ordinal()==0 }">
					      <p>
					      		Book Status: Borrowed
					      </p>
					      <a class="waves-effect waves-light btn" href="<%=basePath%>/user/admin/ReturnBook?bookId=<s:property value="book.id"/>">Return</a>
				   	    </s:if>
				   	    <s:else>
				   	  	  <p>
					      		Book Status: OnBoard
					      </p>
					      <a class="waves-effect waves-light btn modal-trigger" href="#borrowBook">Borrow</a>
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
				    <input type="hidden" name="bookId" value="<s:property value="book.id"/>" />
				    <div class="modal-footer">
				    	<input type="submit" class=" modal-action modal-close waves-effect waves-green btn-flat" value="Borrow">
				    </div>
				</form>
			</div>
  		</s:if>
  		<s:iterator value="pageView.datas" var="data">
	  		<div class="col s3" style="margin: 0px;margin-top: 30px; height: 190px;width: 335px">
			    <div class="card">
			      <div class="row">
			      		<div class="col s4" style="padding: 0px">
			      			
					      	<div class="card-image" onclick="loadBookNumbers(<s:property value="#data.id"/>)">
					        	<img class="activator" src="upload/<s:property value="#data.img"></s:property>">
				        	</div>
			      		</div>
			      		<div class="col s8">
					        	<p>
					        	Book Name：<s:property value="#data.name"></s:property><br>
				        		Author：<s:property value="#data.author"></s:property><br>
				        		Publisher：<s:property value="#data.publisher"></s:property><br>
				        		DateOfPublish：<s:date name="#data.dateOfPub"   format="yyyy-MM-dd" nice="false"/><br>
				        		Type：<s:property value="#data.type.name"></s:property><br> 
				        		</p>
					    </div>
			      </div>
			      <div class="card-reveal">
			      	<div id="content<s:property value="#data.id"/>">
				      <span class="card-title grey-text text-darken-4">Info<i class="material-icons right">close</i></span>
				      <p>
				      		There is <span id="totalNumberSpan<s:property value="#data.id"/>"></span> books<br/>
				      		There is <span id="onBoardNumberSpan<s:property value="#data.id"/>"></span> books on board<br/>
				      </p>
				      <a class="waves-effect waves-light btn" href="<%=basePath%>/user/admin/listBooksOfOneInfo?infoId=<s:property value="#data.id"/>">查看所有</a>
				   	</div>
					<div id="loading<s:property value="#data.id"/>" class="preloader-wrapper big active">
					    <div class="spinner-layer spinner-blue-only">
					      <div class="circle-clipper left">
					        <div class="circle"></div>
					      </div><div class="gap-patch">
					        <div class="circle"></div>
					      </div><div class="circle-clipper right">
					        <div class="circle"></div>
					      </div>
					    </div>
				  </div>
			    </div>
	  		</div>
	  	  </div>
	  	</s:iterator>
	  	
	  	
	  	
	  	
	</div>
	
	<%-- <ul class="pagination">
	   <li <c:choose><c:when test="${currentPage==1 }">class="disabled"</c:when><c:otherwise>class="waves-effect"</c:otherwise></c:choose>><a href="<%=basePath%>user/admin/manageBook?currentPage=${currentPage-1 }"><i class="material-icons">chevron_left</i></a></li>
	   <c:forEach begin="${startPage }" end="${endPage }" var="page">
	   	<li <c:choose><c:when test="${currentPage==page }">class="active"</c:when><c:otherwise>class="waves-effect"</c:otherwise></c:choose>><a href="<%=basePath%>user/admin/manageBook?currentPage=${page }">${page }</a></li>
	   </c:forEach>
	   <li <c:choose><c:when test="${currentPage==totalPage }">class="disabled"</c:when><c:otherwise>class="waves-effect"</c:otherwise></c:choose>><a href="<%=basePath%>user/admin/manageBook?currentPage=${currentPage+1 }"><i class="material-icons">chevron_right</i></a></li>
	
	 </ul> --%>
	 
  	
	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
	<script type="text/javascript" src="js/materialize.min.js"></script>
	<script type="text/javascript">
	
		var curWwwPath=window.document.location.href;  
		var pathName=window.document.location.pathname;  
		var pos=curWwwPath.indexOf(pathName);  
		var localhostPaht=curWwwPath.substring(0,pos);  
		var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
		var baseRoot = localhostPaht+projectName;  
	
		$(function(){
			$("div.card-image").click(function(){
			    //$("#content").hide();
			    //$("#loading").show();
			    $("#content").show();
			});
		});
		
		$(document).ready(function(){
		    $('.modal-trigger').leanModal();
		  });
		
		function loadBookNumbers(infoId){
			$("#content"+infoId).hide();
			$("#loading"+infoId).show();
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
    					$("#content"+infoId).show();
			    		$("#loading"+infoId).hide();
    					$("#totalNumberSpan"+infoId).text(obj.totalNumber);
    					$("#onBoardNumberSpan"+infoId).text(obj.onBoardNumber);
    				}else{

    				}
    			}
  			};
  			
  			xmlhttp.open("POST",baseRoot+ "/user/admin/getBookNumbers?infoId="+infoId,true);
  			xmlhttp.send();
  			
		};
	</script>
  </body>
</html>
