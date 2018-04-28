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
                <input id="sf" type="text" name="build_no" placeholder="请输入施工许可证书编号进行查询" />
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
                        url:'${pageContext.request.contextPath}/build/queryBSG'
                        ">
    <thead>
    <tr>
        <th data-options="field:'checkbox',checkbox:true"></th>
        <th data-options="field:'name',width:50">施工许可证书编号</th>
        <th data-options="field:'region',width:50">区域</th>
        <th data-options="field:'buildname',width:50">工程名称</th>
        <th data-options="field:'address',width:50">建设地址</th>
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
            $("#contractNo").datagrid("load",{build_no:param});
        }else {
            $("#contractNo").datagrid("load",{build_no:''});
        }



    });

</script>