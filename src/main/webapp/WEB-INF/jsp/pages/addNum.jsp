<%--
  Created by IntelliJ IDEA.
  User: as
  Date: 2016/12/10 0010
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="search-box">
    <label style="width: 330px;display: inline-block">
        <input id="firstNum" style="width: 100%;" type="text" placeholder="请输入正确的信息，如有多个，请点击添加按钮" />
    </label>
    <label>
        <input class="blue" type="button" value="添加" id="addNum">
    </label>
</div>
<div class="apply-box" style="height:145px;overflow:auto;margin: 0;">
    <table width="100%" cellpadding="0" cellspacing="0" border="0" class="table-new" id="numList">

    </table>
</div>
<script>

$(function(){
    $("#addNum").on("click",function(){
        var html = '<tr> <td width="80%"> <label> <input type="text" placeholder="请输入正确的信息" /> </label> </td> <td> <input class="blue delete" type="button" value="删除"> </td> </tr>';
        $("#numList").prepend(html);
    });

    $(".delete").live("click",function(){
        $(this).parent().parent().remove();
    });
})

</script>