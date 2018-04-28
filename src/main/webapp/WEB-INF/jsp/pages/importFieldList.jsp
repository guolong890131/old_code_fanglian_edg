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
<div id="toolbar1">
    <div class="ttMsg"> 数据导入中，请稍等……
        <dl class="barbox">
            <dd class="barline">
                <div w="95" style="width:0px;" class="charts"></div>
            </dd>
        </dl>
    </div>
    <span class="srMsg">已导入：<b id="s_id">0条</b></span>
    <span class="srMsg">失败：<b id="f_id">0条</b></span>
</div>
<table id="fieldIMPTble" class="easyui-datagrid" data-options="
                        toolbar:'#toolbar1',
                        rownumbers:true,
                        border:false,
                        method:'post',
                        pagination:true,
                        fitColumns:true,
                        singleSelect:true,
                        url:''
                        ">
    <thead>
    <tr>
        <th data-options="field:'fno_new',width:50">宗地号</th>
        <th data-options="field:'import_state',width:20,formatter:forState">状态</th>
        <th data-options="field:'import_state_thi',width:30,formatter:forStateThi">失败原因</th>
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
    function forStateThi(value, row, index){
        if (row.import_state=="1") {
            return '';
        }
        else {
            return row.import_state;
        }
    }

    function doUpload() {
        var formData = new FormData($( "#importFieldForm" )[0]);
        $.ajax({
            url: '${pageContext.request.contextPath}/uploadFieldExcel/fileupload' ,
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

    <%--$.ajaxFileUpload({--%>
        <%--url: '${pageContext.request.contextPath}/uploadFieldExcel/fileupload', //用于文件上传的服务器端请求地址--%>
        <%--fileElementId:'importFieldFile',//file标签的id //文件上传域的ID--%>
        <%--type:'post',--%>
        <%--dataType: 'json', //返回值类型 一般设置为json--%>
        <%--secureuri: false, //是否需要安全协议，一般设置为false--%>
        <%--//data:{name:'logan'},//一同上传的数据--%>
        <%--success: function (data, status) {--%>
            <%--if(data.success){--%>
                <%--c = setInterval(checkState(),3000);//每3秒执行一次checkState方法--%>
                <%--$("#importFillDialog").dialog('close');--%>
            <%--}else{--%>
                <%--console.debug(data);--%>
                <%--console.debug(status);--%>
                <%--$.messager.alert('提示',data.message,'error');--%>
                <%--$("#importFillList").dialog('close');--%>
            <%--}--%>
        <%--},--%>
         <%--error: function (data, status, e) {--%>
             <%--console.debug(data);--%>
             <%--console.debug(status);--%>
            <%--console.debug(e);--%>
         <%--}--%>
    <%--});--%>

    function checkState() {
        $.get("${pageContext.request.contextPath}/euploadFieldExcel/fileUploadStat",function (data) {
            if(data.success){
                $("#s_id").text(data.s+'条');
                $("#f_id").text(data.f+'条');
                if(data.ipmState){
                    window.clearInterval(c);
                    //查询列表数据
                    var opts = $("#fieldIMPTble").datagrid("options");
                    opts.url="${pageContext.request.contextPath}/uploadFieldExcel/queryFieldIMP";
                    $("#fieldIMPTble").datagrid("load");
                };
            }else {
                $.messager.alert('提示',data.message,'error');
            };
        })
    }


</script>

