<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <head>
        <title>成都市房地产市场数据填报平台</title>
        <!--                       CSS                       -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/easyui.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/icon.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/color.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
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
<div class="newHouse new-newHouse">
    <div class="title">竣工备案数据 - 新增</div>
    <div class="tip new-tip">如遇操作问题，请致电：<span>028-86279869</span>，下载用户手册请点击<span>右侧问号</span>按钮<span class="wen" data-url="${pageContext.request.contextPath}/downLoad/index?FilenameSign=">？</span></div>
</div>
<div class="msg-tip">
    <b>温馨提示</b>
    <p>- 平台填报数据为<span>招拍挂</span>土地对应的<span>竣工备案信息</span>，请勿填报划拨土地信息；</p>
    <p>- 需要填报的竣工备案信息<span>不包含</span>深基坑、装修工程、外立面施工信息</p>
    <p>- 下列指标带“<span>*</span>”的为必填项，如遇指标<span>确实为空</span>，请填写“<span>0</span>”</p>
    <p>- 指标填写<span>提交</span>后，将无法再进行编辑、删除、确需要修改请致电联系。</p>
</div>
<div class="apply-box">
    <form id="addForm">
        <table width="100%" cellpadding="0" cellspacing="0" border="0" class="table-new">
            <tr>
                <%--<th width="2%"><input type="checkbox" checked></th>--%>
                <th width="16%">建设工程规划许可证号*</th>
                <td class="contract-td">
                    <label id="noList" style="display: none">
                    </label>
                    <label id="inputContract">
                        <input readonly="readonly" type="text" name="layout_no" placeholder="点击右侧 添加 按钮，选择建设工程规划许可证号" class="contract-no" />
                    </label>
                    <input type="button" class="blue" value="添加" id="addContract">
                    <input type="hidden" id="contractAll" name="layout_no"/>
                </td>
            </tr>
            <tr>
                <%--<th width="2%"><input type="checkbox" id="estateNo"></th>--%>
                <th width="16%">施工许可证书编号*</th>
                <td class="contract-td">

                    <label id="estateList" style="display: none">
                    </label>
                    <label id="inputEstate">
                        <input readonly="readonly" type="text" name="build_no" placeholder="点击右侧 添加 按钮，选择施工许可证书编号" class="contract-no" id="addEstateNo"  />
                    </label>
                    <input type="button" class="blue addNo" value="添加" data-flags="addEstate" >
                    <input type="hidden" id="estateAll" name="build_no" value=""/>

                </td>
            </tr>
        </table>
        <div class="apply-box">若竣工备案编号对应多个建设工程规划许可证号/施工许可证号，可继续点击右侧<b>添加</b>按钮继续添加</div>
        <table width="100%" cellpadding="0" cellspacing="0" border="0" class="table-new">
            <tr>
                <th width="19%">区域*</th>
                <td width="31%">
                    <label class="new-combo">
                        <input id="regList" type="text" name="region_code" style="width:390px;height:30px;" />
                        <div id="sp">
                            <div class="regBox">
                                <c:forEach var="a" items="${regList}" varStatus="d">
                                    <%--<label style="width: 100%;background-color: #E4DDDD;" vks="${d.index+1}">${a.circle}</label>--%>
                                    <c:forEach var="c" items="${a.circlelist}" varStatus="b">
                                        <label parentvks="${d.index+1}"><input type="radio" value="${c.NAME}" name="region" data_source="${c.CODE}">${c.NAME}</label>
                                    </c:forEach>
                                </c:forEach>
                            </div>
                        </div>
                    </label>
                </td>
                <th width="19%">竣工备案表编号*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateXSS(this)" id="completed_no" name="completed_no" class="easyui-validatebox" required placeholder="请输入工程竣工备案表编号" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>竣工备案时间*</th>
                <td>
                    <label>
                        <input class="easyui-datebox" required id="completed_date" name="completed_date" data-options="width:190,height:25,editable:false" value="" type="text">
                    </label>
                </td>
                <th>竣工规模（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="completed_scale" class="easyui-validatebox" required placeholder="请输入竣工规模" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>竣工规模其中住宅竣工面积（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="j1" class="easyui-validatebox" required placeholder="请输入竣工规模其中住宅竣工面积" />
                    </label>
                </td>
                <th>竣工规模其中商业竣工面积（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="j2" class="easyui-validatebox" required placeholder="请输入竣工规模其中商业竣工面积" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>竣工规模其中办公竣工面积（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="j3" class="easyui-validatebox" required placeholder="请输入竣工规模其中办公竣工面积" />
                    </label>
                </td>
                <th>竣工规模其中酒店竣工面积（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="j4" class="easyui-validatebox" required placeholder="请输入竣工规模其中酒店竣工面积" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>竣工规模其中车位竣工面积（㎡）*</th>
                <td colspan="3">
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="j5" class="easyui-validatebox" required placeholder="请输入竣工规模其中车位竣工面积" />
                    </label>
                </td>
            </tr>
        </table>
        <input type="hidden" name="file_status" value="0"/>
        <input type="hidden" name="inputer_id" value="${userid}"/>
        <input type="hidden" name="id" />
        <div class="apply-btn">
            <input type="button" class="border-yellow" value="取 消" />
            <input type="button" class="yellow" id="saveApplyFile"value="确定" />
        </div>
    </form>
