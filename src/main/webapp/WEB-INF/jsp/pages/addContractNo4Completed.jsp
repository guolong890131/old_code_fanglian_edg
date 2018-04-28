<%--
  Created by IntelliJ IDEA.
  User: as
  Date: 2016/12/10 0010
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="toolbarNo">
    <div class="search-box">
        <form action="">
            <label>
                <input id="sf" value="" type="text" name="layout_no" placeholder="请输入规划许可证号进行查询" />
            </label>
            <label>
                <input id="query" type="button" class="blue" value="查询" />
            </label>
        </form>
    </div>
</div>
<table id="contractNo" data-options="
                        toolbar:'#toolbarNo',
                        rownumbers:false,
                        border:false,
                        method:'post',
                        pagination:false,
                        fitColumns:true,
                        singleSelect:true,
                        url:'${pageContext.request.contextPath}/build/queryBAH'
                        ">
    <thead>
    <tr>
        <th data-options="field:'checkbox',checkbox:true"></th>
        <th data-options="field:'name',width:50">规划许可证号</th>
        <th data-options="field:'region',width:50">区域</th>
        <th data-options="field:'address',width:50">项目地址</th>
        <th data-options="field:'project',width:50">项目名称</th>
        <th data-options="field:'unit',width:50">建设单位</th>

    </tr>
    </thead>
</table>
<script>

    $("#contractNo").datagrid({
        onUncheck: function(rowIndex,rowData){
            deleteFill();
        },
        onCheck: function(rowIndex,rowData){
            deleteFill();
        },
        onCheckAll: function(rowIndex,rowData){
            deleteFill();
        },
        onUncheckAll: function(rowIndex,rowData){
            deleteFill();
        }
    });

    $("#query").click(function () {
        var param = $("#sf").val();
        if(param){
            $("#contractNo").datagrid("load",{layout_no:param});
        }else {
            $("#contractNo").datagrid("load",{layout_no:''});
        }



    });

</script>