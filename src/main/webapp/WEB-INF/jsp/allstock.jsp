<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<%--全域全周期库存总量    stock_code = 1001 --%>
<div class="newHouseStock">
    <div class="newHouse">
        <div class="title">说明：全周期库存总量 =  土地可建面积 + 规划可建面积 + 施工可建面积 + 楼市可销售面积</div>
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
                <span>主城区总量(万㎡)及环比(%)</span>
                <b id="1001Area">--</b><b><span id="1001Rate">--</span></b>
            </li>
            <li value="1002" data-text="二圈层">
                <span>二圈层总量(万㎡)及环比(%)</span>
                <b id="1002Area">--</b><b><span id="1002Rate">--</span></b>
            </li>
            <li value="1003" data-text="三圈层">
                <span>三圈层总量(万㎡)及环比(%)</span>
                <b id="1003Area">--</b><b><span id="1003Rate">--</span></b>
            </li>
        </ul>
    </div>
    <div class="otherContent">
        <div class="chartBox">
            <div class="chartTitle">
                <span class="text" id="titleChange">各阶段统计</span>
                        <span class="time allstock">
                            <span class="on" data-code="1001" name="type_code">阶段</span><span data-code="1002" name="type_code">业态</span>
                        </span>
            </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="tableStage" id="jie">
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
                    <td>库存总量</td>
                    <td>土地可建面积</td>
                    <td>规划可建面积</td>
                    <td>施工可建面积</td>
                    <td>楼市可销售面积</td>
                </tr>
                </thead>
                <tbody id="allStockTbody">

                </tbody>
            </table>
        </div>
    </div>
</div>
<form id="queryValue">
    <input id="stockType" type="hidden" name="stock_code" value="1001">
    <input id="layerType" type="hidden" name="layer_code" value="1001">
    <input id="typeCode" type="hidden" name="type_code" value="1001">
    <input id="flagYM" type="hidden" name="flagYM" value="月">
    <input id="typeReg" type="hidden" name="flagReg" value="行政区域">
</form>

<script type="text/javascript">
    function queryStockLayer(){
        var stock_code =$("#stockType").serializeArray();
        $.get("${pageContext.request.contextPath}/stock/findStockLayer",stock_code,function(data){
            if(data.success == true){
//                console.debug(data.result.list);
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

    function changeType(code){
        $("#typeCode").val(code);
        if('1001'==code){
            $("#type1001").addClass("on");
            $("#type1002").removeClass("on");
        }
        if('1002'==code){
            $("#type1001").removeClass("on");
            $("#type1002").addClass("on");
        }
        queryFullCycleRegionStock();
    }

    function queryFullCycleRegionStock(){
        $("#jie i").text(nullData);
        var paramObj = {};
        $.each($("#queryValue").serializeArray(),function(i,d){
            paramObj[d.name] = d.value;
        });
//        console.debug(paramObj);
        $.get("${pageContext.request.contextPath}/stock/findStockStageUsage",paramObj,function(data){
//            console.debug(data);
            console.log(data.success);
            if(data.success == true){
                console.log(data.result.list);
                var list = data.result.list;
                var jieHtml = "";
                jieHtml +="<tr>";
                for(var i=0;i<list.length;i++){
                    var code = list[i].usage_code;
                    var area = list[i].stock_area;
                    var rate = list[i].rate;
                    var name = list[i].usage_name;
                    if(i%2 == 0){
                        jieHtml +="</tr>";
                        jieHtml +="<tr>";
                    }
                    jieHtml +="<td>"+ name +"</td>";
                    jieHtml +="<td>"+ area +"</td>";
                    jieHtml +="<td>环比</td>";
                    jieHtml +="<td>"+ rate +"%</td>";
                }
                jieHtml +="</tr>";
                $("#jie").html(jieHtml);
            }
        })
    }
    //查询各阶段统计数据
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
        $.get("${pageContext.request.contextPath}/stock/findFullCycleRegionStock",paramObj,function(data){
            var list = data.result.list;
//            console.debug(list);
            var trHtml = "";
            $.each(list,function(k,v){
                trHtml += "<tr>";
                if(v.region_name != null){
                    trHtml += "<td class='area'>"+ v.region_name +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.stock_area != null){
                    trHtml += "<td>"+ v.stock_area +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.field_area != null){
                    trHtml += "<td>"+ v.field_area +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.layout_area != null){
                    trHtml += "<td>"+ v.layout_area +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.build_area != null){
                    trHtml += "<td>"+ v.build_area +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.estate_area != null){
                    trHtml += "<td>"+ v.estate_area +"</td>";
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
    //业态，阶段切换
    $(".time.allstock").on("click","span",function(){
        $(".allstock span").removeClass("on");
        $(this).addClass("on");
        var code = $(this).data("code");
        $("#typeCode").val(code);
        if(code == 1001){
            $("#titleChange").text("各阶段统计");
        }else{
            $("#titleChange").text("各业态统计");
        }
        queryFullCycleRegionStock(code);
    });

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
        queryRegionStock();
    });
    $(function(){
        $(".newHouseStock").css("width",$(".contentBox").width());
    });
</script>