<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<%--全域本年累计成交面积 --%>

                <div class="chartBox">
                    <div class="chartTitle pompAllsell">
                        <span class="select time">
                            <span id="areaLayer1" class="on" onclick="areaChangeLayer('1001')">主城区</span><span id="areaLayer2" onclick="areaChangeLayer('1002')">二圈层</span><span id="areaLayer3" onclick="areaChangeLayer('1003')">三圈层</span>
                        </span>
                        <span class="select time">
                            <span id="areaUsage1" class="on" onclick="areaChangeUsage('1001')">住宅</span><span id="areaUsage2" onclick="areaChangeUsage('1002')">商业</span><span id="areaUsage3" onclick="areaChangeUsage('1003')">办公</span><span id="areaUsage4" onclick="areaChangeUsage('1004')">车位</span>
                        </span>
                        <span class="time">
                            <span id="areaTime1" class="on" onclick="areaChangeTime('本月')">本月</span><span id="areaTime2" onclick="areaChangeTime('三月')">三月</span><span id="areaTime3"  onclick="areaChangeTime('半年')">半年</span><span id="areaTime4" onclick="areaChangeTime('一年')">一年</span>
                        </span>
                    </div>
                    <table cellpadding="0" cellspacing="0" width="100%" class="lrTable">
                        <tr>
                            <td width="40%">行政区域统计</td>
                            <td>项目排行</td>
                        </tr>
                        <tr>
                            <td>
                                <table cellpadding="0" cellspacing="0" width="100%" class="tableArea">
                                    <thead>
                                    <tr>
                                        <td width="70">行政区域</td>
                                        <td width="100">新增项目数量</td>
                                        <td>成交面积</td>
                                    </tr>
                                    </thead>
                                </table>
                                <div class="tableOverflow pomp">
                                    <table cellpadding="0" cellspacing="0" width="100%" class="tableArea">
                                        <tbody id="areaRegionBody">

                                        </tbody>
                                    </table>
                                </div>
                            </td>
                            <td>
                                <table cellpadding="0" cellspacing="0" width="100%" class="tableArea">
                                    <thead>
                                    <tr>
                                        <td width="190">项目名称</td>
                                        <td width="70">区域</td>
                                        <td>成交面积</td>
                                    </tr>
                                    </thead>
                                </table>
                                <div class="tableOverflow pomp">
                                    <table cellpadding="0" cellspacing="0" width="100%" class="tableArea">
                                        <tbody id="areaProjectBody">

                                        </tbody>
                                    </table>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
<form id="areaQueryValue">
    <input id="areaLayerType" type="hidden" name="layer_code" value="1001">
    <input id="areaUsageCode" type="hidden" name="usage_code" value="1001">
    <input id="areaFlagTime" type="hidden" name="flagYM" value="本月">
</form>
<script>
    function areaChangeLayer(code){
        $("#areaLayerType").val(code);
        if('1001'==code){
            $("#areaLayer1").addClass("on");
            $("#areaLayer2").removeClass("on");
            $("#areaLayer3").removeClass("on");
        }
        if('1002'==code){
            $("#areaLayer1").removeClass("on");
            $("#areaLayer2").addClass("on");
            $("#areaLayer3").removeClass("on");
        }
        if('1003'==code){
            $("#areaLayer1").removeClass("on");
            $("#areaLayer2").removeClass("on");
            $("#areaLayer3").addClass("on");
        }
        areaQueryPomp();
    }
    function areaChangeUsage(code){
        $("#areaUsageCode").val(code);
        if('1001'==code){
            $("#areaUsage1").addClass("on");
            $("#areaUsage2").removeClass("on");
            $("#areaUsage3").removeClass("on");
            $("#areaUsage4").removeClass("on");
        }
        if('1002'==code){
            $("#areaUsage1").removeClass("on");
            $("#areaUsage2").addClass("on");
            $("#areaUsage3").removeClass("on");
            $("#areaUsage4").removeClass("on");
        }
        if('1003'==code){
            $("#areaUsage1").removeClass("on");
            $("#areaUsage2").removeClass("on");
            $("#areaUsage3").addClass("on");
            $("#areaUsage4").removeClass("on");
        }
        if('1004'==code){
            $("#areaUsage1").removeClass("on");
            $("#areaUsage2").removeClass("on");
            $("#areaUsage3").removeClass("on");
            $("#areaUsage4").addClass("on");
        }
        areaQueryPomp();
    }

    function areaChangeTime(code){
        $("#areaFlagTime").val(code);
        if('本月'==code){
            $("#areaTime1").addClass("on");
            $("#areaTime2").removeClass("on");
            $("#areaTime3").removeClass("on");
            $("#areaTime4").removeClass("on");
        }
        if('三月'==code){
            $("#areaTime1").removeClass("on");
            $("#areaTime2").addClass("on");
            $("#areaTime3").removeClass("on");
            $("#areaTime4").removeClass("on");
        }
        if('半年'==code){
            $("#areaTime1").removeClass("on");
            $("#areaTime2").removeClass("on");
            $("#areaTime3").addClass("on");
            $("#areaTime4").removeClass("on");
        }
        if('一年'==code){
            $("#areaTime1").removeClass("on");
            $("#areaTime2").removeClass("on");
            $("#areaTime3").removeClass("on");
            $("#areaTime4").addClass("on");
        }
        areaQueryPomp();
    }

    function areaQueryPomp(){
        var paramObj = {};
        $.each($("#areaQueryValue").serializeArray(),function(i,d){
            paramObj[d.name] = d.value;
        });
        $.get("${pageContext.request.contextPath}/market/findPompAllArea",paramObj,function(data){
            var list = data.result.list;
//            console.debug(list);
            var trHtmlOne = "";
            $.each(list[0],function(k,v){
                trHtmlOne += "<tr>";
                if(v.region_name != null){
                    trHtmlOne += "<td class='area' width='70'>"+ v.region_name +"</td>";
                }else{
                    trHtmlOne += "<td>"+ nullData +"</td>";
                }
                if(v.project_num != null){
                    trHtmlOne += "<td width='100'>"+ v.project_num +"</td>";
                }else{
                    trHtmlOne += "<td>"+ nullData +"</td>";
                }
                if(v.area != null){
                    trHtmlOne += "<td>"+ v.area +"</td>";
                }else{
                    trHtmlOne += "<td>"+ nullData +"</td>";
                }
                trHtmlOne += "</tr>"
            });
            $("#areaRegionBody").html(trHtmlOne);

            var trHtmlTwo = "";
            $.each(list[1],function(k,v){
                trHtmlTwo += "<tr>";
                if(v.project_name != null){
                    trHtmlTwo += "<td class='area' width='190'>"+ v.project_name+"</td>";
                }else{
                    trHtmlTwo += "<td>"+ nullData +"</td>";
                }
                if(v.region_name != null){
                    trHtmlTwo += "<td width='70'>"+ v.region_name +"</td>";
                }else{
                    trHtmlTwo += "<td>"+ nullData +"</td>";
                }
                if(v.area != null){
                    trHtmlTwo += "<td>"+ v.area+"</td>";
                }else{
                    trHtmlTwo += "<td>"+ nullData +"</td>";
                }
                trHtmlTwo += "</tr>"
            });
            $("#areaProjectBody").html(trHtmlTwo);

        });
    }

    areaQueryPomp();

</script>