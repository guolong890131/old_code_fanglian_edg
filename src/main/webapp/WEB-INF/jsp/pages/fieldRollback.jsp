<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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
        <%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/xss.js"></script>--%>
    </head>
</head>
<body>
<div class="newHouse new-newHouse">
    <div class="title">土地数据填报 - 新增/编辑</div>
    <div class="tip new-tip">如遇操作问题，请致电：<span>028-86279869</span>，下载用户手册请点击<span>右侧问号</span>按钮<span class="wen" data-url="${pageContext.request.contextPath}/downLoad/index?FilenameSign=">?</span></div>
</div>
<div class="msg-tip">
    <b>温馨提示</b>
    <p>- 平台填报数据为<span>招拍挂土地</span>信息，请勿填报划拨土地信息；</p>
    <p>- 下列指标带“<span>*</span>”的为必填项，如遇指标<span>确实为空</span>，请填写“<span>0</span>”</p>
    <p>- 指标填写<span>提交</span>后，将无法再进行编辑、删除、确需要修改请致电联系。</p>
</div>
<div class="apply-box">
    <form id="addForm" >

        <table width="100%" cellpadding="0" cellspacing="0" border="0" class="table-new">
            <tr>
                <th width="19%">区域*</th>
                <td width="31%">
                    <label class="new-combo">
                        <input id="regList" type="text" name="data_source"  style="width:390px;height:30px;" />
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
                <th width="19%">出让方式*</th>
                <td>
                    <label>
                        <select id="ftradeType" name="ftradetype" class="easyui-validatebox" required>
                            <option value="" >全部</option>
                        </select>
                    </label>
                </td>
            </tr>
            <tr>
                <th>宗地号*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateXSS(this)" id="fno_new" name="fno_new" class="easyui-validatebox" required placeholder="请输入宗地编号" />
                    </label>
                </td>
                <th>宗地位置*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateXSS(this)" name="faddress" class="easyui-validatebox" required placeholder="请输入宗地位置" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>竞得者*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateXSS(this)" name="fowner" class="easyui-validatebox" required placeholder="请输入竞得人" />
                    </label>
                </td>
                <th>评估单价（万元/亩）</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" name="faprice_mu" value="0" placeholder="请输入评估单价" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>评估楼面地价（元/㎡）</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" name="fabprice" value="0"  placeholder="请输入评估楼面地价" />
                    </label>
                </td>
                <th>起拍单价（万元/亩）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" name="fbprice_mu" value="" class="easyui-validatebox" required placeholder="请输入起拍单价" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>起拍楼面地价（元/㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" name="fbbprice" value="" class="easyui-validatebox" required  placeholder="请输入起拍楼面地价" />
                    </label>
                </td>
                <th>成交单价（万元/亩）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" name="ftrprice_mu" value="" class="easyui-validatebox" required  placeholder="请输入成交单价" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>成交楼面地价（元/㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" name="ftrbprice" value="" class="easyui-validatebox" required placeholder="请输入成交楼面地价" />
                    </label>
                </td>
                <th>出让时间*</th>
                <td>
                    <label>
                        <input id="ftrtime" class="easyui-datebox"  name="ftrtime" required data-options="width:190,height:25,editable:false" value="" type="text">
                    </label>
                </td>
            </tr>
            <tr>
                <th>净用地面积(㎡)*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="ffarea"  class="easyui-validatebox" required placeholder="请输入净用地面积" />
                    </label>
                </td>
                <th>土地用途*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateXSS(this)"  name="fusage_type"  class="easyui-validatebox" required placeholder="请输入土地用途" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>规划用地性质*</th>
                <td colspan="3">
                    <label>
                        <input type="text" onblur="validateXSS(this)" name="fplanusg" class="easyui-validatebox" required placeholder="请输入规划用地性质" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>计容总建筑面积下限（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value=""  name="fbtarea_down" class="easyui-validatebox" required placeholder="请输入计容总建筑面积下限" />
                    </label>
                </td>
                <th>计容总建筑面积上限（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="fbtarea_up" class="easyui-validatebox" required placeholder="请输入计容总建筑面积上限" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>计容住宅建筑面积下限（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="fhouse_down" class="easyui-validatebox" required placeholder="请输入计容住宅建筑面积下限" />
                    </label>
                </td>
                <th>计容住宅建筑面积上限（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="fhouse_up" class="easyui-validatebox" required  placeholder="请输入计容住宅建筑面积上限" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>计容商服建筑面积下限（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="fbusiness_down" class="easyui-validatebox" required placeholder="请输入计容商服建筑面积下限" />
                    </label>
                </td>
                <th>计容商服建筑面积上限（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="fbusiness_up" class="easyui-validatebox" required placeholder="请输入计容商服建筑面积上限" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>计容自持商服建筑面积下限（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="fbself_down" class="easyui-validatebox" required placeholder="请输入计容自持商服建筑面积下限" />
                    </label>
                </td>
                <th>计容自持商服建筑面积上限（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="fbself_up" class="easyui-validatebox" required placeholder="请输入计容自持商服建筑面积上限" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>计容自用商服建筑面积下限（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="fbuse_down" class="easyui-validatebox" required placeholder="请输入计容自用商服建筑面积下限" />
                    </label>
                </td>
                <th>计容自用商服建筑面积上限（㎡）*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="fbuse_up" class="easyui-validatebox" required placeholder="请输入计容自用商服建筑面积上限" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>容积率下限*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="frate_dow" class="easyui-validatebox" required placeholder="请输入容积率下限" />
                    </label>
                </td>
                <th>容积率上限*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateNum(this)" value="" name="frate_up" class="easyui-validatebox" required placeholder="请输入容积率上限" />
                    </label>
                </td>
            </tr>

            <tr>
                <th>土地合同编号*</th>
                <td class="contract-td" colspan="1" bgcolor="#f1f1f1">
                    <label id="contractList">

                    </label>
                </td>
                <td class="contract-td" colspan="2">
                    <label id="contractInput">
                        <input type="text" name="fctrno_new" value="" placeholder="在此输入“土地合同编号”  每次仅能添加一个编号" class="contract-no" id="addContractNo" />
                    </label>
                    <input type="button" class="blue addContract" value="添加" data-flags="addContract">
                    <input type="hidden" id="contractAll" name="fctrno_new"/>
                </td>
            </tr>

            <tr>
                <th>国土证号/不动产证书号</th>
                <td class="contract-td" colspan="1" bgcolor="#f1f1f1">
                    <label id="estateList">

                    </label>
                </td>
                <td class="contract-td" colspan="2">
                    <label id="estateInput">
                        <input type="text" name="ffileno" value=""  placeholder="在此输入“国土证号/不动产证书号”  每次仅能添加一个编号" class=" contract-no" id="addEstateNo" />
                    </label>
                    <input type="button" class="blue addEstate" value="添加" data-flags="addEstate">
                    <input type="hidden" id="estateAll" name="ffileno"/>
                </td>
            </tr>
        </table>
        <input type="hidden" name="file_status" value="0"/>
        <input type="hidden" name="inputer_id" value="${userid}" />
        <input type="hidden" name="id" />
        <%--<input type="hidden" name="data_source" />--%>

        <div class="apply-btn">
            <input type="button" class="border-yellow" value="取 消" />
            <input type="button" class="yellow" id="saveApplyFile"value="确定" />
        </div>
    </form>
