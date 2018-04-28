<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta  http-equiv="Refresh" content="0;url=<%=basePath%>user/index"/>
    <title>成都市房地产市场调控信息填报平台</title>
</head>
<body>
<p>请稍等....</p>
</body>
</html>
