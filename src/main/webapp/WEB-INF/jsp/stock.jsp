<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>成都市房地产市场调控信息监测平台</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="screen" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts-all.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
</head>
<body>
<div class="noMove">
    <div class="top">
        <div class="logo">
            成都市房地产市场调控信息监测平台
        </div>
        <ul class="menu">
            <li class="on">可建面积监测</li>
            <li onclick="window.location='${pageContext.request.contextPath}/market/index'">楼市供销监测</li>
            <%--<li onclick="window.location='${pageContext.request.contextPath}/map/index'">项目地图</li>--%>
            <li onclick="window.location='${pageContext.request.contextPath}/stock/allemploy'">商业地产监测</li>
        </ul>
        <div class="user">成都市房管局</div>
        <div class="esc" onclick="window.location='${pageContext.request.contextPath}/user/esc'">退出</div>
    </div>
    <div class="banner">
        <div class="refreshTime">最近更新时间：${timeTemp}</div>
        <div class="showMsg">
            <label><input id="yearTemp" onclick="check('${pageContext.request.contextPath}/user/yearTemp',this.checked,this.value,'${pageContext.request.contextPath}/stock/index')"
                          class="refreshTime" name="yearTemp" type="checkbox" value="${yearTemp}"/>图表查询起始年份：${yearTemp}</label>
            图例：
            <span class="green">健康</span>
            <span class="yellow">警告</span>
            <span class="red">危险</span>
        </div>
    </div>
</div>
<div class="nav">
    <div class="typeMenu stock">
        <ul>
            <li data-stock="allland" class="on">
                <span>供地监测</span>
            </li>
            <li data-stock="allplan">
                <span>规划监测</span>
            </li>
            <li data-stock="allconstruct">
                <span>施工监测</span>
            </li>
            <li data-stock="allsell">
                <span>销售监测</span>
            </li>
            <%--<li data-stock="allemploy">--%>
                <%--<span>商业地产监测</span>--%>
            <%--</li>--%>
        </ul>
    </div>
    <div class="contentBox" id="stock">

    </div>
</div>
</body>

<script>
    $.get("${pageContext.request.contextPath}/stock/findStockList",function(data){
        if(data.success == true) {
            var stockList = data.result.list;
//            console.debug(stockList);
            for(var i=0;i<stockList.length;i++){
                var code = stockList[i].stock_code;
                var val = stockList[i].stock_area
                if("1001"==code && val){
                    $("#1001").text(val);
                }
                if("1002"==code && val){
                    $("#1002").text(val);
                }
                if("1003"==code && val){
                    $("#1003").text(val);
                }
                if("1004"==code && val){
                    $("#1004").text(val);
                }
                if("1005"==code && val){
                    $("#1005").text(val);
                }
                if("1006"==code && val){
                    $("#1006").text(val);
                }
            }
        }
    });

    $(function(){
        $.get("${pageContext.request.contextPath}/user/yearChecked",function(data){
            if('true'==data.result){
                $("input[name=yearTemp]").attr("checked","true");
            }else {
                $("input[name=yearTemp]").removeAttr("checked");
            };
        });
    })





</script>

</html>