</div>

</body>
<script type="text/javascript">


    $(function(){

        $("#estateNo").change(function(){
            if($(this).is(':checked')){
                $("#addEstateNo").removeAttr("disabled");
                $(".addNo").removeAttr("disabled");
            }else{
                $("#addEstateNo").attr("disabled",true);
                $(".addNo").attr("disabled",true);
            }
        });

        $(".del").live("click", function(){
            var $this = $(this).parent();
            var contractNo = "";
            var estateNo = "";
            $this.remove();
            var flag = $("#noList span").length;
            var flag2 = $("#estateList span").length;
            $("#noList span").each(function(i){
                contractNo += $(this).data("no")+',';
            });
            $("#estateList span").each(function(i){
                estateNo += $(this).data("no")+',';
            });
            $("#contractAll").val(contractNo);
            $("#estateAll").val(estateNo);
            if(flag == 0){
                $("#contractAll").val("");
                $("#inputContract").show();
            }
            if(flag2 == 0){
                $("#estateAll").val("");
                $("#inputEstate").show();
            }
        });


        //增加建设工程规划许可证号
        $("#addContract").on("click", function(){
            openDialog("width:460,height:400,winId:'contractList',title:'新增',url: '${pageContext.request.contextPath }/build/addContractNo4Completed',buttons:[{text: '确认',handler:function(){addContract()}}]");
        });

        //增加施工许可证书编号
        $("body").on("click",".addNo",function(){
            openDialog("width:460,height:400,winId:'addNumDialog',title:'新增',url: '${pageContext.request.contextPath }/build/addContractNo4Completed2',buttons:[{text: '确认',handler:function(){addTypeNo()}}]");
        });

        $('#regList').combo({
            editable:false,
            multiple:true,
            multiline:true,
            panelWidth:400,
            panelHeight:'auto',
            value:''
        });
        $('#sp .regBox').appendTo($('#regList').combo('panel'));
        $(".combo-panel .regBox").on("click","label",function(){
            //isSelectAll($(this));
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
            $('.regBox input[type="radio"]:checked').each(function(i){
                source += $(this).attr("data_source");
                s += $(this).parent().text();
            });
            $('#regList').combo('setValue', source).combo('setText', s);
        });



        $(".border-yellow").on("click",function(){
            Cmessager.confirm("警告", "是否放弃当前编辑内容，返回“查询列表”?", function(s){
                if(s){
                    window.location="${pageContext.request.contextPath}/build/completed";
                }
            });
        });

        //提交
        $("#saveApplyFile").on("click",function(){

            if(!$('#regList').combo('getValue')){
                $.messager.alert('提示','请选择区域','info');
                return;
            }

            var flag = $("#addForm").form('validate');
            if(flag){
                $("input[name = file_status]").val('1');
                var paramObj = {};
                var paramArr = $("#addForm").serializeArray();
                $.each(paramArr,function(i,data){
                    paramObj[data.name] = data.value;
                });
                $.post("${pageContext.request.contextPath}/build/addCompleted",paramObj,function (data) {
                    if(data.success){
                        window.location="${pageContext.request.contextPath}/build/completed";
                    }else {
                        $.messager.alert('提示','保存失败：'+data.message,'info');
                        console.debug(data);
                    };
                })
            }
        });


        if('${data}'){
            var data = $.parseJSON('${data}');
//            console.debug(data);
            $("#addForm").form('load',data);
            $('#regList').combo('setValue',data.region_code).combo('setText',data.region);
//            $("#completed_no").attr("readonly","readonly");
            setTimeout(function(){
                var date = (data.completed_date).split(" ")[0];
                $("#completed_date").datebox("setValue", date.replace(/-/g,"/"));
            },100);
        }

    });



    function addContract(){
        var rows = $("#contractNo").datagrid("getChecked");
        if(rows.length>0){
            var contractNo = "";

            $("#noList span").each(function(i){
                contractNo += $(this).data("no")+',';
            });
            $("#noList").show();
            $("#inputContract").hide();
            $.each(rows, function(key, value){
                $("#noList").append('<span data-no="'+ value.name +'">'+ value.name +'<b class="del">x</b></span>');
                contractNo += value.name +",";
            });
            $("#contractAll").val(contractNo);
            $("#contractList").dialog("close");
        }
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
    function addTypeNo(){
        var rows = $("#contractNo").datagrid("getChecked");
        if(rows.length>0){
            var contractNo = "";
            $("#estateList span").each(function(i){
                contractNo += $(this).data("no")+',';
            });
            $("#estateList").show();
            $("#inputEstate").hide();
            $.each(rows, function(key, value){
                $("#estateList").append('<span data-no="'+ value.name +'">'+ value.name +'<b class="del">x</b></span>');
                contractNo += value.name +",";
            });
            $("#estateAll").val(contractNo);
            $("#addNumDialog").dialog("close");
        }
    }


    //文件下载
    $(".wen").on("click",function(){
            var url=$(this).data("url");
            window.location=url+"3";
        }
    )
</script>
</html>
