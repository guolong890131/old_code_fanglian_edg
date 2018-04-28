<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: as
  Date: 2016/12/10 0010
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>成都市房地产市场数据填报平台</title>
    <!--                       CSS                       -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/icon.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/color.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
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
    <div class="newHouse new-newHouse">
        <div class="title">施工证数据填报 - 新增</div>
        <div class="tip new-tip">如遇操作问题，请致电：<span>028-86279869</span>，下载用户手册请点击<span>右侧问号</span>按钮<span class="wen" data-url="${pageContext.request.contextPath}/downLoad/index?FilenameSign=">？</span></div>
    </div>
    <div class="msg-tip">
        <b>温馨提示</b>
        <p>- 平台填报数据为<span>招拍挂</span>土地对应的<span>施工许可信息</span>，请勿填报划拨土地信息；</p>
        <p>- 需要填报的施工许可信息<span>不包含</span>深基坑、装修工程、外立面施工信息</p>
        <p>- 下列指标带“<span>*</span>”的为必填项，如遇指标<span>确实为空</span>，请填写“<span>0</span>”</p>
        <p>- 指标填写<span>提交</span>后，将无法再进行编辑、删除、确需要修改请致电联系。</p>
    </div>
    <div class="apply-box">
        <form action="" id="addBuildForm">
            <input type="hidden" name="uuid" value=""/>
            <input type="hidden" name="xh" value=""/>
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
            </table>
            <div class="apply-box">若施工许可证号对应多个建设工程规划许可证号，可继续点击右侧<b>添加</b>按钮继续添加</div>
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
                <th width="19%">施工许可证号 *</th>
                <td>
                    <label>
                        <input type="text" onblur="validateXSS(this)" placeholder="请输入施工许可证号" name="build_no" id="build_no" class="easyui-validatebox"
                               required="true" value=""/>
                    </label>
                </td>
            </tr>
            <tr>
                <th>工程名称*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateXSS(this)" placeholder="请输入工程名称" name="build_name" class="easyui-validatebox"
                               required="true" value="" />

                    </label>
                </td>
                <th>建设单位*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateXSS(this)" placeholder="请输入建设单位" name="construction_unit" class="easyui-validatebox"
                               required="true" value=""/>

                    </label>
                </td>
            </tr>
            <tr>
                <th>建设地址*</th>
                <td>
                    <label>
                        <input type="text" onblur="validateXSS(this)" placeholder="请输入建设地址" name="address" class="easyui-validatebox"
                               required="true" value=""/>

                    </label>
                </td>
                <th>施工单位</th>
                <td>
                    <label>
                        <input type="text" onblur="validateXSS(this)" placeholder="请输入施工单位" name="build_unit" value=""/>

                    </label>
                </td>
            </tr>
            <tr>
                <th>合同价格（万元）</th>
                <td>
                    <label>
                        <input type="text" placeholder="请输入合同价格" onblur="validateNum(this)" name="price" value=""/>

                    </label>
                </td>
                <th>发证日期*</th>
                <td>
                    <label>
                        <input data-options="width:190,height:25,editable:false" type="text" name="print_date"
                               class="easyui-datebox" required="true" value=""/>

                    </label>
                </td>
            </tr>
            <tr>
                <th>合同开工日期*</th>
                <td>
                    <label>
                        <input data-options="width:190,height:25,editable:false" type="text" name="build_date"
                               class="easyui-datebox" required="true" value=""
                               />
                    </label>
                </td>
                <th>合同竣工日期*</th>
                <td>
                    <label>
                        <input data-options="width:190,height:25,editable:false" type="text" name="compulete_date"
                               class="easyui-datebox" required="true" value=""
                               />
                    </label>
                </td>
            </tr>
            <tr>
                <th>建设规模（㎡）*</th>
                <td>
                    <label>
                        <input type="text" placeholder="请输入建设规模" name="construction_scale" class="easyui-validatebox"
                               onblur="validateNum(this)" required="" value=""/>

                    </label>
                </td>
                <th>建设规模其中住宅施工面积（㎡）*</th>
                <td>
                    <label>
                        <input type="text" placeholder="请输入建设规模其中住宅施工面积" name="s1" class="easyui-validatebox "
                               onblur="validateNum(this)" required="true" value=""
                                />
                    </label>
                </td>
            </tr>
            <tr>
                <th>建设规模其中商业施工面积（㎡）*</th>
                <td>
                    <label>
                        <input type="text" placeholder="请输入建设规模其中商业施工面积" name="s2" class="easyui-validatebox "
                               onblur="validateNum(this)" required="true" value=""
                               />
                    </label>
                </td>
                <th>建设规模其中办公施工面积（㎡）*</th>
                <td>
                    <label>
                        <input type="text" placeholder="请输入建设规模其中办公施工面积" name="s3" class="easyui-validatebox "
                               onblur="validateNum(this)" required="true" value=""
                        />
                    </label>
                </td>
            </tr>
            <tr>
                <th>建设规模其中酒店施工面积（㎡）*</th>
                <td>
                    <label>
                        <input type="text" placeholder="请输入建设规模其中酒店施工面积" name="s4" class="easyui-validatebox "
                               onblur="validateNum(this)" required="true" value=""
                       />
                    </label>
                </td>
                <th>建设规模其中车位施工面积（㎡）*</th>
                <td>
                    <label>
                        <input type="text" placeholder="请输入建设规模其中车位施工面积" name="s5" class="easyui-validatebox "
                               onblur="validateNum(this)" required="true" value=""
                       />
                    </label>
                </td>
            </tr>
        </table>
        <input type="hidden" value="0" name="file_status">

        <div class="apply-btn">
            <input type="button" class="border-yellow" value="取 消"/>
            <input type="button" class="yellow" value="保 存" onclick="addBuild('0',this)"/>
            <input type="button" class="yellow" value="提 交" onclick="addBuild('1',this)"/>
        </div>
    </form>
