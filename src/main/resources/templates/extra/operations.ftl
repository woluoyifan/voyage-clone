<#include "../macro/page.ftl">
<@page>
<center>
    <table border=1 width="95%" bgcolor=#e9e2ce bordercolor=#000000 cellspacing=0>
        <tr>
            <td>
                <div align=center>
                    履历
                </div>
                <br>
                <#list operationList as operation>
                    [${operation.createTime?string('yyyy-MM-dd HH:mm:ss')}] ${operation.description?replace('\n','<br/>') }
                    <br>
                </#list>
            </td>
        </tr>
    </table>
</center>
<br>
</@page>
