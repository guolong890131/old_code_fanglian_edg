<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>登录-成都市房地产市场调控信息填报平台</title>
    <meta name="keywords" content="登录">
    <meta name="description" content="登录">

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style-min.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/funi-min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>


    <%--<!--                       CSS                       -->--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/color.css">

</head>
<body class="login-bg" >
<div class="login-wrap">
    <h1>成都市房地产市场调控信息填报平台</h1>

    <div id="loginDiv" class="login-content">
        <form id="login"  data-back="login"
              method="post">

            <div class="tips"></div>
            <ul>
                <li>
                    <input type="text" name="account" placeholder="输入用户名"
                           class="user-icon" autocomplete="off" maxlength="20" data-rule="required">
                </li>
                <li>
                    <input type="password" name="password" placeholder="输入密码"
                           class="password-icon" autocomplete="off" maxlength="20" data-rule="required">
                </li>
                <li>
                    <input type="text" name="code" placeholder="请输入验证码"
                           class="required-icon" autocomplete="off" maxlength="16" data-rule="required">
                    <img id="img" src="${pageContext.request.contextPath}/tool/verifyCode" title="看不清?换一张" class="yzm"
                        data-source="${pageContext.request.contextPath}/tool/verifyCode"
                         onclick="this.src='${pageContext.request.contextPath}/tool/verifyCode?'+Math.random()"/>
                </li>
                <font color="red"><div id="tips">${message}</div></font>
                <li>
                    <button id="submit" type="button" class="submit-icon">登 录</button>
                </li>
            </ul>
        </form>
    </div>

    <div id="editDiv" class="login-content" style="display:none" >
        <form id="editPass"  data-back="login"
              method="post">

            <font color="red"><div class="tips">密码已失效，请修改密码！</div></font>
            <ul>
                <li>
                    <input type="password" name="oldPassen" placeholder="输入旧密码"
                           class="password-icon" autocomplete="off" maxlength="20" data-rule="required">
                </li>
                <li>
                    <input type="password" name="newPassen" placeholder="输入新密码"
                           class="password-icon" autocomplete="off" maxlength="20" data-rule="required">
                </li>
                <li>
                    <input type="password" name="rePassen" placeholder="再次输入新密码"
                           class="password-icon" autocomplete="off" maxlength="20" data-rule="required">
                </li>
                <font color="red"><div id="tips2"></div></font>
                <li>
                    <button id="edit" type="button" class="submit-icon">确 认</button>

                </li>
            </ul>
        </form>
    </div>



    <p style="color: yellow">系统运行环境要求：win7+Chrome浏览器或IE10及以上</p>
</div>
<!--验证码-->
<script id="tpl-code" type="text/html">
    <input type="text" name="code" placeholder="请输入验证码"
           class="required-icon" autocomplete="off" maxlength="16" data-rule="required"><img
            src="https://user.funi.com/user/getRondImg.json" title="看不清?换一张" class="yzm"
            data-source="https://user.funi.com/user/getRondImg.json"/>

</script>
<script>
    var uuid = null;

    $("#submit").click(function () {

        if(!$("input[name = account]").val()){
            alert("请输入用户名！")
            return;
        };
        if(!$("input[name = password]").val()){
            alert("请输入密码！")
            return;
        };
        if(!$("input[name = code]").val()){
            alert("请输入验证码！")
            return;
        };

        $("#tips").text("");
        var paramObj = {};
        $.each($('#login').serializeArray(),function(i,data){
            paramObj[data.name] = data.value;
        });
        $.post("${pageContext.request.contextPath}/user/loginProcess",paramObj, function (data) {
            if(data.result=='0'){
                window.location.href="${pageContext.request.contextPath}/user/main"
            }else if(data.result=='2'){
                uuid = data.uuid
                $("#loginDiv").css("display", "none");
                $("#editDiv").css("display", "block");
            }else{
                $("#tips").text(data.message);
                $("#img").attr("src","${pageContext.request.contextPath}/tool/verifyCode?"+Math.random());
            };
        })
    })



    //保存用户密码
    $("#edit").click(function (){
        if(null==uuid){
            alert("请先登录！")
            return;
        }
        if(!$("input[name = oldPassen]").val()){
            alert("请输入旧密码！")
            return;
        };
        if(!$("input[name = newPassen]").val()){
            alert("请输入新密码！")
            return;
        };
        if(!$("input[name = rePassen]").val()){
            alert("请再次输入新密码！")
            return;
        };

        $("#tips2").text("");
        var paramObj = {};
        $.each($('#editPass').serializeArray(),function(i,data){
            paramObj[data.name] = data.value;
        });
        paramObj['uuid'] = uuid;
        $.post("${pageContext.request.contextPath}/users/dateEdit",paramObj, function (data) {
            if(data.status=='success') {
                Cmessager.alert(GV.MESSAGER_TITLE, data.message, 'info');
                $("#loginDiv").css("display", "block");
                $("#editDiv").css("display", "none");
                $('#login').reset();
                $("#img").attr("src","${pageContext.request.contextPath}/tool/verifyCode?"+Math.random());
            }else{
                $("#tips2").text(data.message);
            }
        })
    })

</script>
</body>
</html>
