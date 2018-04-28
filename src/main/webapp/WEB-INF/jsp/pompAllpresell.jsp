<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<%--全域本年累计批准预售面积 --%>

                <div class="chartBox">
                    <div class="chartTitle pompAllsell">
                        <span class="select time">
                            <span id="preLayer1" class="on" onclick="preChangeLayer('1001')">主城区</span><span id="preLayer2" onclick="preChangeLayer('1002')">二圈层</span><span id="preLayer3" onclick="preChangeLayer('1003')">三圈层</span>
                        </span>
                        <span class="select time">
                            <span id="preUsage1" class="on" onclick="preChangeUsage('1001')">住宅</span><span id="preUsage2" onclick="preChangeUsage('1002')">商业</span><span id="preUsage3" onclick="preChangeUsage('1003')">办公</span><span id="preUsage4" onclick="preChangeUsage('1004')">车位</span>
                        </span>
                        <span class="time">
                            <span id="preTime1" class="on" onclick="preChangeTime('本月')">本月</span><span id="preTime2" onclick="preChangeTime('三月')">三月</span><span id="preTime3"  onclick="preChangeTime('半年')">半年</span><span id="preTime4" onclick="preChangeTime('一年')">一年</span>
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
                                        <td width="60">行政区域</td>
                                        <td width="90">新增项目数量</td>
                                        <td>批准销售面积</td>
                                    </tr>
                                    </thead>
                                </table>
                                <div class="tableOverflow pomp">
                                    <table cellpadding="0" cellspacing="0" width="100%" class="tableArea">
                                        <tbody id="preRegionBody">

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
                                        <td>批准销售面积</td>
                                    </tr>
                                    </thead>
                                </table>
                                <div class="tableOverflow pomp">
                                    <table cellpadding="0" cellspacing="0" width="100%" class="tableArea">
                                        <tbody id="preProjectBody">

                                        </tbody>
                                    </table>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
<form id="queryValue">
    <input id="preLayerType" type="hidden" name="layer_code" value="1001">
    <input id="preUsageCode" type="hidden" name="usage_code" value="1001">
    <input id="preFlagTime" type="hidden" name="flagYM" value="本月">
</form>
<script>
    function preChangeLayer(code){
        $("#preLayerType").val(code);
        if('1001'==code){
            $("#preLayer1").addClass("on");
            $("#preLayer2").removeClass("on");
            $("#preLayer3").removeClass("on");
        }
        if('1002'==code){
            $("#preLayer1").removeClass("on");
            $("#preLayer2").addClass("on");
            $("#preLayer3").removeClass("on");
        }
        if('1003'==code){
            $("#preLayer1").removeClass("on");
            $("#preLayer2").removeClass("on");
            $("#preLayer3").addClass("on");
        }
        preQueryPomp();
    }
    function preChangeUsage(code){
        $("#preUsageCode").val(code);
        if('1001'==code){
            $("#preUsage1").addClass("on");
            $("#preUsage2").removeClass("on");
            $("#preUsage3").removeClass("on");
            $("#preUsage4").removeClass("on");
        }
        if('1002'==code){
            $("#preUsage1").removeClass("on");
            $("#preUsage2").addClass("on");
            $("#preUsage3").removeClass("on");
            $("#preUsage4").removeClass("on");
        }
        if('1003'==code){
            $("#preUsage1").removeClass("on");
            $("#preUsage2").removeClass("on");
            $("#preUsage3").addClass("on");
            $("#preUsage4").removeClass("on");
        }
        if('1004'==code){
            $("#preUsage1").removeClass("on");
            $("#preUsage2").removeClass("on");
            $("#preUsage3").removeClass("on");
            $("#preUsage4").addClass("on");
        }
        preQueryPomp();
    }

    function preChangeTime(code){
        $("#preFlagTime").val(code);
        if('本月'==code){
            $("#preTime1").addClass("on");
            $("#preTime2").removeClass("on");
            $("#preTime3").removeClass("on");
            $("#preTime4").removeClass("on");
        }
        if('三月'==code){
            $("#preTime1").removeClass("on");
            $("#preTime2").addClass("on");
            $("#preTime3").removeClass("on");
            $("#preTime4").removeClass("on");
        }
        if('半年'==code){
            $("#preTime1").removeClass("on");
            $("#preTime2").removeClass("on");
            $("#preTime3").addClass("on");
            $("#preTime4").removeClass("on");
        }
        if('一年'==code){
            $("#preTime1").removeClass("on");
            $("#preTime2").removeClass("on");
            $("#preTime3").removeClass("on");
            $("#preTime4").addClass("on");
        }
        preQueryPomp();
    }

    function preQueryPomp(){
        var paramObj = {};
        $.each($("#queryValue").serializeArray(),function(i,d){
            paramObj[d.name] = d.value;
        });
        $.get("${pageContext.request.contextPath}/market/findPompAllpresell",paramObj,function(data){
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
            $("#preRegionBody").html(trHtmlOne);

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
            $("#preProjectBody").html(trHtmlTwo);

        });
    }

    preQueryPomp();

</script>