</div>

</body>
<script type="text/javascript">


    $(function () {
        $("#estateNo").change(function () {
            if ($(this).is(':checked')) {
                $("#estateBox").removeAttr("disabled");
            } else {
                $("#estateBox").attr("disabled", true);
            }
        });

        $("#noList>span .del").live("click", function () {
            var $this = $(this).parent();
            var contractNo = "";
            $this.remove();
            var flag = $("#noList span").length;
            $("#noList span").each(function (i) {
                contractNo += $(this).data("no") + ',';
            });
            $("#contractAll").val(contractNo);
            if (flag == 0) {
                $("#contractAll").val("");
                $("#inputContract").show();
            }
        });

        //增加建设工程规划许可证号
        $("#addContract").on("click", function () {
            openDialog("width:900,height:400,winId:'contractList',title:'新增',url: '${pageContext.request.contextPath }/build/addContractNoWithBuild',buttons:[{text: '确认',handler:function(){addContract()}}]");
        });


        $('#regList').combo({
            editable: false,
            multiple: true,
            multiline: true,
            panelWidth: 400,
            panelHeight: 'auto',
            value: ''
        });
        $('#sp .regBox').appendTo($('#regList').combo('panel'));
        $(".combo-panel .regBox").on("click", "label", function () {
            //isSelectAll($(this));
            var v = '', s = '', source = '';
            if (typeof($(this).attr("vks")) != "undefined") {
                var vks = $(this).attr("vks");
                var ifchecked = $(this).find("input").attr("checked");
                $('.combo-panel .regBox label').each(function (i) {
                    if ($(this).attr("parentvks") == vks) {
                        if (ifchecked || ifchecked == 'checked') {
                            $(this).find("input").attr("checked", true);
                        } else {
                            $(this).find("input").attr("checked", false);
                        }
                    }
                });
            }
            $('.regBox input[type="radio"]:checked').each(function (i) {
                source += $(this).attr("data_source");
                s += $(this).parent().text();
            });
            $('#regList').combo('setValue', source).combo('setText', s);
        });

        $(".border-yellow").on("click", function () {
            Cmessager.confirm("警告", "是否放弃当前编辑内容，返回“查询列表”?", function (s) {
                if (s) {
                    window.location = "${pageContext.request.contextPath}/build/build";
                }
            });
        });

        if('${dict!=null}'){
            $('#regList').combo('setValue','${dict.code}').combo('setText','${dict.name}');
        }
        if('${data}'){
            var data = $.parseJSON('${data}');
//            console.debug(data);
            $("#addBuildForm").form('load',data);
            $('#regList').combo('setValue',data.region_code).combo('setText',data.region);
//            $("#completed_no").attr("readonly","readonly");
            setTimeout(function(){
                var date = (data.print_date).split(" ")[0];
                $("#print_date").datebox("setValue", date.replace(/-/g,"/"));
            },100);
        }
    });
    //保存
    function addBuild(type, obj) {
        var form = $(obj).parents("#addBuildForm");

        if (!$("#contractAll").val()) {
            Cmessager.alert(GV.MESSAGER_TITLE, "请选择规划许可证号", 'info');
            return false;
        }
        var region_code = form.find("input[name='region_code']").val();
        if (region_code == '' || region_code == null) {
            Cmessager.alert(GV.MESSAGER_TITLE, "请选择区域", 'info');
            return false;
        }
        form.find("input[name='file_status']").val(type);
        var paramObj = {};
        var paramArr = $("#addBuildForm").serializeArray();
        $.each(paramArr,function(i,data){
            paramObj[data.name] = data.value;
        });
        if (form.form('validate')) {
            //提交表单
            $.post("${pageContext.request.contextPath}/build/addBuild",paramObj,
                function (data) {
                    if (data.status == 'success') {
//                            alert(data.message);
                        setTimeout(function () {
                            history.go(-1);
                        }, 2000);
                    } else {
//                            Cmessager.alert(GV.MESSAGER_TITLE, data.message, 'error');
//                            console.debug(data);
                        $.messager.alert('提示','保存失败：'+data.message,'info');
                    }
                }
            )
        }
    }

    function addContract() {
        var rows = $("#contractNo").datagrid("getChecked");
        if (rows.length > 0) {
            var contractNo = "";
            $("#noList").show();
            $("#inputContract").hide();
            $.each(rows, function (key, value) {
                $("#noList").append('<span data-no="' + value.LAYOUTNO + '">' + value.LAYOUTNO + '<b class="del">x</b></span>');
                contractNo += value.LAYOUTNO + ",";
            });
            $("#contractAll").val(contractNo);
            $("#contractList").dialog("close");
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

    /*combobox region实现多选*/
    function isSelectAll(_this, obj) {
        //是否全选
        if (_this.context.innerText == '全选') {
            if ($("#selectAll").attr("checked")) {
                $('.regBox input[type="checkbox"]').attr("checked", true);
            } else {
                $('.regBox input[type="checkbox"]').attr("checked", false);
            }
        } else {
            //取消选中全选按钮
            $("#selectAll").attr("checked", false);
            //如果其余全选 则选中 全选按钮
            if (($('.regBox input[type="checkbox"]').length - 1) == $('.regBox input[type="checkbox"]:checked').length) {
                $("#selectAll").attr("checked", true);
            }

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
