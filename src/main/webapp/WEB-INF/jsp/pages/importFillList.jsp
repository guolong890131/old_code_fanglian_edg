<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: as
  Date: 2016/12/10 0010
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="toolbar1">
    <div class="ttMsg"> 数据导入中，请稍等……
        <dl class="barbox">
            <dd class="barline">
                <div w="95" style="width:0px;" class="charts"></div>
            </dd>
        </dl>
    </div>
    <span class="srMsg">已导入：<b>7条</b></span>
    <span class="srMsg">失败：<b>2条</b></span>
</div>
<table id="fieldTble" class="easyui-datagrid" data-options="
                        toolbar:'#toolbar1',
                        rownumbers:true,
                        border:false,
                        method:'post',
                        pagination:true,
                        fitColumns:true,
                        singleSelect:true,
                        url:'',
                        onLoadSuccess:checkedBoxCon
                        ">
    <thead>
    <tr>
        <th data-options="field:'region',width:50">规划许可证号</th>
        <th data-options="field:'fowner',width:20">状态</th>
        <th data-options="field:'ftrtime',width:30">失败原因</th>
    </tr>
    </thead>

</table>

<script language="javascript">
    function animate(){
        $(".charts").each(function(i,item){
            var a=parseInt($(item).attr("w"));
            $(item).animate({
                width: a+"%"
            },1000);
        });
    }
    animate();
</script>

