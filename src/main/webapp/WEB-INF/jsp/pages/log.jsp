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
        <div class="title">系统日志</div>
        <div class="tip new-tip">如遇操作问题，请致电：<span>028-86279869</span>，下载用户手册请点击<span>右侧问号</span>按钮<span class="wen">？</span></div>
    </div>
    <div class="search-box">
        <form id="logForm">
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
            <label>
                <select name="role">
                    <option value="">请选择角色</option>
                    <option value="国土局">国土局</option>
                    <option value="规划局">规划局</option>
                    <option value="建委">建委</option>
                    <option value="房管局">房管局</option>
                    <option value="管理员">管理员</option>
                </select>
                <input type="text" name="name" placeholder="请输入用户名称" />
            </label>
            <label>
                操作时间：
                <input class="easyui-datebox" data-options="width:150,height:25" value="" id="startFtr" name="startFtr" type="text"> -
                <input class="easyui-datebox" data-options="width:150,height:25" value="" id="endFtr" name="endFtr" type="text">
            </label>
            <label>
                <input id="query" type="button" class="blue" value="查询" />
            </label>
        </form>
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
                        url:''
                        ">
    <thead>
    <tr>
        <%--<th data-options="field:'checkbox',checkbox:true"></th>--%>
        <th data-options="field:'region',width:50">区域</th>
        <th data-options="field:'name',width:50">用户名</th>
        <th data-options="field:'role',width:50">角色</th>
        <th data-options="field:'operation',width:50">操作</th>
        <th data-options="field:'requestip',width:50">操作IP</th>
        <th data-options="field:'method',width:50">操作方法</th>
        <th data-options="field:'exceptioncode',width:50">操作异常代码</th>
        <th data-options="field:'exceptionDetail',width:50">操作异常信息</th>
        <th data-options="field:'operationDate',width:50">操作时间</th>
    </tr>
    </thead>

</table>
</body>
<script type="text/javascript">


    $(function(){

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

    })

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

    //设置当前时间
    formatterDate = function(date, addDay) {
        var dd = date;
        dd.setDate(dd.getDate() + addDay);
        var day = dd.getDate() > 9 ? dd.getDate() : "0" + dd.getDate();
        var month = (dd.getMonth() + 1) > 9 ? (dd.getMonth() + 1) : "0"
        + (dd.getMonth() + 1);
        return dd.getFullYear() + '/' + month + '/' + day;
    };

    setTimeout(function(){
        $('#startFtr').datebox('setValue', formatterDate(new Date(),-60));
        $('#endFtr').datebox('setValue', formatterDate(new Date(),1));
    },100)


    function search() {
        var paramObj = {};
        var paramArr = $("#logForm").serializeArray();
        $.each(paramArr,function(i,data){
            paramObj[data.name] = data.value;
        });
        if(paramObj['startFtr']!=''&&paramObj['endFtr']!=''){
            if(paramObj['startFtr']>paramObj['endFtr']){
                $.messager.alert('警告','开始时间需小于结束时间！！！','info');
                return;
            }
        }
        var opts = $("#fieldTble").datagrid("options");
        opts.url="${pageContext.request.contextPath}/log/queryLog";
        $("#fieldTble").datagrid("load",paramObj);

    }

    $("#query").click(function () {
        search();
    });

    $(function(){
        setTimeout(function () {
            search();
        },101)
    })
</script>
</html>

