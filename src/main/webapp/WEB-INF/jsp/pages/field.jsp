<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>成都市房地产市场数据填报平台</title>
    <!--                       CSS                       -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/color.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="screen" />
    <!--                       Javascripts                       -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts-all.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
</head>
<body>
<div id="toolbar">
    <div class="newHouse new-newHouse">
        <div class="title">土地数据填报 - 查询</div>
        <div class="tip new-tip">如遇操作问题，请致电：<span>028-86279869</span>，下载用户手册请点击<span>右侧问号</span>按钮<span class="wen" data-url="${pageContext.request.contextPath}/downLoad/index?FilenameSign=" >？</span></div>
    </div>
    <div class="search-box">
        <form id="sForm">
            <label class="new-combo">
                <input id="regList" type="text" name="regList" style="width:180px;height:30px" />
                <div id="sp">
                    <div class="regBox">
                        <label><input type="checkbox" id="selectAll" />全选</label>
                        <%--<c:forEach var="a" items="${regList}" varStatus="d">--%>
                            <%--&lt;%&ndash;<label style="width: 100%;background-color: #E4DDDD;" vks="${d.index+1}"><input type="checkbox" value="${a.circle}" name="circle[${d.count}]" >${a.circle}</label>&ndash;%&gt;--%>
                            <%--<c:forEach var="c" items="${a.circlelist}" varStatus="b">--%>
                                <%--<label parentvks="${d.index+1}"><input type="checkbox" value="${c.NAME}" name="district[${b.count}]" data_source="${c.CODE}">${c.NAME}</label>--%>
                            <%--</c:forEach>--%>
                        <%--</c:forEach>--%>
                        <c:forEach var="a" items="${regList4user}" varStatus="d">
                            <label parentvks="${d.index+1}"><input  type="checkbox" value="${a.name}" name="district[${d.count}]" data_source="${a.code }">${a.name}</label>
                        </c:forEach>
                    </div>
                </div>
            </label>
            <label>
            <select id="ftradeType" name="ftradetype" style="width: 30px;">
                <option value="" >选择出让方式</option>
            </select>
            </label>
            <label>
            <select name="searchType" >
                <option value="" selected>选择查询方式</option>
                <option value="ffileno">国土证号/不动产证书号</option>
                <option value="fctrno_new">土地合同编号</option>
                <option value="fno_new" >宗地编号</option>
                <option value="faddress">宗地位置</option>
                <option value="fowner">竞得者</option>
                <option value="fplanusg">用地性质</option>
            </select>
            <input type="text" name="searchValue" placeholder="请输入查询参数值"  style="width: 30px;"/>
            </label>
            <label >
                <select name="timeType" >
                    <option value="ftrtime">出让时间</option>
                    <option value="create_time">录入时间</option>
                </select>
                <input class="easyui-datebox" data-options="width:120,height:25" value="" id="startTime" name="startFtr" type="text"> -
                <input class="easyui-datebox" data-options="width:120,height:25" value="" id="endTime" name="endFtr" type="text">
            </label>
            <label>
            <input id="query" type="button" class="blue" value="查询" />
            </label>
        </form>
    </div>
    <div class="table-toolbar">
        <button id="addFill" data-url="${pageContext.request.contextPath}/field/apply?type=" data-type="add">新增</button>
        <button id="importFill">导入</button>
        <button id="deleteFill" disabled='disabled'>删除</button>
        <button id="fileFill" disabled='disabled'>提交</button>
    </div>
</div>
<table id="fieldTble" class="easyui-datagrid" data-options="
                        toolbar:'#toolbar',
                        rownumbers:false,
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
        <th data-options="field:'checkbox',checkbox:true"></th>
        <th data-options="field:'region',width:30">区域</th>
        <th data-options="field:'fno_new',width:30">宗地编号</th>
        <th data-options="field:'faddress',width:50">宗地位置</th>
        <th data-options="field:'ffileno',width:50">国土证号/不动产证书号</th>
        <th data-options="field:'fctrno_new',width:60">土地合同编号</th>
        <th data-options="field:'fplanusg',width:40">规划用地性质</th>
        <th data-options="field:'ffarea',width:40">净用地面积(㎡)</th>
        <th data-options="field:'fowner',width:50">竞得者</th>
        <th data-options="field:'create_time',width:40">录入时间</th>
        <th data-options="field:'ftrtime',width:40">出让时间</th>
        <th data-options="field:'file_status',width:25">状态</th>
        <th data-options="field:'true',align:'center',formatter:rowFormater,width:30">操作</th>
    </tr>
    </thead>

