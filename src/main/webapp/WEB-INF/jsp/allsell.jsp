<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<%--全域楼市可销售面积  stock_code = 1005--%>
        <div class="newHouseStock">
            <div class="newHouse">
                <div class="title">说明：楼市可销售面积=  全部取得销售许可且未成交的建筑面积</div>
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
                        <span>主城区楼市可销售面积(万㎡)及环比(%)</span>
                        <b id="1001Area">--</b><b><span id="1001Rate">--</span></b>
                    </li>
                    <li value="1002" data-text="二圈层">
                        <span>二圈层楼市可销售面积(万㎡)及环比(%)</span>
                        <b id="1002Area">--</b><b><span id="1002Rate">--</span></b>
                    </li>
                    <li value="1003" data-text="三圈层">
                        <span>三圈层楼市可销售面积(万㎡)及环比(%)</span>
                        <b id="1003Area">--</b><b><span id="1003Rate">--</span></b>
                    </li>
                </ul>
            </div>
            <div class="otherContent">
                <div class="chartBox">
                    <div class="chartTitle">
                        <span class="text">业态统计</span>
                    </div>
                    <div class="chartContent">
                        <div class="leftPie" id="chartTwo">

                        </div>
                        <div class="rightList">
                            <div class="chartTitle">
                                <span class="text" id="industry">所有业态可销售面积</span>
                            </div>
                            <table cellpadding="0" cellspacing="0" width="100%" class="tableArea">
                                <thead>
                                <tr>
                                    <td width="90">行政区域</td>
                                    <td>可销售面积</td>
                                </tr>
                                </thead>
                            </table>
                            <div class="tableOverflow">
                                <table cellpadding="0" cellspacing="0" width="100%" class="tableArea" id="industryList">
                                    <tr>
                                        <td width="90">金牛区</td>
                                        <td>50</td>
                                    </tr>
                                    <tr>
                                        <td>高新区</td>
                                        <td>50</td>
                                    </tr>
                                    <tr>
                                        <td>成华区</td>
                                        <td>50</td>
                                    </tr>
                                    <tr>
                                        <td>锦江区</td>
                                        <td>50</td>
                                    </tr>
                                    <tr>
                                        <td>天府新区</td>
                                        <td>50</td>
                                    </tr>
                                    <tr>
                                        <td>青白江区</td>
                                        <td>50</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="chartBox">
                    <div class="chartTitle">
                        <span class="text">趋势统计</span>
                        <span class="time">
                            <span class="on" data-type="chartOne" id="mouthFlag" onclick="changeYM('月')">月</span><span data-type="chartOne" id="yearFlag" onclick="changeYM('年')">年</span>
                        </span>

                    </div>

                    <br/>
                    <div style="float:left">
                        <span class="text"> 区域：</span>
                        <select onchange="changeCode();" id="regin" name="region_code">
                            <option value=""></option>
                        </select>
                    </div>


                    <div class="chartContent" id="chartOne"></div>
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
                                <td width="60">区域</td>
                                <td width="90">当月取得销售许可面积</td>
                                <td width="40">环比</td>
                                <td width="40">同比</td>
                                <td width="50">当月成交面积</td>
                                <td width="40">环比</td>
                                <td width="40">同比</td>
                                <td width="50">当前可售面积</td>
                                <td width="40">环比</td>
                                <td width="40">同比</td>
                            </tr>
                        </thead>
                        <tbody id="allStockTbody">

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
<form id="queryValue">
    <input id="stockType" type="hidden" name="stock_code" value="1005">
    <input id="layerType" type="hidden" name="layer_code" value="1001">
    <input id="typeCode" type="hidden" name="type_code" value="1002">
    <input id="flagYM" type="hidden" name="flagYM" value="月">
    <input id="typeReg" type="hidden" name="flagReg" value="行政区域">
    <input id="pieUsageName" type="hidden" name="usage_name" value="">
    <input id="regionCode" type="hidden" name="region_code" value="">
