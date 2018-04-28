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
            <li onclick="window.location='${pageContext.request.contextPath}/stock/index'">可建面积监测</li>
            <li onclick="window.location='${pageContext.request.contextPath}/market/index'">楼市供销监测</li>
            <%--<li onclick="window.location='${pageContext.request.contextPath}/map/index'">项目地图</li>--%>
            <li class="on">商业地产监测</li>
        </ul>
        <div class="user">成都市房管局</div>
        <div class="esc" onclick="window.location='${pageContext.request.contextPath}/user/esc'">退出</div>
    </div>
    <div class="banner">
        <div class="refreshTime">最近更新时间：${timeTemp}</div>
        <div class="showMsg">
            <label><input id="yearTemp" onclick="check('${pageContext.request.contextPath}/user/yearTemp',this.checked,this.value,'${pageContext.request.contextPath}/stock/allemploy')"
                          class="refreshTime" name="yearTemp" type="checkbox" value="${yearTemp}"/>图表查询起始年份：${yearTemp}</label>
            图例：
            <span class="green">健康</span>
            <span class="yellow">警告</span>
            <span class="red">危险</span>
        </div>
    </div>
</div>
<div class="nav">
    <div class="contentBox allemploy">
<%--全域房屋可使用面积  stock_code = 1006--%>
        <div class="newHouseStock">
            <div class="newHouse">
                <div class="title">说明：房屋可使用量 =  全部已竣工且未使用的房屋面积</div>
                <div class="tip"><span>面积单位: 万㎡</span></div>
            </div>
            <div class="fuTitle">
                <div class="erTitle">各圈层统计</div>
                <div class="sanTitle">主城区统计</div>
            </div>
        </div>
        <div class="stockContent">
            <div class="stockContentMenu">
                <ul>
                    <li class="on" value="1001" data-text="主城区">
                        <span>主城区房屋可使用面积(万㎡)及环比(%)</span>
                        <b id="1001Area">--</b><b><span id="1001Rate">--</span></b>
                    </li>
                    <li value="1002" data-text="二圈层">
                        <span>二圈层房屋可使用面积(万㎡)及环比(%)</span>
                        <b id="1002Area">--</b><b><span id="1002Rate">--</span></b>
                    </li>
                    <li value="1003" data-text="三圈层">
                        <span>三圈层房屋可使用面积(万㎡)及环比(%)</span>
                        <b id="1003Area">--</b><b><span id="1003Rate">--</span></b>
                    </li>
                </ul>
            </div>
            <div class="otherContent">
                <div class="chartBox">
                    <div class="chartTitle">
                        <span class="text">业态统计</span>
                    </div>
                    <table cellpadding="0" cellspacing="0" width="100%" class="tableStage">
                        <tr>
                            <td><b>住宅</b></td>
                            <td><i id="a1001">--</i></td>
                            <td><b>环比</b></td>
                            <td><i id="r1001">--</i></td>
                            <td><b>商业</b></td>
                            <td><i id="a1002">--</i></td>
                            <td><b>环比</b></td>
                            <td><i id="r1002">--</i></td>
                        </tr>
                        <tr>
                            <td><b>办公</b></td>
                            <td><i id="a1003">--</i></td>
                            <td><b>环比</b></td>
                            <td><i id="r1003">--</i></td>
                            <td><b>车位</b></td>
                            <td><i id="a1004">--</i></td>
                            <td><b>环比</b></td>
                            <td><i id="r1004">--</i></td>
                        </tr>
                    </table>
                </div>
                <div class="chartBox">
                    <div class="chartTitle">
                        <span class="text">区域统计</span>
                        <span class="time">
                            <span class="on" id="typeReg1" data-type="chartOne" onclick="changeReginType('行政区域')">行政区域</span><span id="typeReg2" data-type="chartOne" onclick="changeReginType('重点片区')">重点片区</span>
                        </span>
                    </div>
                    <table cellpadding="0" cellspacing="0" width="100%" class="tableArea">
                        <thead>
                            <tr>
                                <td>区域</td>
                                <td>住宅</td>
                                <td>商业</td>
                                <td>办公</td>
                                <td>车位</td>
                            </tr>
                        </thead>
                        <tbody id="allStockTbody">
                            <tr>
                                <td class="area">金牛区</td>
                                <td>+1.00%</td>
                                <td>19</td>
                                <td>19</td>
                                <td>5</td>
                            </tr>
                            <tr>
                                <td class="area">高新区</td>
                                <td>50</td>
                                <td>19</td>
                                <td >19</td>
                                <td>5</td>
                            </tr>
                            <tr>
                                <td class="area">成华区</td>
                                <td>50</td>
                                <td>19</td>
                                <td >19</td>
                                <td>5</td>
                            </tr>
                            <tr>
                                <td class="area">锦江区</td>
                                <td>50</td>
                                <td>19</td>
                                <td>19</td>
                                <td>5</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="chartBox">
                    <div class="chartTitle">
                        <span class="text">使用情况统计</span>
                        <span class="time">
                            <span class="on" data-type="chartOne" id="inHouse" onclick="changeInOrOut('入住率')">入住率</span><span data-type="chartOne" id="outHouse" onclick="changeInOrOut('出租率')">出租率</span>
                        </span>
                    </div>
                    <table cellpadding="0" cellspacing="0" width="100%" class="tableArea">
                        <thead>
                        <tr>
                            <td>区域</td>
                            <td>住宅</td>
                            <td>商业</td>
                            <td>办公</td>
                        </tr>
                        </thead>
                        <tbody id="allStockTbody2">
                        <tr>
                            <td class="area">金牛区</td>
                            <td>50</td>
                            <td>+1.00%</td>
                            <td>5</td>
                        </tr>
                        <tr>
                            <td class="area">高新区</td>
                            <td>50</td>
                            <td>50</td>
                            <td>19</td>
                        </tr>
                        <tr>
                            <td class="area">成华区</td>
                            <td>50</td>
                            <td>50</td>
                            <td>19</td>
                        </tr>
                        <tr>
                            <td class="area">锦江区</td>
                            <td>50</td>
                            <td>50</td>
                            <td>5</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
