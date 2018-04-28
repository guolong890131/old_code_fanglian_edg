<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<div class="noMove market">
    <div class="top">
        <div class="logo">
            成都市房地产市场调控信息监测平台
        </div>
        <ul class="menu">
            <li onclick="window.location='${pageContext.request.contextPath}/stock/index'">可建面积监测</li>
            <li class="on">楼市供销监测</li>
            <%--<li onclick="window.location='${pageContext.request.contextPath}/map/index'">项目地图</li>--%>
            <li onclick="window.location='${pageContext.request.contextPath}/stock/allemploy'">商业地产监测</li>
        </ul>
        <div class="user">成都市房管局</div>
        <div class="esc" onclick="window.location='${pageContext.request.contextPath}/user/esc'">退出</div>
    </div>
    <div class="banner">
        <div class="refreshTime">最近更新时间：${timeTemp}</div>
        <div class="showMsg">
            <label>
                <input id="yearTemp" onclick="check('${pageContext.request.contextPath}/user/yearTemp',this.checked,this.value,'${pageContext.request.contextPath}/market/index')"
                       class="refreshTime" name="yearTemp" type="checkbox" value="${yearTemp}"/>图表查询起始年份：${yearTemp}
            </label>
            图例：
            <span class="green">健康</span>
            <span class="yellow">警告</span>
            <span class="red">危险</span>
        </div>
    </div>
    <div class="newHouse market">
        <div class="title">新房市场供销存价情况</div>
        <div class="tip"><span>价格单位: 元/㎡</span><span>面积单位: 万㎡</span></div>
    </div>
</div>
<div class="nav market">
    <div class="typeMenu market">
        <ul>
            <li data-market="allpresell" data-pomp="pompAllpresell" data-title="全域本年累计批准预售面积">
                <span>全域本年累计批准预售面积(万㎡)</span>
                <b id="1001">--</b>
            </li>
            <li data-market="allarea" data-pomp="pompAllarea" data-title="全域本年累计成交面积">
                <span>全域本年累计成交面积(万㎡)</span>
                <b id="1002">--</b>
            </li>
            <li data-market="allsell" data-pomp="pompAllsell" data-title="全域当前可销售面积">
                <span>全域当前可销售面积(万㎡)</span>
                <b id="1003">--</b>
            </li>
            <li>
                <span>全域本年住宅平均成交价格(元/㎡)</span>
                <b id="1004">--</b>
            </li>
            <li>
                <span>全域本年商业平均成交价格(元/㎡)</span>
                <b id="1005">--</b>
            </li>
            <li>
                <span>全域本年办公平均成交价格(元/㎡)</span>
                <b id="1006">--</b>
            </li>
        </ul>
    </div>
    <div class="contentBox market">
        <form id="queyrOne">
        <div class="chartBox">
            <div class="chartTitle">
                <span class="text">批准预售面积与成交面积趋势统计</span>
                <span class="select">
                    <select id="layer_codeOne" name="layer_code" class="selectCh" onchange="changeSel(1)" data-type="chartOne">
                        <option value="1000">全域</option>
                        <option value="1001">主城区</option>
                        <option value="1002">二圈层</option>
                        <option value="1003">三圈层</option>
                    </select>
                    <select id="region_codeOne"  name="region_code" class="selectCh" onchange="change(1)" data-type="chartOne">
                        <option value="">全区</option>
                    </select>
                    <select name="usage_code" class="selectCh" onchange="change(1)" data-type="chartOne">
                        <option value="1000">全部</option>
                        <option value="1001">住宅</option>
                        <option value="1002">商业</option>
                        <option value="1003">办公</option>
                        <option value="1004">车位</option>
                    </select>
                    <input id="flagOne" type="hidden" name="flagYM">
                </span>

                <span class="time">
                    <span id="MOne" class="on" data-type="chartOne" onclick="changeflagM(1)">月</span><span id="YOne" data-type="chartOne" onclick="changeflagY(1)">年</span>
                </span>
            </div>
            <div class="chartContent" id="chartOne">

            </div>
        </div>
        </form>
        <form id="queyrTwo">
        <div class="chartBox">
            <div class="chartTitle">
                <span class="text">可销售面积与销售周期(月)趋势统计</span>
                <span class="select">
                    <select id="layer_codeTwo" name="layer_code" class="selectCh" onchange="changeSel(2)" data-type="charTwo">
                        <option value="1000">全域</option>
                        <option value="1001">主城区</option>
                        <option value="1002">二圈层</option>
                        <option value="1003">三圈层</option>
                    </select>
                    <select id="region_codeTwo"  name="region_code" class="selectCh" onchange="change(2)" data-type="charTwo">
                        <option value="">全区</option>
                    </select>
                    <select name="usage_code" class="selectCh" onchange="change(2)" data-type="charTwo">
                        <option value="1000">全部</option>
                        <option value="1001">住宅</option>
                        <option value="1002">商业</option>
                        <option value="1003">办公</option>
                        <option value="1004">车位</option>
                    </select>

                </span>
                <span class="time">
                    销售周期（月）= 当前可销售面积/连续6个月平均成交面积
                </span>
            </div>
            <div class="chartContent" id="charTwo">

            </div>
        </div>
        </form>
        <form id="queyrThere">
        <div class="chartBox">
            <div class="chartTitle">
                <span class="text">价格趋势统计</span>
                <span class="select">
                    <select id="layer_codeThere" name="layer_code" class="selectCh" onchange="changeSel(3)" data-type="charThere">
                        <option value="1000">全域</option>
                        <option value="1001">主城区</option>
                        <option value="1002">二圈层</option>
                        <option value="1003">三圈层</option>
                    </select>
                    <select id="region_codeThere" name="region_code" class="selectCh" onchange="change(3)" data-type="charThere">
                        <option value="">全区</option>
                    </select>
                    <select name="usage_code" class="selectCh" onchange="change(3)" data-type="charThere">
                        <option value="1000">全部</option>
                        <option value="1001">住宅</option>
                        <option value="1002">商业</option>
                        <option value="1003">办公</option>
                        <option value="1004">车位</option>
                    </select>
                    <input id="flagThere" type="hidden" name="flagYM">

                </span>
                <span class="time">
                    <span id="MThere" class="on" data-type="charThere" onclick="changeflagM(2)">月</span><span id="YThere" data-type="charThere" onclick="changeflagY(2)">年</span>
                </span>
            </div>
            <div class="chartContent" id="charThere">

            </div>
        </div>
        </form>
    </div>