</form>
<script type="text/javascript">
    $(function(){
        $(".newHouseStock").css("width",$(".contentBox").width());
    });
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
                data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
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
        $.get("${pageContext.request.contextPath}/stock/findEstateRegionStock",paramObj,function(data){
            var list = data.result.list;
//            console.debug(list);
            var trHtml = "";
            $.each(list,function(k,v){
                trHtml += "<tr>";
                if(v.region_name != null){
                    trHtml += "<td class='area regionClick' data-pomp='pompRegion' value="+ v.region_code+">"+ v.region_name +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.supply_area != null){
                    trHtml += "<td class='tdColor1'>"+ v.supply_area +"</td>";
                }else{
                    trHtml += "<td class='tdColor1'>"+ nullData +"</td>";
                }
                if(v.supply_rate != null){
                    trHtml += "<td class='tdColor1'>"+ v.supply_rate +'%'+"</td>";
                }else{
                    trHtml += "<td class='tdColor1'>"+ nullData +"</td>";
                }
                if(v.supply_rate_tb != null){
                    trHtml += "<td class='tdColor1'>"+ v.supply_rate_tb +'%'+"</td>";
                }else{
                    trHtml += "<td class='tdColor1'>"+ nullData +"</td>";
                }
                if(v.deal_area != null){
                    trHtml += "<td class='tdColor2'>"+ v.deal_area +"</td>";
                }else{
                    trHtml += "<td class='tdColor2'>"+ nullData +"</td>";
                }
                if(v.deal_rate != null){
                    trHtml += "<td class='tdColor2'>"+ v.deal_rate +'%'+"</td>";
                }else{
                    trHtml += "<td class='tdColor2'>"+ nullData +"</td>";
                }
                if(v.deal_rate_tb != null){
                    trHtml += "<td class='tdColor2'>"+ v.deal_rate_tb +'%'+"</td>";
                }else{
                    trHtml += "<td class='tdColor2'>"+ nullData +"</td>";
                }
                if(v.stock_area != null){
                    trHtml += "<td class='tdColor3'>"+ v.stock_area +"</td>";
                }else{
                    trHtml += "<td class='tdColor3'>"+ nullData +"</td>";
                }
                if(v.stock_rate != null){
                    trHtml += "<td class='tdColor3'>"+ v.stock_rate +'%'+"</td>";
                }else{
                    trHtml += "<td class='tdColor3'>"+ nullData +"</td>";
                }
                if(v.stock_rate_tb != null){
                    trHtml += "<td class='tdColor3'>"+ v.stock_rate_tb +'%'+"</td>";
                }else{
                    trHtml += "<td class='tdColor3'>"+ nullData +"</td>";
                }
                trHtml += "</tr>"
            });
            $("#allStockTbody").html(trHtml);

        });
    }
    //查询区域统计
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

    //饼图begin
    var myChartPie = echarts.init(document.getElementById('chartTwo'));
    optionPie = {
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)",
            selectedOffset : 10
        },
        noDataLoadingOption:{
            text :"暂无数据",
            effect : 'bubble',
            textStyle : {
                fontSize : 30,
                fontFamily: 'Microsoft Yahei'
            }
        },
        legend: {
            orient : 'horizontal',
            x : 'left'
        },
        calculable : true,
        color:['#6495ED', '#32CD32','#DA70D6',"#FF7F50",'#87CEFA','#FFB980','#5AB1EF','#CEF1DB'],
        series : [
            {
                name:'面积',
                type:'pie',
                radius : '75%',
                center: ['50%', '50%'],
                legendHoverLink:false,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            formatter: '{b}{d}%',
                            textStyle:{
                                fontSize : 14,
                                fontFamily: 'Microsoft Yahei'
                            }
                        },
                        labelLine: {
                            show: true
                        }
                    }
                }
            }
        ]
    };
    //饼图end
    function queryFullCycleRegionStock(){
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
                var res = new Array;
                var names =new Array;
                for(var i=0;i<list.length;i++){
                    var name = list[i].usage_name;
                    var area = parseInt(list[i].stock_area);
                    //var rate = list[i].rate;
                    names.push(name);
                    res.push({
                        value: area,
                        name: name
                    });
                }
                optionPie.series[0].data=res;
                optionPie.legend.data=names;
                myChartPie.setOption(optionPie,true);
            }
        })
    }
    //查询业态统计数据-饼图
    queryFullCycleRegionStock();
    //饼图点击
    myChartPie.on("click",function(param){
//        console.log(param);
        $("#industry").text(param.name+"可销售面积");
        $("#pieUsageName").val(param.name);

        queryPieList();
    });

    function queryPieList(){
        var paramObj = {};
        $.each($("#queryValue").serializeArray(),function(i,d){
            paramObj[d.name] = d.value;
        });
//        console.debug(paramObj);
        $.get("${pageContext.request.contextPath}/stock/findRegionPie",paramObj,function(data){
            var list = data.result.list;
//            console.debug(list);
            var intrHtml = "";
            $.each(list,function(k,v){
                intrHtml += "<tr>";
                if(v.region_name != null){
                    intrHtml += "<td width='90'>"+ v.region_name +"</td>";
                }else{
                    intrHtml += "<td>"+ nullData +"</td>";
                }
                if(v.stock_area != null){
                    intrHtml += "<td>"+ v.stock_area +"</td>";
                }else{
                    intrHtml += "<td>"+ nullData +"</td>";
                }
                intrHtml += "</tr>"
            });
            $("#industryList").html(intrHtml);
        });
    }
    queryPieList();


    //二级菜单切换
    $(".stockContentMenu").on("click","li",function(){
        $("#regionCode").val("");
        $(".stockContentMenu li").removeClass("on");
        $(this).addClass("on");
        var layerCode = $(this).val();
        var text = $(this).data("text");
        $(".sanTitle").html(text+"统计");
        $("#layerType").val(layerCode);
        queryFullCycleRegionStock();
        doQuery('queryValue',myChartUrl,myChart,option);
        queryRegionStock();
        $("#industry").text('所有业态可销售面积');
        $("#pieUsageName").val('');
        queryPieList();
        region4ls(layerCode);
    });

//    楼市趋势统计区域条件参数获取
    function region4ls(layerCode){
        var obj=document.getElementById('regin');
        obj.options.length=0;
        var paramObj = {};
        paramObj['layer_code'] = layerCode;
        $.get("${pageContext.request.contextPath}/stock/findStockTrend4lsRegion",paramObj,function(data){
            var list =data.result.list;
            var html='<option value="">-所有-</option>';
            $.each(list, function(i,val){
                html+="<option value="+val.code+">"+val.name+"</option>";
            });
            $("#regin").append(html);
        })
    }
    region4ls('1001');

//    切换区域
    function changeCode(){
        $("#regionCode").val($("#regin").val());
        doQuery('queryValue',myChartUrl,myChart,option);
    }
</script>