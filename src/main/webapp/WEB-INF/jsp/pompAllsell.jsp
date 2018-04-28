<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<%--全域当前可销售面积 --%>

                <div class="chartBox">
                    <div class="chartTitle pompAllsell">
                        <span class="select time">
                            <span id="sellLayer1" class="on" onclick="sellchangeLayer('1001')">主城区</span><span id="sellLayer2" onclick="sellchangeLayer('1002')">二圈层</span><span id="sellLayer3" onclick="sellchangeLayer('1003')">三圈层</span>
                        </span>
                        <span class="select time">
                            <span id="sellusage1" class="on" onclick="sellchangeUsage('1001')">住宅</span><span id="sellusage2" onclick="sellchangeUsage('1002')">商业</span><span id="sellusage3" onclick="sellchangeUsage('1003')">办公</span><span id="sellusage4" onclick="sellchangeUsage('1004')">车位</span>
                        </span>
                        <span class="time">

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
                                        <td>可售面积</td>
                                    </tr>
                                    </thead>
                                </table>
                                <div class="tableOverflow pomp">
                                    <table cellpadding="0" cellspacing="0" width="100%" class="tableArea">
                                        <tbody id="sellregionBody">

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
                                        <td>可售面积</td>
                                    </tr>
                                    </thead>
                                </table>
                                <div class="tableOverflow pomp">
                                    <table cellpadding="0" cellspacing="0" width="100%" class="tableArea">
                                        <tbody id="sellprojectBody">

                                        </tbody>
                                    </table>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
<form id="sellqueryValue">
    <input id="sellLayerType" type="hidden" name="layer_code" value="1001">
    <input id="sellusageCode" type="hidden" name="usage_code" value="1001">
</form>
<script>
    function sellchangeLayer(code){
        $("#sellLayerType").val(code);
        if('1001'==code){
            $("#sellLayer1").addClass("on");
            $("#sellLayer2").removeClass("on");
            $("#sellLayer3").removeClass("on");
        }
        if('1002'==code){
            $("#sellLayer1").removeClass("on");
            $("#sellLayer2").addClass("on");
            $("#sellLayer3").removeClass("on");
        }
        if('1003'==code){
            $("#sellLayer1").removeClass("on");
            $("#sellLayer2").removeClass("on");
            $("#sellLayer3").addClass("on");
        }
        sellqueryPomp();
    }
    function sellchangeUsage(code){
        $("#sellusageCode").val(code);
        if('1001'==code){
            $("#sellusage1").addClass("on");
            $("#sellusage2").removeClass("on");
            $("#sellusage3").removeClass("on");
            $("#sellusage4").removeClass("on");
        }
        if('1002'==code){
            $("#sellusage1").removeClass("on");
            $("#sellusage2").addClass("on");
            $("#sellusage3").removeClass("on");
            $("#sellusage4").removeClass("on");
        }
        if('1003'==code){
            $("#sellusage1").removeClass("on");
            $("#sellusage2").removeClass("on");
            $("#sellusage3").addClass("on");
            $("#sellusage4").removeClass("on");
        }
        if('1004'==code){
            $("#sellusage1").removeClass("on");
            $("#sellusage2").removeClass("on");
            $("#sellusage3").removeClass("on");
            $("#sellusage4").addClass("on");
        }
        sellqueryPomp();
    }

    function sellqueryPomp(){
        var paramObj = {};
        $.each($("#sellqueryValue").serializeArray(),function(i,d){
            paramObj[d.name] = d.value;
        });
        $.get("${pageContext.request.contextPath}/market/findPompAllsell",paramObj,function(data){
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
            $("#sellregionBody").html(trHtmlOne);

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
            $("#sellprojectBody").html(trHtmlTwo);

        });
    }

    sellqueryPomp();



</script>