</div>
</body>

<script type="text/javascript">
    $.get("${pageContext.request.contextPath}/market/findMarketList",function(data){
        if(data.success == true){
            var marketList = data.result.list;
            for(var i=0;i<marketList.length;i++){
                var code = marketList[i].market_code;
                var val = marketList[i].value
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
<script type="text/javascript">
    var chartOneUrl = '${pageContext.request.contextPath}/market/findSupplyDeal';
    var chartTwoUrl = '${pageContext.request.contextPath}/market/findStockCycle';
    var chartThereUrl = '${pageContext.request.contextPath}/market/findSupplyPrice';
    // 基于准备好的dom，初始化echarts图表
    var myChartOne = echarts.init(document.getElementById('chartOne'));
    var myChartTwo = echarts.init(document.getElementById('charTwo'));
    var myChartThere = echarts.init(document.getElementById('charThere'));
    var optionOne = {
        calculable : true,
        legend: {
            data:['批准预售面积','成交面积','供销比'],
            x: 'left'
        },
        noDataLoadingOption:{
            text :"暂无数据",
            effect : 'bubble',
            textStyle : {
                fontSize : 30,
                fontFamily: 'Microsoft Yahei'
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis : [
            {
                type : 'category',
                data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
            }
        ],
        yAxis : [
            {
                name:'面积',
                type : 'value'
            },
            {
                name:'比值',
                type : 'value'
            }
        ],
        series : [

            {
                name:'批准预售面积',
                type:'bar',
                data:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                itemStyle : {
                    normal : {
                        color :'#2EC7C9'
                    }
                }
            },
            {
                name:'成交面积',
                type:'bar',
                data:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                itemStyle : {
                    normal : {
                        color :'#B6A2DE'
                    }
                }
            },
            {
                name:'供销比',
                type:'line',
                data:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                yAxisIndex: 1,
                itemStyle : {
                    normal : {
                        color :'#FE7632'
                    }
                }
            }
        ]
    };
    var optionTwo = {
        calculable : true,
        legend: {
            data:['可销售面积','销售周期（月）'],
            x: 'left'
        },
        noDataLoadingOption:{
            text :"暂无数据",
            effect : 'bubble',
            textStyle : {
                fontSize : 30,
                fontFamily: 'Microsoft Yahei'
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis : [
            {
                type : 'category',
                data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
            }
        ],
        yAxis : [
            {
                name:'面积',
                type : 'value'
            },
            {
                name:'周期（月）',
                type : 'value'
            }
        ],
        series : [

            {
                name:'可销售面积',
                type:'bar',
                data:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                itemStyle : {
                    normal : {
                        color :'#2EC7C9'
                    }
                }
            },
            {
                name:'销售周期（月）',
                type:'line',
                yAxisIndex: 1,
                data:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                itemStyle : {
                    normal : {
                        color :'#FE7632'
                    }
                }
            }
        ]
    };
    var optionThere = {
        calculable : true,
        legend: {
            data:['成交均价','申报均价'],
            x: 'left'
        },
        noDataLoadingOption:{
            text :"暂无数据",
            effect : 'bubble',
            textStyle : {
                fontSize : 30,
                fontFamily: 'Microsoft Yahei'
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis : [
            {
                type : 'category',
                data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
            }
        ],
        yAxis : [
            {
                name:'价格',
                type : 'value'
            }
        ],
        series : [

            {
                name:'成交均价',
                type:'line',
                data:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                itemStyle : {
                    normal : {
                        color :'#2EC7C9'
                    }
                }
            },
            {
                name:'申报均价',
                type:'line',
                data:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                itemStyle : {
                    normal : {
                        color :'#FE7632'
                    }
                }
            }
        ]
    };

    // 为echarts对象加载数据

    doQuery('queyrTwo',chartTwoUrl,myChartTwo,optionTwo);
    doQuery('queyrThere',chartThereUrl,myChartThere,optionThere);
    doQuery('queyrOne',chartOneUrl,myChartOne,optionOne);
//    myChartOne.setOption(optionOne);
//    myChartTwo.setOption(optionTwo);
//    myChartThere.setOption(optionThere);

    function region(layerCode,regionId){
        var obj=document.getElementById(regionId);
        obj.options.length=0;
        var paramObj = {};
        paramObj['layer_code'] = layerCode;
        $.get("${pageContext.request.contextPath}/stock/findStockTrend4lsRegion",paramObj,function(data){
            var list =data.result.list;
            var html='<option value="">全区</option>';
            $.each(list, function(i,val){
                html+="<option value="+val.code+">"+val.name+"</option>";
            });
            $("#"+regionId).append(html);
        })
    }

    function changeSel(index){
        if(index==1){
            var layer_code = $("#queyrOne #layer_codeOne").val();
            region(layer_code,'region_codeOne');
            doQuery('queyrOne',chartOneUrl,myChartOne,optionOne);
        }
        if(index==2){
            var layer_code = $("#queyrTwo #layer_codeTwo").val();
            region(layer_code,'region_codeTwo');
            doQuery('queyrTwo',chartTwoUrl,myChartTwo,optionTwo);
        }
        if(index==3){
            var layer_code = $("#queyrThere #layer_codeThere").val();
            region(layer_code,'region_codeThere');
            doQuery('queyrThere',chartThereUrl,myChartThere,optionThere);
        }
    }

    function change(index){
        if(index==1){
            doQuery('queyrOne',chartOneUrl,myChartOne,optionOne);
        }
        if(index==2){
            doQuery('queyrTwo',chartTwoUrl,myChartTwo,optionTwo);
        }
        if(index==3){
            doQuery('queyrThere',chartThereUrl,myChartThere,optionThere);
        }
    }

    function changeflagM(index){
        if(index==1){
            $("#MOne").addClass("on");
            $("#YOne").removeClass("on");
            $("#flagOne").val("月");
            doQuery('queyrOne',chartOneUrl,myChartOne,optionOne);
        }
        if(index==2){
            $("#MThere").addClass("on");
            $("#YThere").removeClass("on");
            $("#flagThere").val("月");
            doQuery('queyrThere',chartThereUrl,myChartThere,optionThere);
        }
    }
    function changeflagY(index){
        if(index==1) {
            $("#MOne").removeClass("on");
            $("#YOne").addClass("on");
            $("#flagOne").val("年");
            doQuery('queyrOne',chartOneUrl,myChartOne,optionOne);
        }
        if(index==2){
            $("#MThere").removeClass("on");
            $("#YThere").addClass("on");
            $("#flagThere").val("年");
            doQuery('queyrThere',chartThereUrl,myChartThere,optionThere);
        }
    }


</script>
</html>
