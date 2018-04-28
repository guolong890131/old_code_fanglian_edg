<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: as
  Date: 2016/12/10 0010
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
<div id="toolbar3">
    <div class="ttMsg"> 数据导入中，请稍等……
        <dl class="barbox">
            <dd class="barline">
                <div w="95" style="width:0px;" class="charts"></div>
            </dd>
        </dl>
    </div>
    <span class="srMsg">已导入：<b id="bs_id">0条</b></span>
    <span class="srMsg">失败：<b id="bf_id">0条</b></span>
</div>
<table id="buildIMPTble" class="easyui-datagrid" data-options="
                        toolbar:'#toolbar3',
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
        <th data-options="field:'build_no',width:50">施工许可证号</th>
        <th data-options="field:'import_state',width:20,formatter:forState">状态</th>
        <th data-options="field:'import_state_f',width:30,formatter:forState_f">失败原因</th>
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



    function forState(value, row, index){
        if (row.import_state=="1") {
            return '成功';
        }
        else {
            return '失败';
        }
    }
    function forState_f(value, row, index){
        if (row.import_state=="1") {
            return '';
        }
        else {
            return row.import_state;
        }
    }

    function doUpload() {
        var formData = new FormData($( "#importBuildForm" )[0]);
        $.ajax({
            url: '${pageContext.request.contextPath}/uploadBuildExcel/fileupload' ,
            type: 'post',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if(data.success){
                    c = setInterval(checkState(),3000);//每3秒执行一次checkState方法
                    $("#importFillDialog").dialog('close');
                }else{
                    console.debug(data);
                    $.messager.alert('提示',data.message,'error');
                    $("#importFillList").dialog('close');
                }
            },
            error: function (data) {
                console.debug(data);
                console.debug(e);
            }
        });
    }
    doUpload();
    function checkState() {
        $.get("${pageContext.request.contextPath}/uploadBuildExcel/fileUploadState",function (data) {
            if(data.success){
                $("#bs_id").text(data.s+'条');
                $("#bf_id").text(data.f+'条');
                if(data.ipmState){
                    window.clearInterval(c);
                    //查询列表数据
                    var opts = $("#buildIMPTble").datagrid("options");
                    opts.url="${pageContext.request.contextPath}/uploadBuildExcel/queryBuildIMP";
                    $("#buildIMPTble").datagrid("load");
                };
            }else {
                $.messager.alert('提示',data.message,'error');
            };
        })
    }
</script>

