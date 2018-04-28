<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: as
  Date: 2016/12/10 0010
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <div class="apply-box" id="windid">
        <form  id="addUserForm">
            <input type="hidden" value="${userinfo.uuid}" name="uuid">
            <table width="100%" cellpadding="0" cellsp_tempacing="0" border="0" class="table-new">
                <tr>
                    <th>账号</th>
                    <td>
                        <label>
                            <input type="text" placeholder="请输入账号" name="loginname" class="easyui-validatebox" required="true"  value="${userinfo.loginname}" <c:if test="${type =='view'||type=='edit2'}">readonly="readonly" </c:if> />
                        </label>
                    </td>
                    <th>密码</th>
                    <td>
                        <label>
                            <input type="text" placeholder="请输入密码" name="loginpassencode" class="easyui-validatebox" required="true" value="${userinfo.loginpassencode}" <c:if test="${type =='view'}">readonly="readonly" </c:if>/>
                            <input type="hidden" name="loginpass" value="${userinfo.loginpass}">
                        </label>
                    </td>
                </tr>
                <tr>
                    <th>姓名</th>
                    <td>
                        <label>
                            <input type="text" placeholder="请输入姓名" name="name" class="easyui-validatebox" required="true" value="${userinfo.name}" <c:if test="${type =='view'||type=='edit2'}">readonly="readonly" </c:if>/>
                        </label>
                    </td>
                    <th>单位</th>
                    <td>
                        <label>
                            <input type="text" placeholder="请输入单位名称" name="unit" class="easyui-validatebox" required="true" value="${userinfo.unit}" <c:if test="${type =='view'||type=='edit2'}">readonly="readonly" </c:if> />
                        </label>
                    </td>
                </tr>

                <tr>
                    <th>手机</th>
                    <td>
                        <label>
                            <input type="text" placeholder="请输入手机号" name="tel" class="easyui-validatebox" required="true" value="${userinfo.tel}" <c:if test="${type =='view'}">readonly="readonly" </c:if>/>
                        </label>
                    </td>
                    <th>座机</th>
                    <td>
                        <label>
                            <input type="text" placeholder="请输入电话号码" name="phone" class="easyui-validatebox" required="true" value="${userinfo.phone}" <c:if test="${type =='view'}">readonly="readonly" </c:if> />
                        </label>
                    </td>
                </tr>
                <c:if test="${type!='edit2'}">
                <tr>
                    <th>身份证号</th>
                    <td colsp_tempan="3">
                        <label>
                            <input type="text" placeholder="请输入身份证" name="idnum" class="easyui-validatebox" required="true" value="${userinfo.idnum}" <c:if test="${type =='view'}">readonly="readonly" </c:if>/>
                        </label>
                    </td>

                </tr>
                <tr>
                    <th width="19%">区域*</th>
                    <td width="31%">
                        <label class="new-combo">
                            <%--<input name="regionname"  type="hidden" id="regionname" value="${userinfo.regionname}"/>--%>
                            <%--<select id="regioncode" name="regioncode"  class="easyui-validatebox" required="true" <c:if test="${type =='view'}">disabled="disabled" </c:if>>--%>
                                <%--<option value="">====请选择====</option>--%>
                                <%--<c:forEach items="${regList}" var="reg">--%>
                                    <%--<option value="${reg.CODE}" <c:if test="${reg.CODE == userinfo.regioncode}">selected="selected" </c:if> >${reg.NAME}</option>--%>
                                <%--</c:forEach>--%>
                            <%--</select>--%>

                            <c:if test="${type =='view'}">
                                <input type="text" value="${userinfo.regionname}" disabled="disabled"/>
                            </c:if>
                            <c:if test="${type !='view'}">
                                <input id="regList_name" name="regionname"  type="hidden" id="regionname" value=""/>
                                <input id="regList_temp" type="text" name="regioncode" style="width:200px;height:30px" />
                                <div id="sp_temp">
                                    <div class="regBox">
                                        <label><input type="checkbox" id="selectAllu" />全选</label>
                                        <c:forEach var="c" items="${regList}" varStatus="b">
                                                <label parentvks="${b.index+1}"><input ei="o" type="checkbox" value="${c.name}" name="district[${b.count}]" data_source="${c.code}">${c.name}</label>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if>
                        </label>
                    </td>
                    <th width="19%">角色</th>
                    <td>
                        <label>
                            <input name="rolename"  type="hidden" id="rolename" value="${userinfo.rolename}"/>
                            <select id="rolecode" name="rolecode" class="easyui-validatebox" required="true" <c:if test="${type =='view'}">disabled="disabled" </c:if>>
                                <option value="">====请选择====</option>
                                <c:forEach items="${roleList}" var="role">
                                    <option value="${role.ROLEID}" <c:if test="${role.ROLEID == userinfo.rolecode}">selected="selected" </c:if> >${role.ROLENAME}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </td>
                </tr>
                </c:if>
            </table>

        </form>
    </div>

<script type="text/javascript">



    $(function(){
        if(${type!='view'}) {
            $('#regList_temp').combo({
                editable: false,
                multiple: true,
                multiline: true,
                panelWidth: 400,
                panelHeight: 'auto',
                value: ''
            });
            $('#sp_temp .regBox').appendTo($('#regList_temp').combo('panel'));
            $(".regBox").on("click", "label", function () {
                isselectAllu($(this));
                var v = '', s = '', source = '';
                $('.regBox input[ei="o"]:checked').each(function (i, t) {
                    if ($(this).parent().text() != '全选') {
//                    console.debug(t);
                        v += $(this).val() + ',';
                        source += $(this).attr("data_source") + ",";
                        s += $(this).parent().text() + ',';
                    }
                });
                source = source.substring(0, source.length - 1);
                s = s.substring(0, s.length - 1);
                $('#regList_temp').combo('setValue', source).combo('setText', s);
                $("#regList_name").val(s);
            });
        }
        //编辑回显区域
        if(${type =='edit'}){
            $('#regList_temp').combo('setValue', '${userinfo.regioncode}').combo('setText', '${userinfo.regionname}');
            $("#regList_name").val('${userinfo.regionname}');
            var code = [];
            code='${userinfo.regioncode}'.split(',');
            $('.regBox input[ei="o"]').each(function(index,element){
                var data_source = element.getAttribute("data_source");
                for (x in code)
                {
                    if(data_source == code[x]){
                        element.setAttribute("checked", true);
                    }
                }
            });
            if(($('.regBox input[ei="o"]').length)==$('.regBox input[ei="o"]:checked').length){
                $("#selectAllu").attr("checked",true);
            }
        }



        $("#rolecode").change(function(data,vals){
            var rolename = $("#rolecode").find("option:selected").text();
            $("#rolename").val(rolename);
        })


    });

    /*combobox region实现多选*/
    function isselectAllu(_this,obj){
        //是否全选
        if(_this.context.innerText=='全选'){
            if($("#selectAllu").attr("checked")){
                $('.regBox input[ei="o"]').attr("checked", true);
            }else{
                $('.regBox input[ei="o"]').attr("checked", false);
            }
        }else{
            //取消选中全选按钮
            $("#selectAllu").attr("checked", false);
            //如果其余全选 则选中 全选按钮
            if(($('.regBox input[ei="o"]').length)==$('.regBox input[ei="o"]:checked').length){
                $("#selectAllu").attr("checked",true);
            }

        }
    }



</script>
