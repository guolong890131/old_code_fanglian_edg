<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>成都市房地产市场调控信息监测平台</title>
    <link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="screen" />
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=8pCLsdjLWM5ilhvYyOlfojVj"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
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
            <li class="on">项目地图</li>
        </ul>
        <div class="user">成都市房管局</div>
        <div class="esc" onclick="window.location='${pageContext.request.contextPath}/user/esc'">退出</div>
    </div>
    <div class="newHouse map">
        <div class="title">存量柱状分布图</div>
        <div class="tip">最近更新时间：${timeTemp}</div>
    </div>
</div>
<div class="mapNav">
    <div class="selectF">
        <select name="" id="regionType">
            <option value="行政区域类">行政区域</option>
            <option value="重点区域">重点区域</option>
        </select>
        <select name="" id="regionName">
            <option value="">全域成都</option>
        </select>
    </div>
    <div class="checkF">
        <label><input type="checkbox" checked value="土地">土地</label>
        <label><input type="checkbox" checked value="规划">规划</label>
        <label><input type="checkbox" checked value="施工">施工</label>
        <label><input type="checkbox" checked value="项目">可售</label>
    </div>
    <div class="msgF">
        <ul>
            <%--<li><span class="yellow"></span>1 万㎡</li>--%>
            <li>地：未进入建设工程规划阶段的土地</li>
            <li>规：未进入建设施工阶段的工程项目</li>
            <li>建：未进入房屋销售阶段的工程项目</li>
        </ul>
    </div>
    <div id="container"></div>
