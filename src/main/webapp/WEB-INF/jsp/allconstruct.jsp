<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<%--全域施工可建面积   stock_code= 1004--%>
        <div class="newHouseStock">
            <div class="newHouse">
                <div class="title">说明：施工可建面积 =  全部已施工未申报预售项目的建筑面积</div>
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
                        <span>主城区施工可建面积(万㎡)及环比(%)</span>
                        <b id="1001Area">--</b><b><span id="1001Rate">--</span></b>
                    </li>
                    <li value="1002" data-text="二圈层">
                        <span>二圈层施工可建面积(万㎡)及环比(%)</span>
                        <b id="1002Area">--</b><b><span id="1002Rate">--</span></b>
                    </li>
                    <li value="1003" data-text="三圈层">
                        <span>三圈层施工可建面积(万㎡)及环比(%)</span>
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
                        <span class="text">趋势统计</span>
                        <span class="time">
                            <span class="on" data-type="chartOne" id="mouthFlag" onclick="changeYM('月')">月</span><span data-type="chartOne" id="yearFlag" onclick="changeYM('年')">年</span>
                        </span>
                    </div>
                    <div class="chartContent" id="chartOne">

                    </div>
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
                                <td>施工可建面积</td>
                                <td>环比</td>
                                <td>区域</td>
                                <td>施工可建面积</td>
                                <td>环比</td>
                            </tr>
                        </thead>
                        <tbody id="allStockTbody">
                            <tr>
                                <td class="area">金牛区</td>
                                <td>50</td>
                                <td>+1.00%</td>
                                <td class="area">锦江区</td>
                                <td>19</td>
                                <td>5</td>
                            </tr>
                            <tr>
                                <td class="area">高新区</td>
                                <td>50</td>
                                <td>50</td>
                                <td class="area">锦江区</td>
                                <td>19</td>
                                <td>5</td>
                            </tr>
                            <tr>
                                <td class="area">成华区</td>
                                <td>50</td>
                                <td>50</td>
                                <td class="area">金牛区</td>
                                <td>19</td>
                                <td>5</td>
                            </tr>
                            <tr>
                                <td class="area">锦江区</td>
                                <td>50</td>
                                <td>50</td>
                                <td class="area">锦江区</td>
                                <td>19</td>
                                <td>5</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
<form id="queryValue">
    <input id="stockType" type="hidden" name="stock_code" value="1004">
    <input id="layerType" type="hidden" name="layer_code" value="1001">
    <input id="typeCode" type="hidden" name="type_code" value="1002">
    <input id="flagYM" type="hidden" name="flagYM" value="月">
    <input id="typeReg" type="hidden" name="flagReg" value="行政区域">
</form>
<script type="text/javascript">
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



    // 基于准备好的dom，初始化echarts图表
    var myChart = echarts.init(document.getElementById('chartOne'));
    var myChartUrl = '${pageContext.request.contextPath}/stock/findStockTrend';
    var option = {
        calculable : true,
        tooltip: {
            trigger: 'axis'
        },
        noDataLoadingOption:{
            text :"暂无数据",
            effect : 'bubble',
            textStyle : {
                fontSize : 30,
                fontFamily: 'Microsoft Yahei'
            }
        },
        xAxis : [
            {
                type : 'category',
                data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [

            {
                name:'面积',
                type:'bar',
                barWidth : 20,//柱图宽度
                data:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                itemStyle : {
                    normal : {
                        color :'#2EC7C9'
                    }
                }
            }
        ]
    };
    function changeYM(flag){
        $("#flagYM").val(flag);
        if('月'==flag){
            $("#mouthFlag").addClass("on");
            $("#yearFlag").removeClass("on");
        }
        doQuery('queryValue',myChartUrl,myChart,option);
        if('年'==flag){
            $("#mouthFlag").removeClass("on");
            $("#yearFlag").addClass("on");
        }
        doQuery('queryValue',myChartUrl,myChart,option);
    }

    // 为echarts对象加载数据
//    myChart.setOption(option);
    doQuery('queryValue',myChartUrl,myChart,option);

    function queryRegionStock(){
        var paramObj = {};
        $.each($("#queryValue").serializeArray(),function(i,d){
            paramObj[d.name] = d.value;
        });
        $.get("${pageContext.request.contextPath}/stock/findFieldLayoutRegionStock",paramObj,function(data){
            var list = data.result.list;
            console.debug(list);
            var trHtml = "";
            for(var i=0;i<list.length;i+=2){
                trHtml += "<tr>";
                if(list[i].region_name != null){
                    trHtml += "<td class='area'>"+ list[i].region_name +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(list[i].stock_area != null){
                    trHtml += "<td>"+list[i].stock_area +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(list[i].rate != null){
                    trHtml += "<td>"+list[i].rate +'%'+"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(list[i+1]){
                    if(list[i+1].region_name != null){
                        trHtml += "<td class='area'>"+ list[i+1].region_name +"</td>";
                    }else{
                        trHtml += "<td>"+ nullData +"</td>";
                    }
                    if(list[i+1].stock_area != null){
                        trHtml += "<td>"+ list[i+1].stock_area +"</td>";
                    }else{
                        trHtml += "<td>"+ nullData +"</td>";
                    }
                    if(list[i+1].rate != null){
                        trHtml += "<td>"+ list[i+1].rate +'%'+"</td>";
                    }else{
                        trHtml += "<td>"+ nullData +"</td>";
                    }
                }
                trHtml += "</tr>"
            }
            $("#allStockTbody").html(trHtml);
        });
    }
    queryRegionStock()


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

    //二级菜单切换
    $(".stockContentMenu").on("click","li",function(){
        $(".stockContentMenu li").removeClass("on");
        $(this).addClass("on");
        var text = $(this).data("text");
        $(".sanTitle").html(text+"统计");
        var layerCode = $(this).val();
        $("#layerType").val(layerCode);
        queryFullCycleRegionStock();
        doQuery('queryValue',myChartUrl,myChart,option);
        queryRegionStock()
    });
</script>