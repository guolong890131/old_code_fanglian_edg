<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
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
</head>
<body>
<div id="toolbar">
    <div class="newHouse new-newHouse">
        <div class="title">用户管理</div>
        <div class="tip new-tip">如遇操作问题，请致电：<span>028-86279869</span>，下载用户手册请点击<span>右侧问号</span>按钮<span class="wen">？</span></div>
    </div>
    <div class="search-box">
        <form action="" id="UserSearchForm">
            <label class="new-combo">
                <input id="regList" type="text" name="regList" style="width:200px;height:30px" />
                <div id="sp">
                    <div class="regBox">
                        <label><input type="checkbox" id="selectAll" />全选</label>
                        <c:forEach var="a" items="${regList}" varStatus="d">
                            <%--<label style="width: 100%;background-color: #E4DDDD;" vks="${d.index+1}"><input type="checkbox" value="${a.circle}" name="circle[${d.count}]" >${a.circle}</label>--%>
                            <c:forEach var="c" items="${a.circlelist}" varStatus="b">
                                <label parentvks="${d.index+1}"><input type="checkbox" value="${c.NAME}" name="district[${b.count}]" data_source="${c.CODE}">${c.NAME}</label>
                            </c:forEach>
                        </c:forEach>
                    </div>
                </div>
            </label>
            <label style="width: 150px;">
                <select id="roleName" name="rolecode">
                    <option value="">请选择角色</option>
                    <c:forEach items="${roleList}" var="role">
                        <option value="${role.ROLEID}">${role.ROLENAME}</option>
                    </c:forEach>
                </select>
            </label>
            <label style="width:30px;">
                <input type="text" placeholder="请输入用户账号" NAME="loginname" />
            </label>
            <label >
                创建时间：
                <input class="easyui-datebox" data-options="width:130,height:25" value="" id="startTime" name="starttime" type="text"> -
                <input class="easyui-datebox" data-options="width:130,height:25" value="" id="endTime" name="endtime" type="text">
            </label>
            <label>
                <input type="button" class="blue" value="查询" onclick="searchForm()"/>
            </label>
        </form>
    </div>
    <div class="table-toolbar">
        <button id="addFill" data-url="${pageContext.request.contextPath}/user/apply?type=" data-type="add">新增</button>
        <button id="deleteFill" disabled='disabled'>失效</button>
    </div>
</div>
<table id="fieldTble"  data-options="
                        toolbar:'#toolbar',
                        rownumbers:false,
                        border:false,
                        method:'post',
                        pagination:true,
                        fitColumns:true,
                        singleSelect:true,
                        url:'${pageContext.request.contextPath}/users/getUserList',
                        onLoadSuccess:checkedBoxCon
                        ">
    <thead>
    <tr>
        <th data-options="field:'checkbox',checkbox:true"></th>
        <th data-options="field:'regionname',width:50">区域</th>
        <th data-options="field:'name',width:50">姓名</th>
        <th data-options="field:'loginname',width:50">账号</th>
        <th data-options="field:'rolename',width:50">角色</th>
        <th data-options="field:'createtime',width:50">创建时间</th>
        <th data-options="field:'status',width:30,formatter:statusFormater">状态</th>
        <th data-options="field:'uuid',align:'center',formatter:rowFormater,width:30">操作</th>
    </tr>
    </thead>

