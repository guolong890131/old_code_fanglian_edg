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
                <input id="sf" type="text" name="fctrno_new" placeholder="请输入土地合同编号进行查询" />
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
                        url:'${pageContext.request.contextPath}/layout/queryBAH'
                        ">
    <thead>
    <tr>
        <th data-options="field:'checkbox',checkbox:true"></th>
        <th data-options="field:'name',width:50">土地合同编号</th>
        <th data-options="field:'region',width:30">区域</th>
        <th data-options="field:'address',width:50">宗地位置</th>
        <th data-options="field:'fowner',width:50">竞得者</th>

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
            $("#contractNo").datagrid("load",{fctrno_new:param});
        }else {
            $("#contractNo").datagrid("load",{fctrno_new:''});
        }



    });

</script>