<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生成pdf</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript">
		function btnClick(){
			var url = document.getElementById("url").value;
			this.location.href="<%=basePath%>convert?url="+url;
		}
	</script>
  </head>
  
  <body>
  	url:<input type="text" size="30" id="url">
  	<input type="button" value="生成pdf" onclick="btnClick()"/><br/>
  	
  	调用方法：<%=basePath%>convert?url=http://www.baidu.com<br/>
  	说明：url必须带http://
  	
  	
  </body>
</html>
