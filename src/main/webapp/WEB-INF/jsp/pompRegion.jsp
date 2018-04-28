<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<%--全域楼市可销售面积-区域统计 --%>

                <div class="chartBox">
                    <div class="chartTitle">
                        <span class="text">项目可售面积总量排行</span>
                        <span class="select">
                            <input id="flagOne" type="hidden" name="flagYM">
                        </span>
                        <span class="time">
                            <span id="numOne" class="on" onclick="changeNum(10)" >前十</span><span id="numTwo" onclick="changeNum(50)">前五十</span>
                        </span>
                    </div>
                    <table cellpadding="0" cellspacing="0" width="100%" class="tableArea">
                        <thead>
                        <tr>
                            <td>项目名称</td>
                            <td>可售面积</td>
                            <td>住宅</td>
                            <td>商业</td>
                            <td>办公</td>
                            <td>住宅销售周期</td>
                        </tr>
                        </thead>
                        <tbody id="allpompRegion">

                        </tbody>
                    </table>
                </div>
<form id="queryValuePomp">
    <input id="regionHidden" type="hidden" name="regionName" value="">
    <input id="falgNum" type="hidden" name="num" value="10">
</form>
<script type="text/javascript">
    var reg = $("#reginName").text();
    $("#regionHidden").val(reg);


    function changeNum(flag){
        $("#falgNum").val(flag);
        if(10==flag){
            $("#numOne").addClass("on");
            $("#numTwo").removeClass("on");
        }
        if(50==flag){
            $("#numOne").removeClass("on");
            $("#numTwo").addClass("on");
        }
        queryRegionArealist();
    }

    function queryRegionArealist(){
        var paramObj = {};
        $.each($("#queryValuePomp").serializeArray(),function(i,d){
            paramObj[d.name] = d.value;
        });
        $.get("${pageContext.request.contextPath}/stock/findPompRegion",paramObj,function(data){
            console.debug(data);
            var list = data.result.list;
            console.debug(list);
            var trHtml = "";
            $.each(list,function(k,v){
                trHtml += "<tr>";
                if(v.project_name != null){
                    trHtml += "<td class='area'>"+ v.project_name +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.all_area != null){
                    trHtml += "<td>"+ v.all_area +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.house_area != null){
                    trHtml += "<td>"+ v.house_area +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.business_area != null){
                    trHtml += "<td>"+ v.business_area +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                if(v.office_area != null){
                    trHtml += "<td>"+ v.office_area +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
//                if(v.stall != null){
//                    trHtml += "<td>"+ v.stall +"</td>";
//                }else{
//                    trHtml += "<td>"+ nullData +"</td>";
//                }
                if(v.xszq != null){
                    trHtml += "<td>"+ v.xszq +"</td>";
                }else{
                    trHtml += "<td>"+ nullData +"</td>";
                }
                trHtml += "</tr>"
            });
            $("#allpompRegion").html(trHtml);
        });
    }
    queryRegionArealist();
</script>