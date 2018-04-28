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
        <form  id="importLayoutForm">
            <input type="hidden" value="" name="">
            <table width="100%" cellpadding="0" cellsp_tempacing="0" border="0" class="table-new">
                <tr>
                    <th>文件位置：</th>
                    <td>
                        <label>
                            <input id="importLayoutFile" type="file" placeholder="请选择需要导入的文件" name="n_file"   />
                        </label>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <b>导入须知：</b>为规范数据格式，请使用标准模板填报数据！<a href="${pageContext.request.contextPath}/downLoad/excel?FilenameSign=2">点击下载excel模板</a>
                    </td>
                </tr>
            </table>

        </form>
    </div>

<script type="text/javascript">



</script>
