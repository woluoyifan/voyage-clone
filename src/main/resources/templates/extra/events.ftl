<#include "../macro/page.ftl">
<@page>
    <center>
        <table border=1 width="95%" bgcolor=#e9e2ce bordercolor=#000000 cellspacing=0>
            <tr>
                <td>
                    <div align=center>
                        事件
                    </div>
                    <br>
                <#list eventList as event>
                    [${event.createTime?string('yyyy-MM-dd HH:mm:ss')}] ${event.description?replace('\n','<br/>') }
                    <br>
                </#list>
                </td>
            </tr>
        </table>
    </center>
</@page>
