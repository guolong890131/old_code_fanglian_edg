<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
</head>
<body>
<div class="noMove">
    <div class="top">
        <div class="logo">
            成都市房地产市场数据填报平台
        </div>
        <ul class="new-menu">
            <c:forEach var="a" items="${menu}" varStatus="b">
                <c:if test="${b.count==1}">
                    <li data-url="${pageContext.request.contextPath}/${a.menu_code}/index" class="on">${a.menu_name}</li>
                </c:if>
                <c:if test="${b.count!=1}">
                    <li data-url="${pageContext.request.contextPath}/${a.menu_code}/index">${a.menu_name}</li>
                </c:if>
            </c:forEach>
            <%--<li data-url="${pageContext.request.contextPath}/field/index" class="on">土地填报</li>--%>
            <%--<li data-url="${pageContext.request.contextPath}/layout/index">规划填报</li>--%>
            <%--<li data-url="${pageContext.request.contextPath}/build/index">施工填报</li>--%>
            <%--<li data-url="${pageContext.request.contextPath}/users/index">用户管理</li>--%>
            <%--<li data-url="${pageContext.request.contextPath}/log/index">系统日志</li>--%>
        </ul>
        <div class="user" onclick="edit()">${account}</div>
        <div class="esc" onclick="window.location='${pageContext.request.contextPath}/user/esc'">退出</div>
    </div>
</div>
<div class="nav new-nav">
    <iframe id="iframeId" scrolling="auto" frameborder="0"  src="${pageContext.request.contextPath}/${menu[0].menu_code}/index" style="width:100%;height:89%;"></iframe>
</div>
</body>
<script>


    $(function(){

        //菜单控制
        $(".new-menu").on("click","li",function(){
            var url = $(this).data("url");
            $(".new-menu li").removeClass("on");
            $("#iframeId").attr("src", url);
            $(this).addClass("on");
        });
        //账号相同的处理
       self.setInterval(login,5000);

    });
    //轮循检查是否有其他人登录
    function login(){
        $.get("${pageContext.request.contextPath}/users/checkLogin",function(data){
            console.debug(data.result);
            if(!data.result){
                    window.location='${pageContext.request.contextPath}/user/esc';
            }
        })
    }

    function edit(){
        openDialog("width:660,height:238,winId:'addUserWin',isIframe:false,title:'编辑',url: '${pageContext.request.contextPath}/users/view4fastEdit?uuid=${uuid}&&type=edit2',buttons:[{text: '确认',handler:function(){saveUser()}}]");
    }

    //保存用户信息
    function saveUser(){
        if($("#addUserForm").form('validate')){
            $("#addUserForm").form('submit', {
                url : '${pageContext.request.contextPath}/users/fastEdit',
                onSubmit : function() {
                    return $(this).form('validate');//对数据进行格式化
                },
                success : function(data) {
                    var dataObj=eval("("+data+")");
                    if(dataObj.status=='success') {
                        Cmessager.alert(GV.MESSAGER_TITLE, dataObj.message, 'info');
                        $("#addUserWin").dialog('close');
                    }else{
                        Cmessager.alert(GV.MESSAGER_TITLE, dataObj.message, 'error');
                    }
                }
            });
        }
    }

</script>
</html>
