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
  				<s:iterator value="#request.messages" var="msg">
  					<li onclick="removeNew(<s:property value="#msg.id"/>)">
				      <div class="collapsible-header" style="vertical-align: middle;">
				      	<div class="row">
				      		<div class="col s10">
						      	${msg.header }
						      	&nbsp;&nbsp;&nbsp;<span style="color: #CECFD1;font-size: 5px;"><s:property value="#msg.timeAgo"/></span>
						  	</div>
						  	<s:if test="%{#msg.hasRead==false}">
						  		<span id="new<s:property value="#msg.id"/>" style="width: 35px;height:20px;font-weight: 300;font-size: 0.8rem;color: #fff;background-color:#F44336;border-radius:4px;text-align: center;margin-left: 100px;padding-left: 6px;padding-right: 6px;padding-top: 4px;padding-bottom: 4px;">new</span>
					  		</s:if>
					  	</div>
					  </div>
				      <div class="collapsible-body">
				      	<div class="row">
				      		<p>
				      			${msg.content }
						  	</p>
					  	</div>
					  </div>
				    </li>
  				</s:iterator>
			  </ul>
  		</div>
  	</div>
  	
  	<script type="text/javascript">
  		var curWwwPath=window.document.location.href;  
		var pathName=window.document.location.pathname;  
		var pos=curWwwPath.indexOf(pathName);  
		var localhostPaht=curWwwPath.substring(0,pos);  
		var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
		var baseRoot = localhostPaht+projectName;  
  	
  		function removeNew(id){
  			if($('#new'+id) != null){
  				$('#new'+id).hide();
  			
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
	    						$('#newsBadge').html(obj.msgToRead + " news");
	    					}else{
	    						$('#newsBadge').hide();
	    					}
	    				}else{
	
	    				}
	    			}
	  			};
	  		
	  			xmlhttp.open("POST",baseRoot+ "/user/hasReadMessage?id="+id,true);
	  			xmlhttp.send();
  			}
  		};
  	</script>
  </body>
</html>