<form id="queryValue">
    <input id="stockType" type="hidden" name="stock_code" value="1006">
    <input id="layerType" type="hidden" name="layer_code" value="1001">
    <input id="typeCode" type="hidden" name="type_code" value="1002">
    <input id="typeReg" type="hidden" name="flagReg" value="行政区域">
</form>
    </div>
</div>
</body>
<script type="text/javascript">

    $(function(){
        $.get("${pageContext.request.contextPath}/user/yearChecked",function(data){
            if('true'==data.result){
                $("input[name=yearTemp]").attr("checked","true");
            }else {
                $("input[name=yearTemp]").removeAttr("checked");
            };
        });
    })



    $(function(){
        $(".newHouseStock").css("width",$(".contentBox").width());
    });
    function queryStockLayer(){
        var stock_code =$("#stockType").serializeArray();
        $.get("${pageContext.request.contextPath}/stock/findStockLayer",stock_code,function(data){
            if(data.success == true){
                console.debug(data.result.list);
                var list = data.result.list;
                for(var i=0;i<list.length;i++){
                    var code = list[i].layer_code;
                    var area = list[i].stock_area;
                    var rate = list[i].rate;
                    if("1001"==code){
                        $("#1001Area").text(area);
                        $("#1001Rate").text(rate+'%');
                    }
                    if("1002"==code){
                        $("#1002Area").text(area);
                        $("#1002Rate").text(rate+'%');
                    }
                    if("1003"==code){
                        $("#1003Area").text(area);
                        $("#1003Rate").text(rate+'%');
                    }
                }
            }
        })
    }
    //查询圈层数据
    queryStockLayer();

    function queryFullCycleRegionStock(){
        $("#a1001").text(nullData);
        $("#r1001").text(nullData);
        $("#a1002").text(nullData);
        $("#r1002").text(nullData);
        $("#a1003").text(nullData);
        $("#r1003").text(nullData);
        $("#a1004").text(nullData);
        $("#r1004").text(nullData);
        var paramObj = {};
        $.each($("#queryValue").serializeArray(),function(i,d){
            paramObj[d.name] = d.value;
        });
//        console.debug(paramObj);
        $.get("${pageContext.request.contextPath}/stock/findStockStageUsage",paramObj,function(data){
//            console.debug(data);
            if(data.success == true){
//                console.debug(data.result.list);
                var list = data.result.list;
                for(var i=0;i<list.length;i++){
                    var code = list[i].usage_code;
                    var area = list[i].stock_area;
                    var rate = list[i].rate;
                    if("1001"==code){
                        $("#a1001").text(area);
                        $("#r1001").text(rate+'%');
                    }
                    if("1002"==code){
                        $("#a1002").text(area);
                        $("#r1002").text(rate+'%');
                    }
                    if("1003"==code){
                        $("#a1003").text(area);
                        $("#r1003").text(rate+'%');
                    }
                    if("1004"==code){
                        $("#a1004").text(area);
                        $("#r1004").text(rate+'%');
                    }
                }
            }
        })
    }
    //查询业态统计数据
    queryFullCycleRegionStock();

    //区域统计
    function queryRegionStock(){
        var paramObj = {};
        $.each($("#queryValue").serializeArray(),function(i,d){
            paramObj[d.name] = d.value;
        });
        $.get("${pageContext.request.contextPath}/stock/findEdgCanuseStock",paramObj,function(data){
            var list = data.result.list;
            console.debug(list);
            var trHtml = "";
            $.each(list,function(k,v){
                trHtml += "<tr>";
                if(v.regin != null){
                    trHtml += "<td class='area'>"+ v.regin +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.house != null){
                    trHtml += "<td>"+ v.house +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.business != null){
                    trHtml += "<td>"+ v.business +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.office != null){
                    trHtml += "<td>"+ v.office +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.stall != null){
                    trHtml += "<td>"+ v.stall +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                trHtml += "</tr>"
            });
            $("#allStockTbody").html(trHtml);
        });
    }
    //区域统计
    queryRegionStock();

    function changeReginType(type){
        $("#typeReg").val(type);
        if('行政区域'==type){
            $("#typeReg1").addClass("on");
            $("#typeReg2").removeClass("on");
            queryRegionStock();
        }
        if('重点片区'==type){
            $("#typeReg1").removeClass("on");
            $("#typeReg2").addClass("on");
            $("#allStockTbody").html('');
        }

    }

    //使用情况统计
    function queryRegionStock2(){
        var paramObj = {};
        $.each($("#queryValue").serializeArray(),function(i,d){
            paramObj[d.name] = d.value;
        });
        $.get("${pageContext.request.contextPath}/stock/findEdgCanuseRate",paramObj,function(data){
            var list = data.result.list;
            console.debug(list);
            var trHtml = "";
            $.each(list,function(k,v){
                trHtml += "<tr>";
                if(v.regin != null){
                    trHtml += "<td class='area'>"+ v.regin +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.house != null){
                    trHtml += "<td>"+ v.house +'%'+"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.business != null){
                    trHtml += "<td>"+ v.business +'%'+"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.office != null){
                    trHtml += "<td>"+ v.office +'%'+"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                trHtml += "</tr>"
            });
            $("#allStockTbody2").html(trHtml);
        });
    }
    //使用情况统计
    queryRegionStock2();

    function changeInOrOut(type){
//        $("#typeReg").val(type);
        if('入住率'==type){
            $("#inHouse").addClass("on");
            $("#outHouse").removeClass("on");
            queryRegionStock2();
        }
        if('出租率'==type){
            $("#inHouse").removeClass("on");
            $("#outHouse").addClass("on");
            $("#allStockTbody2").html('');
        }
    }

    //二级菜单切换
    $(".stockContentMenu").on("click","li",function(){
        $(".stockContentMenu li").removeClass("on");
        $(this).addClass("on");
        var text = $(this).data("text");
        $(".sanTitle").html(text+"统计");
        var layerCode = $(this).val();
        $("#layerType").val(layerCode);
        queryFullCycleRegionStock();
        queryRegionStock();
        queryRegionStock2();
    });

</script>
</html>