</table>
</body>
<script type="text/javascript">

    function rowFormater(val,row,index) {
        <%--改变状态的功能   if(row.file_status=="已提交" &&  ${role}!=1){--%>
        if(row.file_status=="已提交" && ${role}!=1 ){
            return '<a href="javascript:void(0);" onclick="view('+ index +',\'view\')">查看</a>';
        }else if(row.file_status=="已提交" && ${role}==1){
            return '<a href="javascript:void(0);" onclick="rollback('+ index +',\'rollback\')">回撤</a> '+
                    ' <a href="javascript:void(0);" onclick="view('+ index +',\'view\')">查看</a>';
        }else{
            return '<a href="javascript:void(0);" onclick="edit('+ index +',\'edit\')">编辑</a> '+
                ' <a href="javascript:void(0);" onclick="view('+ index +',\'view\')">查看</a>';
        }
    }

    //table操作
    function deleteFill(){
        var getChecked = $("#fieldTble").datagrid("getChecked");
        if(getChecked.length > 0){
            $("#deleteFill").removeAttr("disabled");
            $("#fileFill").removeAttr("disabled");
        }else{
            $("#deleteFill").attr("disabled",true);
            $("#fileFill").attr("disabled",true);
        }
    }
    $(function(){
        $("#fieldTble").datagrid({
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

        //根据状态使checkbox不可以
        var rows = $("#fieldTble").datagrid("getRows");
        $.each(rows, function(key, value){
//            console.log(rows);
            if(value.file_status=="已提交"){
                $('tbody input:checkbox').eq(key+1).attr("disabled",'disabled');
            }
        });

        //新增
        $("#addFill").on("click",function(){
            var url = $(this).data("url");
            var type = $(this).data("type");
            //$('#iframeId', window.parent.document).attr("src", url);
            window.location = url + type;
        });

        //导入
        $("#importFill").on("click",function(){
            openDialog("title:'批量导入',winId:'importFillDialog',url:'${pageContext.request.contextPath}/uploadFieldExcel/importField',buttons:[{text:'下一步',handler:function(){importFillNext()}}],width:570,height:205");
        });


        //删除
        $("#deleteFill").on("click",function(){
            var ids = "";
            var rows = $('#fieldTble').datagrid('getChecked');
            for(var i=0; i<rows.length; i++){


                //验证录入者
                var input = rows[i].inputer_id;
                if(input != '${userid}'){
                    var x = i+1;
                    $.messager.alert('提示','您选中的第'+x+'条数据，没有对其进行操作的权限！','info');
                    return;
                }

                ids+= rows[i].id + ',';
            }
            $.get("${pageContext.request.contextPath}/field/deleteField",{ids:ids},function (data) {
                if(data.success){
                    var a = rows.length;
                    var b = data.result;
                    var c = a-b;
                    $.messager.alert('提示','选中'+a+'条，已删除'+b+'条,失败'+c+'条','info');
                    search();
                };
            })
        });

        //提交
        $("#fileFill").on("click",function() {
            var ids = "";
            var rows = $('#fieldTble').datagrid('getChecked');
            for(var i=0; i<rows.length; i++){

                //验证录入者
                var input = rows[i].inputer_id;
                if(input != '${userid}'){
                    var x = i+1;
                    $.messager.alert('提示','您选中的第'+x+'条数据，没有对其进行操作的权限！','info');
                    return;
                }

                ids+= rows[i].id + ',';
            }
            $.get("${pageContext.request.contextPath}/field/inputField",{ids:ids},function (data) {
                if(data.success){
                    var a = rows.length;
                    var b = data.result;
                    var c = a-b;
                    $.messager.alert('提示','选中'+a+'条，已提交'+b+'条,失败'+c+'条','info');
                    search();
                };
            })

        });

        $('#regList').combo({
            editable:false,
            multiple:true,
            multiline:true,
            panelWidth:400,
            panelHeight:'auto',
            value:'全域成都'
        });
        $('#sp .regBox').appendTo($('#regList').combo('panel'));
        $(".combo-panel .regBox").on("click","label",function(){
            isSelectAll($(this));
            var v = '',s='',source='';
            if(typeof($(this).attr("vks"))!="undefined"){
                var vks = $(this).attr("vks");
                var ifchecked = $(this).find("input").attr("checked");
                $('.combo-panel .regBox label').each(function (i) {
                    if($(this).attr("parentvks")==vks){
                        if(ifchecked || ifchecked == 'checked') {
                            $(this).find("input").attr("checked", true);
                        }else{
                            $(this).find("input").attr("checked", false);
                        }
                    }
                });
            }
            $('.regBox input[type="checkbox"]:checked').each(function(i){
                if($(this).parent().text()!='全选' && typeof($(this).parent().attr("vks"))=="undefined"){
                    v += $(this).val()+',';
                    source += $(this).attr("data_source")+",";
                    s += $(this).parent().text()+',';
                }
            });
            $('#regList').combo('setValue', source+";"+v).combo('setText', s);
        });

    });

    //查看
    function view(index,type){
        var url = $("#addFill").data("url");
        //$('#iframeId', window.parent.document).attr("src", url);
        var row = $('#fieldTble').datagrid('getRows')[index];
        var rowStr =JSON.stringify(row);
        window.location = url + type +"&data="+encodeURIComponent(rowStr);
    }
    //编辑
    function edit(index,type){
        var url = $("#addFill").data("url");
        //$('#iframeId', window.parent.document).attr("src", url);
        var row = $('#fieldTble').datagrid('getRows')[index];

        //验证录入者
        var input = row.inputer_id;
        if(input != '${userid}'){
            $.messager.alert('提示','您不是此数据的录入者，无法对其进行操作！','info');
            return;
        }
        var rowStr =JSON.stringify(row);
        window.location = url + type +"&data="+encodeURIComponent(rowStr);
    }

    //回撤
    function rollback(index,type){
        var url = $("#addFill").data("url");
        //$('#iframeId', window.parent.document).attr("src", url);
        var row = $('#fieldTble').datagrid('getRows')[index];
        var rowStr =JSON.stringify(row);
        window.location = url + type +"&data="+encodeURIComponent(rowStr);
    }


    //导入下一步
    function importFillNext(){
        var file = $("#importFieldFile").val();
        if(!file){
            $.messager.alert('提示','请选择excel文件','info');
            return;
        };
        var fileExt=file.substr(file.lastIndexOf(".")).toLowerCase();//获得文件后缀名
        if(fileExt!='.xls' && fileExt!='.xlsx' ){
            $.messager.alert('提示','请上传excel文件','info');
            return;
        }
        openDialog("title:'批量导入',winId:'importFillList',url:'${pageContext.request.contextPath}/uploadFieldExcel/importFieldList',buttons:'none',width:670,height:445");
        $("#importFillList").dialog({
            id:'importFillList',
            closable: false,
            buttons:[{
                text:'完成',
                handler:function(){
                    //点击完成对应的方法
                    transferTable();
                }
            },{
                text:'关闭',
                handler:function(){
                    //点击关闭提示相关信息
                    Cmessager.confirm("提示", '<b style="font-size: 16px">确定退出导入程序?<br/></b><br/>确定，将退出程序，并删除撤销已导入数据。取消，继续导入。', function(r){
                        if(r){
                            window.clearInterval(c);
                            $("#importFillList").dialog('close');
                        }
                    })
                }
            }]
        })
    }

    //导入临时表转入正式表
    function transferTable(){
        $.get("${pageContext.request.contextPath}/uploadFieldExcel/transferTable",function (data) {
            if(data.success){
                $.messager.alert('提示','导入成功','info');
                $("#importFillList").dialog('close');
                search();
            }else {
                $.messager.alert('提示',data.message,'error');
            };
        })


    }



    /*combobox region实现多选*/
    function isSelectAll(_this,obj){
        //是否全选
        if(_this.context.innerText=='全选'){
            if($("#selectAll").attr("checked")){
                $('.regBox input[type="checkbox"]').attr("checked", true);
            }else{
                $('.regBox input[type="checkbox"]').attr("checked", false);
            }
        }else{
            //取消选中全选按钮
            $("#selectAll").attr("checked", false);
            //如果其余全选 则选中 全选按钮
            if(($('.regBox input[type="checkbox"]').length-1)==$('.regBox input[type="checkbox"]:checked').length){
                $("#selectAll").attr("checked",true);
            }

        }
    }



    $.get("${pageContext.request.contextPath}/field/queryFtradetype",function (data) {
        var html='';
        $.each(data.result.list,function (a,v) {
            html+='<option value="'+v.name+'" >'+v.name+'</option>' ;
        })
        $("#ftradeType").append(html);
    });

    function checkedBoxCon(){
        var rows = $("#fieldTble").datagrid("getRows");
        $.each(rows, function(key, value){
            if(value.file_status=="已提交"){
                $('tbody input:checkbox').eq(key+1).attr("disabled",'disabled');
            }
        });
    }

    function search() {
        var paramObj = {};
        var paramArr = $("#sForm").serializeArray();
        $.each(paramArr,function(i,data){
            paramObj[data.name] = data.value.replace('，',',');
        });
        if(paramObj['startFtr']!=''&&paramObj['endFtr']!=''){
            if(paramObj['startFtr']>paramObj['endFtr']){
                $.messager.alert('警告','开始时间需小于结束时间！！！','info');
                return;
            }
        }

        var opts = $("#fieldTble").datagrid("options");
        opts.url="${pageContext.request.contextPath}/field/queryField";
        $("#fieldTble").datagrid("load",paramObj);

    }

    $("#query").click(function () {
        search();
    });

    $(function(){
        setTimeout(function () {
            search();
        },501)
    })

    //文件下载
    $(".wen").on("click",function(){
              var url=$(this).data("url");
                window.location=url+"1";
            }
    )


</script>
</html>