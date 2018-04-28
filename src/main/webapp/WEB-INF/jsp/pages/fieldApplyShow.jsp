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
</head>
</head>
<body>
    <div class="newHouse new-newHouse">
        <div class="title">土地数据填报 - 查看</div>
        <div class="tip new-tip">如遇操作问题，请致电：<span>028-86279869</span>，下载用户手册请点击<span>右侧问号</span>按钮<span class="wen" data-url="${pageContext.request.contextPath}/downLoad/index?FilenameSign=" >？</span></div>
    </div>
    <div class="apply-box">
        <form id="showForm">

            <table width="100%" cellpadding="0" cellspacing="0" border="0" class="table-new">
                <tr>
                    <th width="19%">区域*</th>
                    <td width="31%">
                        <label class="new-combo">
                            <input type="text" name="region" readOnly="true"/>
                        </label>
                    </td>
                    <th width="19%">出让方式*</th>
                    <td>
                        <label>
                            <input type="text" name="ftradetype" readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>宗地编号*</th>
                    <td>
                        <label>
                            <input type="text" name="fno_new" readOnly="true"/>
                        </label>
                    </td>
                    <th>宗地位置*</th>
                    <td>
                        <label>
                            <input type="text" name="faddress"  readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>竞得人*</th>
                    <td>
                        <label>
                            <input type="text" name="fowner" readOnly="true" />
                        </label>
                    </td>
                    <th>评估单价（万元/亩）</th>
                    <td>
                        <label>
                            <input type="text" name="faprice_mu"  readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>评估楼面地价（元/㎡）</th>
                    <td>
                        <label>
                            <input type="text" name="fabprice" readOnly="true"/>
                        </label>
                    </td>
                    <th>起拍单价（万元/亩）*</th>
                    <td>
                        <label>
                            <input type="text" name="fbprice_mu" readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>起拍楼面地价（元/㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="fbbprice"  readOnly="true"/>
                        </label>
                    </td>
                    <th>成交单价（万元/亩）*</th>
                    <td>
                        <label>
                            <input type="text" name="ftrprice_mu" readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>成交楼面地价（元/㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="ftrbprice"  readOnly="true"/>
                        </label>
                    </td>
                    <th>成交时间*</th>
                    <td>
                        <label>
                            <input type="text" name="ftrtime" readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>净用地面积(㎡)*</th>
                    <td>
                        <label>
                            <input type="text" name="ffarea" readOnly="true"/>
                        </label>
                    </td>
                    <th>土地用途*</th>
                    <td>
                        <label>
                            <input type="text" name="fusage_type"  readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>规划用地性质</th>
                    <td colspan="3">
                        <label>
                            <input type="text" name="fplanusg"  readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>计容总建筑面积下限（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="fbtarea_down"  readOnly="true"/>
                        </label>
                    </td>
                    <th>计容总建筑面积上限（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="fbtarea_up" readOnly="true" />
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>计容住宅建筑面积下限（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="fhouse_down" readOnly="true" />
                        </label>
                    </td>
                    <th>计容住宅建筑面积上限（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="fhouse_up"  readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>计容商服建筑面积下限（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="fbusiness_down" readOnly="true"/>
                        </label>
                    </td>
                    <th>计容商服建筑面积上限（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="fbusiness_up" readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>计容自持商服建筑面积下限（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="fbself_down" readOnly="true"/>
                        </label>
                    </td>
                    <th>计容自持商服建筑面积上限（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="fbself_up"  readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>计容自用商服建筑面积下限（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="fbuse_down"  readOnly="true"/>
                        </label>
                    </td>
                    <th>计容自用商服建筑面积上限（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="fbuse_up" readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>容积率下限*</th>
                    <td>
                        <label>
                            <input type="text" name="frate_dow" readOnly="true"/>
                        </label>
                    </td>
                    <th>容积率上限*</th>
                    <td>
                        <label>
                            <input type="text" name="frate_up" readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>土地合同编号*</th>
                    <td colspan="3">
                        <label>
                            <input type="text" name="fctrno_new"  readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>国土证号/不动产证书号*</th>
                    <td colspan="3">
                        <label>
                            <input type="text" name="ffileno"  readOnly="true"/>
                        </label>
                    </td>
                </tr>
            </table>
            <input type="hidden" name="file_status" />
            <input type="hidden" name="inputer_id" />
            <input type="hidden" name="id" />
            <input type="hidden" name="data_source" />
            <div class="apply-btn">
                <input type="button" class="border-yellow" value="返 回" />

            </div>
        </form>
    </div>

</body>
<script type="text/javascript">


    $(function(){

        $(".border-yellow").on("click",function(){
            window.location="${pageContext.request.contextPath}/field/index";
        });



        var data = $.parseJSON('${data}');
        $("#showForm").form('load',data);

        var file_status = $("input[name = file_status]").val();
        if("未提交"==file_status){
            $(".apply-btn").append('<input type="button" class="yellow" value="提 交" />')
        }

        $(".yellow").on("click",function(){
            Cmessager.confirm("警告", "是否提交当前记录?", function(s){
                if(s){
                    var ids = $("input[name = id]").val();
                    $.get("${pageContext.request.contextPath}/field/inputField",{ids:ids},function (data) {
                        if(data.success){
                            $.messager.alert('提示','已提交'+b+'条,失败'+c+'条','info');
                            search();
                        };
                    })
                }
            });
        });
    })
    //文件下载
    $(".wen").on("click",function(){
                var url=$(this).data("url");
                window.location=url+"1";
            }
    )

</script>
</html>
