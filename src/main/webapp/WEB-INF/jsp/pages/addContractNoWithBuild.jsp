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
        <form action="" id="addsearchForm">
            <label>
                <input type="text" name="layoutno" placeholder="请输入规划许可证号进行查询" />
            </label>
            <label>
                <input id="query" type="button" class="blue" value="查询" />
            </label>
        </form>
    </div>
</div>
<table id="contractNo"  data-options="
                        toolbar:'#toolbarNo',
                        rownumbers:false,
                        border:false,
                        method:'post',
                        pagination:false,
                        fitColumns:true,
                        singleSelect:true,
                        url:'${pageContext.request.contextPath}/build/getContractNoList'
                        ">
    <thead>
    <tr>
        <th data-options="field:'checkbox',checkbox:true"></th>
        <th data-options="field:'LAYOUTNO',width:50">规划许可证号</th>
        <th data-options="field:'REGION',width:50">区域</th>
        <th data-options="field:'ADDRESS',width:50">项目名称</th>
        <th data-options="field:'PROJECT',width:50">项目地址</th>
        <th data-options="field:'UNIT',width:50">建设单位</th>
    </tr>
    </thead>

</table>
<script>

    $("#contractNo").datagrid({
        onUncheck: function (rowIndex, rowData) {
            deleteFill();
        },
        onCheck: function (rowIndex, rowData) {
        },
        onCheckAll: function (rowIndex, rowData) {
            deleteFill();
        },
        onUncheckAll: function (rowIndex, rowData) {
            deleteFill();
        }
    });

    $("#query").click(function () {
        var param = {};
        var searchword = $("#addsearchForm").find("input[name='layoutno']").val();
        if(searchword!=null && searchword!=undefined && $.trim(searchword)!=''){
            param['name']=searchword;
        }
        $("#contractNo").datagrid("load",param);
    });

</script>