</table>
</body>
<script type="text/javascript">

    function searchForm() {
        var form = $("#UserSearchForm");
        var param = {};

        var regList = $('#regList').combo('getValue');
        if('全域成都'!=regList){
            regList=regList.substring(0,regList.length-1);
            param['regList'] = regList;
        }
        var rolename = form.find("select[name='rolecode']").val();
        if(rolename!=null && rolename!='' && rolename!=undefined){
            param['rolecode']=rolename;
        }

        var loginname = form.find("input[name='loginname']").val();
        if(loginname!=null && loginname!='' && loginname!=undefined){
            param['loginname']=loginname;
        }
        var starttime = form.find("input[name='starttime']").val();
        if(starttime!=null && starttime!='' && starttime!=undefined){
            param['starttime']=starttime;
        }
        var endtime = form.find("input[name='endtime']").val();
        if(endtime!=null && endtime!='' && endtime!=undefined){
            param['endtime']=endtime;
        }
//        console.debug(param);
        $('#fieldTble').datagrid({queryParams: param});   //点击搜索
    }

    function rowFormater(val,row,index) {
        if(row.status!=1){
            return '<a href="javascript:void(0);" onclick="view(\''+ val +'\')">查看</a>';
        }else{
            return '<a href="javascript:void(0);" onclick="edit(\''+ val +'\')">编辑</a> '+
                    ' <a href="javascript:void(0);" onclick="view(\''+ val +'\')">查看</a>';
        }
    }

    function statusFormater(val,row,index) {
        if(row.status!=1){
            return '失效';
        }else{
            return '有效';
        }
    }

    //table操作
    function deleteFill(){
        var getChecked = $("#fieldTble").datagrid("getChecked");
        if(getChecked.length > 0){
            $("#deleteFill").removeAttr("disabled");
        }else{
            $("#deleteFill").attr("disabled",true);
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
            if(value.deal=="失效"){
                $('tbody input:checkbox').eq(key+1).attr("disabled",'disabled');
            }
        });

        //新增
        $("#addFill").on("click",function(){
            openDialog("width:660,height:338,winId:'addUserWin',isIframe:false,title:'新增',url: '${pageContext.request.contextPath }/users/apply',buttons:[{text: '确认',handler:function(){saveUser()}}]");
        });

        //删除
        $("#deleteFill").on("click",function() {
            var rows = $("#fieldTble").datagrid("getChecked");
            if(rows.length==0){
                Cmessager.alert(GV.MESSAGER_TITLE, '请选择要删除的项目！', 'warning');
                return false;
            }
            var param = {};
            var i = 0;
            $.each(rows, function (key, value) {
                param['uuidList['+i+']']=value.uuid;
                i++;
            });
            Cmessager.confirm(GV.MESSAGER_TITLE, '确认要删除吗?', function (r) {
                if (r) {
                    $.post(
                            '${pageContext.request.contextPath}/users/deleteUser',
                            param,
                            function(data){
                                if(data.status=='success') {
                                    $("#fieldTble").datagrid('reload');
                                    Cmessager.alert(GV.MESSAGER_TITLE, data.message, 'info');
                                }else{
                                    Cmessager.alert(GV.MESSAGER_TITLE, data.message, 'error');
                                }
                            }
                    )
                }
            });
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
            $('.regBox input[type="checkbox"]:checked').each(function(i,t){
                if($(this).parent().text()!='全选' && typeof($(this).parent().attr("vks"))=="undefined"){
//                    console.debug(t);
                    v += $(this).val()+',';
                    source += $(this).attr("data_source")+",";
                    s += $(this).parent().text()+',';
                }
            });
            $('#regList').combo('setValue', source).combo('setText', s);
        });

    });

    //保存用户信息
    function saveUser(){
        if($("#addUserForm").form('validate')){
            $("#addUserForm").form('submit', {
                url : '${pageContext.request.contextPath}/users/add',
                onSubmit : function() {
                    return $(this).form('validate');//对数据进行格式化
                },
                success : function(data) {
                    var dataObj=eval("("+data+")");
                    if(dataObj.status=='success') {
                        $("#fieldTble").datagrid('reload');
                        Cmessager.alert(GV.MESSAGER_TITLE, dataObj.message, 'info');
                        $("#addUserWin").dialog('close');
                    }else{
                        Cmessager.alert(GV.MESSAGER_TITLE, dataObj.message, 'error');
                    }
                }
            });
        }
    }

    //查看用户信息
    function view(obj){
        openDialog("width:660,height:338,winId:'addUserWin',isIframe:false,title:'查看',url: '${pageContext.request.contextPath }/users/view?uuid="+obj+"&&type=view',buttons:false");
    }

    //编辑用户信息
    function edit(obj){
        openDialog("width:660,height:338,winId:'addUserWin',isIframe:false,title:'编辑',url: '${pageContext.request.contextPath }/users/view?uuid="+obj+"&&type=edit',buttons:[{text: '确认',handler:function(){saveUser()}}]");
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


    function checkedBoxCon(){
        var rows = $("#fieldTble").datagrid("getRows");
        $.each(rows, function(key, value){
            if(value.status==0){
                $('tbody input:checkbox').eq(key+1).attr("disabled",'disabled');
            }
        });
    }

</script>
</html>
