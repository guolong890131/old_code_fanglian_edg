<%--
  Created by IntelliJ IDEA.
  User: as
  Date: 2017/2/17 0017
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>排号</title>
    <style>
        body{
            text-align: center;
            background: url("${pageContext.request.contextPath}/images/bg.png") no-repeat;
            background-size: 100%;
            font-family: "Sans Noto", "Hiragino Sans GB", Arial, Helvetica, "微软雅黑", sans-serif;
        }
        .all{
            width: 80%;
            margin: 0 auto;
        }
        .now-time{
            color: #b253f4;
            margin: 50px 0 30px 0;
            font-size: 20px;
        }
        .big-number{
            float: left;
            width: 50%;
            color: #FFFFFF;
            height: 240px;
            background: -webkit-linear-gradient(#7069dc,#b75af5); /* Safari 5.1 - 6.0 */
            background: -o-linear-gradient(#7069dc,#b75af5); /* Opera 11.1 - 12.0 */
            background: -moz-linear-gradient(#7069dc,#b75af5); /* Firefox 3.6 - 15 */
            background: linear-gradient(#7069dc,#b75af5);
        }
        .big-number span{
            font-size: 70px;
            display: block;
            margin: 0 auto;
            margin-bottom: 10px;
            width: 80%;
            padding: 55px 0 35px 0;
            border-bottom: dashed 1px #ffffff;
        }
        .now-number{
            width: 50%;
            float: left;
            height: 120px;
            color: #FFFFFF;
            background: -webkit-linear-gradient(#0dc984,#97e27a); /* Safari 5.1 - 6.0 */
            background: -o-linear-gradient(#0dc984,#97e27a); /* Opera 11.1 - 12.0 */
            background: -moz-linear-gradient(#0dc984,#97e27a); /* Firefox 3.6 - 15 */
            background: linear-gradient(#0dc984,#97e27a);
        }
        .now-number span{
            display: block;
            margin: 0 auto;
            margin-bottom: 10px;
            width: 80%;
            font-size: 50px;
            padding: 15px 0 5px 0;
            border-bottom: dashed 1px #ffffff;
        }
        .pre-number{
            width: 50%;
            float: left;
            height: 120px;
            color: #FFFFFF;
            background: -webkit-linear-gradient(#31c1fb,#4de7ce); /* Safari 5.1 - 6.0 */
            background: -o-linear-gradient(#31c1fb,#4de7ce); /* Opera 11.1 - 12.0 */
            background: -moz-linear-gradient(#31c1fb,#4de7ce); /* Firefox 3.6 - 15 */
            background: linear-gradient(#31c1fb,#4de7ce);
        }
        .pre-number span{
            display: block;
            margin: 0 auto;
            margin-bottom: 10px;
            width: 80%;
            font-size: 50px;
            padding: 15px 0 5px 0;
            border-bottom: dashed 1px #ffffff;
        }
        .time-number{
            clear: both;
            float: left;
            width: 100%;
            color: #FFFFFF;
            padding: 10px 0;
            margin-top: 10px;
            background: #2bd7bb;
        }
    </style>
</head>

<body>
<div class="all">
    <div class="now-time">${time}</div>
    <div class="big-number"><span>${rankno}</span>领取号</div>
    <div class="now-number"><span>${currnetNo}</span>当前号</div>
    <div class="pre-number"><span>${waitNo}</span>等待数</div>
    <div class="time-number">预计等待时间<span>${waitTime}</span>分钟</div>
</div>
</body>
</html>