</div>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
<script>
    //百度地图加载

    var mp = new BMap.Map("container",{enableMapClick:false});
    mp.centerAndZoom(new BMap.Point(104.078618,30.81), 13);
    mp.addControl(new BMap.NavigationControl({type: BMAP_NAVIGATION_CONTROL_SMALL}));
    mp.enableScrollWheelZoom();
    mp.addControl(new BMap.NavigationControl());

    var points =[];


    $(function(){
        var regionName = $("#regionName option:selected").text();
        if (regionName=="全域成都")regionName="成都市";
        loadRegion(regionName);


        $("#regionType").change(function(){
            loadRegion(regionName);
        });

        $("#regionName").change(function(){
            loadPoint();
        });

        $(".checkF").find("input").change(function(){
            loadPoint();
        });

        mp.addEventListener("zoomend", function(){
            var zoom = this.getZoom();
            if(zoom >= 14){
                loadPoint(zoom);
            }else{
                loadHeatMap(zoom);
            }
        });

    });

    //选择区域后构建点
    function loadPoint(zoom){
        var regionName = $("#regionName").val();
        var obj = $(".checkF").find("input");
        var types = [];
        for(var i=0; i<obj.length; i++){
            if(obj[i].checked)
                types.push(obj[i].value);
        }
        $.ajax({
            url: '${pageContext.request.contextPath}/map/findCoordinate',
            type:"post",
            data: "code="+regionName+"&types="+types.join(','),
            dataType : 'json',
            success : function(result){
                var pointData = result.result;

                var bdary = new BMap.Boundary();
                var name = $("#regionName option:selected").text();
                if (name=="全域成都")name="成都市";

                bdary.get(name, function(rs){       //获取行政区域
                    mp.clearOverlays();        //清除地图覆盖物
                    var count = rs.boundaries.length; //行政区域的点有多少个
                    for(var i = 0; i < count; i++){
                        var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#0A58F6", strokeOpacity:1,fillColor:"#E8EFF9",strokeStyle:"dashed"}); //建立多边形覆盖物
                        mp.addOverlay(ply);  //添加覆盖物
                        if(!zoom){
                            mp.setViewport(ply.getPath());    //调整视野
                            //mp.setZoom(12);
                        }
                       // mp.setViewport(ply.getPath());    //调整视野
                        //mp.setZoom(zoom);
                    }
                    for (var i = 0; i<pointData.length;i++){
                        var mySquare = new ComplexCustomOverlay(new BMap.Point(pointData[i].X,pointData[i].Y), pointData[i].TYPE, pointData[i].AREA, pointData[i].ID);
                        mp.addOverlay(mySquare);
                    }

                });


            },
            error : function() {
                alert('数据加载错误！');
            }
        });
    };


    //热力图
    function loadHeatMap (zoom){
        if(!isSupportCanvas()){
            alert('热力图目前只支持有canvas的浏览器,您所使用的浏览器不能使用热力图功能！')
        }

        var regionName = $("#regionName").val();
        var obj = $(".checkF").find("input");
        var types = [];
        for(var i=0; i<obj.length; i++){
            if(obj[i].checked)
                types.push(obj[i].value);
        }
        $.ajax({
            url: '${pageContext.request.contextPath}/map/findCoordinate',
            type:"post",
            data: "code="+regionName+"&types="+types.join(','),
            dataType : 'json',
            success : function(result){
                var pointData = result.result;

                var bdary = new BMap.Boundary();
                var name = $("#regionName option:selected").text();
                if (name=="全域成都")name="成都市";

                bdary.get(name, function(rs){       //获取行政区域
                    mp.clearOverlays();        //清除地图覆盖物
                    var count = rs.boundaries.length; //行政区域的点有多少个
                    var mySquare = [];
                    for(var i = 0; i < count; i++){
                        var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#0A58F6", strokeOpacity:1,fillColor:"#E8EFF9",strokeStyle:"dashed"}); //建立多边形覆盖物
                        mp.addOverlay(ply);  //添加覆盖物
                        //mp.setViewport(ply.getPath());    //调整视野
                        //mp.setZoom(zoom);
                    }
                    for (var i = 0; i<pointData.length;i++){
                        mySquare.push({lng:pointData[i].X,lat:pointData[i].Y,count:pointData[i].AREA});
                    }
                    //console.log(mySquare);
                    heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":30,"opacity":0});
                    mp.addOverlay(heatmapOverlay);
                    heatmapOverlay.setDataSet({data:mySquare,max:3});

                });


            },
            error : function() {
                alert('数据加载错误！');
            }
        });

    }

    //根据不同的区域构建遮罩
    function loadRegion (regionName){
        var regionType = $("#regionType").val();

        $.ajax({
            url: '${pageContext.request.contextPath}/map/findRegion',
            type:"post",
            data:"Type="+ regionType,
            dataType : 'json',
            success : function(data){
                var options = "";
                var data = data.result;
                $.each(data, function(key, val) {
                    options += '<option value="'+ data[key].CODE +'">'+ data[key].NAME +'</option>'
                });

                $("#regionName").html(options);

                var bdary = new BMap.Boundary();
                var name = regionName;

                bdary.get(name, function(rs){       //获取行政区域
                    mp.clearOverlays();        //清除地图覆盖物
                    var count = rs.boundaries.length; //行政区域的点有多少个
                    for(var i = 0; i < count; i++){
                        var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#0A58F6", strokeOpacity:1,fillColor:"#E8EFF9",strokeStyle:"dashed"}); //建立多边形覆盖物
                        mp.addOverlay(ply);  //添加覆盖物
                        mp.setViewport(ply.getPath());    //调整视野
                    }

                    loadHeatMap();
                    //loadPoint();

                });

            },
            error : function() {
                alert('数据加载错误！');
            }
        });
    }

    //判断浏览区是否支持canvas
    function isSupportCanvas(){
        var elem = document.createElement('canvas');
        return !!(elem.getContext && elem.getContext('2d'));
    }

    // 复杂的自定义覆盖物
    function ComplexCustomOverlay(point, text, area ,id){
        this._point = point;
        this._text = text;
        this._area = area;
        this._id = id;
    }

    ComplexCustomOverlay.prototype = new BMap.Overlay();
    ComplexCustomOverlay.prototype.initialize = function(map){
        this._map = map;
        var height = this._area*10;
        var id = this._id;
        var type = this._text;
        var div = this._div = document.createElement("div");
        div.style.position = "absolute";
        div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
        div.style.color = "#333333";
        div.style.padding = "35px 5px 5px 5px";
        div.style.fontSize = "16px";
        div.style.fontWeight = "900";
        div.style.textAlign = "center";
        var that = this;
        var area = this._area = document.createElement("div");
        area.style.background = "url(${pageContext.request.contextPath}/images/marker_red.png) no-repeat bottom";
        area.style.width = "24px";
        area.style.height = "25px";
        area.style.margin = "0 auto";
        //area.style.border = "1px solid #FFFFFF";
        //area.style.backgroundColor = "#FFFFFF";
        area.style.overflow = "hidden";
        div.appendChild(area);
        var span = this._span = document.createElement("span");
        div.appendChild(span);
        span.appendChild(document.createTextNode(this._text));
        span.style.whiteSpace = "nowrap";

        div.onclick = function(){

            $.ajax({
                url: '${pageContext.request.contextPath}/map/findWin',
                type:"post",
                data: "id="+ id +"&type="+ type,
                dataType : 'json',
                success : function(result){
                    var data = result.result;

                    var startDate, oneDate, twoDate, endDate, allDays, oneDays, twoDays, threeDays;
//                    aDate = result.projectTime.split("-");
//                    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);  //转换为yyyy-MM-dd格式
//                    aDate = result.landTime.split("-");
//                    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
//                    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
                    startDate = new Date(data[0].DEAL_DATE);//土地成交
                    oneDate = new Date(data[0].PRINT_DATE);//工程规划
                    twoDate = new Date(data[0].BUILD_DATE);//建设施工
                    endDate = new Date(data[0].PROJECT_DATE);//房屋销售
                    allDays = parseInt(Math.abs(endDate - startDate)/ 1000 / 60 / 60 / 24);//总时长以天为单位
                    oneDays = parseInt(Math.abs(oneDate - startDate)/ 1000 / 60 / 60 / 24);
                    twoDays = parseInt(Math.abs(twoDate - oneDate)/ 1000 / 60 / 60 / 24);
                    threeDays = parseInt(Math.abs(endDate - twoDate)/ 1000 / 60 / 60 / 24);
                    oneJie = parseInt(Math.abs(oneDate - startDate)/ 1000 / 60 / 60 / 24);
                    twoJie = parseInt(Math.abs(twoDate - startDate)/ 1000 / 60 / 60 / 24);

                    console.log(twoDate);
                    var content = "";
                    content += "<div class='pointTitle'>"+ data[0].PROJECT_NAME +"</div>";
                    if(data[0].ADDRESS){
                        content += "<div class='pointText'>【地址】" + data[0].ADDRESS +"</div>";
                    }else {
                        content += "<div class='pointText'>【地址】</div>";
                    }
                    if(data[0].DEVELOPER){
                        content += "<div class='pointText'>【开发商】" + data[0].DEVELOPER +"</div>";
                    }else {
                        content += "<div class='pointText'>【开发商】</div>";
                    }
                    if(data[0].STOCK_AREA){
                        content += "<div class='pointText'>【项目存量】<span>" + data[0].STOCK_AREA +"</span>万㎡</div>";
                    }else {
                        content += "<div class='pointText'>【项目存量】<span>--</span>万㎡</div>";
                    }
                    content += '<table class="lineTable" cellpadding="0" cellspacing="0">';
                    if(data[0].PRINT_DATE){
                        if(data[0].BUILD_DATE){
                            if(data[0].PROJECT_DATE){
                                content += '<tr><td></td><td><div class="timeBox">土地成交：'+ data[0].DEAL_DATE +'</div></td></tr>';
                                content += '<tr><td class="bg1" width="20" height="'+ oneDays / allDays*100 +'"></td><td></td></tr>';
                                content += '<tr><td></td><td><div class="timeBox">工程规划：' + data[0].PRINT_DATE + '</div></td></tr>';
                                content += '<tr><td class="bg2" width="20" height="' + twoDays / allDays*100 + '"></td><td></td></tr>';
                                content += '<tr><td></td><td><div class="timeBox">建设施工：' + data[0].BUILD_DATE + '</div></td></tr>';
                                content += '<tr><td class="bg3" width="20" height="' + threeDays / allDays*100 + '"></td><td></td></tr>';
                                content += '<tr><td></td><td><div class="timeBox">房屋销售：' + data[0].PROJECT_DATE + '</div></td></tr>';
                            }else{
                                content += '<tr><td></td><td><div class="timeBox">土地成交：'+ data[0].DEAL_DATE +'</div></td></tr>';
                                content += '<tr><td class="bg1" width="20" height="'+ oneDays / twoJie*100 +'"></td><td></td></tr>';
                                content += '<tr><td></td><td><div class="timeBox">工程规划：' + data[0].PRINT_DATE + '</div></td></tr>';
                                content += '<tr><td class="bg2" width="20" height="' + twoDays / twoJie*100 + '"></td><td></td></tr>';
                                content += '<tr><td></td><td><div class="timeBox">建设施工：' + data[0].BUILD_DATE + '</div></td></tr>';
                            }
                        }else{
                            content += '<tr><td></td><td><div class="timeBox">土地成交：'+ data[0].DEAL_DATE +'</div></td></tr>';
                            content += '<tr><td class="bg1" width="20" height="'+ oneDays / oneJie*100 +'"></td><td></td></tr>';
                            content += '<tr><td></td><td><div class="timeBox">工程规划：' + data[0].PRINT_DATE + '</div></td></tr>';
                        }
                    }else{
                        content += '<tr><td></td><td><div class="timeBox">土地成交：'+ data[0].DEAL_DATE +'</div></td></tr>';
                        content += '<tr><td class="bg1" width="20" height="100"></td><td></td></tr>';
                        content += '<tr><td></td><td><div class="timeBox">工程规划：--</div></td></tr>';
                    }

                    content += '</table>';
                    var searchInfoWindow = new BMapLib.SearchInfoWindow(mp, content, {
                        title: " 项目概况", //标题
                        width: 240, //宽度
                        height: 240, //高度
                        enableAutoPan : true, //自动平移
                        enableMessage:false,
                        searchTypes :[
                        ]
                    });
                    searchInfoWindow.open(new BMap.Point(data[0].X,data[0].Y));
                },
                error : function() {
                    alert('数据加载错误！');
                }
            });
        }

        mp.getPanes().labelPane.appendChild(div);

        return div;
    }
    ComplexCustomOverlay.prototype.draw = function(){
        var map = this._map;
        var pixel = map.pointToOverlayPixel(this._point);
        this._div.style.left = pixel.x - 25 + "px";
        this._div.style.top  = pixel.y - 30 + "px";
    }

    //var myCompOverlay = new ComplexCustomOverlay(new BMap.Point(104.078618,30.81), "项目名称",2.2);

    //将日期转换为高度



</script>
</html>
