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
        <div class="title">中间库数据导入</div>
        <div class="tip new-tip">本模板提供<span>中间库数据</span>的导入功能</div>
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
            <label >
                创建时间：
                <input class="easyui-datebox" data-options="width:130,height:30" value="" id="startTime" name="starttime" type="text"> -
                <input class="easyui-datebox" data-options="width:130,height:30" value="" id="endTime" name="endtime" type="text">
            </label>
            <label>
                <input type="button" class="blue" value="查询" onclick="searchForm()"/>
            </label>
        </form>
    </div>
    <div class="table-toolbar">
        <button id="addFill" data-url="${pageContext.request.contextPath}/user/apply?type=" data-type="add">导入</button>
    </div>
</div>
<table id="fieldTble"  data-options="
                        toolbar:'#toolbar',
                        rownumbers:false,
                        border:false,
                        method:'post',
                        pagination:true,
                        fitColumns:false,
                        singleSelect:true,
                        url:'${pageContext.request.contextPath}/users/getEtlField',
                        onLoadSuccess:checkedBoxCon
                        ">
    <thead>
    <tr>
        <th data-options="field:'checkbox',checkbox:true"></th>
        <th data-options="field:'region',width:50">区域</th>
        <th data-options="field:'fno_new',width:70">宗地编号</th>
        <th data-options="field:'faddress',width:70">宗地位置</th>
        <th data-options="field:'ffileno',width:150">国土证号/不动产证书号</th>
        <th data-options="field:'fctrno_new',width:100">土地合同编号</th>
        <th data-options="field:'ftradetype',width:70">出让方式</th>
        <th data-options="field:'fowner',width:70">竞得者</th>
        <th data-options="field:'faprice_mu',width:150">评估单价(万元/亩)</th>
        <th data-options="field:'fabprice',width:150">评估楼面地价(元/平方米)</th>
        <th data-options="field:'fbprice_mu',width:150">起拍单价(万元/亩)</th>
        <th data-options="field:'fbbprice',width:150">起拍楼面地价(元/平方米)</th>
        <th data-options="field:'ftrprice_mu',width:150">成交单价(万元/亩)</th>
        <th data-options="field:'ftrbprice',width:150">成交楼面地价(元/平方米)</th>
        <th data-options="field:'famount',width:150">土地价款（万元）</th>
        <th data-options="field:'ftrtime',width:150">成交时间</th>
        <th data-options="field:'ffarea',width:150">净用地面积(㎡)</th>
        <th data-options="field:'fusage_id',width:150">土地用途id</th>
        <th data-options="field:'fusage_type',width:150">土地用途修改</th>
        <th data-options="field:'fusage_name',width:150">原土地用途</th>
        <th data-options="field:'fplanusg',width:150">规划用地性质</th>
        <th data-options="field:'fbtarea',width:200">计入容积率的总建筑面积（m2）</th>
        <th data-options="field:'fbtarea_up',width:200">计入容积率的总建筑面积（上限）（m2）</th>
        <th data-options="field:'fbtarea_down',width:200">计入容积率的总建筑面积（下限）（m2）</th>
        <th data-options="field:'fhouse',width:200">计入容积率的住宅面积（m2）</th>
        <th data-options="field:'fhouse_up',width:200">计入容积率的住宅面积（上限）（m2）</th>
        <th data-options="field:'fhouse_down',width:200">计入容积率的住宅面积（下限）（m2）</th>
        <th data-options="field:'fbusiness',width:200">计入容积率的商服面积（m2）</th>
        <th data-options="field:'fbusiness_up',width:200">计入容积率的商服面积（上限）（m2）</th>
        <th data-options="field:'fbusiness_down',width:200">计入容积率的商服面积（下限）（m2）</th>
        <th data-options="field:'fbself_up',width:200">计入容积率的自持商服建筑面积（上限）</th>
        <th data-options="field:'fbself_down',width:200">计入容积率的自持商服建筑面积（下限）</th>
        <th data-options="field:'fbuse_up',width:200">计入容积率的自用商服建筑面积（上限）</th>
        <th data-options="field:'fbuse_down',width:200">计入容积率的自用商服建筑面积（下限）</th>
        <th data-options="field:'frate_up',width:100">容积率（上限）</th>
        <th data-options="field:'frate_dow',width:100">容积率（下限）</th>
        <th data-options="field:'file_status',width:100">归档状态</th>
        <th data-options="field:'inputer_id',width:100">录入者id</th>
        <th data-options="field:'create_time',width:100">创建日期</th>
        <th data-options="field:'update_time',width:100">更新时间</th>
        <th data-options="field:'delete_time',width:100">删除时间</th>
        <%--<th data-options="field:'delete_time',width:30">删除时间</th>--%>
        <th data-options="field:'deleted',width:100,formatter:statusFormater">删除状态</th>
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


    function statusFormater(val,row,index) {
        if(row.status!=1 && row.status!=null ){
            return '已删除';
        }else{
            return '未删除';
        }
    }
    //导入数据
    $("#addFill").on('click',function(){
        openDialog("title:'批量导入',winId:'importFillListEtl',url:'${pageContext.request.contextPath}/uploadFieldExcel/importFieldListEtl',buttons:'none',width:670,height:445");
        $("#importFillListEtl").dialog({
            id:'importFillListEtl',
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
                            $("#importFillListEtl").dialog('close');
                        }
                    })
                }
            }]
        })
    });

    function transferTable(){
        $.get("${pageContext.request.contextPath}/uploadFieldExcel/transferTable",function (data) {
            if(data.success){
                $.messager.alert('提示','导入成功','info');
                $("#importFillListEtl").dialog('close');
                search();
            }else {
                $.messager.alert('提示',data.message,'error');
            };
        })


    }

    //table操作
    $(function(){

        $("#fieldTble").datagrid({

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