</div>

</body>
<script type="text/javascript">


    $(function(){
        //增加不动产证书号
        $("body").on("click",".addEstate",function(){
            //openDialog("width:460,height:300,winId:'addNumDialog',title:'新增',url: '${pageContext.request.contextPath }/field/addNum',buttons:[{text: '确认',handler:function(){addEstateNo()}}]");
            if(!$("#addEstateNo").val()){
                $.messager.alert('提示','请输入一个不动产证书号','info');
                return;
            }
            var estateNo = $("#addEstateNo").val()+",";
            var estateAll = $("#estateAll").val();
            var spanHtml = $("#estateList").html();
            $("#estateAll").val(estateAll + estateNo);
            spanHtml += '<span data-no="'+ $("#addEstateNo").val() +'">'+ $("#addEstateNo").val() +'<b class="del">x</b></span>';
            $("#estateList").html(spanHtml);
            $("#addEstateNo").val("");
        });
        //增加土地合同编号
        $("body").on("click",".addContract",function(){
            //openDialog("width:460,height:300,winId:'addNumDialog',title:'新增',url: '${pageContext.request.contextPath }/field/addNum',buttons:[{text: '确认',handler:function(){addContractNo()}}]");
            if(!$("#addContractNo").val()){
                $.messager.alert('提示','请输入一个土地合同编号','info');
                return;
            }
            var contractNo = $("#addContractNo").val()+",";
            var contractAll = $("#contractAll").val();
            var spanHtml = $("#contractList").html();
            $("#contractAll").val(contractAll + contractNo);
            spanHtml += '<span data-no="'+$("#addContractNo").val() +'">'+ $("#addContractNo").val() +'<b class="del">x</b></span>';
            $("#contractList").html(spanHtml);
            $("#addContractNo").val("");
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
                    window.location="${pageContext.request.contextPath}/field/index";
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
                $.post("${pageContext.request.contextPath}/field/add",paramObj,function (data) {
                    if(data.success){
                        window.location="${pageContext.request.contextPath}/field/index";
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
            $('#regList').combo('setValue',data.data_source).combo('setText',data.region);

            setTimeout(function(){
                $("#ftradeType").val(data.ftradetype);
                var date = (data.ftrtime).split(" ")[0];
                $("#ftrtime").datebox("setValue", date.replace(/-/g,"/"));

                if(data.fctrno_new){
                    var spanHtml = "";

                    if('${associate}'){
                        $.each('${associate}'.substring(0,'${associate}'.length-1).split(','), function(key, value){
                            spanHtml += '<span data-no="'+ value +'">'+ value +'</span>';
                        });
                    }
                    if('${unassociate}'){
                        $.each('${unassociate}'.substring(0,'${unassociate}'.length-1).split(','), function(key, value){
                            spanHtml += '<span data-no="'+ value +'">'+ value +'<b class="del">x</b></span>';
                        });
                    }
                    $("#contractList").html(spanHtml);
                    $("#addContractNo").val("");
                }

                if(data.ffileno){
                    var spanHtml = "";
                    if('${ffileno_associate}'){
                        $.each('${ffileno_associate}'.substring(0,'${ffileno_associate}'.length-1).split(','), function(key, value){
                            spanHtml += '<span data-no="'+ value +'">'+ value +'<b class="del">x</b></span>';
                        });
                    }
                    if('${ffileno_unassociate}'){
                        $.each('${ffileno_unassociate}'.substring(0,'${ffileno_unassociate}'.length-1).split(','), function(key, value){
                            spanHtml += '<span data-no="'+ value +'">'+ value +'</span>';
                        });
                    }
                    $("#estateList").html(spanHtml);
                    $("#addEstateNo").val("");
                }


            },100);
        }

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


    $.get("${pageContext.request.contextPath}/field/queryFtradetype",function (data) {
        var html='';
        $.each(data.result.list,function (a,v) {
            html+='<option value="'+v.name+'" >'+v.name+'</option>' ;
        })
        $("#ftradeType").append(html);
    })




    function addEstateNo(){
        var firstNum = $("#firstNum").val();
        if(firstNum == ""){
            Cmessager.alert("警告", "至少添加一条有效信息！");
        }else{
            var numList = $("#numList").find("input[type=text]");
            var allNum = firstNum + ",";
            var estateNo = "";
            var spanHtml =""
            $("#estateList").show();
            $("#estateInput").hide();
            numList.each(function(){
                allNum += $(this).val() + ",";
            });
            estateNo = $('#estateAll').val()+allNum;
            $('#estateAll').val(estateNo);
            console.log( estateNo.substring(0,estateNo.length-1));
            $.each(estateNo.substring(0,estateNo.length-1).split(','), function(key, value){
                spanHtml += '<span data-no="'+ value +'">'+ value +'<b class="del">x</b></span>';
            });
            $("#estateList").html(spanHtml);
            $("#addNumDialog").dialog("close");
        }
    }
    function addContractNo(){
        var firstNum = $("#firstNum").val();
        if(firstNum == ""){
            Cmessager.alert("警告", "至少添加一条有效信息！");
        }else{
            var numList = $("#numList").find("input[type=text]");
            var allNum = firstNum + ",";
            var contractNo = "";
            var spanHtml =""
            $("#contractList").show();
            $("#contractInput").hide();
            numList.each(function(){
                allNum += $(this).val() + ",";
            });
            contractNo = $('#contractAll').val()+allNum;
            $('#contractAll').val(contractNo);
            console.log( contractNo.substring(0,contractNo.length-1));
            $.each(contractNo.substring(0,contractNo.length-1).split(','), function(key, value){
                spanHtml += '<span data-no="'+ value +'">'+ value +'<b class="del">x</b></span>';
            });

            $("#contractList").html(spanHtml);
            $("#addNumDialog").dialog("close");
        }
    }

    $(".del").live("click", function(){
        var $this = $(this).parent();
        var contractNo = "";
        var estateNo = "";
        $this.remove();
        var flag = $("#contractList span").length;
        var flag2 = $("#estateList span").length;
        $("#contractList span").each(function(i){
            contractNo += $(this).data("no")+',';
        });
        $("#estateList span").each(function(i){
            estateNo += $(this).data("no")+',';
        });
        $("#contractAll").val(contractNo);
        $("#estateAll").val(estateNo);
        if(flag == 0){
            $("#contractAll").val("");
            $("#contractInput").show();
        }
        if(flag2 == 0){
            $("#estateAll").val("");
            $("#estateInput").show();
        }
    });


    //文件下载
    $(".wen").on("click",function(){
            var url=$(this).data("url");
            window.location=url+"1";
        }
    )
</script>
</html>
