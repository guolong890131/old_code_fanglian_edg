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
        <div class="title">规划数据填报 - 查看</div>
        <div class="tip new-tip">如遇操作问题，请致电：<span>028-86279869</span>，下载用户手册请点击<span>右侧问号</span>按钮<span class="wen" data-url="${pageContext.request.contextPath}/downLoad/index?FilenameSign=">？</span></div>
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
                    <th width="19%">建设工程规划许可证号*</th>
                    <td>
                        <label>
                            <input type="text" name="layout_no" readOnly="true" />
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>项目名称*</th>
                    <td>
                        <label>
                            <input type="text" name="construction_project_name" readOnly="true" />
                        </label>
                    </td>
                    <th>项目地址*</th>
                    <td>
                        <label>
                            <input type="text" name="address" readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>建设单位*</th>
                    <td>
                        <label>
                            <input type="text" name="construction_unit" readOnly="true" />
                        </label>
                    </td>
                    <th>发证日期</th>
                    <td>
                        <label>
                            <input  type="text" name="print_date" readOnly="true">
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>规划总建筑面积（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="construction_scale" readOnly="true" />
                        </label>
                    </td>
                    <th>其中计容积率住宅面积（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="c2" readOnly="true" />
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>其中计容商业面积（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="c3" readOnly="true" />
                        </label>
                    </td>
                    <th>其中计容办公面积（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="c4" readOnly="true" />
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>其中计容酒店面积（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="c5" readOnly="true" />
                        </label>
                    </td>
                    <th>其中计容其他面积（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="c6" readOnly="true"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>其中计容机动车位面积（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="c7" readOnly="true" />
                        </label>
                    </td>
                    <th>其中不计容机动车位面积（㎡）*</th>
                    <td>
                        <label>
                            <input type="text" name="c8" readOnly="true" />
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>其中不计容其他面积（㎡）*</th>
                    <td colspan="3">
                        <label>
                            <input type="text" name="c9" readOnly="true" />
                        </label>
                    </td>
                </tr>


                <tr>
                    <th>土地合同编号*</th>
                    <td colspan="3">
                        <label>
                            <input type="text" name="fctrno" readOnly="true" />
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>国土证号*</th>
                    <td colspan="3">
                        <label>
                            <input type="text" name="ffileno" readOnly="true" />
                        </label>
                    </td>
                </tr>
            </table>
            <input type="hidden" name="file_status" />
            <input type="hidden" name="inputer_id" />
            <input type="hidden" name="id" />
            <input type="hidden" name="region_code" />
            <div class="apply-btn">
                <input type="button" class="border-yellow" value="返 回" />
            </div>
        </form>
    </div>

</body>
<script type="text/javascript">


    $(function(){


        $(".border-yellow").on("click",function(){
              window.location="${pageContext.request.contextPath}/layout/index";
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
                    $.get("${pageContext.request.contextPath}/layout/inputField",{ids:ids},function (data) {
                        if(data.success){
                            $.messager.alert('提示','已提交','info');
                            $(".yellow").remove();
                        };
                    })
                }
            });
        });

    });
    //文件下载
    $(".wen").on("click",function(){
                var url=$(this).data("url");
                window.location=url+"2";
            }
    )

</script>
</html>
