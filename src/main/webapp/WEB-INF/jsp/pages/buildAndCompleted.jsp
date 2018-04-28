<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
<head>
    <title>成都市房地产市场数据填报平台</title>
    <!--                       CSS                       -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/color.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="screen" />
    <!--                       Javascripts                       -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts-all.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
</head>
</head>
<body>
<div class="easyui-tabs" data-options="fit:true,border:false">
    <div title="施工证数据填报">
        <iframe scrolling="auto" frameborder="0"  src="${pageContext.request.contextPath}/build/build" style="width:100%;height:100%;"></iframe>
    </div>
    <div title="竣工备案数据填报">
        <iframe scrolling="auto" frameborder="0"  src="${pageContext.request.contextPath}/build/completed" style="width:100%;height:100%;"></iframe>
    </div>
</div>

</body>
</html>


